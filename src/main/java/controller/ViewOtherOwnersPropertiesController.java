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
import main.java.model.Property;
import main.java.model.PropertyView;
import main.java.model.User;

import java.io.IOException;

public class ViewOtherOwnersPropertiesController {

    @FXML
    private Label title;

    @FXML
    private ComboBox<String> searchMenu;

    @FXML
    private TextField searchTerm;

    @FXML
    private Button searchButton;

    @FXML
    private Button viewDetailsButton;

    @FXML
    private Button backButton;

    @FXML
    private TableView<PropertyView> validProperties;

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
    private TableColumn<PropertyView, Integer> visitsCol;

    @FXML
    private TableColumn<PropertyView, Integer> avgRatingCol;

    private User user;

    public void onBack() {
        backButton.getScene().getWindow().hide();
    }

    public void onDetails() {
        Parent root = null;
        FXMLLoader loader = new FXMLLoader();
        OtherPropertiesDetailsController nextController;
        try {
            loader.setLocation(getClass().getResource("/main/resources/view/OtherPropertiesDetails.fxml"));
            root = loader.load();
            nextController = loader.getController();
            nextController.loadUser(this.user);
            nextController.loadProperties(validProperties.getSelectionModel().getSelectedItem());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        Stage stage = new Stage();
        stage.setTitle("Other Property Details");
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
        visitsCol.setCellValueFactory(new PropertyValueFactory<PropertyView, Integer>("visits"));
        avgRatingCol.setCellValueFactory(new PropertyValueFactory<PropertyView, Integer>("averageRating"));

        PropertyViewServiceImpl propertyViewService = new PropertyViewServiceImpl();

        validProperties.getItems().setAll(propertyViewService.findAllOtherConfirmedOrdered("Name ASC", null, null, user.getUsername()));

        searchMenu.getItems().addAll(
                "Name",
                "Street",
                "City",
                "ID",
                "Zip",
                "Size",
                "PropertyType",
                "isPublic",
                "isCommercial",
                "Visits",
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
            visitsCol.setCellValueFactory(new PropertyValueFactory<PropertyView, Integer>("visits"));
            avgRatingCol.setCellValueFactory(new PropertyValueFactory<PropertyView, Integer>("averageRating"));

            PropertyViewServiceImpl propertyViewService = new PropertyViewServiceImpl();

            validProperties.getItems().setAll(propertyViewService.findAllOtherConfirmedOrdered("Name ASC", searchMenu.getValue(), searchTerm.getText(), user.getUsername()));
        }
    }

}

