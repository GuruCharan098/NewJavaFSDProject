package com.example.college.controller;
import java.time.LocalDate;
import java.util.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.example.college.dto.MarkAttendanceRequest;
import com.example.college.entity.*;
import com.example.college.enums.AttendanceStatus;
import com.example.college.service.CollegeService;
@RestController
@RequestMapping("/api/faculty")
@PreAuthorize("hasRole('FACULTY')")
public class FacultyController {
 private final CollegeService service;
 public FacultyController(CollegeService service){this.service=service;}
 @GetMapping("/courses") public List<FacultyCourse> courses(){return service.facultyCourses();}
 @GetMapping("/courses/{courseId}/students") public List<StudentCourse> students(@PathVariable Long courseId){return service.studentsInCourse(courseId);}
 @PostMapping("/attendance/students") public StudentAttendance markStudent(@RequestBody MarkAttendanceRequest r){return service.markStudentAttendance(r);}
 @PostMapping("/attendance/self") public FacultyAttendance markSelf(@RequestParam AttendanceStatus status,@RequestParam LocalDate date){return service.markFacultyAttendance(status,date);}
 @GetMapping("/attendance/self") public List<FacultyAttendance> ownAttendance(){return service.myFacultyAttendance();}
}
