package model; // Package declaration for the model

public class User {

    private int id; // User ID
    private String name; // User name
    private String email; // User email
    private String phone; // User phone number
    private String password; // User password
    private String status; // User status (e.g., active, inactive)

    // Getter for user status
    public String getStatus() {
        return status;
    }

    // Setter for user status
    public void setStatus(String status) {
        this.status = status;
    }

    // Getter for user ID
    public int getId() {
        return id;
    }

    // Setter for user ID
    public void setId(int id) {
        this.id = id;
    }

    // Getter for user name
    public String getName() {
        return name;
    }

    // Setter for user name
    public void setName(String name) {
        this.name = name;
    }

    // Getter for user email
    public String getEmail() {
        return email;
    }

    // Setter for user email
    public void setEmail(String email) {
        this.email = email;
    }

    // Getter for user phone number
    public String getPhone() {
        return phone;
    }

    // Setter for user phone number
    public void setPhone(String phone) {
        this.phone = phone;
    }

    // Getter for user password
    public String getPassword() {
        return password;
    }

    // Setter for user password
    public void setPassword(String password) {
        this.password = password;
    }
}
