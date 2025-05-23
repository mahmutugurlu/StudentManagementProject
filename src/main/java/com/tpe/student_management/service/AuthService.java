package com.tpe.student_management.service;

import com.tpe.student_management.entity.classes.user.User;
import com.tpe.student_management.exception.BadRequestException;
import com.tpe.student_management.payload.authentication.AuthenticationResponseDTO;
import com.tpe.student_management.payload.messages.ErrorMessages;
import com.tpe.student_management.payload.request.UpdatePasswordRequestDTO;
import com.tpe.student_management.payload.request.UserLoginDTO;
import com.tpe.student_management.repository.user.UserRepository;
import com.tpe.student_management.security.jwt.JwtUtils;
import com.tpe.student_management.security.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationResponseDTO authenticateUser(UserLoginDTO dto) {
        String username = dto.getUsername();
        String password = dto.getPassword(); //hashlenmis degil, duz sifre.

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String generatedToken = jwtUtils.generateJWT(authentication);

        //ssn, role, firstName'e nasil ulasabilirim?
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal(); //User burada

        Set<String> roles = userDetails.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toSet());

        Optional<String> role = roles.stream().findFirst();

        AuthenticationResponseDTO.AuthenticationResponseDTOBuilder builder = AuthenticationResponseDTO.builder();
        //biraz daha islem yapabilirim
        builder.username(userDetails.getUsername());
        builder.token(generatedToken);
        builder.firstName(userDetails.getFirstName());
        builder.ssn(userDetails.getSsn());
        //builder.role(role);
        role.ifPresent(builder::role);

        return builder.build();
    }

    public void updatePassword(UpdatePasswordRequestDTO dto, HttpServletRequest request) {
        String username = (String) request.getAttribute("username");

        User user = userRepository.findByUsername(username);

        if (!passwordEncoder.matches(dto.getOldPassword(), user.getPassword())){
            throw new BadRequestException(ErrorMessages.OLD_PASSWORD_MISMATCH);
        }
        if (Boolean.TRUE.equals(user.getBuiltIn())){ //user.getBuiltIn().equals(Boolean.TRUE)
            throw new BadRequestException(ErrorMessages.NOT_PERMITTED_ACTION);
        }

        String hashedNewPassword = passwordEncoder.encode(dto.getNewPassword());
        user.setPassword(hashedNewPassword);
        userRepository.save(user);
    }
}
