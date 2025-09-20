package edu.ccrm.cli;

import edu.ccrm.config.AppConfig;
import edu.ccrm.domain.*;
import edu.ccrm.service.*;
import edu.ccrm.io.*;
import java.util.*;
import java.nio.file.Paths;

public class MainCLI {
    private static final Scanner sc = new Scanner(System.in);
    private static final StudentService stSvc = new StudentService();
    private static final DataStore store = DataStore.getInstance();
    private static final ImportExportService ioSvc = new ImportExportService();
    private static final EnrollmentService enrSvc = new EnrollmentService();
    private static final TranscriptService trSvc = new TranscriptService();
    private static final BackupService backupSvc = new BackupService();

    public static void main(String[] args) {
        System.out.println("Yo! CCRM up and running. Config: " + AppConfig.getInstance());
        seedStuff();
        boolean exit = false;
        while (!exit) {
            showMenu();
            String opt = sc.nextLine().trim();
            switch (opt) {
                case "1" -> studentsMenu();
                case "2" -> coursesMenu();
                case "3" -> enrollMenu();
                case "4" -> gradeMenu();
                case "5" -> exportAll();
                case "6" -> backupStuff();
                case "7" -> reportsMenu();
                case "0" -> exit = true;
                default -> System.out.println("Oops, wrong choice!");
            }
        }
        System.out.println("Bye!");
    }

    private static void showMenu() {
        System.out.println("\n--- CCRM Menu ---");
        System.out.println("1. Students");
        System.out.println("2. Courses");
        System.out.println("3. Enroll/Drop");
        System.out.println("4. Grades");
        System.out.println("5. Export stuff");
        System.out.println("6. Backup");
        System.out.println("7. Reports");
        System.out.println("0. Exit");
        System.out.print("Pick one: ");
    }

    private static void studentsMenu() {
        boolean back = false;
        do {
            System.out.println("[Students] 1:Add 2:List 3:Import Test 4:Transcript 0:Back");
            String c = sc.nextLine().trim();
            switch (c) {
                case "1" -> {
                    System.out.print("id: "); String id = sc.nextLine().trim();
                    System.out.print("regNo: "); String reg = sc.nextLine().trim();
                    System.out.print("name: "); String name = sc.nextLine().trim();
                    System.out.print("email: "); String email = sc.nextLine().trim();
                    Student s = new Student(id, reg, name, email);
                    stSvc.addStudent(s);
                    System.out.println("Cool, added: " + s);
                }
                case "2" -> store.students().values().forEach(System.out::println);
                case "3" -> {
                    try {
                        ioSvc.importStudents(Paths.get("test-data/students.csv"));
                        System.out.println("Imported test students!");
                    } catch (Exception e) { e.printStackTrace(); }
                }
                case "4" -> {
                    System.out.print("studentId: "); String sid = sc.nextLine().trim();
                    System.out.println(trSvc.transcriptString(sid));
                }
                case "0" -> back = true;
                default -> System.out.println("Nope, try again!");
            }
        } while(!back);
    }

    private static void coursesMenu() {
        boolean back = false;
        do {
            System.out.println("[Courses] 1:Add 2:List 3:Import Test 0:Back");
            String c = sc.nextLine().trim();
            switch (c) {
                case "1" -> {
                    System.out.print("code: "); String code = sc.nextLine().trim();
                    System.out.print("title: "); String title = sc.nextLine().trim();
                    System.out.print("credits: "); int cr = Integer.parseInt(sc.nextLine().trim());
                    System.out.print("dept: "); String dept = sc.nextLine().trim();
                    var course = new Course.Builder(code).title(title).credits(cr).department(dept).build();
                    store.courses().put(course.getCode(), course);
                    System.out.println("Added: " + course);
                }
                case "2" -> store.courses().values().forEach(System.out::println);
                case "3" -> {
                    try {
                        ioSvc.importCourses(Paths.get("test-data/courses.csv"));
                        System.out.println("Courses imported.");
                    } catch (Exception e) { e.printStackTrace(); }
                }
                case "0" -> back = true;
                default -> System.out.println("Nope!");
            }
        } while(!back);
    }

    private static void enrollMenu() {
        System.out.println("1:Enroll 2:Drop 3:List 0:Back");
        String c = sc.nextLine().trim();
        switch(c) {
            case "1" -> {
                System.out.print("studentId: "); String s = sc.nextLine().trim();
                System.out.print("courseCode: "); String co = sc.nextLine().trim();
                try { enrSvc.enroll(s, co); System.out.println("Enrolled!"); }
                catch (Exception e) { System.out.println("Oops: " + e.getMessage()); }
            }
            case "2" -> {
                System.out.print("studentId: "); String s = sc.nextLine().trim();
                System.out.print("courseCode: "); String co = sc.nextLine().trim();
                enrSvc.unenroll(s, co); System.out.println("Dropped if existed.");
            }
            case "3" -> {
                System.out.print("studentId: "); String s = sc.nextLine().trim();
                enrSvc.listEnrollmentsForStudent(s).forEach(System.out::println);
            }
            case "0" -> {}
            default -> System.out.println("Bad pick");
        }
    }

    private static void gradeMenu() {
        System.out.print("studentId: "); String s = sc.nextLine().trim();
        System.out.print("courseCode: "); String co = sc.nextLine().trim();
        System.out.print("marks (0-100): "); int m = Integer.parseInt(sc.nextLine().trim());
        try { enrSvc.recordMarks(s, co, m); System.out.println("Done!"); }
        catch (Exception e) { System.out.println("Failed: " + e.getMessage()); }
    }

    private static void exportAll() {
        try {
            ioSvc.exportStudents(Paths.get("exports/students.csv"));
            ioSvc.exportCourses(Paths.get("exports/courses.csv"));
            ioSvc.exportEnrollments(Paths.get("exports/enrollments.csv"));
            System.out.println("Exports done!");
        } catch (Exception e) { e.printStackTrace(); }
    }

    private static void backupStuff() {
        try {
            var p = backupSvc.backupDirectory(Paths.get("exports"), Paths.get("backups"));
            System.out.println("Backup at: " + p + ", size=" + backupSvc.computeDirectorySize(p));
        } catch (Exception e) { e.printStackTrace(); }
    }

    private static void reportsMenu() {
        System.out.println("1:Top GPA 2:GPA distribution 0:Back");
        String c = sc.nextLine().trim();
        switch(c) {
            case "1" -> store.students().values().stream()
                .collect(java.util.stream.Collectors.toMap(s->s.getId(), s->trSvc.computeGPA(s.getId())))
                .entrySet().stream()
                .sorted(Map.Entry.<String,Double>comparingByValue().reversed())
                .limit(10)
                .forEach(e -> System.out.println(store.students().get(e.getKey()).getFullName() + " -> " + String.format("%.2f", e.getValue())));
            case "2" -> {
                var dist = store.students().values().stream().collect(
                    java.util.stream.Collectors.groupingBy(s -> {
                        double g = trSvc.computeGPA(s.getId());
                        if(g>=9) return "9-10";
                        if(g>=8) return "8-9";
                        if(g>=7) return "7-8";
                        if(g>=6) return "6-7";
                        if(g>=5) return "5-6";
                        return "<5";
                    }, java.util.stream.Collectors.counting())
                );
                System.out.println("GPA dist:");
                dist.forEach((k,v)-> System.out.println(k + ": " + v));
            }
            case "0" -> {}
            default -> System.out.println("Try again!");
        }
    }

    private static void seedStuff() {
        if(store.courses().isEmpty()) {
            var c1 = new Course.Builder("CS101").title("Intro to CS").credits(4).department("CSE").build();
            var c2 = new Course.Builder("MA101").title("Calculus").credits(3).department("Math").build();
            store.courses().put(c1.getCode(), c1);
            store.courses().put(c2.getCode(), c2);
        }

        if(store.students().isEmpty()) {
            var s1 = new Student("s1", "REG2025/001", "Aman Sharma", "aman@example.com");
            var s2 = new Student("s2", "REG2025/002", "Alice Smith", "alice@example.com");
            store.students().put(s1.getId(), s1);
            store.students().put(s2.getId(), s2);
        }
    }
}
