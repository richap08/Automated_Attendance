package com.rural.attendance.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rural.attendance.model.Attendance;
import com.rural.attendance.model.Student;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    // Get attendance of a student
    List<Attendance> findByStudent(Student student);

    // Get attendance by date
    List<Attendance> findByAttendanceDate(LocalDate attendanceDate);
    
    List<Attendance> findByStudentAndAttendanceDateBetween(
            Student student,
            LocalDate startDate,
            LocalDate endDate
    );
}
