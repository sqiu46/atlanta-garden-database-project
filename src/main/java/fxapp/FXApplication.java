package main.java.fxapp;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.java.db.ConnectionPool;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class FXApplication extends Application {
    private ConnectionPool dataSource;

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            dataSource = ConnectionPool.getInstance();
        } catch(Exception e) {
            e.printStackTrace(System.err);

            //OPEN ERROR WINDOW
        }

        Parent root = FXMLLoader.load(getClass().getResource("/main/resources/view/UserLoginWindow.fxml"));
        primaryStage.setTitle("User Login");
        primaryStage.setScene(new Scene(root, 700, 500));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}