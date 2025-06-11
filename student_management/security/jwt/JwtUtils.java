package com.tpe.student_management.security.jwt;

import com.tpe.student_management.security.service.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
//@Slf4j asagida yaptigimizin aynisidir. Bize "logger" isminde bir obje verir.
public class JwtUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${backendapi.app.jwtExpirationMs}")
    private long jwtExpirationMs;

    @Value("${backendapi.app.jwtSecret}")
    private String jwtSecret;

    //TASK: Generate JWT ************************************
    public String generateJWT(Authentication authentication){
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return generateJWTWithUsername(userDetails.getUsername());
    }

    private String generateJWTWithUsername(String username){
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
    //TASK: Validate JWT ************************************
    public boolean validateJWT(String jwt){
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwt);
            return true;
        } catch (ExpiredJwtException e) {
            LOGGER.error("JWT is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            LOGGER.error("JWT is unsupported: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            LOGGER.error("JWT is malformed: {}", e.getMessage());
        } catch (SignatureException e) {
            LOGGER.error("JWT has unexpected signature: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            LOGGER.error("Illegal Argument: {}", e.getMessage());
        }
        return false;
    }
    //TASK: Get Username From JWT *************************** //ODEV
    public String getUsernameFromJWT(String jwt){
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(jwt)
                .getBody()
                .getSubject();
    }
}
