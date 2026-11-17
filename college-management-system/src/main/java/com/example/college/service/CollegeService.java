package com.example.college.service;

import java.time.*;
import java.util.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.college.dto.*;
import com.example.college.entity.*;
import com.example.college.enums.*;
import com.example.college.repository.*;
import com.example.college.security.CurrentUser;

@Service
public class CollegeService {
    private final UserRepository users; private final CourseRepository courses;
    private final FacultyCourseRepository facultyCourses; private final StudentCourseRepository studentCourses;
    private final StudentAttendanceRepository studentAttendance; private final FacultyAttendanceRepository facultyAttendance;
    private final AccountService accounts;
    public CollegeService(UserRepository users,CourseRepository courses,FacultyCourseRepository facultyCourses,
        StudentCourseRepository studentCourses,StudentAttendanceRepository studentAttendance,
        FacultyAttendanceRepository facultyAttendance,AccountService accounts){
        this.users=users;this.courses=courses;this.facultyCourses=facultyCourses;this.studentCourses=studentCourses;
        this.studentAttendance=studentAttendance;this.facultyAttendance=facultyAttendance;this.accounts=accounts;
    }
    public User current(){return CurrentUser.require(users);}
    public Map<String,Object> profile(){
        User u=current(); Map<String,Object> m=new LinkedHashMap<>();
        m.put("id",u.getId());m.put("fullName",u.getFullName());m.put("email",u.getEmail());
        m.put("role",u.getRole());m.put("status",u.getStatus());m.put("authProvider",u.getAuthProvider());
        return m;
    }
    public List<Course> allCourses(){return courses.findByActiveTrue();}
    @Transactional public Course createCourse(CreateCourseRequest r){
        if(courses.existsByCourseCodeIgnoreCase(r.courseCode())) throw new IllegalArgumentException("Course code already exists");
        return courses.save(new Course(r.courseCode(),r.courseName(),r.department(),r.credits()));
    }
    @Transactional public User createStudent(CreatePersonRequest r){
        return accounts.createPerson(r.fullName(),r.email(),r.password(),Role.STUDENT,r.identifier(),r.department(),r.detail());
    }
    @Transactional public User createFaculty(CreatePersonRequest r){
        return accounts.createPerson(r.fullName(),r.email(),r.password(),Role.FACULTY,r.identifier(),r.department(),r.detail());
    }
    @Transactional public FacultyCourse assignFaculty(Long facultyId,Long courseId){
        User f=users.findById(facultyId).orElseThrow(()->new IllegalArgumentException("Faculty not found"));
        if(f.getRole()!=Role.FACULTY) throw new IllegalArgumentException("Selected user is not faculty");
        Course c=courses.findById(courseId).orElseThrow(()->new IllegalArgumentException("Course not found"));
        if(facultyCourses.existsByFacultyIdAndCourseId(facultyId,courseId)) throw new IllegalArgumentException("Already assigned");
        return facultyCourses.save(new FacultyCourse(f,c));
    }
    @Transactional public StudentCourse enroll(Long courseId,String session){
        User s=current(); if(s.getRole()!=Role.STUDENT) throw new IllegalArgumentException("Student account required");
        Course c=courses.findById(courseId).orElseThrow(()->new IllegalArgumentException("Course not found"));
        if(studentCourses.existsByStudentIdAndCourseIdAndAcademicSession(s.getId(),courseId,session))
            throw new IllegalArgumentException("Already enrolled in this course and session");
        return studentCourses.save(new StudentCourse(s,c,session));
    }
    public List<StudentCourse> myCourses(){return studentCourses.findByStudentId(current().getId());}
    public List<FacultyCourse> facultyCourses(){return facultyCourses.findByFacultyId(current().getId());}
    public List<StudentCourse> studentsInCourse(Long courseId){
        User f=current();
        if(!facultyCourses.existsByFacultyIdAndCourseId(f.getId(),courseId)) throw new IllegalArgumentException("Course is not assigned to you");
        return studentCourses.findByCourseId(courseId);
    }
    @Transactional public StudentAttendance markStudentAttendance(MarkAttendanceRequest r){
        User marker=current();
        if(marker.getRole()!=Role.FACULTY) throw new IllegalArgumentException("Faculty account required");
        LocalDate date=r.attendanceDate();
        if(date.getDayOfWeek()==DayOfWeek.SATURDAY || date.getDayOfWeek()==DayOfWeek.SUNDAY)
            throw new IllegalArgumentException("Attendance cannot be marked on Saturday or Sunday");
        StudentCourse sc=studentCourses.findById(r.studentCourseId()).orElseThrow(()->new IllegalArgumentException("Enrolment not found"));
        if(!facultyCourses.existsByFacultyIdAndCourseId(marker.getId(),sc.getCourse().getId()))
            throw new IllegalArgumentException("You are not assigned to this course");
        boolean exists=studentAttendance.findByStudentCourseStudentId(sc.getStudent().getId()).stream()
            .anyMatch(a->a.getAttendanceDate().equals(date) && a.getStudentCourse().getId().equals(sc.getId()));
        if(exists) throw new IllegalArgumentException("Attendance already marked for this date");
        return studentAttendance.save(new StudentAttendance(sc,date,r.status(),marker));
    }
    @Transactional public FacultyAttendance markFacultyAttendance(AttendanceStatus status,LocalDate date){
        User f=current(); if(f.getRole()!=Role.FACULTY) throw new IllegalArgumentException("Faculty account required");
        if(date.getDayOfWeek()==DayOfWeek.SATURDAY || date.getDayOfWeek()==DayOfWeek.SUNDAY)
            throw new IllegalArgumentException("Attendance cannot be marked on Saturday or Sunday");
        if(facultyAttendance.findByFacultyId(f.getId()).stream().anyMatch(a->a.getAttendanceDate().equals(date)))
            throw new IllegalArgumentException("Attendance already marked for this date");
        return facultyAttendance.save(new FacultyAttendance(f,date,status));
    }
    public List<StudentAttendance> myStudentAttendance(){return studentAttendance.findByStudentCourseStudentId(current().getId());}
    public List<FacultyAttendance> myFacultyAttendance(){return facultyAttendance.findByFacultyId(current().getId());}
    public Map<String,Object> dashboard(){
        User u=current(); Map<String,Object> m=new LinkedHashMap<>();
        m.put("user",profile());
        switch(u.getRole()){
            case ADMIN -> {m.put("totalUsers",users.count());m.put("totalCourses",courses.count());}
            case STUDENT -> {m.put("enrolledCourses",studentCourses.findByStudentId(u.getId()).size());}
            case FACULTY -> {m.put("assignedCourses",facultyCourses.findByFacultyId(u.getId()).size());}
        }
        return m;
    }
}
