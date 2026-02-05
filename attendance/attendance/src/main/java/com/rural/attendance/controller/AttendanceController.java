package com.rural.attendance.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rural.attendance.dto.AttendanceRequest;
import com.rural.attendance.model.Attendance;
import com.rural.attendance.model.Student;
import com.rural.attendance.repository.AttendanceRepository;
import com.rural.attendance.repository.StudentRepository;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {

    private final AttendanceRepository attendanceRepository;
    private final StudentRepository studentRepository;

    public AttendanceController(AttendanceRepository attendanceRepository,
                                StudentRepository studentRepository) {
        this.attendanceRepository = attendanceRepository;
        this.studentRepository = studentRepository;
    }

    // 1️⃣ MARK ATTENDANCE (Single student)
    @PostMapping("/mark")
    public ResponseEntity<String> markAttendance(
            @RequestParam Long studentId,
            @RequestParam String status) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));

        Attendance attendance = new Attendance();
        attendance.setStudent(student);
        attendance.setAttendanceDate(LocalDate.now());
        attendance.setStatus(status);

        attendanceRepository.save(attendance);

        return ResponseEntity.ok("Attendance marked successfully");
    }

    // 2️⃣ VIEW ATTENDANCE BY STUDENT
    @GetMapping("/student/{id}")
    public ResponseEntity<List<Attendance>> getAttendanceByStudent(
            @PathVariable Long id) {

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));

        return ResponseEntity.ok(
                attendanceRepository.findByStudent(student)
        );
    }

    // 3️⃣ VIEW ATTENDANCE BY DATE
    @GetMapping("/date")
    public ResponseEntity<List<Attendance>> getAttendanceByDate(
            @RequestParam String date) {

        LocalDate localDate = LocalDate.parse(date);
        return ResponseEntity.ok(
                attendanceRepository.findByAttendanceDate(localDate)
        );
    }

    // 4️⃣ MONTHLY REPORT (Single student)
    @GetMapping("/monthly-report")
    public ResponseEntity<Map<String, Object>> getMonthlyReport(
            @RequestParam Long studentId,
            @RequestParam int year,
            @RequestParam int month) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));

        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        List<Attendance> records =
                attendanceRepository.findByStudentAndAttendanceDateBetween(
                        student, startDate, endDate);

        long presentDays = records.stream()
                .filter(a -> "PRESENT".equalsIgnoreCase(a.getStatus()))
                .count();

        long totalDays = records.size();
        long absentDays = totalDays - presentDays;

        double percentage = totalDays == 0
                ? 0
                : (presentDays * 100.0) / totalDays;

        return ResponseEntity.ok(
                Map.of(
                        "studentName", student.getName(),
                        "month", month,
                        "year", year,
                        "totalDays", totalDays,
                        "presentDays", presentDays,
                        "absentDays", absentDays,
                        "attendancePercentage", percentage
                )
        );
    }

    // 5️⃣ MARK ATTENDANCE FOR ALL STUDENTS
    @PostMapping("/mark-all")
    public ResponseEntity<String> markAttendanceForAll(
            @RequestBody List<AttendanceRequest> attendanceList) {

        LocalDate today = LocalDate.now();

        for (AttendanceRequest req : attendanceList) {

            Student student = studentRepository.findById(req.getStudentId())
                    .orElseThrow(() -> new IllegalArgumentException("Student not found"));

            Attendance attendance = new Attendance();
            attendance.setStudent(student);
            attendance.setAttendanceDate(today);
            attendance.setStatus(req.getStatus());

            attendanceRepository.save(attendance);
        }

        return ResponseEntity.ok("Attendance marked for all students");
    }
}
