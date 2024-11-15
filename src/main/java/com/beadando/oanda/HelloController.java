package com.beadando.oanda;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import com.oanda.v20.Context;
import com.oanda.v20.account.AccountID;
import com.oanda.v20.account.AccountSummary;

public class HelloController {
    @FXML
    private StackPane contentStack;  // StackPane to switch content views

    @FXML
    private TableView<String[]> accountSummaryTable;  // Existing TableView for account summary

    @FXML
    private TableColumn<String[], String> idColumn;
    @FXML
    private TableColumn<String[], String> aliasColumn;
    @FXML
    private TableColumn<String[], String> currencyColumn;
    @FXML
    private TableColumn<String[], String> balanceColumn;
    @FXML
    private TableColumn<String[], String> plColumn;
    @FXML
    private TableColumn<String[], String> marginRateColumn;
    @FXML
    private TableColumn<String[], String> marginAvailableColumn;
    @FXML
    private TableColumn<String[], String> withdrawalLimitColumn;

    // Method to initialize the table with account data
    @FXML
    protected void initialize() {
        try {

            // Set up the context and fetch account summary
            Context ctx = new Context("https://api-fxpractice.oanda.com", Config.TOKEN);
            AccountSummary summary = ctx.account.summary(new AccountID(Config.ACCOUNTID)).getAccount();
            String summaryString = summary.toString();
            String[] parts = summaryString.replace("AccountSummary(", "").replace(")", "").split(", ");
            ObservableList<String[]> data = FXCollections.observableArrayList();
            String[] row = new String[8];
            for (int i = 0; i < parts.length; i++) {
                String[] keyValue = parts[i].split("=");
                if (keyValue.length == 2) {
                    switch (keyValue[0]) {
                        case "id":
                            row[0] = keyValue[1];
                            break;
                        case "alias":
                            row[1] = keyValue[1];
                            break;
                        case "currency":
                            row[2] = keyValue[1];
                            break;
                        case "balance":
                            row[3] = keyValue[1];
                            break;
                        case "pl":
                            row[4] = keyValue[1];
                            break;
                        case "marginRate":
                            row[5] = keyValue[1];
                            break;
                        case "marginAvailable":
                            row[6] = keyValue[1];
                            break;
                        case "withdrawalLimit":
                            row[7] = keyValue[1];
                            break;
                    }
                }
            }
            data.add(row);
            accountSummaryTable.setItems(data);
            idColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[0]));
            aliasColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[1]));
            currencyColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[2]));
            balanceColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[3]));
            plColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[4]));
            marginRateColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[5]));
            marginAvailableColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[6]));
            withdrawalLimitColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue()[7]));
            accountSummaryTable.getColumns().addAll(idColumn, aliasColumn, currencyColumn, balanceColumn,
                    plColumn, marginRateColumn, marginAvailableColumn, withdrawalLimitColumn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to show the account information
    @FXML
    private void showAccountInfo() {
        // Clear current content
        contentStack.getChildren().clear();

        // Add the account summary table to the stack (your default view)
        contentStack.getChildren().add(accountSummaryTable);
    }

    // Placeholder method for "Aktuális árak"
    @FXML
    private void showCurrentPrice() {
        // Clear current content
        contentStack.getChildren().clear();

        // Add a label or a new component for the current price section
        Label currentPriceLabel = new Label("Current Price Section (Placeholder)");
        contentStack.getChildren().add(currentPriceLabel);
    }

    // Placeholder method for "Historikus árak"
    @FXML
    private void showHistoricalPrices() {
        // Clear current content
        contentStack.getChildren().clear();

        // Add a label or a new component for the historical prices section
        Label historicalPriceLabel = new Label("Historical Prices Section (Placeholder)");
        contentStack.getChildren().add(historicalPriceLabel);
    }

    // Placeholder method for "Pozíció nyitás"
    @FXML
    private void showOpenPosition() {
        // Clear current content
        contentStack.getChildren().clear();

        // Add a label or a new component for the open position section
        Label openPositionLabel = new Label("Open Position Section (Placeholder)");
        contentStack.getChildren().add(openPositionLabel);
    }

    // Placeholder method for "Pozíció zárás"
    @FXML
    private void showClosePosition() {
        // Clear current content
        contentStack.getChildren().clear();

        // Add a label or a new component for the close position section
        Label closePositionLabel = new Label("Close Position Section (Placeholder)");
        contentStack.getChildren().add(closePositionLabel);
    }

    // Placeholder method for "Nyitott pozíciók"
    @FXML
    private void showOpenPositions() {
        // Clear current content
        contentStack.getChildren().clear();

        // Add a label or a new component for the open positions section
        Label openPositionsLabel = new Label("Open Positions Section (Placeholder)");
        contentStack.getChildren().add(openPositionsLabel);
    }
}
