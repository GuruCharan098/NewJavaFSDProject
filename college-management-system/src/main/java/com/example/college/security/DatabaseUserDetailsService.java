package com.example.college.security;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import com.example.college.entity.User;
import com.example.college.enums.UserStatus;
import com.example.college.repository.UserRepository;

@Service
public class DatabaseUserDetailsService implements UserDetailsService {
    private final UserRepository users;
    public DatabaseUserDetailsService(UserRepository users){this.users=users;}
    @Override public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=users.findByEmailIgnoreCase(username)
            .orElseThrow(()->new UsernameNotFoundException("Account not found"));
        if(user.getStatus()!=UserStatus.ACTIVE) throw new DisabledException("Account is not active");
        return org.springframework.security.core.userdetails.User
            .withUsername(user.getEmail())
            .password(user.getPassword()==null ? "" : user.getPassword())
            .roles(user.getRole().name())
            .disabled(user.getStatus()!=UserStatus.ACTIVE)
            .build();
    }
}
