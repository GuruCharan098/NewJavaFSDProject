package com.example.college.entity;

import java.time.LocalDate;

import com.example.college.entity.User;

import jakarta.persistence.*;

@Entity
@Table(name = "student_profiles")
public class StudentProfile {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;
    @Column(nullable = false, unique = true, length = 30)
    private String rollNumber;
    private String department;
    private String semester;
    private LocalDate admissionDate;
    private String phone;
    @Column(length = 500) private String address;

    protected StudentProfile() {}
    public StudentProfile(User user, String rollNumber, String department, String semester) {
        this.user=user; this.rollNumber=rollNumber; this.department=department; this.semester=semester;
        this.admissionDate=LocalDate.now();
    }
    public Long getId(){return id;}
    public User getUser(){return user;}
    public String getRollNumber(){return rollNumber;}
    public String getDepartment(){return department;}
    public String getSemester(){return semester;}
    public LocalDate getAdmissionDate(){return admissionDate;}
    public String getPhone(){return phone;}
    public void setPhone(String v){phone=v;}
    public String getAddress(){return address;}
    public void setAddress(String v){address=v;}
    public void setDepartment(String v){department=v;}
    public void setSemester(String v){semester=v;}
}
