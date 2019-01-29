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
import main.java.model.FarmItem;
import main.java.model.Has;
import main.java.model.Property;
import main.java.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddNewPropertyController {

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
    private ComboBox<Property.PropertyType> typeMenu;

    @FXML
    private ComboBox<String> animalMenu;

    @FXML
    private ComboBox<String> cropMenu;

    @FXML
    private ComboBox<String> publicMenu;

    @FXML
    private ComboBox<String> commercialMenu;

    @FXML
    private Button addButton;

    @FXML
    private Button cancelButton;

    private User u;

    @FXML
    public void setTitle(String name) {
        title.setText("Add New Property: " + name);
    }

    private List<FarmItem> farmItems = null;

    public FarmItemDAOImpl im = new FarmItemDAOImpl();
    public HasDAOImpl has = new HasDAOImpl();
    public PropertyDAOImpl prop = new PropertyDAOImpl();

    public void loadUser(User u) {
        this.u = u;
    }

    @FXML
    public void initialize() {
        fillTypeBox();

        ObservableList<String> commercial = FXCollections.observableArrayList("YES", "NO");
        commercialMenu.setItems(commercial);

        ObservableList<String> publicType = FXCollections.observableArrayList("YES", "NO");
        publicMenu.setItems(publicType);


        farmItems = im.findAll();




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

    private void fillTypeBox() {
        ObservableList<Property.PropertyType> propTypes = FXCollections.observableArrayList(Property.PropertyType.FARM, Property.PropertyType.ORCHARD, Property.PropertyType.GARDEN);
        typeMenu.setItems(propTypes);

        typeMenu.valueProperty().addListener(new ChangeListener<Property.PropertyType>() {
            @Override
            public void changed(ObservableValue<? extends Property.PropertyType> observable, Property.PropertyType oldValue, Property.PropertyType newValue) {
                if (newValue == Property.PropertyType.GARDEN) {
                    animalMenu.setVisible(false);
                    fillGardenBox(farmItems);
                } else if (newValue == Property.PropertyType.FARM) {
                    animalMenu.setVisible(true);
                    fillAnimalBox(farmItems);
                    fillCropBox(farmItems);
                } else if (newValue == Property.PropertyType.ORCHARD) {
                    animalMenu.setVisible(false);
                    fillOrchardBox(farmItems);
                }
            }
        });
    }

    public int getNewPropertyID() {
        List<Property> properties = prop.findAll();
        int id = properties.get(properties.size()-1).getId() + 1;
        return id;
    }

    public void onBack() {
        cancelButton.getScene().getWindow().hide();
    }

    public void onAdd() {

        String propName = nameField.getText();
        String address = addressField.getText();

        String city = cityField.getText();
        String zip = zipField.getText();
        int zipcode = Integer.parseInt(zip);

        String animal = null;
        String crop = null;
        if (typeMenu.getValue() == Property.PropertyType.FARM) {
            animal = animalMenu.getValue();
            crop = cropMenu.getValue();
        } else if (typeMenu.getValue() == Property.PropertyType.ORCHARD) {
            crop = cropMenu.getValue();
        } else if (typeMenu.getValue() == Property.PropertyType.GARDEN) {
            crop = cropMenu.getValue();
        } else {}

        String acres = sizeField.getText();
        float ac = Float.parseFloat(acres);
        String pub = publicMenu.getValue();
        String com = commercialMenu.getValue();

        boolean publ = false;
        boolean commercial = false;

        if (pub.equals("YES")) {
            publ = true;
        }

        if (com.equals("YES")) {
            commercial = true;
        }

        Pattern zipPat = Pattern.compile("[0-9]{5}");
        Matcher zipMat = zipPat.matcher(zip);


         if (!zipMat.matches()){
            System.out.println("Invalid ZIP");
        } else  {
            // Add user table
            int prop_id = getNewPropertyID();
            Property p = new Property(prop_id, propName, ac, commercial, publ, address, city, zipcode, typeMenu.getValue(), u.getUsername(), null);
            prop.insertProperty(p);
            if (typeMenu.getValue() == Property.PropertyType.FARM) {
                Has anHas = new Has(prop_id, animal);
                Has crHas = new Has(prop_id, crop);
                has.insertHas(anHas);
                has.insertHas(crHas);
            } else {
                Has crHas = new Has(prop_id, crop);
                has.insertHas(crHas);
            }
            System.out.println("New Property Added");
            addButton.getScene().getWindow().hide();
        }
    }

}