package com.timetablexpert;

import java.sql.*;
import java.util.Scanner;

/**
 * Thin JDBC helper shared by the controllers.
 *
 * <p>Connection handling notes (bug fix):
 * <ul>
 *   <li>{@link #connect()} is now idempotent - it hands back the existing open
 *       connection instead of opening a brand new one every time a controller
 *       method runs. The old code re-opened on every call and never closed the
 *       previous handle, so a long session leaked connections until MySQL hit
 *       {@code max_connections}.</li>
 *   <li>{@link #retrieve(PreparedStatement)} and the DML helpers no longer swallow
 *       {@link SQLException} and return {@code null}/{@code false}. Callers used the
 *       result immediately ({@code resultSet.next()}), so a swallowed error became
 *       an opaque {@link NullPointerException}. They now throw
 *       {@link DataAccessException} with the real cause.</li>
 *   <li>Connection settings come from {@link EmbeddedDatabase} when the bundled
 *       server is running, otherwise from the {@code db.url} / {@code db.user} /
 *       {@code db.password} system properties, otherwise from the legacy localhost
 *       defaults - so a developer with their own MySQL can still point at it.</li>
 * </ul>
 */
public class DataBaseLayer {

    protected static Connection connection;
    protected static String username;
    protected static String password;
    private static String url;

    /** Kept only so the legacy console helpers in {@code Session} still compile. */
    protected Scanner sc;
    protected PreparedStatement statement;
    protected ResultSet resultSet;

    public DataBaseLayer() {
    }

    public static Connection getConnection() {
        return connect();
    }

    public static void setConnection(Connection connection) {
        DataBaseLayer.connection = connection;
    }

    private static void resolveSettings() {
        if (EmbeddedDatabase.isRunning()) {
            url = EmbeddedDatabase.jdbcUrl();
            username = EmbeddedDatabase.user();
            password = EmbeddedDatabase.password();
            return;
        }
        url = System.getProperty("db.url", "jdbc:mysql://localhost:3306/time_table_automation");
        username = System.getProperty("db.user", "root");
        password = System.getProperty("db.password", "root");
    }

    /**
     * Returns a live connection, opening one only if there is not already an open
     * one. Returns {@code null} only if the connection could not be established.
     */
    public static Connection connect() {
        try {
            if (connection != null && !connection.isClosed()) {
                return connection;
            }
        } catch (SQLException ignored) {
            // fall through and re-open
        }

        resolveSettings();
        try {
            connection = DriverManager.getConnection(url, username, password);
            return connection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            connection = null;
            return null;
        }
    }

    public static boolean disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
            connection = null;
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static boolean insert(PreparedStatement statement) {
        try {
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataAccessException("Insert failed", e);
        }
    }

    public static boolean update(PreparedStatement statement) {
        try {
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataAccessException("Update failed", e);
        }
    }

    public static boolean delete(PreparedStatement statement) {
        try {
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataAccessException("Delete failed", e);
        }
    }

    public static ResultSet retrieve(PreparedStatement statement) {
        try {
            return statement.executeQuery();
        } catch (SQLException e) {
            throw new DataAccessException("Query failed", e);
        }
    }
}
