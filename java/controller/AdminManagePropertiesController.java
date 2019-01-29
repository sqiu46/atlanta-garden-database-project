package main.java.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

public class AdminManagePropertiesController {

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
    private Label animalList;

    @FXML
    private ComboBox<String> cropMenu;

    @FXML
    private Button addCropButton;

    @FXML
    private ComboBox<String> animalMenu;

    @FXML
    private Button addAnimalButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button saveButton;

    @FXML
    private Button backButton;

    private PropertyView p;
    private Property property;
    private User u;
    private HasDAOImpl has = new HasDAOImpl();
    private List<FarmItem> farmItems = null;
    private PropertyDAOImpl propertyDAO = new PropertyDAOImpl();

    public FarmItemDAOImpl im = new FarmItemDAOImpl();


    public void onBack() {
        backButton.getScene().getWindow().hide();
    }

    public void loadUser(User u) {
        this.u = u;
    }

    public void loadProperties(PropertyView p) {
        this.p = p;
        title.setText(p.getName());
        nameField.setText(p.getName());
        addressField.setText(p.getStreet());
        cityField.setText(p.getCity());
        zipField.setText(Integer.toString(p.getZipcode()));
        sizeField.setText(Float.toString(p.getSize()));
        type.setText(p.getPropertyType().toString());
        id.setText(Integer.toString(p.getId()));

        List<Has> animals;
        List<Has> crops;
        String cropL = "";
        String aniL = "";
        if (p.getPropertyType() == Property.PropertyType.FARM) {
            animals = has.findAnimalsByProperty(Integer.toString(p.getId()));
            for (Has f: animals) {
                aniL = aniL.concat(f.getItemName() +",");
            }
            crops = has.findCropsByProperty(Integer.toString(p.getId()));
            for (Has f : crops) {
                cropL = cropL.concat(f.getItemName() + ",");
            }
            cropList.setText(cropL);
            animalList.setText(aniL);
        } else {
            crops = has.findCropsByProperty(Integer.toString(p.getId()));
            for (Has f : crops) {
                cropL = cropL.concat(f.getItemName() + ",");
            }
            cropList.setText(cropL);
        }

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

        if (p.getPropertyType() != Property.PropertyType.FARM) {
            animalMenu.setVisible(false);
            addAnimalButton.setVisible(false);

        }

        if (p.getPropertyType() == Property.PropertyType.FARM) {
            fillCropBox(im.findAll());
            fillAnimalBox(im.findAll());
        } else if (p.getPropertyType() == Property.PropertyType.GARDEN) {
            fillGardenBox(im.findAll());
        } else {
            fillOrchardBox(im.findAll());
        }

    }

    public void onAddCrop() {
        Has newHas = new Has(p.getId(), cropMenu.getValue());
        has.insertHas(newHas);
    }

    public void onAddAnimal() {
        Has newHas = new Has(p.getId(), animalMenu.getValue());
        has.insertHas(newHas);
    }

    public void onSave() {
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

    public void loadProperties(Property p) {
        this.property  = p;
        title.setText(p.getName());
        nameField.setText(p.getName());
        addressField.setText(p.getStreet());
        cityField.setText(p.getCity());
        zipField.setText(Integer.toString(p.getZipcode()));
        sizeField.setText(Float.toString(p.getSize()));
        type.setText(p.getPropertyType().toString());
        id.setText(Integer.toString(p.getId()));

        List<Has> animals;
        List<Has> crops;
        String cropL = "";
        String aniL = "";
        if (p.getPropertyType() == Property.PropertyType.FARM) {
            animals = has.findAnimalsByProperty(Integer.toString(p.getId()));
            for (Has f: animals) {
                aniL = aniL.concat(f.getItemName() +",");
            }
            crops = has.findCropsByProperty(Integer.toString(p.getId()));
            for (Has f : crops) {
                cropL = cropL.concat(f.getItemName() + ",");
            }
            cropList.setText(cropL);
            animalList.setText(aniL);
        } else {
            crops = has.findCropsByProperty(Integer.toString(p.getId()));
            for (Has f : crops) {
                cropL = cropL.concat(f.getItemName() + ",");
            }
            cropList.setText(cropL);
        }

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

        if (p.getPropertyType() != Property.PropertyType.FARM) {
            animalMenu.setVisible(false);
            addAnimalButton.setVisible(false);
        }

        if (p.getPropertyType() == Property.PropertyType.FARM) {
            fillCropBox(im.findAll());
            fillAnimalBox(im.findAll());
        } else if (p.getPropertyType() == Property.PropertyType.GARDEN) {
            fillGardenBox(im.findAll());
        } else {
            fillOrchardBox(im.findAll());
        }

    }

    private void fillGardenBox(List<FarmItem> farmItems) {
        List<String> crops = new ArrayList<>();
        for (int i = 0; i < farmItems.size(); i++) {
            if (farmItems.get(i).getItemType() == FarmItem.FarmItemType.VEGETABLE || farmItems.get(i).getItemType() == FarmItem.FarmItemType.FLOWER && farmItems.get(i).getIsApproved() ) {
                crops.add(farmItems.get(i).getName());
            }
        }

        ObservableList<String> c = FXCollections.observableArrayList(crops);
        cropMenu.setItems(c);

    }

    private void fillCropBox(List<FarmItem> farmItems) {
        List<String> crops = new ArrayList<>();
        for (int i = 0; i < farmItems.size(); i++) {
            if (farmItems.get(i).getItemType() != FarmItem.FarmItemType.ANIMAL && farmItems.get(i).getIsApproved()) {
                crops.add(farmItems.get(i).getName());
            }
        }

        ObservableList<String> c = FXCollections.observableArrayList(crops);
        cropMenu.setItems(c);


    }

    private void fillOrchardBox(List<FarmItem> farmItems) {
        List<String> crops = new ArrayList<>();
        for (int i = 0; i < farmItems.size(); i++) {
            if (farmItems.get(i).getItemType() == FarmItem.FarmItemType.FRUIT || farmItems.get(i).getItemType() == FarmItem.FarmItemType.NUT  && farmItems.get(i).getIsApproved()) {
                crops.add(farmItems.get(i).getName());
            }
        }

        ObservableList<String> c = FXCollections.observableArrayList(crops);
        cropMenu.setItems(c);

    }



    private void fillAnimalBox(List<FarmItem> farmItems) {
        List<String> animals = new ArrayList<>();
        for (int i = 0; i < farmItems.size(); i++) {
            if (farmItems.get(i).getItemType() == FarmItem.FarmItemType.ANIMAL && farmItems.get(i).getIsApproved()) {
                animals.add(farmItems.get(i).getName());
            }
        }
        ObservableList<String> a = FXCollections.observableArrayList(animals);
        animalMenu.setItems(a);
    }


    }


