package edu.ccrm.service;

import edu.ccrm.domain.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * TranscriptService computes GPA and prints transcripts.
 */
public class TranscriptService {
    private final DataStore store = DataStore.getInstance();

    public double computeGPA(String studentId) {
        List<Enrollment> ens = store.enrollments().stream()
                .filter(e -> e.getStudentId().equals(studentId) && e.getGrade() != null)
                .collect(Collectors.toList());
        if (ens.isEmpty()) return 0.0;
        double totalPoints = 0;
        int totalCredits = 0;
        for (Enrollment e : ens) {
            Course c = store.courses().get(e.getCourseCode());
            if (c == null) continue;
            int credits = c.getCredits();
            int gp = e.getGrade().getPoints();
            totalPoints += gp * credits;
            totalCredits += credits;
        }
        return totalCredits == 0 ? 0.0 : totalPoints / totalCredits;
    }

    public String transcriptString(String studentId) {
        Student s = store.students().get(studentId);
        if (s == null) return "Student not found";
        StringBuilder sb = new StringBuilder();
        sb.append("Transcript for: ").append(s.getFullName()).append("\n");
        var ens = store.enrollments().stream()
                .filter(e -> e.getStudentId().equals(studentId))
                .collect(Collectors.toList());
        ens.forEach(e -> {
            Course c = store.courses().get(e.getCourseCode());
            String line = String.format("%s | %s | Credits: %d | Grade: %s\n",
                    e.getCourseCode(),
                    c != null ? c.getTitle() : "(course missing)",
                    c != null ? c.getCredits() : 0,
                    e.getGrade() != null ? e.getGrade().name() : "-");
            sb.append(line);
        });
        double gpa = computeGPA(studentId);
        sb.append(String.format("GPA: %.2f\n", gpa));
        return sb.toString();
    }
}
