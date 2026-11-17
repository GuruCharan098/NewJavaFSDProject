package com.example.college.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.college.entity.User;
import com.example.college.enums.*;
import com.example.college.repository.UserRepository;

@Configuration
public class DataInitializer {
    @SuppressWarnings("unused")
	@Bean
    CommandLineRunner createInitialAdmin(UserRepository users,PasswordEncoder encoder,
        @Value("${app.bootstrap-admin.email}") String email,
        @Value("${app.bootstrap-admin.password}") String password) {
        return args -> {
            if(!users.existsByEmailIgnoreCase(email)) {
                users.save(new User("College Administrator",email,encoder.encode(password),
                    Role.ADMIN,UserStatus.ACTIVE,AuthProvider.LOCAL));
                System.out.println("Initial admin created: "+email);
            }
        };
    }
}
