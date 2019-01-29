package main.java.model;

public class OwnerView {
    private String username;
    private String email;
    private int numOfProperties;

    public OwnerView(String username, String email, int numOfProperties) {
        this.username = username;
        this.email = email;
        this.numOfProperties = numOfProperties;
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

    public int getNumOfProperties() {
        return numOfProperties;
    }

    public void setNumOfProperties(int numOfProperties) {
        this.numOfProperties = numOfProperties;
    }
}
