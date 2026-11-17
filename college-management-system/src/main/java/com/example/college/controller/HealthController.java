package com.example.college.controller;
import java.util.Map;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api")
public class HealthController {
 @GetMapping("/health")
 public Map<String,String> health(){return Map.of("application","College Management System","status","UP");}
}
