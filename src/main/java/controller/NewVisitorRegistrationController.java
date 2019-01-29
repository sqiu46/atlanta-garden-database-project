package main.java.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.java.model.User;
import main.java.db.*;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.security.MessageDigest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewVisitorRegistrationController {

    @FXML
    private TextField emailField;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPassField;

    @FXML
    private Button registerButton;

    @FXML
    private Button cancelButton;

    public void onClickRegister() {
        try {
            User x = null;
            User y = null;
            MessageDigest md = MessageDigest.getInstance("MD5");
            UserDAOImpl userDAO = new UserDAOImpl();

            String email = emailField.getText();
            String password = (new HexBinaryAdapter()).marshal(md.digest(passwordField.getText().
                    getBytes("UTF-8")));
            String username = usernameField.getText();
            String conf_pass = (new HexBinaryAdapter().marshal(md.digest(confirmPassField.getText().
                    getBytes("UTF-8"))));

            x = userDAO.findByUsername(username);
            y = userDAO.findByEmail(email);

            Pattern emailPat = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
            Matcher mat = emailPat.matcher(email);
            if (x != null || y != null) {
                //User exists
                System.out.println("User already exists!");
            } else if (!password.equals(conf_pass))    {
                System.out.println("Pass doesnt match");
            } else if (password.length() < 8) {
                System.out.println("Password too short");
            } else if (!mat.matches())  {
                System.out.println("Email not valid");
            } else  {
                // Add user table
                User newUser = new User(username, email, password, User.UserType.VISITOR);
                userDAO.insertUser(newUser);
                System.out.println("New User Added");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void onClickCancel() {
        cancelButton.getScene().getWindow().hide();

    }

}

