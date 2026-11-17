package com.example.college.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.college.entity.FacultyCourse;
public interface FacultyCourseRepository extends JpaRepository<FacultyCourse,Long>{
 List<FacultyCourse> findByFacultyId(Long facultyId);
 boolean existsByFacultyIdAndCourseId(Long facultyId,Long courseId);
}