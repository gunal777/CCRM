package edu.ccrm.domain;

/**
 * Simple Instructor class.
 */
public class Instructor extends Person {
    private String department;

    public Instructor(String id, String name, String email, String department) {
        super(id, name, email);
        this.department = department;
    }

    public String getDepartment() { return department; }
    public void setDepartment(String d) { department = d; }

    @Override
    public String profile() {
        return String.format("Instructor: %s | Dept: %s", fullName, department);
    }
}
