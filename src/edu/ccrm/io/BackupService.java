package edu.ccrm.io;

import java.nio.file.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

/**
 * BackupService creates timestamped backups for exported files/folders and computes recursive size.
 */
public class BackupService {
    private static final DateTimeFormatter TF = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    public Path backupDirectory(Path source, Path targetRoot) throws IOException {
        String ts = LocalDateTime.now().format(TF);
        Path dest = targetRoot.resolve("backup_" + ts);
        Files.createDirectories(dest);
        // copy recursively
        if (Files.isDirectory(source)) {
            try (Stream<Path> paths = Files.walk(source)) {
                paths.forEach(p -> {
                    try {
                        Path rel = source.relativize(p);
                        Path dst = dest.resolve(rel.toString());
                        if (Files.isDirectory(p)) Files.createDirectories(dst);
                        else Files.copy(p, dst);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                });
            }
        } else {
            Files.copy(source, dest.resolve(source.getFileName()));
        }
        return dest;
    }

    public long computeDirectorySize(Path path) throws IOException {
        try (Stream<Path> paths = Files.walk(path)) {
            return paths.filter(p -> Files.isRegularFile(p)).mapToLong(p -> {
                try { return Files.size(p); } catch (Exception ex) { return 0L; }
            }).sum();
        }
    }
}
