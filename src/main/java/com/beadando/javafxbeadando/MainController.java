package com.beadando.javafxbeadando;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.sql.Connection;

public class MainController {

    @FXML
    private void handleOpenDatabaseMenu(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DatabaseView.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Connection connection = DatabaseManager.connect(); // Use the connect method from DatabaseManager
        if (connection != null) {
            showAlert("Database Connection", "Connection to the database was successful!");
            DatabaseManager.closeConnection(); // Close the connection
        } else {
            showAlert("Database Connection Error", "Failed to connect to the database.");
        }
    }

    @FXML
    private void handleOpenSoapClientMenu() {
        showAlert("SOAP Client Menu", "Opening SOAP Client Menu...");
    }

    @FXML
    private void handleOpenForexMenu() {
        showAlert("Forex Menu", "Opening Forex Menu...");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
