package com.example.college.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.college.entity.Course;
public interface CourseRepository extends JpaRepository<Course,Long>{
 List<Course> findByActiveTrue();
 boolean existsByCourseCodeIgnoreCase(String code);
}