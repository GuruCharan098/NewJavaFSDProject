package com.example.college.entity;

import java.time.LocalDateTime;
import com.example.college.enums.AuthProvider;
import com.example.college.enums.Role;
import com.example.college.enums.UserStatus;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "users")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String fullName;
    
    @Column(nullable = false, unique = true, length = 150)
    private String email;
    
    @JsonIgnore
    @Column(length = 255)
    private String password;
    
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20)
    private Role role;
    
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20)
    private UserStatus status = UserStatus.ACTIVE;
    
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20)
    private AuthProvider authProvider = AuthProvider.LOCAL;
    
    @Column(unique = true, length = 150)
    private String googleSubject;
    
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    protected User() {}
    public User(String fullName, String email, String password, Role role, UserStatus status, AuthProvider provider) {
        this.fullName = fullName; this.email = email; this.password = password;
        this.role = role; this.status = status; this.authProvider = provider;
    }
    @PrePersist void onCreate() { createdAt = LocalDateTime.now(); updatedAt = createdAt; }
    @PreUpdate void onUpdate() { updatedAt = LocalDateTime.now(); }

    public Long getId() { return id; }
    public String getFullName() { return fullName; }
    public void setFullName(String v) { fullName = v; }
    public String getEmail() { return email; }
    public void setEmail(String v) { email = v; }
    public String getPassword() { return password; }
    public void setPassword(String v) { password = v; }
    public Role getRole() { return role; }
    public void setRole(Role v) { role = v; }
    public UserStatus getStatus() { return status; }
    public void setStatus(UserStatus v) { status = v; }
    public AuthProvider getAuthProvider() { return authProvider; }
    public void setAuthProvider(AuthProvider v) { authProvider = v; }
    public String getGoogleSubject() { return googleSubject; }
    public void setGoogleSubject(String v) { googleSubject = v; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
