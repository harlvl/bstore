package com.lv.api;

import io.micronaut.context.annotation.Value;
import jakarta.inject.Singleton;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.lang.reflect.Array;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Data
@Slf4j
@Singleton
public class Environment {
    private static final int LABEL_PAD_LENGTH = 50;

    @Value("${database.url}")
    private String databaseUrl;

    @Value("${database.user}")
    private String databaseUser;

    @Value("${database.password}")
    private String databasePassword;

    @Value("${database.schema}")
    private String databaseSchema;

    @Value("${database.driver}")
    private String databaseDriver;


    public void logConfig() {
        StringBuilder b = new StringBuilder("========== ENVIRONMENT CONFIGURATION ==========\n");

        b.append(pad("DATABASE_URL")).append(databaseUrl).append("\n");
        b.append(pad("DATABASE_USER")).append(databaseUser).append("\n");
        b.append(pad("DATABASE_PASSWORD")).append(databasePassword).append("\n");
        b.append(pad("DATABASE_SCHEMA")).append(databaseSchema).append("\n");
        b.append(pad("DATABASE_DRIVER")).append(databaseDriver).append("\n");
        b.append(pad("Server time zone")).append(ZoneId.systemDefault().getId()).append("\n");
        b.append("====================");

        if (log.isDebugEnabled()) {
            log.debug("Initializing environment...\n{}", b);
        } else if (log.isInfoEnabled()) {
            log.info("Initializing environment...\n{}", b);
        }
    }

    public void validateConfig() {
        List<String> missing = new ArrayList<>();

        if (empty(databaseUrl)) {
            missing.add("DATABASE_URL");
        }
        if (empty(databaseUser)) {
            missing.add("DATABASE_USER");
        }
        if (empty(databasePassword)) {
            missing.add("DATABASE_PASSWORD");
        }
        if (empty(databaseSchema)) {
            missing.add("DATABASE_SCHEMA");
        }
        if (empty(databaseDriver)) {
            missing.add("DATABASE_DRIVER");
        }

        if (!missing.isEmpty()) {
            String msg = "Missing environment configuration variables: " + missing;
            log.error(msg);
            throw new IllegalArgumentException(msg);
        }
    }

    @SuppressWarnings("unchecked")
    public boolean empty(Object value) {
        if (value == null) {
            return true;
        }

        if (value instanceof String) {
            return ((String) value).trim().isEmpty();
        } else if (value instanceof Collection) {
            return ((Collection<Object>) value).isEmpty();
        } else if (value instanceof Map) {
            return ((Map<Object, Object>) value).isEmpty();
        } else if (value.getClass().isArray()) {
            return (Array.getLength(value) < 1);
        } else if (value instanceof File) {
            return !((File) value).exists();
        }

        return false;
    }

    public String pad(String value) {
        return String.format("%1$-" + LABEL_PAD_LENGTH + "s", value + ":");
    }
}
