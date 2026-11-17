package com.example.college.controller;
import java.util.Map;
import org.springframework.web.bind.annotation.*;
import com.example.college.service.CollegeService;
@RestController
@RequestMapping("/api")
public class ProfileController {
 private final CollegeService service;
 public ProfileController(CollegeService service){this.service=service;}
 @GetMapping("/profile") public Map<String,Object> profile(){return service.profile();}
 @GetMapping("/dashboard") public Map<String,Object> dashboard(){return service.dashboard();}
}
