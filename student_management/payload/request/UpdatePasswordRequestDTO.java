package com.tpe.student_management.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePasswordRequestDTO {

    @NotBlank(message = "Old password must be entered.")
    private String oldPassword;

    @NotBlank(message = "New password must be entered.")
    private String newPassword;
}
