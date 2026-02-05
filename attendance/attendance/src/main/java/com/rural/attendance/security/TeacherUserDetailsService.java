package com.rural.attendance.security;

import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import com.rural.attendance.model.Teacher;
import com.rural.attendance.repository.TeacherRepository;

@Service
public class TeacherUserDetailsService implements UserDetailsService {

    private final TeacherRepository teacherRepository;

    public TeacherUserDetailsService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        Teacher teacher = teacherRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Teacher not found"));

        return User.builder()
                .username(teacher.getUsername())
                .password(teacher.getPassword())
                .roles("TEACHER")
                .build();
    }
}
