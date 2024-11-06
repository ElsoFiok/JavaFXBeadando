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
    private ComboBox<Integer> comboBox;

    @FXML
    private RadioButton radioButton;

    @FXML
    private CheckBox checkBox;

    @FXML
    private Button listazasButton;
    @FXML
    private Spinner<Integer> darabSpinner = new Spinner<Integer>();

    @FXML
    private Spinner<Integer> teljesitmenySpinner = new Spinner<Integer>();

    @FXML
    private Spinner<Integer> kezdevSpinner = new Spinner<Integer>();

    @FXML
    private Spinner<Integer> helyszinidSpinner = new Spinner<Integer>();
    @FXML
    private Button rekordHozzaadButton = new Button();
    @FXML
    private TextArea helyszinNameTextArea = new TextArea();
    @FXML
    private Spinner<Integer> megyeidSpinner = new Spinner<Integer>();

    @FXML
    private Button megyeAddButton = new Button();

    @FXML
    private TextArea nevTextArea = new TextArea();

    @FXML
    private TextArea regioTextArea = new TextArea();
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
    private TextArea helyszinNevTextArea = new TextArea();
    @FXML
    private Spinner<Integer> helyszinMegyeidSpinner=new Spinner<Integer>();
    @FXML
    private ComboBox<Integer> helyszinIdComboBox=new ComboBox<Integer>();
    @FXML
    private Button helyszinModositasButton=new Button();
    @FXML
    private ComboBox<Integer> megyeIdComboBox = new ComboBox<Integer>();

    @FXML
    private TextArea megyeNevTextArea=new TextArea();

    @FXML
    private TextArea megyeRegioTextArea=new TextArea();

    @FXML
    private Button megyeModositasButton=new Button();

    @FXML
    private ComboBox<Integer> toronyDeleteIdComboBox = new ComboBox<Integer>();
    @FXML
    private Button toronyDeleteButton = new Button();

    @FXML
    private ComboBox<Integer> helyszinDeleteIdComboBox = new ComboBox<Integer>();
    @FXML
    private Button helyszinDeleteButton= new Button();

    @FXML
    private ComboBox<Integer> megyeDeleteIdComboBox = new ComboBox<Integer>();
    @FXML
    private Button megyeDeleteButton= new Button();

    @FXML
    private void updateTorony() {
        Integer selectedId = updateToronyIdComboBox.getValue();
        if (selectedId != null) {

            int darab = updateToronyDarabBemenet.getValue();
            int teljesitmeny = updateToronyTeljesitmenyBemenet.getValue();
            int kezdev = updateToronyKezdevBemenet.getValue();
            int helyszinid = updateToronyHelyszinidBemenet.getValue();

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

    private final DatabaseManager dbManager = new DatabaseManager();

    @FXML
    public void initialize() {

        darabSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1));
        teljesitmenySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(200, 3000, 200));
        kezdevSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2000, 2024, 2000));
        helyszinidSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 50, 1));
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, 1);
        megyeidSpinner.setValueFactory(valueFactory);
        rekordHozzaadButton.setOnAction(event -> addHelyszinRecord());

        SpinnerValueFactory<Integer> darabValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
        updateToronyDarabBemenet.setValueFactory(darabValueFactory);

        SpinnerValueFactory<Integer> teljesitmenyValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 3000, 100);
        updateToronyTeljesitmenyBemenet.setValueFactory(teljesitmenyValueFactory);

        SpinnerValueFactory<Integer> kezdevValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(2000, 2025, 2023);
        updateToronyKezdevBemenet.setValueFactory(kezdevValueFactory);

        SpinnerValueFactory<Integer> helyszinidValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
        updateToronyHelyszinidBemenet.setValueFactory(helyszinidValueFactory);

        SpinnerValueFactory<Integer> idValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
        updateToronyIdBemenet.setValueFactory(idValueFactory);

        List<Integer> toronyIds = dbManager.getAllToronyIds();
        ObservableList<Integer> toronyIdList = FXCollections.observableArrayList(toronyIds);
        updateToronyIdComboBox.setItems(toronyIdList);
        List<Helyszin> helyszinList = dbManager.getAllHelyszin();

        helyszinMegyeidSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 50, 1));

        ObservableList<Integer> ids = FXCollections.observableArrayList();
        for (Helyszin helyszin : helyszinList) {
            ids.add(helyszin.getId());
        }
        helyszinIdComboBox.setItems(ids);

        List<Integer> megyeIds = dbManager.getAllMegyeIds();
        megyeIdComboBox.getItems().addAll(megyeIds);

        megyeModositasButton.setOnAction(event -> updateMegye());

        populateToronyDeleteComboBox();
        populateHelyszinDeleteComboBox();
        populateMegyeDeleteComboBox();

    }
    private void populateToronyDeleteComboBox() {
        List<Integer> toronyIds = dbManager.getAllToronyIds();
        toronyDeleteIdComboBox.getItems().addAll(toronyIds);
    }

    private void populateHelyszinDeleteComboBox() {
        List<Integer> helyszinIds = dbManager.getAllHelyszinIds();
        helyszinDeleteIdComboBox.getItems().addAll(helyszinIds);
    }

    private void populateMegyeDeleteComboBox() {
        List<Integer> megyeIds = dbManager.getAllMegyeIds();
        megyeDeleteIdComboBox.getItems().addAll(megyeIds);
    }
    @FXML
    public void handleToronyDelete() {
        Integer selectedId = toronyDeleteIdComboBox.getValue();
        if (selectedId != null) {
            dbManager.deleteTorony(selectedId);
            populateToronyDeleteComboBox();
        } else {
            showAlert("Please select a Torony ID to delete.");
        }
        toronyDeleteIdComboBox.setValue(toronyDeleteIdComboBox.getItems().get(0));

    }

    @FXML
    public void handleHelyszinDelete() {
        Integer selectedId = helyszinDeleteIdComboBox.getValue();
        if (selectedId != null) {
            dbManager.deleteHelyszin(selectedId);
            populateHelyszinDeleteComboBox();
        } else {
            showAlert("Please select a Helyszin ID to delete.");
        }
        helyszinDeleteIdComboBox.setValue(helyszinDeleteIdComboBox.getItems().get(0));

    }

    @FXML
    public void handleMegyeDelete() {
        Integer selectedId = megyeDeleteIdComboBox.getValue();
        if (selectedId != null) {
            dbManager.deleteMegye(selectedId);
            populateMegyeDeleteComboBox();
        } else {
            showAlert("Please select a Megye ID to delete.");
        }
        megyeDeleteIdComboBox.setValue(megyeDeleteIdComboBox.getItems().get(0));

    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void updateHelyszin() {
        System.out.println("Button clicked!");
        int id = helyszinIdComboBox.getValue();
        String nev = helyszinNevTextArea.getText();
        int megyeid = helyszinMegyeidSpinner.getValue();

        DatabaseManager dbManager = new DatabaseManager();
        dbManager.updateHelyszin(id, nev, megyeid);
    }
    @FXML
    public void handleAddMegyeButtonClick(ActionEvent event) {

        String nev = nevTextArea.getText().trim();
        String regio = regioTextArea.getText().trim();

        if (!nev.isEmpty() && !regio.isEmpty()) {

            dbManager.addMegye(nev, regio);
        } else {

            System.out.println("Please fill in both fields.");
        }
    }
    private void updateMegye() {
        Integer selectedId = megyeIdComboBox.getValue();
        String nev = megyeNevTextArea.getText();
        String regio = megyeRegioTextArea.getText();

        if (selectedId != null && !nev.isEmpty() && !regio.isEmpty()) {

            dbManager.updateMegye(selectedId, nev, regio);
        } else {
            System.out.println("Please fill in all fields and select a valid Megye ID.");
        }
    }
    @FXML
    private void handleAddButtonTorony() {

        darabValue = darabSpinner.getValue();
        teljesitmenyValue = teljesitmenySpinner.getValue();
        kezdevValue = kezdevSpinner.getValue();
        megyeidValue = helyszinidSpinner.getValue();

        System.out.println("Darab: " + darabValue);
        System.out.println("Teljesítmény: " + teljesitmenyValue);
        System.out.println("Kezdév: " + kezdevValue);
        System.out.println("Megyeid: " + megyeidValue);

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
        String helyszinNev = helyszinNameTextArea.getText();
        int megyeid = megyeidSpinner.getValue();

        if (helyszinNev.isEmpty()) {
            System.out.println("Location name cannot be empty!");
            return;
        }

        dbManager.addHelyszin(helyszinNev, megyeid);
    }
    @FXML
    private void loadToronyData() {

        teljesTableView.getColumns().clear();
        teljesTableView.getItems().clear();

        List<Torony> toronyData = dbManager.getAllTorony();

        if (!toronyData.isEmpty()) {

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

            teljesTableView.getColumns().addAll(idColumn, darabColumn, teljesitmenyColumn, kezdevColumn, helyszinidColumn);

            ObservableList<Torony> tableData = FXCollections.observableArrayList(toronyData);
            teljesTableView.setItems(tableData);
        }
    }
    @FXML
    private void loadMegyeData() {

        teljesTableView.getColumns().clear();
        teljesTableView.getItems().clear();

        List<Megye> megyeData = dbManager.getAllMegye();

        if (!megyeData.isEmpty()) {

            TableColumn<Megye, Integer> idColumn = new TableColumn<>("ID");
            idColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));

            TableColumn<Megye, String> nameColumn = new TableColumn<>("Név");
            nameColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getName()));

            TableColumn<Megye, String> regioColumn = new TableColumn<>("Régió");
            regioColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getName()));

            teljesTableView.getColumns().addAll(idColumn, nameColumn,regioColumn);

            ObservableList<Megye> tableData = FXCollections.observableArrayList(megyeData);
            teljesTableView.setItems(tableData);
        } else {
            showAlert("No Data", "No megye data available.");
        }
    }

    @FXML
    private void loadHelyszinData() {

        teljesTableView.getColumns().clear();
        teljesTableView.getItems().clear();

        List<Helyszin> helyszinData = dbManager.getAllHelyszin();

        if (!helyszinData.isEmpty()) {

            TableColumn<Helyszin, Integer> idColumn = new TableColumn<>("ID");
            idColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));

            TableColumn<Helyszin, String> locationNameColumn = new TableColumn<>("Helyszín Név");
            locationNameColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getName()));

            TableColumn<Helyszin, Integer> megyeIdColumn = new TableColumn<>("Megye ID");
            megyeIdColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getMegyeId()));

            teljesTableView.getColumns().addAll(idColumn, locationNameColumn, megyeIdColumn);

            ObservableList<Helyszin> tableData = FXCollections.observableArrayList(helyszinData);
            teljesTableView.setItems(tableData);
        } else {
            showAlert("No Data", "No helyszin data available.");
        }
    }

    @FXML
    private void handleListazasAction() {

        szuroslista.getColumns().clear();
        szuroslista.getItems().clear();

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

        List<String> toronyData = dbManager.executeCustomQuery(sqlQuery.toString());

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

        szuroslista.getColumns().addAll(toronyIdColumn, darabColumn, teljesitmenyColumn, kezdevColumn, helyszinIdColumn, helyszinNevColumn, megyeIdColumn, megyeNevColumn, regioColumn);

        ObservableList<String> tableData = FXCollections.observableArrayList(toronyData);
        szuroslista.setItems(tableData);

        System.out.println("TableView item count: " + szuroslista.getItems().size());
    }

    private String getValueOrDefault(String dataString, String key) {
        String pattern = key + ":\\s*(.*?)(,|$)";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(dataString);
        if (m.find()) {
            return m.group(1).trim();
        }
        return "N/A";
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