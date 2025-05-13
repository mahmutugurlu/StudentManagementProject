package com.tpe.student_management.contact_us.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ContactMessageRequestDTO {

    @NotNull(message = "Please enter a name.")
    @Size(min = 3, max = 32, message = "Your name must be between {min}-{max} characters.")
    @Pattern(regexp = "^[A-Za-z' ]+$", message = "Your name must contain only letters and spaces.")
    private String name;

    @NotNull(message = "Please enter an email.")
    @Email(message = "Please enter a valid email address.")
    private String email;

    @NotNull(message = "Please enter a subject.")
    @Size(min = 3, max = 64, message = "The subject must be between {min}-{max} characters.")
    private String subject;

    @NotNull(message = "Please enter a message.")
    @Size(min = 3, max = 2000, message = "The message must be between {min}-{max} characters.")
    private String message;
}