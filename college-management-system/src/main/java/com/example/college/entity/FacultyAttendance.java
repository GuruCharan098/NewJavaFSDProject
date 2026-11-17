package com.example.college.entity;

import java.time.LocalDate;
import jakarta.persistence.*;
import com.example.college.enums.AttendanceStatus;

@Entity
@Table(name="faculty_attendance", uniqueConstraints=@UniqueConstraint(columnNames={"faculty_id","attendance_date"}))
public class FacultyAttendance {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional=false) @JoinColumn(name="faculty_id")
    private User faculty;
    @Column(name="attendance_date",nullable=false)
    private LocalDate attendanceDate;
    @Enumerated(EnumType.STRING) @Column(nullable=false,length=20)
    private AttendanceStatus status;
    public FacultyAttendance(){}
    public FacultyAttendance(User faculty,LocalDate date,AttendanceStatus status){
        this.faculty=faculty;this.attendanceDate=date;this.status=status;
    }
    public Long getId(){return id;}
    public LocalDate getAttendanceDate(){return attendanceDate;}
    public AttendanceStatus getStatus(){return status;}
}
