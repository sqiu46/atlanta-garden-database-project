package main.java.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.db.FarmItemDAOImpl;
import main.java.model.FarmItem;
import main.java.model.User;

public class ApprovedAnimalsCropsController {

    @FXML
    private Label title;

    @FXML
    private TableView<FarmItem> approvedItems;

    @FXML
    private TableColumn<FarmItem, String> nameCol;

    @FXML
    private TableColumn<FarmItem, String> typeCol;

    @FXML
    private ComboBox<FarmItem.FarmItemType> typeMenu;

    @FXML
    private TextField approveName;

    @FXML
    private Button approveButton;

    @FXML
    private ComboBox<String> searchMenu;

    @FXML
    private TextField searchTerm;

    @FXML
    private Button searchButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button backButton;

    private User user;
    FarmItemDAOImpl f = new FarmItemDAOImpl();

    public void onBack() {
        backButton.getScene().getWindow().hide();
    }

    public void loadUser(User user) { this.user = user; }

    public void loadFarmItems() {
        nameCol.setCellValueFactory(new PropertyValueFactory<FarmItem, String>("name"));
        typeCol.setCellValueFactory(new PropertyValueFactory<FarmItem, String>("itemTypeStr"));

        FarmItemDAOImpl farmItemDAO = new FarmItemDAOImpl();

        approvedItems.getItems().setAll(farmItemDAO.findApprovedOrdered("Name ASC", null, null));

        ObservableList<FarmItem.FarmItemType> types = FXCollections.observableArrayList();
        types.add(FarmItem.FarmItemType.FLOWER);
        types.add(FarmItem.FarmItemType.NUT);
        types.add(FarmItem.FarmItemType.FRUIT);
        types.add(FarmItem.FarmItemType.ANIMAL);
        types.add(FarmItem.FarmItemType.VEGETABLE);

        typeMenu.setItems(types);

        searchMenu.getItems().addAll(
                "Name",
                "Type");
    }

    public void onDelete() {
        FarmItem tbd = approvedItems.getSelectionModel().getSelectedItem();
        f.deleteFarmItem(tbd);
        loadFarmItems();
    }

    public void onAdd() {
        FarmItem tba = new FarmItem(approveName.getText(), true, typeMenu.getValue());
        f.insertFarmItem(tba);
        loadFarmItems();
    }

    public void onSearch() {
        if (searchTerm.getText() != null && !searchMenu.getSelectionModel().isEmpty()) {
            nameCol.setCellValueFactory(new PropertyValueFactory<FarmItem, String>("name"));
            typeCol.setCellValueFactory(new PropertyValueFactory<FarmItem, String>("itemTypeStr"));

            FarmItemDAOImpl farmItemDAO = new FarmItemDAOImpl();

            approvedItems.getItems().setAll(farmItemDAO.findApprovedOrdered("Name ASC", searchMenu.getValue(), searchTerm.getText()));
        }
    }
}

