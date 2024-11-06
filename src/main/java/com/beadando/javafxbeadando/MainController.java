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
import javafx.scene.control.TableView;

import java.io.IOException;
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
    @FXML
    private Spinner<Integer> darabSpinner = new Spinner<Integer>();

    @FXML
    private Spinner<Integer> teljesitmenySpinner = new Spinner<Integer>();

    @FXML
    private Spinner<Integer> kezdevSpinner = new Spinner<Integer>();

    @FXML
    private Spinner<Integer> helyszinidSpinner = new Spinner<Integer>();
    @FXML
    private Button rekordHozzaadButton = new Button();  // Button for adding a new record
    @FXML
    private TextArea helyszinNameTextArea = new TextArea();  // TextArea for entering the name of the location
    @FXML
    private Spinner<Integer> megyeidSpinner = new Spinner<Integer>();  // Spinner for selecting the County ID (MegyeID)

    @FXML
    private Button megyeAddButton = new Button();  // The button to trigger the adding action

    @FXML
    private TextArea nevTextArea = new TextArea();   // TextArea for "Név"

    @FXML
    private TextArea regioTextArea = new TextArea(); // TextArea for "Régió"
    @FXML
    private Spinner<Integer> updateToronyDarabBemenet =new Spinner<Integer>();
    @FXML
    private Spinner<Integer> updateToronyTeljesitmenyBemenet =new Spinner<Integer>();
    @FXML
    private Spinner<Integer> updateToronyKezdevBemenet =new Spinner<Integer>();
    @FXML
    private Spinner<Integer> updateToronyHelyszinidBemenet =new Spinner<Integer>();
    @FXML
    private Spinner<Integer> updateToronyIdBemenet =new Spinner<Integer>();
    @FXML
    private ComboBox<Integer> updateToronyIdComboBox = new ComboBox<Integer>();
    @FXML
    private void updateTorony() {
        Integer selectedId = updateToronyIdComboBox.getValue();
        if (selectedId != null) {
            // Get the updated values from the spinners
            int darab = updateToronyDarabBemenet.getValue();
            int teljesitmeny = updateToronyTeljesitmenyBemenet.getValue();
            int kezdev = updateToronyKezdevBemenet.getValue();
            int helyszinid = updateToronyHelyszinidBemenet.getValue();

            // Call the updateTorony method from your DatabaseManager
            dbManager.updateTorony(selectedId, darab, teljesitmeny, kezdev, helyszinid);
        } else {
            System.out.println("Please select a Torony ID to update.");
        }
    }
    @FXML
    private Button addButtonTorony;
    private int darabValue;
    private int teljesitmenyValue;
    private int kezdevValue;
    private int megyeidValue;
    // Declare DatabaseManager as an instance variable
    private final DatabaseManager dbManager = new DatabaseManager();

    @FXML
    public void initialize() {
        // Set up the spinners with ranges and default values
        darabSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1));
        teljesitmenySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(200, 3000, 200));
        kezdevSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2000, 2024, 2000));
        helyszinidSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 50, 1));
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, 1);
        megyeidSpinner.setValueFactory(valueFactory);
        rekordHozzaadButton.setOnAction(event -> addHelyszinRecord());
        // Initialize the "Darab" Spinner
        SpinnerValueFactory<Integer> darabValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1); // min, max, initial value
        updateToronyDarabBemenet.setValueFactory(darabValueFactory);

        // Initialize the "Teljesítmény" Spinner
        SpinnerValueFactory<Integer> teljesitmenyValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 3000, 100); // min, max, initial value
        updateToronyTeljesitmenyBemenet.setValueFactory(teljesitmenyValueFactory);

        // Initialize the "Kezdés Éve" Spinner
        SpinnerValueFactory<Integer> kezdevValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(2000, 2025, 2023); // min, max, initial value
        updateToronyKezdevBemenet.setValueFactory(kezdevValueFactory);

        // Initialize the "Helyszín ID" Spinner (you might want to populate it dynamically later)
        SpinnerValueFactory<Integer> helyszinidValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1); // Placeholder values
        updateToronyHelyszinidBemenet.setValueFactory(helyszinidValueFactory);

        // Initialize the "ID" Spinner for editing an existing record
        SpinnerValueFactory<Integer> idValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1); // Placeholder values
        updateToronyIdBemenet.setValueFactory(idValueFactory);

        List<Integer> toronyIds = dbManager.getAllToronyIds(); // This is a method you'd create to get all Torony IDs
        ObservableList<Integer> toronyIdList = FXCollections.observableArrayList(toronyIds);
        updateToronyIdComboBox.setItems(toronyIdList);
    }
    @FXML
    public void handleAddMegyeButtonClick(ActionEvent event) {
        // Get the values from the TextArea fields
        String nev = nevTextArea.getText().trim();
        String regio = regioTextArea.getText().trim();

        // Check that the input fields are not empty
        if (!nev.isEmpty() && !regio.isEmpty()) {
            // Call the addMegye method in your DatabaseManager class
            // Assuming that the "id" is auto-incremented
            dbManager.addMegye(nev, regio);  // Make sure this method accepts these parameters
        } else {
            // Handle error (e.g., show a message)
            System.out.println("Please fill in both fields.");
        }
    }
    @FXML
    private void handleAddButtonTorony() {
        // Retrieve the values from each spinner and store them
        darabValue = darabSpinner.getValue();
        teljesitmenyValue = teljesitmenySpinner.getValue();
        kezdevValue = kezdevSpinner.getValue();
        megyeidValue = helyszinidSpinner.getValue();

        // Example output to show that values are stored
        System.out.println("Darab: " + darabValue);
        System.out.println("Teljesítmény: " + teljesitmenyValue);
        System.out.println("Kezdév: " + kezdevValue);
        System.out.println("Megyeid: " + megyeidValue);

        // Additional processing can be done here
        dbManager.addTorony( darabValue, teljesitmenyValue, kezdevValue, megyeidValue);
    }


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
    private void addHelyszinRecord() {
        String helyszinNev = helyszinNameTextArea.getText();  // Get the location name from the TextArea
        int megyeid = megyeidSpinner.getValue();  // Get the selected Megye ID from the Spinner

        // Make sure that name is not empty
        if (helyszinNev.isEmpty()) {
            System.out.println("Location name cannot be empty!");
            return;
        }

        // Call the DatabaseManager to insert the new location record
        dbManager.addHelyszin(helyszinNev, megyeid);
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
                "SELECT torony.*, helyszin.nev AS helyszin_nev, megye.id AS megye_id,megye.nev AS megye_nev, megye.regio AS regio " +
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
