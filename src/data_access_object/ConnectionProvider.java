package data_access_object;

import java.sql.*; // Importing SQL classes for database connectivity

public class ConnectionProvider {

    // Method to establish and return a connection to the database
    public static Connection getCon() {
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish a connection to the database
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/gcms?useSSL=false", // Database URL
                    "root", // Database username
                    "" // Database password [ENTER LOCAL SQL PASSWORD]
            );
            return con; // Return the established connection
        } catch (Exception e) {
            // If an exception occurs, return null (connection failed)
            return null;
        }
    }
}
