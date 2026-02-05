package com.rural.attendance.dto;

public class AttendanceRequest {

    private Long studentId;
    private String status; // PRESENT or ABSENT

    public AttendanceRequest() {
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
