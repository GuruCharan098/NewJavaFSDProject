package com.example.college.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "faculty_profiles")
public class FacultyProfile {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;
    @Column(nullable = false, unique = true, length = 30)
    private String employeeId;
    private String department;
    private String designation;
    private String qualification;
    private String phone;
    @Column(length = 500) private String address;

    protected FacultyProfile() {}
    public FacultyProfile(User user, String employeeId, String department, String designation) {
        this.user=user; this.employeeId=employeeId; this.department=department; this.designation=designation;
    }
    public Long getId(){return id;}
    public User getUser(){return user;}
    public String getEmployeeId(){return employeeId;}
    public String getDepartment(){return department;}
    public String getDesignation(){return designation;}
    public String getQualification(){return qualification;}
    public void setQualification(String v){qualification=v;}
    public String getPhone(){return phone;}
    public void setPhone(String v){phone=v;}
    public String getAddress(){return address;}
    public void setAddress(String v){address=v;}
}
