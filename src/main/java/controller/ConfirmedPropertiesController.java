package main.java.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import main.java.db.PropertyViewServiceImpl;
import main.java.model.PropertyView;
import main.java.model.User;

import java.io.IOException;

public class ConfirmedPropertiesController {

    @FXML
    private Label title;

    @FXML
    private TableView<PropertyView> confirmedProperties;

    @FXML
    private TableColumn<PropertyView, String> nameCol;

    @FXML
    private TableColumn<PropertyView, String> addressCol;

    @FXML
    private TableColumn<PropertyView, String> cityCol;

    @FXML
    private TableColumn<PropertyView, Integer> zipCol;

    @FXML
    private TableColumn<PropertyView, Float> sizeCol;

    @FXML
    private TableColumn<PropertyView, String> typeCol;

    @FXML
    private TableColumn<PropertyView, Boolean> publicCol;

    @FXML
    private TableColumn<PropertyView, Boolean> commercialCol;

    @FXML
    private TableColumn<PropertyView, Integer> idCol;

    @FXML
    private TableColumn<PropertyView, String> verifiedCol;

    @FXML
    private TableColumn<PropertyView, Integer> avgRatingCol;

    @FXML
    private ComboBox<String> searchMenu;

    @FXML
    private TextField searchTerm;

    @FXML
    private Button searchButton;

    @FXML
    private Button manageButton;

    @FXML
    private Button backButton;

    private User user;

    public void onBack() {
        backButton.getScene().getWindow().hide();
    }

    public void onManageProperty() {
        Parent root = null;
        FXMLLoader loader = new FXMLLoader();
        AdminManagePropertiesController nextController;
        try {
            loader.setLocation(getClass().getResource("/main/resources/view/AdminManageProperties.fxml"));
            root = loader.load();
            nextController = loader.getController();
            nextController.loadUser(this.user);
            nextController.loadProperties(confirmedProperties.getSelectionModel().getSelectedItem());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        Stage stage = new Stage();
        stage.setTitle("Manage Property");
        stage.setScene(new Scene(root, 750, 600));
        stage.show();
    }

    public void loadUser(User user) {
        this.user = user;
    }

    public void loadProperties() {
        nameCol.setCellValueFactory(new PropertyValueFactory<PropertyView, String>("name"));
        addressCol.setCellValueFactory(new PropertyValueFactory<PropertyView, String>("street"));
        cityCol.setCellValueFactory(new PropertyValueFactory<PropertyView, String>("city"));
        zipCol.setCellValueFactory(new PropertyValueFactory<PropertyView, Integer>("zipcode"));
        sizeCol.setCellValueFactory(new PropertyValueFactory<PropertyView, Float>("size"));
        typeCol.setCellValueFactory(new PropertyValueFactory<PropertyView, String>("propertyType"));
        publicCol.setCellValueFactory(new PropertyValueFactory<PropertyView, Boolean>("isPublic"));
        commercialCol.setCellValueFactory(new PropertyValueFactory<PropertyView, Boolean>("isCommercial"));
        idCol.setCellValueFactory(new PropertyValueFactory<PropertyView, Integer>("id"));
        verifiedCol.setCellValueFactory(new PropertyValueFactory<PropertyView, String>("approverUsername"));
        avgRatingCol.setCellValueFactory(new PropertyValueFactory<PropertyView, Integer>("averageRating"));

        PropertyViewServiceImpl propertyViewService = new PropertyViewServiceImpl();

        confirmedProperties.getItems().setAll(propertyViewService.findAllConfirmedOrdered("Name ASC", null, null));

        searchMenu.getItems().addAll(
                "Name",
                "ID",
                "Street",
                "City",
                "Zip",
                "Size",
                "PropertyType",
                "isPublic",
                "isCommercial",
                "ApprovedBy",
                "Avg_Rating");
    }

    public void onSearch() {
        if (searchTerm.getText() != null && !searchMenu.getSelectionModel().isEmpty()) {
            nameCol.setCellValueFactory(new PropertyValueFactory<PropertyView, String>("name"));
            addressCol.setCellValueFactory(new PropertyValueFactory<PropertyView, String>("street"));
            cityCol.setCellValueFactory(new PropertyValueFactory<PropertyView, String>("city"));
            zipCol.setCellValueFactory(new PropertyValueFactory<PropertyView, Integer>("zipcode"));
            sizeCol.setCellValueFactory(new PropertyValueFactory<PropertyView, Float>("size"));
            typeCol.setCellValueFactory(new PropertyValueFactory<PropertyView, String>("propertyType"));
            publicCol.setCellValueFactory(new PropertyValueFactory<PropertyView, Boolean>("isPublic"));
            commercialCol.setCellValueFactory(new PropertyValueFactory<PropertyView, Boolean>("isCommercial"));
            idCol.setCellValueFactory(new PropertyValueFactory<PropertyView, Integer>("id"));
            verifiedCol.setCellValueFactory(new PropertyValueFactory<PropertyView, String>("approverUsername"));
            avgRatingCol.setCellValueFactory(new PropertyValueFactory<PropertyView, Integer>("averageRating"));

            PropertyViewServiceImpl propertyViewService = new PropertyViewServiceImpl();

            confirmedProperties.getItems().setAll(propertyViewService.findAllConfirmedOrdered("Name ASC", searchMenu.getValue(), searchTerm.getText()));
        }
    }
}

