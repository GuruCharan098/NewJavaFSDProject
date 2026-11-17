package com.example.college.entity;

import jakarta.persistence.*;

@Entity
@Table(name="courses")
public class Course {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false, unique=true, length=30)
    private String courseCode;
    @Column(nullable=false, length=120)
    private String courseName;
    private String department;
    private Integer credits;
    private boolean active=true;
    protected Course(){}
    public Course(String courseCode,String courseName,String department,Integer credits){
        this.courseCode=courseCode;this.courseName=courseName;this.department=department;this.credits=credits;
    }
    public Long getId(){return id;}
    public String getCourseCode(){return courseCode;}
    public String getCourseName(){return courseName;}
    public String getDepartment(){return department;}
    public Integer getCredits(){return credits;}
    public boolean isActive(){return active;}
    public void setActive(boolean v){active=v;}
}
