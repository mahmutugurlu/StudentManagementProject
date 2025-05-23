package com.tpe.student_management.service.user;

import com.tpe.student_management.entity.classes.user.User;
import com.tpe.student_management.entity.enums.Role;
import com.tpe.student_management.payload.mapper.UserMapper;
import com.tpe.student_management.payload.request.user.StudentRequestDTO;
import com.tpe.student_management.payload.response.ResponseMessage;
import com.tpe.student_management.payload.response.user.StudentResponseDTO;
import com.tpe.student_management.repository.user.UserRepository;
import com.tpe.student_management.service.helpers.MethodHelper;
import com.tpe.student_management.service.validator.UniquePropertyValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final UserRepository userRepository;
    private final MethodHelper methodHelper;
    private final UniquePropertyValidator uniquePropertyValidator;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleService userRoleService;

    public ResponseMessage<StudentResponseDTO> saveStudent(StudentRequestDTO dto) {
        //advisor olarak verilen id ile bir kullanici var mi?
        User user = methodHelper.isUserExist(dto.getAdvisorTeacherId());
        //o kullanici bir advisor teacher mi?
        methodHelper.checkAdvisor(user);
        //buraya gelebilirsem user kesinlikle bir advisor teacher'dir.
        //unique kontroller
        uniquePropertyValidator.checkIfDuplicateProperty(
                dto.getUsername(),
                dto.getEmail(),
                dto.getSsn(),
                dto.getPhoneNumber()
        );
        User student = userMapper.mapStudentRequestDTOToUser(dto);
        student.setAdvisorTeacherId(user.getId());

        //sifre encode
        student.setPassword(passwordEncoder.encode(student.getPassword()));
        //rol setleme
        student.setUserRole(userRoleService.getUserRoleByRole(Role.STUDENT));
        //DIZAYN HATASI, YAZILMAMALI
        student.setIsAdvisor(Boolean.FALSE);
        //Bir student'in isAdvisor fieldinin null olmasi beklenir. Semantic olarak FALSE dendiginde, TRUE olma ihtimali
        //oldugu da belirtilir. null oldugunda ise, isAdvisor fieldinin student rolu ile hicbir alakasi olmadigi
        //belirtilir.

        return null;
    }
}