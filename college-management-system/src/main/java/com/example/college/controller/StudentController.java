package com.example.college.controller;
import java.util.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.example.college.entity.*;
import com.example.college.service.CollegeService;
import com.example.college.repository.StudentAttendanceRepository;
@RestController
@RequestMapping("/api/student")
@PreAuthorize("hasRole('STUDENT')")
public class StudentController {
 private final CollegeService service;
 public StudentController(CollegeService service){this.service=service;}
 @PostMapping("/courses/{courseId}/enroll")
 public StudentCourse enroll(@PathVariable Long courseId,@RequestParam(defaultValue="2026-2027") String session){return service.enroll(courseId,session);}
 @GetMapping("/courses") public List<StudentCourse> courses(){return service.myCourses();}
 @GetMapping("/attendance") public List<StudentAttendance> attendance(){return service.myStudentAttendance();}
}
