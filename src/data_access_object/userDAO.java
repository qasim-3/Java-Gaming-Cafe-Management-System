package data_access_object;

import javax.swing.JOptionPane; // Import for dialog messages
import model.User; // Import User model
import java.sql.*; // Import SQL classes
import java.util.ArrayList; // Import ArrayList for storing user records

public class userDAO {

    // Method to save a new user to the database
    public static void save(User user) {
        String query = "insert into user(name, email, password, phone, status) values('"
                + user.getName() + "','" + user.getEmail() + "','" + user.getPassword() + "','"
                + user.getPhone() + "', '" + user.getStatus() + "')";
        DatabaseOperations.setDataOrDelete(query, "Registred Successfully!"); // Execute query and show message
    }

    // Method to authenticate a user based on email and password
    public static User login(String email, String password) {
        User user = null; // Initialize user object
        try {
            ResultSet rs = DatabaseOperations.getData("select * from user where email='" + email + "' and password='" + password + "'");
            while (rs.next()) { // Iterate through result set
                user = new User(); // Create new User object
                user.setStatus(rs.getString("status")); // Set user status
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e); // Show error message
        }
        return user; // Return user object or null
    }

    // Method to retrieve all user records matching a given email pattern
    public static ArrayList<User> getAllRecords(String email) {
        ArrayList<User> arrayList = new ArrayList<>(); // Initialize ArrayList for users
        try {
            ResultSet rs = DatabaseOperations.getData("select * from user where email like '%" + email + "%'");
            while (rs.next()) { // Iterate through result set
                User user = new User(); // Create new User object
                user.setId(rs.getInt("id")); // Set user ID
                user.setName(rs.getString("name")); // Set user name
                user.setEmail(rs.getString("email")); // Set user email
                user.setPhone(rs.getString("phone")); // Set user phone
                user.setStatus(rs.getString("status")); // Set user status
                arrayList.add(user); // Add user to the list
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e); // Show error message
        }
        return arrayList; // Return list of users
    }

    // Method to change the status of a user based on their email
    public static void changeStatus(String email, String status) {
        String query = "UPDATE user SET status = '" + status + "' WHERE email = '" + email + "'";
        DatabaseOperations.setDataOrDelete(query, "Status changed successfully for user: " + email); // Execute query and show message
    }
}
