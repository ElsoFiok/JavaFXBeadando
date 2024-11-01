package com.beadando.javafxbeadando;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainController {

    @FXML
    private TableView teljesTableView;

    @FXML
    private TableView szuroslista;
    @FXML
    private TextArea textArea;

    @FXML
    private ComboBox<Integer> comboBox; // Assuming you want to select integer values

    @FXML
    private RadioButton radioButton; // For filtering darab > 2

    @FXML
    private CheckBox checkBox; // For filtering darab < 5

    @FXML
    private Button listazasButton; // The button to trigger the listing action

    // Declare DatabaseManager as an instance variable
    private final DatabaseManager dbManager = new DatabaseManager();

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
    }

    @FXML
    private void loadToronyData() {
        // Clear any existing columns or data in the table
        teljesTableView.getColumns().clear();
        teljesTableView.getItems().clear();

        // Fetch data from the database
        List<Torony> toronyData = dbManager.getAllTorony();

        if (!toronyData.isEmpty()) {
            // Define the columns for the Torony model
            TableColumn<Torony, Integer> idColumn = new TableColumn<>("ID");
            idColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));

            TableColumn<Torony, Integer> darabColumn = new TableColumn<>("Darab");
            darabColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getDarab()));

            TableColumn<Torony, Integer> teljesitmenyColumn = new TableColumn<>("Teljesitmeny");
            teljesitmenyColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getTeljesitmeny()));

            TableColumn<Torony, Integer> kezdevColumn = new TableColumn<>("Kezdev");
            kezdevColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getKezdev()));

            TableColumn<Torony, Integer> helyszinidColumn = new TableColumn<>("Helyszin ID");
            helyszinidColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getHelyszinid()));

            // Add the columns to the TableView
            teljesTableView.getColumns().addAll(idColumn, darabColumn, teljesitmenyColumn, kezdevColumn, helyszinidColumn);

            // Set the data in the TableView
            ObservableList<Torony> tableData = FXCollections.observableArrayList(toronyData);
            teljesTableView.setItems(tableData);
        }
    }
    @FXML
    private void loadMegyeData() {
        // Clear any existing columns or data in the table
        teljesTableView.getColumns().clear();
        teljesTableView.getItems().clear();

        // Fetch data for "Megye" from the database
        List<Megye> megyeData = dbManager.getAllMegye(); // Assuming you have a method in dbManager for Megye

        if (!megyeData.isEmpty()) {
            // Define the columns for the Megye model
            TableColumn<Megye, Integer> idColumn = new TableColumn<>("ID");
            idColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));

            TableColumn<Megye, String> nameColumn = new TableColumn<>("Név");
            nameColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getName()));

            TableColumn<Megye, String> regioColumn = new TableColumn<>("Régió");
            regioColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getName()));

            // Add the columns to the TableView
            teljesTableView.getColumns().addAll(idColumn, nameColumn,regioColumn);

            // Set the data in the TableView
            ObservableList<Megye> tableData = FXCollections.observableArrayList(megyeData);
            teljesTableView.setItems(tableData);
        } else {
            showAlert("No Data", "No megye data available."); // Inform the user if no data is found
        }
    }

    @FXML
    private void loadHelyszinData() {
        // Clear any existing columns or data in the table
        teljesTableView.getColumns().clear();
        teljesTableView.getItems().clear();

        // Fetch data for "Helyszin" from the database
        List<Helyszin> helyszinData = dbManager.getAllHelyszin(); // Ensure this method is implemented in dbManager

        if (!helyszinData.isEmpty()) {
            // Define the columns for the Helyszin model
            TableColumn<Helyszin, Integer> idColumn = new TableColumn<>("ID");
            idColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));

            TableColumn<Helyszin, String> locationNameColumn = new TableColumn<>("Helyszín Név");
            locationNameColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getName())); // Use correct getter method

            TableColumn<Helyszin, Integer> megyeIdColumn = new TableColumn<>("Megye ID"); // New column for megyeId
            megyeIdColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getMegyeId())); // Use correct getter method for megyeId

            // Add the columns to the TableView
            teljesTableView.getColumns().addAll(idColumn, locationNameColumn, megyeIdColumn); // Add megyeIdColumn here

            // Set the data in the TableView
            ObservableList<Helyszin> tableData = FXCollections.observableArrayList(helyszinData);
            teljesTableView.setItems(tableData);
        } else {
            showAlert("No Data", "No helyszin data available."); // Inform the user if no data is found
        }
    }

    @FXML
    private void handleListazasAction() {
        // Clear existing columns or data in the table
        szuroslista.getColumns().clear();
        szuroslista.getItems().clear();

        // Retrieve filter criteria
        String megyeNev = textArea.getText().trim();
        Integer teljesitmeny = (Integer) comboBox.getValue();
        boolean darabGreaterThan2 = radioButton.isSelected();
        boolean darabLessThan5 = checkBox.isSelected();

        StringBuilder sqlQuery = new StringBuilder(
                "SELECT torony.*, helyszin.nev AS helyszin_nev, megye.nev AS megye_nev, megye.regio AS regio " +
                        "FROM torony " +
                        "JOIN helyszin ON torony.helyszinid = helyszin.id " +
                        "JOIN megye ON helyszin.megyeid = megye.id " +
                        "WHERE 1=1"
        );

        if (!megyeNev.isEmpty()) {
            sqlQuery.append(" AND megye.id IN (SELECT id FROM megye WHERE nev = '").append(megyeNev).append("')");
        }
        if (teljesitmeny != null) {
            sqlQuery.append(" AND teljesitmeny = ").append(teljesitmeny);
        }
        if (darabGreaterThan2) {
            sqlQuery.append(" AND darab > 2");
        }
        if (darabLessThan5) {
            sqlQuery.append(" AND darab < 5");
        }

        // Execute the query and populate TableView
        List<String> toronyData = dbManager.executeCustomQuery(sqlQuery.toString());

        // Define columns for each piece of data you want to display
        TableColumn<String, String> toronyIdColumn = new TableColumn<>("Torony ID");
        toronyIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(getValueOrDefault(cellData.getValue(), "Torony ID")));

        TableColumn<String, String> darabColumn = new TableColumn<>("Darab");
        darabColumn.setCellValueFactory(cellData -> new SimpleStringProperty(getValueOrDefault(cellData.getValue(), "Darab")));

        TableColumn<String, String> teljesitmenyColumn = new TableColumn<>("Teljesítmény");
        teljesitmenyColumn.setCellValueFactory(cellData -> new SimpleStringProperty(getValueOrDefault(cellData.getValue(), "Teljesítmény")));

        TableColumn<String, String> kezdevColumn = new TableColumn<>("Kezdev");
        kezdevColumn.setCellValueFactory(cellData -> new SimpleStringProperty(getValueOrDefault(cellData.getValue(), "Kezdev")));

        TableColumn<String, String> helyszinIdColumn = new TableColumn<>("Helyszín ID");
        helyszinIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(getValueOrDefault(cellData.getValue(), "Helyszín ID")));

        TableColumn<String, String> helyszinNevColumn = new TableColumn<>("Helyszín Név");
        helyszinNevColumn.setCellValueFactory(cellData -> new SimpleStringProperty(getValueOrDefault(cellData.getValue(), "Helyszín Név")));

        TableColumn<String, String> megyeIdColumn = new TableColumn<>("Megye ID");
        megyeIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(getValueOrDefault(cellData.getValue(), "Megye ID")));

        TableColumn<String, String> megyeNevColumn = new TableColumn<>("Megye Név");
        megyeNevColumn.setCellValueFactory(cellData -> new SimpleStringProperty(getValueOrDefault(cellData.getValue(), "Megye Név")));

        TableColumn<String, String> regioColumn = new TableColumn<>("Régió");
        regioColumn.setCellValueFactory(cellData -> new SimpleStringProperty(getValueOrDefault(cellData.getValue(), "Régió")));

        // Add all columns to the TableView
        szuroslista.getColumns().addAll(toronyIdColumn, darabColumn, teljesitmenyColumn, kezdevColumn, helyszinIdColumn, helyszinNevColumn, megyeIdColumn, megyeNevColumn, regioColumn);

        // Populate the TableView with data
        ObservableList<String> tableData = FXCollections.observableArrayList(toronyData);
        szuroslista.setItems(tableData);

        System.out.println("TableView item count: " + szuroslista.getItems().size());
    }

    // Helper method to safely get a value from the string
    private String getValueOrDefault(String dataString, String key) {
        String pattern = key + ":\\s*(.*?)(,|$)";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(dataString);
        if (m.find()) {
            return m.group(1).trim(); // Return the matched value, trimmed of whitespace
        }
        return "N/A"; // Return a default value if not found
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
