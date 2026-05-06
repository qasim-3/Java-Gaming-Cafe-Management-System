package data_access_object;

import javax.swing.JOptionPane;

public class Tables {

    public static void main(String[] args) {
        try {
            // SQL Query to drop the existing user table if it exists
            String dropUserTable = "DROP TABLE IF EXISTS user";
            DatabaseOperations.setDataOrDelete(dropUserTable, "Old User Table Dropped Successfully!");

            // SQL Query to create the user table with the status column set to 'false' by default
            String userTable = "CREATE TABLE user ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "name VARCHAR(200) NOT NULL, "
                    + "email VARCHAR(200) UNIQUE NOT NULL, "
                    + "password VARCHAR(100) NOT NULL, "
                    + "phone VARCHAR(20) NOT NULL, "
                    + "status VARCHAR(50) DEFAULT 'false', " // Default status is 'false' [USERS WILL BE APPROVED BY ADMIN]
                    + "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
                    + ")";
            // Corrected the INSERT statement
            String adminDetails = "INSERT INTO user (name, email, password, phone, status) VALUES ('admin', 'admin@gmail.com', 'admin', '0123456789', 'true')";

            // Execute queries
            DatabaseOperations.setDataOrDelete(userTable, "User Table Created Successfully");
            DatabaseOperations.setDataOrDelete(adminDetails, "Admin Details Added Successfully");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
}
