package com.rural.attendance.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.rural.attendance.model.Student;
import com.rural.attendance.repository.StudentRepository;

@Controller
@RequestMapping("/ui/students")
public class StudentUIController {

    private final StudentRepository studentRepository;

    public StudentUIController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // Show student page
    @GetMapping
    public String showStudents(Model model) {
        List<Student> students = studentRepository.findAll();
        model.addAttribute("students", students);
        return "students";
    }

    // Handle form submit
    @PostMapping("/add")
    public String addStudent(Student student) {
        studentRepository.save(student);
        return "redirect:/ui/students";
    }
}
