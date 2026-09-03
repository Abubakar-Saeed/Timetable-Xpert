package com.timetablexpert;
import java.sql.SQLException;
import java.util.LinkedList;

public class Session extends DataBaseLayer{

    String title;
    int id;

    LinkedList<String> sessions;

    public Session() {

        sessions = new LinkedList<String>();
    }

    public Session(int id,String name){

        this.id = id;
        this.title = name;
    }

    public String getSessionName(){

        return title;
    }
    public int getSessionID(){

        return id;
    }

    public void addSession() {

        System.out.println("\t\t================================================");
        System.out.println("\t\t\t\t   Sessions");
        System.out.println("\t\t================================================\n");
        System.out.print("\t\t\tEnter Session Name: ");
        title = sc.nextLine(); // Declare and initialize title variable

        try {

            // Prepare SQL statement to check if the session already exists
            statement = DataBaseLayer.connection.prepareStatement("SELECT * FROM SessionTable WHERE title = ?");
            statement.setString(1, title); // Set the session title in the query

            // Execute the query to check if the session already exists
            resultSet = retrieve(statement);

            if (resultSet.next()) {

                // If a session with the given title already exists, inform the user and return
                System.out.println("This session already exists. Please add another.");
                return;
            } else {

                // If the session does not exist, insert it into the SessionTable
                statement = DataBaseLayer.connection.prepareStatement("INSERT INTO SessionTable (title) VALUES (?)");
                statement.setString(1, title); // Set the session title in the insert query
                insert(statement);
                System.out.println("Session added successfully.");
            }
        } catch (SQLException e) {

            throw new RuntimeException("Error while adding session: " + e.getMessage(), e);
        }
    }

    public void updateSession() {

        System.out.println("\t\t================================================");
        System.out.println("\t\t\t\t   Sessions");
        System.out.println("\t\t================================================\n");
        System.out.print("\t\t\tEnter Session title to Update: ");
        String tempSession = sc.nextLine();

        try {

            // Prepare SQL statement to check if the session already exists
            statement = DataBaseLayer.connection.prepareStatement("SELECT * FROM SessionTable WHERE title = ?");
            statement.setString(1, tempSession); // Set the session title in the query

            // Execute the query to check if the session already exists
            resultSet = retrieve(statement);

            if (resultSet.next()) {

                System.out.print("\t\t\tEnter Updated Session: ");
                title = sc.nextLine();

                statement = DataBaseLayer.connection.prepareStatement("Update  SessionTable set title = ? where title = ? ");
                statement.setString(1, title); // Set the session title in the insert query
                statement.setString(2, tempSession); // previous title
                statement.executeUpdate(); // Execute the insert query
                System.out.println("\t\t\tUpdated Successfully.");

            } else {

                System.out.println("Session not exist nothing to update."); // session not found

            }
        } catch (SQLException e) {

            throw new RuntimeException("Error while adding session: " + e.getMessage(), e);
        }


    }

    public void searchSession() {

        System.out.println("\t\t================================================");
        System.out.println("\t\t\t\t   Sessions");
        System.out.println("\t\t================================================\n");
        System.out.print("\t\t\tEnter Session title to Search: ");
        title = sc.nextLine();

        try {

            // Prepare SQL statement to check if the session  exists
            statement = DataBaseLayer.connection.prepareStatement("select * from SessionTable WHERE title = ?");
            statement.setString(1, title); // Set the session title in the query
            // Execute the query to check if the session already exists
            resultSet = retrieve(statement);

            if (resultSet.next()) {
                // if matched session is found
                System.out.println("Found Successfully.. ");
                System.out.println("\tSession ID: " + resultSet.getInt(1) + "\n\tSession Title: " + resultSet.getString(2));

            } else {

                System.out.println("Session not exist nothing to update."); // session not found

            }
        } catch (SQLException e) {

            throw new RuntimeException("Error while adding session: " + e.getMessage(), e);
        }

    }

    public void deleteSession() {

        System.out.println("\t\t================================================");
        System.out.println("\t\t\t\t   Sessions");
        System.out.println("\t\t================================================\n");
        System.out.print("\t\t\tEnter Session title to delete: ");
        title = sc.nextLine();

        try {

            // Prepare SQL statement to check if the session already exists
            statement = DataBaseLayer.connection.prepareStatement("SELECT * FROM SessionTable WHERE title = ?");
            statement.setString(1, title); // Set the session title in the query
            statement.execute();
            // Execute the query to check if the session already exists
            resultSet = retrieve(statement);

            if (resultSet.next()) {

                // if matched session is found delete this
                statement = DataBaseLayer.connection.prepareStatement("delete from SessionTable WHERE title = ?");
                statement.setString(1, title); // Set the session title in the query
                delete(statement);
                System.out.println("Deleted Successfully.. ");

            } else {

                System.out.println("Session not exist nothing to update."); // session not found

            }
        } catch (SQLException e) {

            throw new RuntimeException("Error while adding session: " + e.getMessage(), e);
        }

    }

    public void displaySessions() {


        try {

            statement = DataBaseLayer.connection.prepareStatement("Select * from SessionTable"); // retreive all data
            resultSet = retrieve(statement);

            while (resultSet.next()) {

                sessions.add(String.valueOf(resultSet.getInt(1)));
                sessions.add(resultSet.getString(2));

            }

            if (!sessions.isEmpty()) {

                System.out.println("\t\t================================================");
                System.out.println("\t\t\t\t   Sessions List");
                System.out.println("\t\t================================================\n");

                while (!sessions.isEmpty()) {

                    System.out.println("\t\t\tSession ID: " + sessions.pop());
                    System.out.println("\t\t\tSession Title: " + sessions.pop());
                    System.out.println("\t\t\t-----------------------------------------");

                }
            } else {

                System.out.println("No session exist to display...");
            }


        } catch (SQLException e) {

            throw new RuntimeException("Error while adding session: " + e.getMessage(), e);
        }


    }
}
