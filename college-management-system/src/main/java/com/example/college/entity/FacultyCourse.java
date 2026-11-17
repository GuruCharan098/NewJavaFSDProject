package com.example.college.entity;

import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
@Table(name="faculty_courses", uniqueConstraints=@UniqueConstraint(columnNames={"faculty_id","course_id"}))
public class FacultyCourse {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional=false) @JoinColumn(name="faculty_id")
    private User faculty;
    @ManyToOne(optional=false) @JoinColumn(name="course_id")
    private Course course;
    private LocalDate assignedOn=LocalDate.now();
    protected FacultyCourse(){}
    public FacultyCourse(User faculty,Course course){this.faculty=faculty;this.course=course;}
    public Long getId(){return id;}
    public User getFaculty(){return faculty;}
    public Course getCourse(){return course;}
}
