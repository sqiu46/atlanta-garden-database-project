package main.java.controller;

import com.sun.glass.ui.View;
import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.java.db.PropertyViewServiceImpl;
import main.java.model.Property;
import main.java.model.PropertyView;
import main.java.model.User;

import java.io.IOException;
import java.security.acl.Owner;

public class OwnerDefaultViewController {

    private User me;

    @FXML
    private Label title;

    @FXML
    private Label propertyName;

    @FXML
    private Label ownerName;

    @FXML
    private Label ownerEmail;

    @FXML
    private Label numVisits;

    @FXML
    private Label address;

    @FXML
    private Label city;

    @FXML
    private Label zip;

    @FXML
    private Label size;

    @FXML
    private Label avgRating;

    @FXML
    private Label type;

    @FXML
    private Label isPublic;

    @FXML
    private Label isCommercial;

    @FXML
    private Label id;

    @FXML
    private Label cropList;

    @FXML
    private Label animalList;

    @FXML
    private Button logOutButton;

    @FXML
    private ComboBox<String> searchMenu;

    @FXML
    private TextField searchTerm;

    @FXML
    private Button searchButton;

    @FXML
    private Button manageButton;

    @FXML
    private Button addPropertyButton;

    @FXML
    private Button ViewOtherPropButton;

    @FXML
    private TableView<PropertyView> ownersProperties;

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

    @FXML
    private TableColumn<PropertyView, Boolean> isValidCol;

    private User user;


    public void onLogOut() {
        logOutButton.getScene().getWindow().hide();
    }


    public void loadProperties(){
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
        isValidCol.setCellValueFactory(new PropertyValueFactory<PropertyView, Boolean>("isValid"));

        PropertyViewServiceImpl propertyViewService = new PropertyViewServiceImpl();

        ownersProperties.getItems().setAll(propertyViewService.findByOwnerOrdered("Name ASC", null, null, user.getUsername()));

        searchMenu.getItems().addAll(
                "Name",
                "Street",
                "City",
                "Zip",
                "Size",
                "PropertyType",
                "isPublic",
                "isCommercial",
                "ID",
                "IsValid",
                "Visits",
                "Avg_Rating");
    }

    @FXML
    public void setOwnerName(String name) {
        ownerName.setText(name);
    }

    public void onAddProperty() {
        Parent root = null;
        FXMLLoader loader = new FXMLLoader();
        AddNewPropertyController nextController;
        try {
            loader.setLocation(getClass().getResource("/main/resources/view/AddNewProperty.fxml"));
            root = loader.load();
            nextController = loader.getController();
            nextController.loadUser(this.user);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        Stage stage = new Stage();
        stage.setTitle("Add New Property");
        stage.setScene(new Scene(root, 750, 600));
        stage.show();
    }

    public void onViewOthers() {
        Parent root = null;
        FXMLLoader loader = new FXMLLoader();
        ViewOtherOwnersPropertiesController nextController;
        try {
            loader.setLocation(getClass().getResource("/main/resources/view/ViewOtherOwnersProperties.fxml"));
            root = loader.load();
            nextController = loader.getController();

            nextController.loadUser(this.user);
            nextController.loadProperties();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        Stage stage = new Stage();
        stage.setTitle("View Other Owners' Properties");
        stage.setScene(new Scene(root, 750, 600));
        stage.show();
    }

    public void onManageProperty() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/resources/view/OwnerManageProperties.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Manage Property");
        try {
            stage.setScene(new Scene((Pane) loader.load(), 750, 600));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        OwnerManagePropertiesController omp = loader.<OwnerManagePropertiesController>getController();
        omp.setP(ownersProperties.getSelectionModel().getSelectedItem(), this.user);
        stage.show();
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
            isValidCol.setCellValueFactory(new PropertyValueFactory<PropertyView, Boolean>("isValid"));

            PropertyViewServiceImpl propertyViewService = new PropertyViewServiceImpl();

            ownersProperties.getItems().setAll(propertyViewService.findByOwnerOrdered("Name ASC", searchMenu.getValue(), searchTerm.getText(), user.getUsername()));
        }
    }

    public void loadUser(User user) {
        this.user = user;
    }

}

