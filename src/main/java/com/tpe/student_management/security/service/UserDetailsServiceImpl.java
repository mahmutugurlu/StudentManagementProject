package com.tpe.student_management.security.service;

import com.tpe.student_management.entity.classes.user.User;
import com.tpe.student_management.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user != null){
            return new UserDetailsImpl(user.getId(),
                    user.getUsername(),
                    user.getFirstName(),
                    user.getIsAdvisor(),
                    user.getPassword(),
                    user.getSsn(),
                    user.getUserRole().getRole().name());
        }else {
            throw new UsernameNotFoundException("Username: '" + username + "' not found!");
        }
    }
}
