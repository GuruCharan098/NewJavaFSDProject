
package com.example.college.security;

import java.io.IOException;
import java.util.Objects;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.college.entity.User;
import com.example.college.enums.AuthProvider;
import com.example.college.enums.Role;
import com.example.college.enums.UserStatus;
import com.example.college.repository.UserRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class GoogleLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository users;

    public GoogleLoginSuccessHandler(UserRepository users) {
        this.users = users;
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws IOException, ServletException {

        // Get Google user details using OAuth2User
        OAuth2User google = (OAuth2User) authentication.getPrincipal();

        String subject = google.getAttribute("sub");
        String email = google.getAttribute("email");
        String name = google.getAttribute("name");

        // Validate required Google attributes
        if (subject == null || subject.isBlank()
                || email == null || email.isBlank()) {

            response.sendRedirect(
                    "http://localhost:5173/login?google=invalid-profile");
            return;
        }

        if (name == null || name.isBlank()) {
            name = email;
        }

        final String googleSubject = subject;
        final String googleEmail = email;
        final String googleName = name;

        // Find existing Google account using Google subject ID
        User user = users.findByGoogleSubject(googleSubject)
                .orElse(null);

        if (user == null) {

            // Check whether a local account already exists
            User existingUser = users.findByEmailIgnoreCase(googleEmail)
                    .orElse(null);

            if (existingUser != null) {

                // Do not automatically link local accounts
                response.sendRedirect(
                        "http://localhost:5173/login?google=link-required");
                return;
            }

            // Create a new Google student account
            User newUser = new User(
                    googleName,
                    googleEmail,
                    null,
                    Role.STUDENT,
                    UserStatus.PENDING,
                    AuthProvider.GOOGLE
            );

            newUser.setGoogleSubject(googleSubject);

            user = users.save(newUser);
        }

        // Check account status
        if (user.getStatus() != UserStatus.ACTIVE) {
            response.sendRedirect(
                    "http://localhost:5173/login?google=pending");
            return;
        }

        // Successful login
        response.sendRedirect("http://localhost:5173/");
    }
}
