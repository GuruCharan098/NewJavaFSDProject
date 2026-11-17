package com.example.college.dto;
import java.time.LocalDate;
import com.example.college.enums.AttendanceStatus;
import jakarta.validation.constraints.NotNull;
public record MarkAttendanceRequest(
 @NotNull Long studentCourseId,
 @NotNull LocalDate attendanceDate,
 @NotNull AttendanceStatus status
) {}
