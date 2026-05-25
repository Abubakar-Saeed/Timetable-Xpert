package com.example.gui;

import java.sql.*;
import java.util.Scanner;

public class DataBaseLayer {

    protected static Connection connection; // Connection object to manage database connections
    protected static String username; // Database username
    protected static String password; // Database password
    private static String url; // Database URL
    protected Scanner sc; // Scanner object for user input
    protected PreparedStatement statement; // PreparedStatement object to execute SQL queries
    protected ResultSet resultSet; // ResultSet object to store query results

    public static Connection getConnection() {
        return connection;
    }

    public static void setConnection(Connection connection) {
        DataBaseLayer.connection = connection;
    }

    public DataBaseLayer() {


    }

    // Method to establish database connection
    public static Connection connect() {


        String database = "time_table_automation";

        DataBaseLayer.url =  "jdbc:mysql://localhost:3306/" + database; // Set database URL
        DataBaseLayer.username = "root"; // Set database username
        DataBaseLayer.password = "root"; // Set database password

        try {

            connection = DriverManager.getConnection(url, username, password); // Establish database connection
            return connection; // Return true if connection successful

        } catch (SQLException e) {
            System.out.println(e.getMessage()); // Print SQL error message
            return null; // Return false if connection failed
        }
    }

    // Method to disconnect from the database
    public static boolean disconnect() {
        try {
            if (connection != null && !connection.isClosed()) { // Check if connection is not null and not closed
                connection.close(); // Close the database connection
            }
            return true; // Return true if disconnection successful
        } catch (SQLException e) {
            System.out.println(e.getMessage()); // Print SQL error message
            return false; // Return false if disconnection failed
        }
    }

    // Method to insert data into the database
    public static boolean insert(PreparedStatement statement) {

        try {
            int rowsInserted = statement.executeUpdate(); // Execute insert query
            return rowsInserted > 0; // Return true if rows inserted
        } catch (SQLException e) {
            System.out.println(e.getMessage()); // Print SQL error message
            return false; // Return false if insert failed
        }
    }

    // Method to update data in the database
    public static boolean update(PreparedStatement statement) {

        try {
            int rowsUpdated = statement.executeUpdate(); // Execute update query
            return rowsUpdated > 0; // Return true if rows updated
        } catch (SQLException e) {
            System.out.println(e.getMessage()); // Print SQL error message
            return false; // Return false if update failed
        }
    }

    // Method to delete data from the database
    public static boolean delete(PreparedStatement statement) {
        try {
            int rowsDeleted = statement.executeUpdate(); // Execute delete query
            return rowsDeleted > 0; // Return true if rows deleted
        } catch (SQLException e) {
            System.out.println(e.getMessage()); // Print SQL error message
            return false; // Return false if delete failed
        }
    }

    // Method to retrieve data from the database
    public static ResultSet retrieve(PreparedStatement statement) {
        try {
            return statement.executeQuery(); // Return result set
        } catch (SQLException e) {
            System.out.println(e.getMessage()); // Print SQL error message
            return null; // Return null if retrieval failed
        }
    }

}
