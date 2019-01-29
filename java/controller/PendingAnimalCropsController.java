package main.java.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.db.FarmItemDAOImpl;
import main.java.model.FarmItem;
import main.java.model.User;

public class PendingAnimalCropsController {

    @FXML
    private Label title;

    @FXML
    private TableView<FarmItem> pendingItems;

    @FXML
    private TableColumn<FarmItem, String> nameCol;

    @FXML
    private TableColumn<FarmItem, String> typeCol;

    @FXML
    private Button approveButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button backButton;
    private User user;
    FarmItemDAOImpl f = new FarmItemDAOImpl();

    public void onBack() {
        backButton.getScene().getWindow().hide();
    }

    public void loadUser(User user) {
        this.user = user;
    }

    public void loadFarmItems() {
        nameCol.setCellValueFactory(new PropertyValueFactory<FarmItem, String>("name"));
        typeCol.setCellValueFactory(new PropertyValueFactory<FarmItem, String>("itemTypeStr"));

        FarmItemDAOImpl farmItemDAO = new FarmItemDAOImpl();

        pendingItems.getItems().setAll(farmItemDAO.findPendingOrdered("Name ASC"));
    }

    public void onApprove() {
        f.updateFarmItem(new FarmItem(pendingItems.getSelectionModel().getSelectedItem().getName(), true, pendingItems.getSelectionModel().getSelectedItem().getItemType()));
        loadFarmItems();
    }

    public void onDelete() {
        pendingItems.getItems().remove(pendingItems.getSelectionModel().getSelectedItem());

    }
}

