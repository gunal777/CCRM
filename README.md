# Campus Course & Records Manager (CCRM) - Completed Starter Project

This repository contains a complete Java SE console application for the CCRM assignment.
It demonstrates required concepts: packages, OOP, enums, Singleton & Builder patterns,
NIO.2 usage, Streams, custom exceptions, basic CLI, backup utilities, and transcript/GPA computation.

## Build & Run (tested with JDK 11+ / OpenJDK 17)

From project root:

```bash
# compile (on Linux/Mac). On Windows adapt the find command or use an IDE.
javac -d out -sourcepath src $(find src -name "*.java")
# run
java -cp out edu.ccrm.cli.MainCLI
```

Or import into Eclipse as a Java project and run `edu.ccrm.cli.MainCLI`.

## What was implemented

- Domain: `Person` (abstract), `Student`, `Instructor`, `Course` (Builder), `Enrollment`, `Semester`, `Grade` enums.
- Services: `StudentService`, `EnrollmentService`, `TranscriptService`, `DataStore` (Singleton).
- IO: `ImportExportService` (NIO.2 + Streams), `BackupService` (timestamped backup + recursive size).
- Exceptions: `DuplicateEnrollmentException`, `MaxCreditLimitExceededException`.
- CLI: `MainCLI` supports student/course management, enrollments, grading, export, backup, reports.
- Test data under `test-data/` and quick import.
- README includes instructions and placeholders for screenshots.

## Project Layout

See `src/edu/ccrm/*` packages: cli, domain, service, io, util (placeholders), config.

## Java Evolution Timeline

- **1995**: Java 1.0 - Initial release by Sun Microsystems
- **1997**: Java 1.1 - Inner classes, JavaBeans, JDBC
- **1998**: Java 1.2 (J2SE) - Collections Framework, Swing
- **2000**: Java 1.3 - HotSpot JVM, JavaSound
- **2002**: Java 1.4 - Assertions, Regular expressions, NIO
- **2004**: Java 5.0 - Generics, Annotations, Enums, Autoboxing
- **2006**: Java 6 - Performance improvements, Scripting support
- **2011**: Java 7 - Diamond operator, Try-with-resources, Switch on strings
- **2014**: Java 8 - Lambda expressions, Stream API, Default methods
- **2017**: Java 9 - Module system, JShell
- **2018**: Java 10 - Local variable type inference (var)
- **2018**: Java 11 - HTTP Client API, String methods
- **2019**: Java 12 - Switch expressions (preview)
- **2019**: Java 13 - Text blocks (preview)
- **2020**: Java 14 - Pattern matching for instanceof (preview)
- **2020**: Java 15 - Text blocks (standard), Sealed classes (preview)
- **2021**: Java 16 - Pattern matching for instanceof, Records
- **2021**: Java 17 - Sealed classes (standard), Strong encapsulation
- **2022**: Java 18 - Simple web server, Code snippets in Javadoc
- **2022**: Java 19 - Virtual threads (preview), Pattern matching for switch (preview)
- **2023**: Java 20 - Scoped values (incubator), Pattern matching for switch (fourth preview)
- **2023**: Java 21 - Virtual threads, Pattern matching for switch (standard)

## Java ME vs SE vs EE (short)

- Java ME: Micro Edition for embedded/mobile devices.
- Java SE: Standard Edition for desktop/server applications (this project).
- Java EE / Jakarta EE: Enterprise APIs for web & distributed systems (servlets, EJB, JPA).

## JDK vs JRE vs JVM

- JDK: Java Development Kit — compiler + tools.
- JRE: Java Runtime Environment — JVM + core libraries (for running apps).
- JVM: Java Virtual Machine — executes bytecode, platform-specific.

## Errors vs Exceptions in Java

In Java, both **Errors** and **Exceptions** inherit from `Throwable`, but they serve very different purposes:

| Aspect | Errors | Exceptions |
|--------|--------|------------|
| Definition | Serious problems that occur at runtime, typically not recoverable. | Conditions that disrupt normal program flow but can often be caught and handled. |
| Examples | `OutOfMemoryError`, `StackOverflowError`, `InternalError`. | `IOException`, `NullPointerException`, `SQLException`. |
| Typical Cause | Issues with the JVM or system environment (hardware, memory exhaustion, JVM corruption). | Issues with application logic or external resources (invalid input, missing file, DB connection failure). |
| Recovery | Usually **not** recoverable — the program should not attempt to continue. | Often recoverable by catching and handling the exception gracefully. |
| Handling | Rarely caught in code; JVM generally terminates the program. | Caught using `try` / `catch` / `finally` or propagated with `throws`. |

**Key takeaway:**  

- **Errors** are beyond the application’s control and should not be handled explicitly.  
- **Exceptions** are expected runtime anomalies that *should* be handled to keep the program stable.  

## Screenshots

Setup and execution screenshots are provided in the [screenshots/](screenshots/) folder:

- `jdk-version.png` — JDK installation verification (`java -version`).  
- `program-menus.png` — CLI menu display.  
- `program-sample-ops.png` — Sample operations (add/list/enroll/grade).  
- `exports-backups.png` — Exported files and timestamped backup folders.  

## Mapping (example)

- Enums, Grade & Semester -> `src/edu/ccrm/domain/Grade.java`, `Semester.java`.
- Singleton AppConfig/DataStore -> `src/edu/ccrm/config/AppConfig.java`, `src/edu/ccrm/service/DataStore.java`.
- Builder Pattern -> `src/edu/ccrm/domain/Course.java` (Builder nested class).
- NIO.2 & Streams -> `src/edu/ccrm/io/ImportExportService.java` / `BackupService.java`.
- Custom exceptions -> `src/edu/ccrm/service/exceptions`.

## Acknowledgements

Starter code generated to help accelerate development. Replace sample data & screenshots with your own before submission.