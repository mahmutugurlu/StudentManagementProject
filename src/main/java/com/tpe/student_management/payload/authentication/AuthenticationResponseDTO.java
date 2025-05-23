package com.tpe.student_management.payload.authentication;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthenticationResponseDTO {
    private String username;
    private String ssn;
    private String role;
    private String token;
    private String firstName;
}
