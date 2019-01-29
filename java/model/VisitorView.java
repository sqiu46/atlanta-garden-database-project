package main.java.model;

public class VisitorView {
    private String username;
    private String email;
    private int loggedVisits;


    public VisitorView(String username, String email, int loggedVisits) {
        this.username = username;
        this.email = email;
        this.loggedVisits = loggedVisits;
    }

    public int getLoggedVisits() {
        return loggedVisits;
    }

    public void setLoggedVisits(int loggedVisits) {
        this.loggedVisits = loggedVisits;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
