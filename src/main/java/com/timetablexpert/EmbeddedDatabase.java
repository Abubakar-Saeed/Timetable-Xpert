package com.timetablexpert;

import ch.vorburger.mariadb4j.DBConfigurationBuilder;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;

/**
 * Boots a private MariaDB engine that ships inside the application, so the user
 * never has to install MySQL or run {@code time_table_automation_backup.sql} by
 * hand.
 *
 * <p>On the very first launch the engine binaries are unpacked once into
 * {@code %LOCALAPPDATA%\TimetableXpert\engine}, a fresh data directory is
 * initialised under {@code ...\TimetableXpert\data}, the {@code time_table_automation}
 * schema (tables + views + the timetable-generation stored procedures) is imported
 * from the bundled {@code db/schema.sql}, and a default {@code admin / admin} login
 * is seeded. Every later launch just restarts the same data directory, so all data
 * the user entered is still there.
 */
public final class EmbeddedDatabase {

    public static final String DB_NAME = "time_table_automation";
    private static final String SCHEMA_RESOURCE = "db/schema.sql";

    private static PersistentDB db;
    private static String jdbcUrl;
    private static int port;

    private EmbeddedDatabase() {
    }

    public static synchronized boolean isRunning() {
        return db != null && jdbcUrl != null;
    }

    public static synchronized String jdbcUrl() {
        return jdbcUrl;
    }

    public static String user() {
        return "root";
    }

    public static String password() {
        return "";
    }

    /**
     * Starts the engine and provisions the schema if needed. Safe to call more
     * than once; only the first call does work.
     */
    public static synchronized void start() throws Exception {
        if (db != null) {
            return;
        }

        Path appDir = appDataDir();
        Path dataDir = appDir.resolve("data");
        Path engineDir = appDir.resolve("engine");
        Path tmpDir = appDir.resolve("tmp");
        Files.createDirectories(dataDir);
        Files.createDirectories(engineDir);
        Files.createDirectories(tmpDir);

        // If a previous run did not shut its server down cleanly (window closed
        // hard, crash, kill), a stray mysqld can still be holding the data files
        // locked -> the new server then fails with "ibdata1 must be writable".
        // Reap it and clear any read-only bits before starting.
        reapStaleServer(dataDir);
        makeWritable(dataDir);

        DBConfigurationBuilder config = DBConfigurationBuilder.newBuilder();
        config.setPort(0);                          // 0 => pick a free TCP port
        config.setDataDir(dataDir.toFile());        // persists between runs
        config.setBaseDir(engineDir.toFile());      // binaries unpacked here once
        config.setLibDir(tmpDir.toFile());

        boolean alreadyInitialised = Files.isDirectory(dataDir.resolve("mysql"));
        db = PersistentDB.create(config.build(), alreadyInitialised);
        db.start();

        port = config.getPort();
        jdbcUrl = "jdbc:mariadb://127.0.0.1:" + port + "/" + DB_NAME;

        String serverUrl = "jdbc:mariadb://127.0.0.1:" + port + "/";
        try (Connection c = DriverManager.getConnection(serverUrl, user(), password());
             Statement s = c.createStatement()) {
            s.executeUpdate("CREATE DATABASE IF NOT EXISTS `" + DB_NAME
                    + "` CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci");
        }

        if (schemaMissing()) {
            db.source(SCHEMA_RESOURCE, user(), password(), DB_NAME);
        }

        Runtime.getRuntime().addShutdownHook(new Thread(EmbeddedDatabase::stop, "embedded-db-stop"));
    }

    private static boolean schemaMissing() {
        try (Connection c = DriverManager.getConnection(jdbcUrl, user(), password());
             Statement s = c.createStatement();
             ResultSet rs = s.executeQuery(
                     "SELECT COUNT(*) FROM information_schema.tables "
                             + "WHERE table_schema = '" + DB_NAME + "' AND table_name = 'admintable'")) {
            return rs.next() && rs.getInt(1) == 0;
        } catch (Exception e) {
            // If we cannot tell, assume it needs provisioning - the script is
            // idempotent (DROP TABLE IF EXISTS ... / CREATE) on a fresh DB.
            return true;
        }
    }

    public static synchronized void stop() {
        if (db != null) {
            try {
                db.stop();
            } catch (Exception ignored) {
                // best effort on shutdown
            }
            db = null;
            jdbcUrl = null;
        }
    }

    /**
     * Kill a leftover engine process from a previous run. MariaDB writes a
     * {@code <host>.pid} file into the data directory while it runs and removes it
     * on a clean shutdown; if one is still there, the PID inside it may still be
     * alive and locking the data files.
     */
    private static void reapStaleServer(Path dataDir) {
        if (!Files.isDirectory(dataDir)) {
            return;
        }
        try (DirectoryStream<Path> pids = Files.newDirectoryStream(dataDir, "*.pid")) {
            for (Path pidFile : pids) {
                try {
                    String first = Files.readString(pidFile).trim().split("\\s+")[0];
                    long pid = Long.parseLong(first);
                    ProcessHandle.of(pid).ifPresent(ph -> {
                        String cmd = ph.info().command().orElse("").toLowerCase();
                        if (cmd.contains("mysqld") || cmd.contains("mariadbd")) {
                            ph.destroyForcibly();
                            try {
                                ph.onExit().get(15, TimeUnit.SECONDS);
                            } catch (Exception ignored) {
                                // proceed anyway
                            }
                        }
                    });
                } catch (Exception ignored) {
                    // unreadable / not a number / already gone
                }
                try {
                    Files.deleteIfExists(pidFile);
                } catch (IOException ignored) {
                    // will be overwritten by the new server
                }
            }
        } catch (IOException ignored) {
            // no data dir yet, or cannot list - nothing to reap
        }
    }

    /** Clear the read-only attribute on every file under the data directory. */
    private static void makeWritable(Path dir) {
        if (!Files.exists(dir)) {
            return;
        }
        try (var walk = Files.walk(dir)) {
            walk.forEach(p -> {
                try {
                    p.toFile().setWritable(true, false);
                } catch (RuntimeException ignored) {
                    // best effort
                }
            });
        } catch (IOException | UncheckedIOException ignored) {
            // best effort
        }
    }

    private static Path appDataDir() {
        String localAppData = System.getenv("LOCALAPPDATA");
        if (localAppData != null && !localAppData.isBlank()) {
            return Paths.get(localAppData, "TimetableXpert");
        }
        return Paths.get(System.getProperty("user.home"), ".timetablexpert");
    }
}
