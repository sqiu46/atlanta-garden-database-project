package main.java.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.java.db.FarmItemDAOImpl;
import main.java.db.HasDAOImpl;
import main.java.db.PropertyDAOImpl;
import main.java.model.*;

import java.util.ArrayList;
import java.util.List;

public class OwnerManagePropertiesController {

    @FXML
    private Label title;

    @FXML
    private TextField nameField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField cityField;

    @FXML
    private TextField zipField;

    @FXML
    private TextField sizeField;

    @FXML
    private Label type;

    @FXML
    private ComboBox<String> publicMenu;

    @FXML
    private ComboBox<String> commercialMenu;

    @FXML
    private Label id;

    @FXML
    private Label cropList;

    @FXML
    private ComboBox<String> approvedCropMenu;

    @FXML
    private Button addCropButton;

    @FXML
    private TextField cropName;

    @FXML
    private ComboBox<FarmItem.FarmItemType> cropTypeMenu;

    @FXML
    private Button submitButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button saveButton;

    @FXML
    private Button backButton;


    private User u;
    private PropertyView p;
    private HasDAOImpl has = new HasDAOImpl();
    private List<FarmItem> farmItems = null;
    private PropertyDAOImpl propertyDAO = new PropertyDAOImpl();

    public FarmItemDAOImpl im = new FarmItemDAOImpl();

    public void onBack() {
        backButton.getScene().getWindow().hide();
    }

    @FXML
    public void setP(PropertyView p, User u) {
        this.p = p;
        this.u = u;

        title.setText("Manage " + p.getName());
        nameField.setText(p.getName());
        addressField.setText(p.getStreet());
        cityField.setText(p.getCity());
        zipField.setText(Integer.toString(p.getZipcode()));
        sizeField.setText(Float.toString(p.getSize()));
        type.setText("Type: " + p.getPropertyType());

        id.setText(Integer.toString(p.getId()));
        List<Has> animals;
        List<Has> crops;
        String cropL = "";
        crops = has.findCropsByProperty(Integer.toString(p.getId()));
        for (Has f: crops) {
            cropL = cropL.concat(f.getItemName() + ",");
        }
        cropList.setText(cropL);

        farmItems = im.findAll();
        fillCropBox(farmItems);

        ObservableList<FarmItem.FarmItemType> cropTypes = FXCollections.observableArrayList();
        cropTypes.add(FarmItem.FarmItemType.FLOWER);
        cropTypes.add(FarmItem.FarmItemType.NUT);
        cropTypes.add(FarmItem.FarmItemType.VEGETABLE);
        cropTypes.add(FarmItem.FarmItemType.FRUIT);

        cropTypeMenu.setItems(cropTypes);

        ObservableList<String> pubMenu = FXCollections.observableArrayList();
        pubMenu.add("True");
        pubMenu.add("False");

        ObservableList<String> comMenu = FXCollections.observableArrayList();
        comMenu.add("True");
        comMenu.add("False");

        publicMenu.setItems(pubMenu);
        commercialMenu.setItems(comMenu);

        if (p.getIsPublic()) {
            publicMenu.getSelectionModel().selectFirst();
        } else {
            publicMenu.getSelectionModel().selectLast();
        }

        if (p.getIsCommercial()) {
            commercialMenu.getSelectionModel().selectFirst();
        } else {
            commercialMenu.getSelectionModel().selectLast();
        }




    }

    private void fillCropBox(List<FarmItem> farmItems) {
        List<String> crops = new ArrayList<>();
        for (int i = 0; i < farmItems.size(); i++) {
            if (farmItems.get(i).getItemType() != FarmItem.FarmItemType.ANIMAL && farmItems.get(i).getIsApproved()) {
                crops.add(farmItems.get(i).getName());
            }
        }

        ObservableList<String> c = FXCollections.observableArrayList(crops);
        approvedCropMenu.setItems(c);

    }

    public void onAddCroptoProperty() {
            Has newCrop = new Has(p.getId(), approvedCropMenu.getValue());
            has.insertHas(newCrop);
    }

    public void onSubmitRequest() {
            FarmItem newCrop = new FarmItem(cropName.getText(), false, cropTypeMenu.getValue());
            im.insertFarmItem(newCrop);
    }

    public void onSaveChanges() {
        Property updatedProperty = new Property(p.getId(), nameField.getText(), Float.parseFloat(sizeField.getText()), Boolean.valueOf(commercialMenu.getValue()),
                Boolean.valueOf(publicMenu.getValue()),addressField.getText(), cityField.getText(), Integer.valueOf(zipField.getText()), p.getPropertyType(), u.getUsername());
        propertyDAO.updateProperty(updatedProperty);
        deleteButton.getScene().getWindow().hide();

    }

    public void onDelete() {
        Property updatedProperty = new Property(p.getId(), nameField.getText(), Float.parseFloat(sizeField.getText()), Boolean.valueOf(commercialMenu.getValue()),
                Boolean.valueOf(publicMenu.getValue()),addressField.getText(), cityField.getText(), Integer.valueOf(zipField.getText()), p.getPropertyType(), u.getUsername());
        propertyDAO.deleteProperty(updatedProperty);
        deleteButton.getScene().getWindow().hide();
    }


}

