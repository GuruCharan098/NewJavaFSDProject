package com.example.college.entity;

import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
@Table(name="student_courses", uniqueConstraints=@UniqueConstraint(columnNames={"student_id","course_id","academic_session"}))
public class StudentCourse {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional=false) @JoinColumn(name="student_id")
    private User student;
    @ManyToOne(optional=false) @JoinColumn(name="course_id")
    private Course course;
    @Column(name="academic_session",nullable=false,length=30)
    private String academicSession;
    private LocalDate enrolledOn=LocalDate.now();
    @Column(nullable=false,length=20)
    private String status="ENROLLED";
    protected StudentCourse(){}
    public StudentCourse(User student,Course course,String session){this.student=student;this.course=course;this.academicSession=session;}
    
    public Long getId(){return id;}
    public User getStudent(){return student;}
    public Course getCourse(){return course;}
    public String getAcademicSession(){return academicSession;}
    public String getStatus(){return status;}
}
