package edu.ccrm.io;

import edu.ccrm.service.DataStore;
import edu.ccrm.domain.*;
import java.nio.file.*;
import java.io.IOException;
import java.util.stream.*;

/**
 * ImportExportService: NIO.2 & Streams for students, courses, enrollments.
 */
public class ImportExportService {
    private final DataStore ds = DataStore.getInstance();

    public void exportStudents(Path out) throws IOException {
        Files.createDirectories(out.getParent());
        try (Stream<String> lines = ds.students().values().stream()
                .map(s -> String.join(",", s.getId(), s.getRegNo(), s.getFullName(), s.getEmail()))) {
            Files.write(out, (Iterable<String>)lines::iterator);
        }
    }

    public void exportCourses(Path out) throws IOException {
        Files.createDirectories(out.getParent());
        try (Stream<String> lines = ds.courses().values().stream()
                .map(c -> String.join(",", c.getCode(), c.getTitle(), String.valueOf(c.getCredits()), c.getDepartment(), c.getSemester().name(), c.getInstructorId()==null?"":c.getInstructorId()))) {
            Files.write(out, (Iterable<String>)lines::iterator);
        }
    }

    public void exportEnrollments(Path out) throws IOException {
        Files.createDirectories(out.getParent());
        try (Stream<String> lines = ds.enrollments().stream()
                .map(e -> String.join(",", e.getStudentId(), e.getCourseCode(), e.getEnrolledAt().toString(), e.getGrade()==null?"":e.getGrade().name()))) {
            Files.write(out, (Iterable<String>)lines::iterator);
        }
    }

    // simple imports for test-data students and courses
    public void importStudents(Path in) throws IOException {
        try (Stream<String> lines = Files.lines(in)) {
            lines.forEach(l -> {
                String[] p = l.split(",");
                if (p.length>=4) {
                    Student s = new Student(p[0], p[1], p[2], p[3]);
                    ds.students().put(s.getId(), s);
                }
            });
        }
    }

    public void importCourses(Path in) throws IOException {
        try (Stream<String> lines = Files.lines(in)) {
            lines.forEach(l -> {
                String[] p = l.split(",");
                if (p.length>=3) {
                    Course c = new Course.Builder(p[0]).title(p[1]).credits(Integer.parseInt(p[2])).build();
                    ds.courses().put(c.getCode(), c);
                }
            });
        }
    }
}
