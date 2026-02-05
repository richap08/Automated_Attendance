package com.rural.attendance.dto;

public class MonthlyAttendanceReport {

    private String studentName;
    private long totalDays;
    private long presentDays;
    private long absentDays;
    private double percentage;

    public MonthlyAttendanceReport(String studentName,
                                   long totalDays,
                                   long presentDays,
                                   long absentDays,
                                   double percentage) {
        this.studentName = studentName;
        this.totalDays = totalDays;
        this.presentDays = presentDays;
        this.absentDays = absentDays;
        this.percentage = percentage;
    }

    public String getStudentName() {
        return studentName;
    }

    public long getTotalDays() {
        return totalDays;
    }

    public long getPresentDays() {
        return presentDays;
    }

    public long getAbsentDays() {
        return absentDays;
    }

    public double getPercentage() {
        return percentage;
    }
}
