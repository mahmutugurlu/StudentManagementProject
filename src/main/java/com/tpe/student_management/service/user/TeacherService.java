package com.tpe.student_management.service.user;

import com.tpe.student_management.entity.classes.user.User;
import com.tpe.student_management.entity.enums.Role;
import com.tpe.student_management.payload.mapper.UserMapper;
import com.tpe.student_management.payload.messages.SuccessMessages;
import com.tpe.student_management.payload.request.user.TeacherRequestDTO;
import com.tpe.student_management.payload.response.ResponseMessage;
import com.tpe.student_management.payload.response.user.StudentResponseDTO;
import com.tpe.student_management.payload.response.user.TeacherResponseDTO;
import com.tpe.student_management.repository.user.UserRepository;
import com.tpe.student_management.service.helpers.MethodHelper;
import com.tpe.student_management.service.validator.UniquePropertyValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeacherService {
    private final UserRepository userRepository;
    private final UniquePropertyValidator uniquePropertyValidator;
    private final UserMapper userMapper;
    private final UserRoleService userRoleService;
    private final PasswordEncoder passwordEncoder;
    private final MethodHelper methodHelper;

    public ResponseMessage<TeacherResponseDTO> saveTeacher(TeacherRequestDTO dto) {
        //TODO: Lesson Programlar Getirilecek.
        uniquePropertyValidator.checkIfDuplicateProperty(
                dto.getUsername(),
                dto.getEmail(),
                dto.getSsn(),
                dto.getPhoneNumber()
        );

        User teacher = userMapper.mapTeacherRequestDTOToUser(dto);

        teacher.setUserRole(userRoleService.getUserRoleByRole(Role.TEACHER));

        //TODO: Lesson Programlar Setlenecek.

        teacher.setPassword(passwordEncoder.encode(teacher.getPassword()));

        User savedTeacher = userRepository.save(teacher);

        return ResponseMessage.<TeacherResponseDTO>builder()
                .message(SuccessMessages.TEACHER_CREATE_SUCCESS)
                .object(userMapper.mapUserToTeacherResponseDTO(savedTeacher))
                .build();
    }

    public List<StudentResponseDTO> getAllStudentsOfAdvisorByUsername(String username) {
        User teacher = methodHelper.isUserExistByUsername(username);

        methodHelper.checkAdvisor(teacher);

        return userRepository.findAllByAdvisorTeacherId(teacher.getId())
                .stream()
                .map(userMapper::mapUserToStudentResponseDTO)
                .collect(Collectors.toList());
    }
}
