package edu.ccrm.domain;

import java.time.LocalDate;

/**
 * Enrollment linking studentId and courseCode; demonstrates immutability of id-like fields.
 */
public class Enrollment {
    private final String studentId;
    private final String courseCode;
    private LocalDate enrolledAt;
    private Grade grade;

    public Enrollment(String studentId, String courseCode) {
        this.studentId = studentId;
        this.courseCode = courseCode;
        this.enrolledAt = LocalDate.now();
    }

    public String getStudentId(){ return studentId; }
    public String getCourseCode(){ return courseCode; }
    public LocalDate getEnrolledAt(){ return enrolledAt; }
    public Grade getGrade(){ return grade; }
    public void setGrade(Grade g){ this.grade = g; }

    @Override
    public String toString() {
        return String.format("Enrollment[%s->%s | %s | Grade=%s]", studentId, courseCode, enrolledAt, grade);
    }
}
