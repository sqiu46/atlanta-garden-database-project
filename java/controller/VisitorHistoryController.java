package main.java.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class VisitorHistoryController {

    @FXML
    private Label title;

    @FXML
    private TableView<?> visitHistory;

    @FXML
    private TableColumn<?, ?> nameCol;

    @FXML
    private TableColumn<?, ?> dateLoggedCol;

    @FXML
    private TableColumn<?, ?> ratingCol;

    @FXML
    private Button deleteButton;

    @FXML
    private Button backButton;

    public void onBack() {
        backButton.getScene().getWindow().hide();
    }

}

