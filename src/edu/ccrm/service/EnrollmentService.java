package edu.ccrm.service;

import edu.ccrm.domain.*;
import edu.ccrm.service.exceptions.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * EnrollmentService handles enroll/unenroll and grade recording.
 */
public class EnrollmentService {
    private final DataStore store = DataStore.getInstance();
    private final int maxCreditsPerSemester = 18; // business rule

    // enroll student in course by IDs/Code
    public void enroll(String studentId, String courseCode) throws DuplicateEnrollmentException, MaxCreditLimitExceededException {
        Objects.requireNonNull(studentId);
        Objects.requireNonNull(courseCode);
        // check existence
        Student s = store.students().get(studentId);
        Course c = store.courses().get(courseCode);
        if (s == null) throw new IllegalArgumentException("Student not found: " + studentId);
        if (c == null) throw new IllegalArgumentException("Course not found: " + courseCode);
        // duplicate check
        boolean already = store.enrollments().stream()
                .anyMatch(e -> e.getStudentId().equals(studentId) && e.getCourseCode().equals(courseCode));
        if (already) throw new DuplicateEnrollmentException("Already enrolled in " + courseCode);
        // check credit limit for semester
        int existingCredits = store.enrollments().stream()
                .filter(e -> e.getStudentId().equals(studentId))
                .map(e -> store.courses().get(e.getCourseCode()))
                .filter(Objects::nonNull)
                .mapToInt(Course::getCredits)
                .sum();
        if (existingCredits + c.getCredits() > maxCreditsPerSemester) {
            throw new MaxCreditLimitExceededException("Enrolling " + courseCode + " exceeds max credits (" + maxCreditsPerSemester + ")");
        }
        Enrollment en = new Enrollment(studentId, courseCode);
        store.enrollments().add(en);
        // also update student's enrolled list
        s.enroll(courseCode);
    }

    public void unenroll(String studentId, String courseCode) {
        store.enrollments().removeIf(e -> e.getStudentId().equals(studentId) && e.getCourseCode().equals(courseCode));
        Student s = store.students().get(studentId);
        if (s != null) s.unenroll(courseCode);
    }

    public void recordMarks(String studentId, String courseCode, int marks) {
        Optional<Enrollment> oe = store.enrollments().stream()
                .filter(e -> e.getStudentId().equals(studentId) && e.getCourseCode().equals(courseCode))
                .findFirst();
        if (oe.isEmpty()) throw new IllegalArgumentException("Not enrolled: " + studentId + " in " + courseCode);
        Enrollment e = oe.get();
        Grade g = mapMarksToGrade(marks);
        e.setGrade(g);
    }

    private Grade mapMarksToGrade(int marks) {
        if (marks >= 90) return Grade.S;
        if (marks >= 80) return Grade.A;
        if (marks >= 70) return Grade.B;
        if (marks >= 60) return Grade.C;
        if (marks >= 50) return Grade.D;
        if (marks >= 40) return Grade.E;
        return Grade.F;
    }

    public List<Enrollment> listEnrollmentsForStudent(String studentId) {
        return store.enrollments().stream()
                .filter(e -> e.getStudentId().equals(studentId))
                .collect(Collectors.toList());
    }
}
