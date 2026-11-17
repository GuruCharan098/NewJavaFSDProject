package com.example.college.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.college.dto.RegisterRequest;
import com.example.college.entity.*;
import com.example.college.enums.*;
import com.example.college.repository.*;

@Service
public class AccountService {
    private final UserRepository users;
    private final StudentProfileRepository students;
    private final FacultyProfileRepository faculty;
    private final PasswordEncoder encoder;
    public AccountService(UserRepository users,StudentProfileRepository students,
                          FacultyProfileRepository faculty,PasswordEncoder encoder){
        this.users=users;this.students=students;this.faculty=faculty;this.encoder=encoder;
    }
    @Transactional
    public User registerStudent(RegisterRequest r){
        if(users.existsByEmailIgnoreCase(r.email())) throw new IllegalArgumentException("Email already registered");
        if(students.findByRollNumber(r.rollNumber()).isPresent()) throw new IllegalArgumentException("Roll number already exists");
        User u=users.save(new User(r.fullName(),r.email().trim().toLowerCase(),
            encoder.encode(r.password()),Role.STUDENT,UserStatus.ACTIVE,AuthProvider.LOCAL));
        students.save(new StudentProfile(u,r.rollNumber(),r.department(),r.semester()));
        return u;
    }
    @Transactional
    public User createPerson(String name,String email,String password,Role role,String identifier,String department,String detail){
        if(users.existsByEmailIgnoreCase(email)) throw new IllegalArgumentException("Email already registered");
        User u=users.save(new User(name,email.trim().toLowerCase(),encoder.encode(password),role,UserStatus.ACTIVE,AuthProvider.LOCAL));
        if(role==Role.STUDENT) students.save(new StudentProfile(u,identifier,department,detail));
        else if(role==Role.FACULTY) faculty.save(new FacultyProfile(u,identifier,department,detail));
        return u;
    }
}
