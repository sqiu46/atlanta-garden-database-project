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
import main.java.db.PropertyDAOImpl;
import main.java.db.PropertyViewServiceImpl;
import main.java.model.Property;
import main.java.model.PropertyView;
import main.java.model.User;

import javax.swing.text.View;
import java.io.IOException;

public class UnconfirmedPropertiesController {

    @FXML
    private Label title;

    @FXML
    private TableView<Property> unconfirmedProps;

    @FXML
    private TableColumn<Property, String> nameCol;

    @FXML
    private  TableColumn<Property, String> addressCol;

    @FXML
    private  TableColumn<Property, String> cityCol;

    @FXML
    private TableColumn<Property, Integer> zipCol;

    @FXML
    private TableColumn<Property, Float> sizeCol;

    @FXML
    private TableColumn<Property, String> typeCol;

    @FXML
    private TableColumn<Property, Boolean> publicCol;

    @FXML
    private TableColumn<Property, Boolean> commercialCol;

    @FXML
    private TableColumn<Property, Integer> idCol;

    @FXML
    private TableColumn<Property, String> ownerCol;

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

    public void onManage() {
        Parent root = null;
        FXMLLoader loader = new FXMLLoader();
        AdminManagePropertiesController nextController;
        try {
            loader.setLocation(getClass().getResource("/main/resources/view/AdminManageProperties.fxml"));
            root = loader.load();
            nextController = loader.getController();
            nextController.loadUser(this.user);
            nextController.loadProperties(unconfirmedProps.getSelectionModel().getSelectedItem());
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
        nameCol.setCellValueFactory(new PropertyValueFactory<Property, String>("name"));
        addressCol.setCellValueFactory(new PropertyValueFactory<Property, String>("street"));
        cityCol.setCellValueFactory(new PropertyValueFactory<Property, String>("city"));
        zipCol.setCellValueFactory(new PropertyValueFactory<Property, Integer>("zipcode"));
        sizeCol.setCellValueFactory(new PropertyValueFactory<Property, Float>("size"));
        typeCol.setCellValueFactory(new PropertyValueFactory<Property, String>("propertyType"));
        publicCol.setCellValueFactory(new PropertyValueFactory<Property, Boolean>("isPublic"));
        commercialCol.setCellValueFactory(new PropertyValueFactory<Property, Boolean>("isCommercial"));
        idCol.setCellValueFactory(new PropertyValueFactory<Property, Integer>("id"));
        ownerCol.setCellValueFactory(new PropertyValueFactory<Property, String>("ownerUsername"));

        PropertyDAOImpl propertyDAO = new PropertyDAOImpl();

        unconfirmedProps.getItems().setAll(propertyDAO.findUnapprovedOrdered("Name ASC", null, null));

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
                "Owner");
    }

    public void onSearch() {
        if (searchTerm.getText() != null && !searchMenu.getSelectionModel().isEmpty()) {
            nameCol.setCellValueFactory(new PropertyValueFactory<Property, String>("name"));
            addressCol.setCellValueFactory(new PropertyValueFactory<Property, String>("street"));
            cityCol.setCellValueFactory(new PropertyValueFactory<Property, String>("city"));
            zipCol.setCellValueFactory(new PropertyValueFactory<Property, Integer>("zipcode"));
            sizeCol.setCellValueFactory(new PropertyValueFactory<Property, Float>("size"));
            typeCol.setCellValueFactory(new PropertyValueFactory<Property, String>("propertyType"));
            publicCol.setCellValueFactory(new PropertyValueFactory<Property, Boolean>("isPublic"));
            commercialCol.setCellValueFactory(new PropertyValueFactory<Property, Boolean>("isCommercial"));
            idCol.setCellValueFactory(new PropertyValueFactory<Property, Integer>("id"));
            ownerCol.setCellValueFactory(new PropertyValueFactory<Property, String>("ownerUsername"));

            PropertyDAOImpl propertyDAO = new PropertyDAOImpl();

            unconfirmedProps.getItems().setAll(propertyDAO.findUnapprovedOrdered("Name ASC", searchMenu.getValue(), searchTerm.getText()));
        }
    }
}

