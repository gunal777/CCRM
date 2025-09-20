package edu.ccrm.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Student class demonstrating encapsulation, immutability for id, and enrolled courses list.
 */
public class Student extends Person {
    private final String regNo;
    private boolean active = true;
    private final List<String> enrolledCourseCodes = new ArrayList<>();
    private LocalDate dob;

    public Student(String id, String regNo, String fullName, String email) {
        super(id, fullName, email);
        this.regNo = regNo;
    }

    public String getRegNo() { return regNo; }
    public boolean isActive() { return active; }
    public void deactivate() { this.active = false; }
    public List<String> getEnrolledCourseCodes() { return enrolledCourseCodes; }

    public void enroll(String courseCode) {
        if (!enrolledCourseCodes.contains(courseCode)) {
            enrolledCourseCodes.add(courseCode);
        }
    }

    public void unenroll(String courseCode) {
        enrolledCourseCodes.remove(courseCode);
    }

    @Override
    public String profile() {
        return String.format("Student: %s | RegNo: %s | Active: %s", fullName, regNo, active);
    }

    @Override
    public String toString() {
        return profile();
    }
}
