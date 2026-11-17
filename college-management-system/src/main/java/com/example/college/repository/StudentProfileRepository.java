package com.example.college.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.college.entity.StudentProfile;

public interface StudentProfileRepository
        extends JpaRepository<StudentProfile, Long> {

    Optional<StudentProfile> findByRollNumber(String rollNumber);

}

