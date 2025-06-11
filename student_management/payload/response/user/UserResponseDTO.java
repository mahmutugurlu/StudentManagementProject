package com.tpe.student_management.payload.response.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tpe.student_management.payload.response.abstracts.BaseUserResponse;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@SuperBuilder
public class UserResponseDTO extends BaseUserResponse {
}
