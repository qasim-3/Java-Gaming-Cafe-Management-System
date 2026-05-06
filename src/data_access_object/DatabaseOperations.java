package data_access_object;

import javax.swing.JOptionPane; // Importing JOptionPane for displaying messages
import java.sql.*; // Importing SQL classes for database operations

public class DatabaseOperations {

    // Method to execute an update or delete operation on the database
    public static void setDataOrDelete(String query, String message) {
        try {
            // Establish a connection to the database
            Connection con = ConnectionProvider.getCon();
            Statement st = con.createStatement();

            // Execute the provided SQL query (update or delete)
            st.executeUpdate(query);

            // If a message is provided, display it in a dialog
            if (!message.equals("")) {
                JOptionPane.showMessageDialog(null, message);
            }
        } catch (Exception e) {
            // If an exception occurs, display the error message
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to execute a query and return the result set
    public static ResultSet getData(String query) {
        try {
            // Establish a connection to the database
            Connection con = ConnectionProvider.getCon();
            Statement st = con.createStatement();

            // Execute the provided SQL query and return the result set
            ResultSet rs = st.executeQuery(query);
            return rs;
        } catch (Exception e) {
            // If an exception occurs, display the error message
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
            return null; // Return null if an error occurs
        }
    }
}
