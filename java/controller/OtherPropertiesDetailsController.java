package main.java.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import main.java.db.HasDAOImpl;
import main.java.db.VisitDAOImpl;
import main.java.model.Has;
import main.java.model.Property;
import main.java.model.PropertyView;
import main.java.model.User;

import java.util.List;

public class OtherPropertiesDetailsController {

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
    private Button backButton;

    private User u;
    private PropertyView propertyView;
    VisitDAOImpl visits = new VisitDAOImpl();
    HasDAOImpl has = new HasDAOImpl();

    public void onBack() {
        backButton.getScene().getWindow().hide();
    }

    public void loadProperties(PropertyView p) {
        this.propertyView = p;

        title.setText(p.getName());
        propertyName.setText(p.getName());
        ownerName.setText(u.getUsername());
        ownerEmail.setText(u.getEmail());
        numVisits.setText(Integer.toString(visits.findByProperty(p.getId()).size()));
        address.setText(p.getStreet());
        city.setText(p.getCity());
        zip.setText(Integer.toString(p.getZipcode()));
        size.setText(Float.toString(p.getSize()));
        avgRating.setText(Integer.toString(p.getAverageRating()));
        type.setText(p.getPropertyType().toString());
        isPublic.setText(Boolean.toString(p.getIsPublic()));
        isCommercial.setText(Boolean.toString(p.getIsCommercial()));
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

    }

    public void loadUser(User u) {
        this.u = u;
    }

}

