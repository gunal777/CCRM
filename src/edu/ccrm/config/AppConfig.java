package edu.ccrm.config;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;


public final class AppConfig {
    private static final AppConfig INSTANCE = new AppConfig();
    private final Path dataFolder;

    private AppConfig() {
    
        this.dataFolder = Paths.get("data");
    }

    public static AppConfig getInstance() {
        return INSTANCE;
    }

    public Path getDataFolder() {
        return dataFolder;
    }

    @Override
    public String toString() {
        return "AppConfig{dataFolder=" + dataFolder + "}";
    }
}
