package com.tpe.student_management.service.user;

import com.tpe.student_management.entity.classes.user.User;
import com.tpe.student_management.entity.enums.Role;
import com.tpe.student_management.exception.BadRequestException;
import com.tpe.student_management.exception.ConflictException;
import com.tpe.student_management.exception.ResourceNotFoundException;
import com.tpe.student_management.payload.mapper.UserMapper;
import com.tpe.student_management.payload.messages.ErrorMessages;
import com.tpe.student_management.payload.messages.SuccessMessages;
import com.tpe.student_management.payload.request.user.UserRequestDTO;
import com.tpe.student_management.payload.request.user.UserRequestDTOWithoutPassword;
import com.tpe.student_management.payload.response.ResponseMessage;
import com.tpe.student_management.payload.response.abstracts.BaseUserResponse;
import com.tpe.student_management.payload.response.user.StudentResponseDTO;
import com.tpe.student_management.payload.response.user.UserResponseDTO;
import com.tpe.student_management.repository.user.UserRepository;
import com.tpe.student_management.service.helpers.MethodHelper;
import com.tpe.student_management.service.helpers.PageableHelper;
import com.tpe.student_management.service.validator.UniquePropertyValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UniquePropertyValidator uniquePropertyValidator;
    private final UserMapper userMapper;
    private final UserRoleService userRoleService;
    private final PasswordEncoder passwordEncoder;
    private final PageableHelper pageableHelper;
    private final MethodHelper methodHelper;

    public ResponseMessage<UserResponseDTO> saveUser(@Valid UserRequestDTO dto, String role) {
        uniquePropertyValidator.checkIfDuplicateProperty(
                dto.getUsername(),
                dto.getEmail(),
                dto.getSsn(),
                dto.getPhoneNumber()
        );
        //buraya gelebildiysek unique kontrolu tamam
        User user = userMapper.mapUserRequestDTOToUser(dto);

        //rol setleme basliyor
        if (role.equalsIgnoreCase(Role.ADMIN.name())){
            if (Objects.equals(dto.getUsername(), "Admin")){
                user.setBuiltIn(true);
            }
            user.setUserRole(userRoleService.getUserRoleByRole(Role.ADMIN));
        } else if (role.equalsIgnoreCase("Dean")) {
            user.setUserRole(userRoleService.getUserRoleByRole(Role.MANAGER));
        } else if (role.equalsIgnoreCase("ViceDean")) {
            user.setUserRole(userRoleService.getUserRoleByRole(Role.ASSISTANT_MANAGER));
        }else {
            throw new ResourceNotFoundException(String.format(ErrorMessages.ROLE_DEFINITION_NOT_FOUND, role));
        }

        //Sifreyi hashleyecegiz.
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = userRepository.save(user);

        return ResponseMessage.<UserResponseDTO>builder()
                .message(SuccessMessages.USER_CREATE_SUCCESS)
                .object(userMapper.mapUserToUserResponseDTO(savedUser))
                .build();
    }

    public Page<UserResponseDTO> getUsersByPage(int page, int size, String sortBy, Sort.Direction order, String userRole) {
        Pageable pageable = pageableHelper.getPageableWithProps(page,size,sortBy,order);

        return userRepository.findAllByRole(pageable, userRole).map(userMapper::mapUserToUserResponseDTO);
    }

    public ResponseMessage<BaseUserResponse> getUserById(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ErrorMessages.USER_ID_NOT_FOUND, userId)));

        //UserResponseDTO -> Admin, Manager, Assistant_Manager
        //TeacherResponseDTO -> Teacher
        //StudentResponseDTO -> Student

        BaseUserResponse response;

        if (user.getUserRole().getRole() == Role.STUDENT){
            //Exercise: Mapper'in icindeki ilk 2 metodu tum tiplerle calisacak sekilde uyarlayin.
            response = userMapper.mapUserToStudentResponseDTO(user);
        } else if (user.getUserRole().getRole() == Role.TEACHER) {
            response = userMapper.mapUserToTeacherResponseDTO(user);
        }else {
            response = userMapper.mapUserToUserResponseDTO(user);
        }

        return ResponseMessage.<BaseUserResponse>builder()
                .message(SuccessMessages.USER_FOUND)
                .httpStatus(HttpStatus.OK)
                .object(response)
                .build();
    }
    public String deleteUserById(Long id, HttpServletRequest request) {
        // silinecek user var mi kontrolu
        User user = methodHelper.isUserExist(id); // silinmesi istenen user
        // metodu tetikleyen user role bilgisi aliniyor
        String username = (String) request.getAttribute("username");
        User user2 = userRepository.findByUsername(username); // silme islemini talep eden user
        // builtIn kontrolu
        if (Boolean.TRUE.equals(user.getBuiltIn())) { // condition : user.getBuilt_in()
            throw new ConflictException(ErrorMessages.NOT_PERMITTED_ACTION);
            // MANAGER sadece Teacher, student, Assistant_Manager silebilir
        } else if (user2.getUserRole().getRole() == Role.MANAGER) {
            if (!(  (user.getUserRole().getRole() == Role.TEACHER) ||
                    (user.getUserRole().getRole() == Role.STUDENT) ||
                    (user.getUserRole().getRole() == Role.ASSISTANT_MANAGER) )) {
                throw new BadRequestException(ErrorMessages.NOT_PERMITTED_ACTION);
            }
            // Mudur Yardimcisi sadece Teacher veya Student silebilir
        } else if (user2.getUserRole().getRole() == Role.ASSISTANT_MANAGER) {
            if (!((user.getUserRole().getRole() == Role.TEACHER) ||
                    (user.getUserRole().getRole() == Role.STUDENT))) {
                throw new BadRequestException(ErrorMessages.NOT_PERMITTED_ACTION);
            }
        }
        userRepository.deleteById(id);
        return SuccessMessages.USER_DELETE;
    }
    public ResponseMessage<BaseUserResponse> updateDeanOrViceDean(UserRequestDTO userRequest, Long userId) {

        User user = methodHelper.isUserExist(userId);
        // !!! bulit_in kontrolu
        methodHelper.checkBuiltIn(user);
        //!!! update isleminde gelen request de unique olmasi gereken eski datalar hic degismedi ise
        // dublicate kontrolu yapmaya gerek yok :
        uniquePropertyValidator.checkUniqueProperties(user, userRequest);
        //!!! DTO --> POJO
        User updatedUser = userMapper.mapUserRequestToUpdatedUser(userRequest, userId);
        // !!! Password Encode
        updatedUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        updatedUser.setUserRole(user.getUserRole());
        User savedUser = userRepository.save(updatedUser);

        return ResponseMessage.<BaseUserResponse>builder()
                .message(SuccessMessages.USER_UPDATE_MESSAGE)
                .httpStatus(HttpStatus.OK)
                .object(userMapper.mapUserToUserResponseDTO(savedUser))
                .build();
    }
    // Not: updateUserForUser() **********************************************************
    public ResponseEntity<String> updateUserOwnInfo(UserRequestDTOWithoutPassword userRequest,
                                                     HttpServletRequest request) {
        String username = (String) request.getAttribute("username");
        User user = userRepository.findByUsername(username);

        // !!! bulit_in kontrolu
        methodHelper.checkBuiltIn(user);

        // !!! unique kontrolu
        uniquePropertyValidator.checkUniqueProperties(user, userRequest);
        // !!! DTO --> pojo donusumu burada yazildi
        user.setUsername(userRequest.getUsername());
        user.setDateOfBirth(userRequest.getDateOfBirth());
        user.setEmail(userRequest.getEmail());
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setBirthPlace(userRequest.getBirthPlace());
        user.setGender(userRequest.getGender());
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setSsn(userRequest.getSsn());

        userRepository.save(user);

        String message = SuccessMessages.USER_UPDATE;

        return ResponseEntity.ok(message);
    }


    // Not : getByName() ***************************************************************
    public List<UserResponseDTO> getUserByFirstName(String firstName) {

        return userRepository.getUserByFirstNameContains(firstName)
                .stream()
                .map(userMapper::mapUserToUserResponseDTO)
                .collect(Collectors.toList());
    }

    public long countAllAdmins(){
        return userRepository.countAdmin(Role.ADMIN);
    }

    public User getTeacherByUsername(String teacherUsername){
        return userRepository.findByUsername(teacherUsername);
    }

    public User getUserByUserId(Long userId){
        return userRepository.findById(userId).orElseThrow(()->
                new ResourceNotFoundException(ErrorMessages.USER_ID_NOT_FOUND));
    }

    public List<User> getStudentById(Long[] studentIds){
        return userRepository.findByIds(studentIds);
    }
}
