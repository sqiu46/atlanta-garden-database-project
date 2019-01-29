package main.java.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.java.db.UserDAOImpl;
import main.java.model.User;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.acl.Owner;

public class UserLoginWindowController {

    public static User me;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button newOwnerButton;

    @FXML
    private Button newVisitorButton;

    public void onClickLogin() {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            UserDAOImpl userDAO = new UserDAOImpl();

            String email = emailField.getText();
            String password = (new HexBinaryAdapter()).marshal(md.digest(passwordField.getText().
                    getBytes("UTF-8")));

            User user = userDAO.findByEmail(email);
            Stage stage;
            Parent root = null;

            if (user.getPassword().equalsIgnoreCase(password)) {
                me = user;
                FXMLLoader loader;
                switch (user.getUserType()) {
                    case ADMIN:
                        loader = new FXMLLoader(getClass().getResource("/main/resources/view/AdminDefaultView.fxml"));
                        stage = new Stage();
                        stage.setTitle("Admin View");
                        try {
                            stage.setScene(new Scene((Pane) loader.load(), 750, 600));
                        } catch (IOException e) {
                            System.out.println(e.getMessage());
                        }
                        AdminDefaultViewController adc = loader.<AdminDefaultViewController>getController();
                        adc.loadUser(user);
                        adc.setTitle(me.getUsername());
                        stage.show();
                        break;
                    case OWNER:
                        loader = new FXMLLoader(getClass().getResource("/main/resources/view/OwnerDefaultView.fxml"));
                        stage = new Stage();
                        stage.setTitle("Owner View");
                        try {
                            stage.setScene(new Scene((Pane) loader.load(), 750, 600));
                        } catch (IOException e) {
                            System.out.println(e.getMessage());
                        }
                        OwnerDefaultViewController controller = loader.<OwnerDefaultViewController>getController();
                        controller.setOwnerName("Welcome " + me.getUsername());
                        controller.loadUser(user);
                        controller.loadProperties();
                        stage.show();
                        break;
                    case VISITOR:
                        loader = new FXMLLoader(getClass().getResource("/main/resources/view/VisitorDefaultView.fxml"));
                        stage = new Stage();
                        stage.setTitle("Visitor View");
                        try {
                            stage.setScene(new Scene((Pane) loader.load(), 750, 600));
                        } catch (IOException e) {
                            System.out.println(e.getMessage());
                        }

                        VisitorDefaultViewController vc = loader.getController();
                        vc.loadUser(user);
                        vc.loadProperties();
                        vc.setTitle(me.getUsername());
                        stage.show();
                        break;
                }
            } else {
                Stage error = new Stage();
                error.setTitle("Error");
                error.show();
                System.out.println("Incorrect Login Credentials");
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }

    public void onClickNewOwnerRegistration() {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/main/resources/view/NewOwnerRegistration.fxml"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        Stage stage = new Stage();
        stage.setTitle("New Visitor Registration");
        stage.setScene(new Scene(root, 750, 600));
        stage.show();
    }

    public void onClickNewVisitorRegistration() {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/main/resources/view/NewVisitorRegistration.fxml"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        Stage stage = new Stage();
        stage.setTitle("New Visitor Registration");
        stage.setScene(new Scene(root, 750, 600));
        stage.show();
    }

}

