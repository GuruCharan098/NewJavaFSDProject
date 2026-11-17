package com.example.college.controller;
import java.util.List;
import org.springframework.web.bind.annotation.*;
import com.example.college.entity.Course;
import com.example.college.service.CollegeService;
@RestController
@RequestMapping("/api/courses")
public class CourseController {
 private final CollegeService service;
 public CourseController(CollegeService service){this.service=service;}
 @GetMapping public List<Course> all(){return service.allCourses();}
}
