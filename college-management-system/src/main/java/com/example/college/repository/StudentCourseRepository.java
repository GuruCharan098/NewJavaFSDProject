package com.example.college.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.college.entity.StudentCourse;
public interface StudentCourseRepository extends JpaRepository<StudentCourse,Long>{
 List<StudentCourse> findByStudentId(Long studentId);
 List<StudentCourse> findByCourseId(Long courseId);
 boolean existsByStudentIdAndCourseIdAndAcademicSession(Long studentId,Long courseId,String session);
}