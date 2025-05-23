package com.tpe.student_management.controller.user;

import com.tpe.student_management.payload.authentication.AuthenticationResponseDTO;
import com.tpe.student_management.payload.messages.SuccessMessages;
import com.tpe.student_management.payload.request.UpdatePasswordRequestDTO;
import com.tpe.student_management.payload.request.UserLoginDTO;
import com.tpe.student_management.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDTO> authenticateUser(@RequestBody @Valid UserLoginDTO dto){
        return ResponseEntity.ok(authService.authenticateUser(dto));
    }

    @PatchMapping("/update-password")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER','TEACHER','STUDENT')")
    public ResponseEntity<String> updatePassword(@Valid @RequestBody UpdatePasswordRequestDTO dto,
                                                 HttpServletRequest request){
        authService.updatePassword(dto, request);

        String message = SuccessMessages.PASSWORD_UPDATE_SUCCESS;

        return ResponseEntity.ok(message);
    }

}
