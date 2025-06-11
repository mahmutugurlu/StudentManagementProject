package com.tpe.student_management.payload.request.user;

import com.tpe.student_management.payload.request.abstracts.AbstractUserRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class UserRequestDTOWithoutPassword extends AbstractUserRequest {
}
