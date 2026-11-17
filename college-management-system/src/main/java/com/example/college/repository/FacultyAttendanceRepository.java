package com.example.college.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.college.entity.FacultyAttendance;
public interface FacultyAttendanceRepository extends JpaRepository<FacultyAttendance,Long>{
 List<FacultyAttendance> findByFacultyId(Long facultyId);
}