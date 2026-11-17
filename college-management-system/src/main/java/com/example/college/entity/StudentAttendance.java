package com.example.college.entity;

import java.time.LocalDate;
import jakarta.persistence.*;
import com.example.college.enums.AttendanceStatus;

@Entity
@Table(name="student_attendance", uniqueConstraints=@UniqueConstraint(columnNames={"student_course_id","attendance_date"}))
public class StudentAttendance {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional=false) @JoinColumn(name="student_course_id")
    private StudentCourse studentCourse;
    @Column(name="attendance_date",nullable=false)
    private LocalDate attendanceDate;
    @Enumerated(EnumType.STRING) @Column(nullable=false,length=20)
    private AttendanceStatus status;
    @ManyToOne(optional=false) @JoinColumn(name="marked_by")
    private User markedBy;
    public StudentAttendance(){}
    public StudentAttendance(StudentCourse sc,LocalDate date,AttendanceStatus status,User markedBy){
        this.studentCourse=sc;this.attendanceDate=date;this.status=status;this.markedBy=markedBy;
    }
    public Long getId(){return id;}
    public LocalDate getAttendanceDate(){return attendanceDate;}
    public AttendanceStatus getStatus(){return status;}
    public StudentCourse getStudentCourse(){return studentCourse;}
}
