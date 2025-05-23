package com.tpe.student_management.payload.mapper;

import com.tpe.student_management.entity.classes.user.User;
import com.tpe.student_management.payload.request.abstracts.BaseUserRequest;
import com.tpe.student_management.payload.request.user.TeacherRequestDTO;
import com.tpe.student_management.payload.request.user.UserRequestDTO;
import com.tpe.student_management.payload.response.user.StudentResponseDTO;
import com.tpe.student_management.payload.response.user.TeacherResponseDTO;
import com.tpe.student_management.payload.response.user.UserResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    //Exercise: Bu metod normalde tek basina yeterli.
    //Cast kullanilarak Student ve Teacher icin de kullanilabilirdi.
    //Deneyin, sonucu gorun.
    public User mapUserRequestDTOToUser(BaseUserRequest userRequest){
        return User.builder()
                .username(userRequest.getUsername())
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .password(userRequest.getPassword())
                .ssn(userRequest.getSsn())
                .dateOfBirth(userRequest.getDateOfBirth())
                .birthPlace(userRequest.getBirthPlace())
                .phoneNumber(userRequest.getPhoneNumber())
                .gender(userRequest.getGender())
                .email(userRequest.getEmail())
                .builtIn(userRequest.getBuiltIn())
                .build();
    }

    public UserResponseDTO mapUserToUserResponseDTO(User user){
        return UserResponseDTO.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .gender(user.getGender())
                .dateOfBirth(user.getDateOfBirth())
                .birthPlace(user.getBirthPlace())
                .ssn(user.getSsn())
                .email(user.getEmail())
                .userRole(user.getUserRole().getRole().name())
                .build();
    }

    public StudentResponseDTO mapUserToStudentResponseDTO(User student){
        return StudentResponseDTO.builder()
                .userId(student.getId())
                .username(student.getUsername())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .dateOfBirth(student.getDateOfBirth())
                .birthPlace(student.getBirthPlace())
                .phoneNumber(student.getPhoneNumber())
                .gender(student.getGender())
                .email(student.getEmail())
                .fatherName(student.getFatherName())
                .motherName(student.getMotherName())
                .studentNumber(student.getStudentNumber())
                .isActive(student.isActive())
                .build();
    }

    public TeacherResponseDTO mapUserToTeacherResponseDTO(User teacher){
        return TeacherResponseDTO.builder()
                .userId(teacher.getId())
                .username(teacher.getUsername())
                .firstName(teacher.getFirstName())
                .lastName(teacher.getLastName())
                .dateOfBirth(teacher.getDateOfBirth())
                .birthPlace(teacher.getBirthPlace())
                .ssn(teacher.getSsn())
                .phoneNumber(teacher.getPhoneNumber())
                .gender(teacher.getGender())
                .email(teacher.getEmail())
                .lessonProgramList(teacher.getLessonProgramList())
                .isAdvisor(teacher.getIsAdvisor())
                .build();
    }
    public User mapUserRequestToUpdatedUser(UserRequestDTO userRequest, Long userId){
        return User.builder()
                .id(userId)
                .username(userRequest.getUsername())
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .password(userRequest.getPassword())
                .ssn(userRequest.getSsn())
                .dateOfBirth(userRequest.getDateOfBirth())
                .birthPlace(userRequest.getBirthPlace())
                .phoneNumber(userRequest.getPhoneNumber())
                .gender(userRequest.getGender())
                .email(userRequest.getEmail())
                .build();
    }

    public User mapTeacherRequestDTOToUser(TeacherRequestDTO dto){
        return User.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .ssn(dto.getSsn())
                .username(dto.getUsername())
                .dateOfBirth(dto.getDateOfBirth())
                .birthPlace(dto.getBirthPlace())
                .password(dto.getPassword())
                .phoneNumber(dto.getPhoneNumber())
                .email(dto.getEmail())
                .isAdvisor(dto.getIsAdvisor())
                .builtIn(dto.getBuiltIn())
                .gender(dto.getGender())
                .build();

    }
}
