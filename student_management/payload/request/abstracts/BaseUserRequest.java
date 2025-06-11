package com.tpe.student_management.payload.request.abstracts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class BaseUserRequest extends AbstractUserRequest {
    @NotNull(message = "Please enter your password")
    @Size(min = 8, max = 60, message = "Your password should be {min}-{max} characters long.")
    private String password;

    private Boolean builtIn = false;
}
