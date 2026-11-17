package com.example.college.controller;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.example.college.dto.*;
import com.example.college.entity.*;
import com.example.college.repository.*;
import com.example.college.service.CollegeService;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final CollegeService service; private final UserRepository users;
    public AdminController(CollegeService service,UserRepository users){this.service=service;this.users=users;}
    @PostMapping("/students") public User createStudent(@RequestBody @jakarta.validation.Valid CreatePersonRequest r){return service.createStudent(r);}
    @PostMapping("/faculty") public User createFaculty(@RequestBody @jakarta.validation.Valid CreatePersonRequest r){return service.createFaculty(r);}
    @GetMapping("/users") public List<User> users(){return users.findAll();}
    @PostMapping("/courses") public Course createCourse(@RequestBody @jakarta.validation.Valid CreateCourseRequest r){return service.createCourse(r);}
    @PostMapping("/faculty/{facultyId}/courses/{courseId}") public FacultyCourse assign(@PathVariable Long facultyId,@PathVariable Long courseId){return service.assignFaculty(facultyId,courseId);}
}
