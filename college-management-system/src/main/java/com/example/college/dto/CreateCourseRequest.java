package com.example.college.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
public record CreateCourseRequest(
 @NotBlank String courseCode,
 @NotBlank String courseName,
 String department,
 @NotNull @Positive Integer credits
) {}
