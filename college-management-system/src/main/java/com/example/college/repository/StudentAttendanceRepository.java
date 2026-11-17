package com.example.college.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.college.entity.StudentAttendance;
public interface StudentAttendanceRepository extends JpaRepository<StudentAttendance,Long>{
 List<StudentAttendance> findByStudentCourseStudentId(Long studentId);
}