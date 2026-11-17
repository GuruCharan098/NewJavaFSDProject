
package com.example.college.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.college.dto.RegisterRequest;
import com.example.college.entity.User;
import com.example.college.repository.UserRepository;
import com.example.college.service.AccountService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@Validated
public class AuthController {

    private final AccountService accounts;
    private final AuthenticationManager manager;
    private final UserRepository users;

    public AuthController(
            AccountService accounts,
            AuthenticationManager manager,
            UserRepository users) {

        this.accounts = accounts;
        this.manager = manager;
        this.users = users;
    }

    // CSRF Token
    @GetMapping("/csrf")
    public Map<String, String> csrf(CsrfToken token) {
        return Map.of("token", token.getToken());
    }

    // Student Registration
    @PostMapping("/register")
    public ResponseEntity<?> register(
            @Valid @RequestBody RegisterRequest request) {

        User user = accounts.registerStudent(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                Map.of(
                        "message", "Student account created. Please log in.",
                        "email", user.getEmail()
                )
        );
    }

    // Email and Password Login
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody Map<String, String> body,
            HttpServletRequest request) {

        String email = body.get("email");
        String password = body.get("password");

        if (email == null || email.isBlank()
                || password == null || password.isBlank()) {

            return ResponseEntity.badRequest().body(
                    Map.of("message", "Email and password are required.")
            );
        }

        Authentication authentication = manager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        SecurityContext context =
                SecurityContextHolder.createEmptyContext();

        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);

        request.getSession(true).setAttribute(
                "SPRING_SECURITY_CONTEXT", context
        );

        User user = users.findByEmailIgnoreCase(email.trim())
                .orElseThrow(() ->
                        new AuthenticationCredentialsNotFoundException(
                                "Authenticated user not found in database."
                        )
                );

        return ResponseEntity.ok(userResponse(user));
    }

    // Get Current Logged-in User
    @GetMapping("/me")
    public ResponseEntity<?> me(Authentication authentication) {

        if (authentication == null
                || !authentication.isAuthenticated()
                || "anonymousUser".equals(authentication.getPrincipal())) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    Map.of("message", "Please log in first.")
            );
        }

        String email = getCurrentUserEmail(authentication);

        if (email == null || email.isBlank()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    Map.of("message", "Unable to identify the logged-in user.")
            );
        }

        User user = users.findByEmailIgnoreCase(email.trim())
                .orElse(null);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("message",
                            "User account not found. Please contact administrator.")
            );
        }

        return ResponseEntity.ok(userResponse(user));
    }

    // Extract Email from Authentication
    private String getCurrentUserEmail(Authentication authentication) {

        if (authentication instanceof OAuth2AuthenticationToken) {

            OAuth2User oauth2User =
                    (OAuth2User) authentication.getPrincipal();

            return oauth2User.getAttribute("email");
        }

        return authentication.getName();
    }

    // Common User Response
    private Map<String, Object> userResponse(User user) {

        return Map.of(
                "id", user.getId(),
                "fullName", user.getFullName(),
                "email", user.getEmail(),
                "role", user.getRole().name(),
                "status", user.getStatus().name()
        );
    }
}
