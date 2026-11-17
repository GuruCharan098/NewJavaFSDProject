package com.example.college.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.college.entity.FacultyProfile;

public interface FacultyProfileRepository
        extends JpaRepository<FacultyProfile, Long> {

    boolean existsByEmployeeId(String employeeId);

}

