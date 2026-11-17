package com.example.college.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.college.entity.User;

public interface UserRepository
        extends JpaRepository<User, Long> {

    Optional<User> findByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCase(String email);

    Optional<User> findByGoogleSubject(String googleSubject);

}
