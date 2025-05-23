package com.tpe.student_management.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDTO {
    @NotNull(message = "You must enter a username.")
    private String username;

    @NotNull(message = "You must enter a password.")
    private String password;
}
