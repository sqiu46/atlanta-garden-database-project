package main.java.model;

public class User {
    private String username;
    private String email;

    public enum UserType {
        ADMIN, OWNER, VISITOR
    };

    private UserType userType;
    private String password;

    public User(String username, String email, String password, UserType type) {
        this.username = username;
        this.email = email;
        this.userType = type;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType type) {
        this.userType = type;
    }
}
