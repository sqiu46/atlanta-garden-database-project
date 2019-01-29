package main.java.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import main.java.model.User;

import java.io.IOException;

public class AdminDefaultViewController {

    private User me;

    @FXML
    private Label title;

    @FXML
    private Button visitorListButton;

    @FXML
    private Button ownerListButton;

    @FXML
    private Button confirmedPropButton;

    @FXML
    private Button unconfirmedPropButton;

    @FXML
    private Button ApprovedItemsButton;

    @FXML
    private Button pendingItemsButton;

    @FXML
    private Button logOutButton;

    private User user;

    @FXML
    public void setTitle(String name) {
        title.setText("Welcome " + name);
    }

    public void onClickVisitorsList() {
        Parent root = null;
        FXMLLoader loader;
        ViewAllVisitorsController nextController;

        try {
            loader = new FXMLLoader(getClass().getResource("/main/resources/view/ViewAllVisitors.fxml"));
            root = loader.load();
            nextController = loader.getController();

            nextController.loadUser(this.user);
            nextController.loadVisitors();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        Stage stage = new Stage();
        stage.setTitle("View Visitors List");
        stage.setScene(new Scene(root, 750, 600));
        stage.show();
    }

    public void onClickOwnersList() {
        Parent root = null;
        FXMLLoader loader;
        ViewAllOwnersController nextController;

        try {
            loader = new FXMLLoader(getClass().getResource("/main/resources/view/ViewAllOwners.fxml"));
            root = loader.load();
            nextController = loader.getController();


            if (nextController == null) {
                System.out.println("TEST");
            }

            nextController.loadUser(this.user);
            nextController.loadOwners();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        Stage stage = new Stage();
        stage.setTitle("View Owners List");
        stage.setScene(new Scene(root, 750, 600));
        stage.show();

    }

    public void onConfirmedProperties() {
        Parent root = null;
        FXMLLoader loader;
        ConfirmedPropertiesController nextController;
        try {
            loader = new FXMLLoader(getClass().getResource("/main/resources/view/ConfirmedProperties.fxml"));
            root = loader.load();
            nextController = loader.getController();

            nextController.loadUser(this.user);
            nextController.loadProperties();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        Stage stage = new Stage();
        stage.setTitle("View Confirmed Properties");
        stage.setScene(new Scene(root, 750, 600));
        stage.show();

    }

    public void onUnconfirmedProperties() {
        Parent root = null;
        FXMLLoader loader;
        UnconfirmedPropertiesController nextController;
        try {
            loader = new FXMLLoader(getClass().getResource("/main/resources/view/UnconfirmedProperties.fxml"));
            root = loader.load();
            nextController = loader.getController();

            nextController.loadUser(this.user);
            nextController.loadProperties();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        Stage stage = new Stage();
        stage.setTitle("View Unconfirmed Properties");
        stage.setScene(new Scene(root, 750, 600));
        stage.show();

    }

    public void onApproved() {
        Parent root = null;
        FXMLLoader loader;
        ApprovedAnimalsCropsController nextController;

        try {
            loader = new FXMLLoader(getClass().getResource("/main/resources/view/ApprovedAnimalsCrops.fxml"));
            root = loader.load();
            nextController = loader.getController();

            nextController.loadUser(this.user);
            nextController.loadFarmItems();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        Stage stage = new Stage();
        stage.setTitle("Approved Animals and Crops");
        stage.setScene(new Scene(root, 750, 600));
        stage.show();

    }

    public void onPending() {
        Parent root = null;
        FXMLLoader loader;
        PendingAnimalCropsController nextController;

        try {
            loader = new FXMLLoader(getClass().getResource("/main/resources/view/PendingAnimalsCrops.fxml"));
            root = loader.load();
            nextController = loader.getController();

            nextController.loadUser(this.user);
            nextController.loadFarmItems();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        Stage stage = new Stage();
        stage.setTitle("Pending Animals and Crops");
        stage.setScene(new Scene(root, 750, 600));
        stage.show();
    }

    public void loadUser(User user) {
        this.user = user;
    }

    public void onLogout() {
        logOutButton.getScene().getWindow().hide();
    }
}

