package com.example.college.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import com.example.college.entity.User;
import com.example.college.repository.UserRepository;

public final class CurrentUser {
    private CurrentUser() {}
    public static User require(UserRepository users) {
        Authentication auth=SecurityContextHolder.getContext().getAuthentication();
        if(auth==null || !auth.isAuthenticated()) throw new IllegalStateException("Not authenticated");
        Object principal=auth.getPrincipal();
        String email=principal instanceof UserDetails ud ? ud.getUsername() : auth.getName();
        return users.findByEmailIgnoreCase(email)
            .orElseThrow(()->new IllegalStateException("Authenticated user not found"));
    }
}
