package edu.ccrm.domain;

import java.util.Objects;

/**
 * Course class with a nested static Builder to demonstrate Builder pattern.
 */
public class Course {
    private final String code;
    private final String title;
    private final int credits;
    private String instructorId;
    private final Semester semester;
    private final String department;

    private Course(Builder b) {
        this.code = b.code;
        this.title = b.title;
        this.credits = b.credits;
        this.instructorId = b.instructorId;
        this.semester = b.semester;
        this.department = b.department;
    }

    public String getCode() { return code; }
    public String getTitle() { return title; }
    public int getCredits() { return credits; }
    public String getInstructorId() { return instructorId; }
    public void setInstructorId(String id) { instructorId = id; }
    public Semester getSemester() { return semester; }
    public String getDepartment() { return department; }

    @Override
    public String toString() {
        return String.format("%s - %s (%dcr) [%s]", code, title, credits, semester);
    }

    public static class Builder {
        private final String code;
        private String title = "";
        private int credits = 3;
        private String instructorId;
        private Semester semester = Semester.FALL;
        private String department = "General";

        public Builder(String code) {
            this.code = Objects.requireNonNull(code);
        }
        public Builder title(String t){ this.title = t; return this; }
        public Builder credits(int c){ this.credits = c; return this; }
        public Builder instructor(String id){ this.instructorId = id; return this; }
        public Builder semester(Semester s){ this.semester = s; return this; }
        public Builder department(String d){ this.department = d; return this; }
        public Course build(){ return new Course(this); }
    }
}
