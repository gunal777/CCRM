package edu.ccrm.service;

import edu.ccrm.domain.Student;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Minimal StudentService demonstrating listing, add, update, search using Streams.
 */
public class StudentService {
    private final DataStore store = DataStore.getInstance();

    public void addStudent(Student s) {
        store.students().put(s.getId(), s);
    }

    public List<Student> listAll() {
        return new ArrayList<>(store.students().values());
    }

    public Optional<Student> findByRegNo(String regNo) {
        return store.students().values().stream()
                .filter(s -> regNo.equals(s.getRegNo()))
                .findFirst();
    }
}
