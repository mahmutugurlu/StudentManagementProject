package com.tpe.student_management.payload.response.abstracts;

import com.tpe.student_management.entity.enums.Gender;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class BaseUserResponse {
    private Long userId;
    private String username;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String ssn;
    private String birthPlace;
    private String phoneNumber;
    private Gender gender;
    private String email;
    private String userRole;
}

