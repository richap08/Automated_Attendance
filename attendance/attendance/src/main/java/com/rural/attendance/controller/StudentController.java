package com.rural.attendance.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rural.attendance.model.Student;
import com.rural.attendance.repository.StudentRepository;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    // 1️⃣ Add new student
    @PostMapping("/add")
    public Student addStudent(@RequestBody Student student) {
        return studentRepository.save(student);
    }

    // 2️⃣ Get all students
    @GetMapping("/all")
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id) {

        if (!studentRepository.existsById(id)) {
            return ResponseEntity.badRequest().body("Student not found");
        }

        studentRepository.deleteById(id);
        return ResponseEntity.ok("Student deleted successfully");
    }

}
