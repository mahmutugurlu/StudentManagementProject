package com.tpe.student_management.payload.request.abstracts;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tpe.student_management.entity.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class AbstractUserRequest {
    @NotNull(message = "Please enter your username.")
    @Size(min = 4, max = 16,message = "Your username should be at least 4 characters.")
    @Pattern(regexp = "\\A(?!\\s*\\Z).+", message = "Your username must consist of the characters.")
    private String username;

    @NotNull(message = "Please enter your name.")
    @Size(min = 4, max = 16,message = "Your name should be at least 4 characters.")
    @Pattern(regexp = "\\A(?!\\s*\\Z).+", message = "Your name must consist of the characters.")
    private String firstName;

    @NotNull(message = "Please enter your surname")
    @Size(min = 4, max = 16,message = "Your surname should be at least 4 chars")
    @Pattern(regexp = "\\A(?!\\s*\\Z).+", message = "Your surname must consist of the characters .")
    private String lastName;

    @NotNull(message = "Please enter your birthday")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Past(message = "Your birthday can not be in the future")
    private LocalDate dateOfBirth;

    @NotNull
    @Pattern(regexp = "^(?!000|666)[0-8][0-9]{2}-(?!00)[0-9]{2}-(?!0000)[0-9]{4}$",
            message = "Please enter valid SSN number")
    private String ssn;

    @NotNull(message = "Please enter your birthplace")
    @Size(min = 2, max = 16,message = "Your birthplace should be at least 2 characters")
    @Pattern(regexp = "\\A(?!\\s*\\Z).+", message = "Your birthplace must consist of the characters .")
    private String birthPlace;

    @NotNull(message = "Please enter your phone number")
    @Size(min = 12, max = 12,message = "Your phone number should be 12 characters long")
    @Pattern(regexp = "^((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$",
            message = "Please enter valid phone number")
    private String phoneNumber;

    @NotNull(message = "Please enter your gender")
    private Gender gender;

    @NotNull(message = "Please enter your email")
    @Email(message = "Please enter valid email")
    @Size(min=5, max=50 , message = "Your email should be between 5 and 50 chars")
    private String email;
}
