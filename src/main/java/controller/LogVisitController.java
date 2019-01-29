package main.java.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.java.db.*;
import main.java.model.*;
import org.controlsfx.control.Rating;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class LogVisitController {

    @FXML
    private Label title;

    @FXML
    private Label name;

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
    private Button logVisitButton;

    @FXML
    private Button backButton;

    @FXML
    private Rating ratingTool;

    private Property property;
    private PropertyView propertyView;
    private UserDAOImpl users = new UserDAOImpl();
    private VisitDAOImpl visits = new VisitDAOImpl();
    private PropertyDAOImpl p = new PropertyDAOImpl();
    private HasDAOImpl has = new HasDAOImpl();
    private User u;

    @FXML
    public void setTitle(String name) {
        title.setText(name + " Details");
    }


    @FXML
    public void setProperty(Property p) {
        property = p;
        String pubtext = "No";
        String comtext = "No";
        if (property.getIsCommercial()) {
            comtext = "Yes";
        }

        if (property.getIsPublic()) {
            pubtext = "Yes";
        }
        List<Visit> v = visits.findByProperty(property.getId());
        name.setText(property.getName());
        ownerName.setText(property.getOwnerUsername());
        ownerEmail.setText(users.findByUsername(property.getOwnerUsername()).getEmail());
        address.setText(property.getStreet());
        city.setText(property.getCity());
        zip.setText(String.valueOf(property.getZipcode()));
        size.setText("" + property.getSize());
        type.setText(property.getPropertyType().toString());
        isPublic.setText(pubtext);
        isCommercial.setText(comtext);
        id.setText("" + property.getId());
        numVisits.setText("" + v.size());

        List<Has> animals;
        List<Has> crops;
        String cropL = "";
        String animalL = "";
        if (property.getPropertyType() == Property.PropertyType.FARM) {
            animals = has.findAnimalsByProperty(Integer.toString(property.getId()));
            for (Has f : animals) {
                animalL = animalL.concat(f.getItemName() + ",");
            }
            animalList.setText(animalL);
        }
        crops = has.findCropsByProperty(Integer.toString(property.getId()));
        for (Has f: crops) {
            cropL = cropL.concat(f.getItemName() + ",");
        }
        cropList.setText(cropL);

    }

    @FXML
    public void setProperty(PropertyView propertyView) {
        this.propertyView = propertyView;
        String pubtext = "No";
        String comtext = "No";
        if (propertyView.getIsCommercial()) {
            comtext = "Yes";
        }

        if (propertyView.getIsPublic()) {
            pubtext = "Yes";
        }
        List<Visit> v = visits.findByProperty(propertyView.getId());
        name.setText(propertyView.getName());
        Property x = p.findByID(propertyView.getId());
        ownerName.setText(x.getOwnerUsername());
        User owner = users.findByUsername(x.getOwnerUsername());
        ownerEmail.setText(owner.getEmail());
        city.setText(propertyView.getCity());
        zip.setText(String.valueOf(propertyView.getZipcode()));
        size.setText("" + propertyView.getSize());
        type.setText(propertyView.getPropertyType().toString());
        isPublic.setText(pubtext);
        isCommercial.setText(comtext);
        id.setText("" + propertyView.getId());
        numVisits.setText("" + v.size());

        List<Has> animals;
        List<Has> crops;
        String cropL = "";
        String animalL = "";
        if (propertyView.getPropertyType() == Property.PropertyType.FARM) {
            animals = has.findAnimalsByProperty(Integer.toString(propertyView.getId()));
            for (Has f : animals) {
                animalL = animalL.concat(f.getItemName() + ",");
            }
            animalList.setText(animalL);
        }
        crops = has.findCropsByProperty(Integer.toString(propertyView.getId()));
        for (Has f: crops) {
            cropL = cropL.concat(f.getItemName() + ",");
        }
        cropList.setText(cropL);

    }
    @FXML
    public void initialize() {


    }

    public void loadUser(User u) {
        this.u = u;
    }
    public void onBack() {
        backButton.getScene().getWindow().hide();
    }

    public void onLog() {
        long time = new Date().getTime();
        Visit newVisit = new Visit(u.getUsername(), propertyView.getId(),null,(int)ratingTool.getRating());
        visits.insertVisit(newVisit);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main/resources/view/UnlogVisit.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Unlog Visit");
        try {
            stage.setScene(new Scene((Pane) loader.load(), 750, 600));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        UnlogVisitController controller = loader.<UnlogVisitController>getController();
        controller.setProperty(propertyView);
        controller.loadUser(u);
        controller.loadVisit(newVisit);
        stage.show();
    }

}

