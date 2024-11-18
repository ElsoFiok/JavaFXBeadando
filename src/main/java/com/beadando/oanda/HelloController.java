package com.beadando.oanda;

import com.oanda.v20.ContextBuilder;
import com.oanda.v20.pricing.ClientPrice;
import com.oanda.v20.pricing.PricingGetRequest;
import com.oanda.v20.pricing.PricingGetResponse;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.StackPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import com.oanda.v20.Context;
import com.oanda.v20.account.AccountID;
import com.oanda.v20.account.AccountSummary;
import java.util.Collections;
import java.util.List;

public class HelloController {
    @FXML
    private StackPane contentStack;
    @FXML
    private TableView<String[]> accountSummaryTable;
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
    @FXML
    private ComboBox<String> currencyPairComboBox;
    @FXML
    private Label currentPriceLabel;
    @FXML
    protected void initialize() {
        currencyPairComboBox.setItems(FXCollections.observableArrayList(
                "EUR/USD", "USD/JPY", "GBP/USD", "AUD/USD", "USD/CAD"
        ));
        try {
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
    @FXML
    private void showAccountInfo() {
        contentStack.getChildren().clear();
        contentStack.getChildren().add(accountSummaryTable);
    }
    @FXML
    private void handleGetPrice() {
        String selectedPair = currencyPairComboBox.getValue();
        if (selectedPair == null) {
            currentPriceLabel.setText("Kérjük válasszon devizapárt!");
            return;
        }

        try {
            String currentPrice = fetchCurrentPrice(selectedPair);
            currentPriceLabel.setText("Jelenlegi ár: " + currentPrice);
        } catch (Exception e) {
            currentPriceLabel.setText("Valami baj van.");
            e.printStackTrace();
        }
    }
    private String fetchCurrentPrice(String currencyPair) {
        try {
            Context ctx = new ContextBuilder(Config.URL)
                    .setToken(Config.TOKEN)
                    .setApplication("PricePolling")
                    .build();
            String instrument = currencyPair.replace("/", "_");
            AccountID accountId = Config.ACCOUNTID;
            List<String> instruments = Collections.singletonList(instrument);
            PricingGetRequest request = new PricingGetRequest(accountId, instruments);
            PricingGetResponse resp = ctx.pricing.get(request);
            for (ClientPrice price : resp.getPrices()) {
                if (price.getInstrument().equals(instrument)) {
                    return price.getBids().get(0).getPrice().toString();
                }
            }
            return "Nem elérhető a devizapár ára " + currencyPair;
        } catch (Exception e) {
            e.printStackTrace();
            return "Valami baj történt: " + e.getMessage();
        }
    }
    @FXML
    private void showCurrentPrice() {
        contentStack.getChildren().clear();
        Label currentPriceLabel = new Label("Jelenlegi ár részleg: (Placeholder)");
        contentStack.getChildren().add(currentPriceLabel);
    }
    @FXML
    private void showHistoricalPrices() {
        contentStack.getChildren().clear();
        Label historicalPriceLabel = new Label("Historical Prices Section (Placeholder)");
        contentStack.getChildren().add(historicalPriceLabel);
    }
    @FXML
    private void showOpenPosition() {
        contentStack.getChildren().clear();
        Label openPositionLabel = new Label("Open Position Section (Placeholder)");
        contentStack.getChildren().add(openPositionLabel);
    }
    @FXML
    private void showClosePosition() {
        contentStack.getChildren().clear();
        Label closePositionLabel = new Label("Close Position Section (Placeholder)");
        contentStack.getChildren().add(closePositionLabel);
    }
    @FXML
    private void showOpenPositions() {
        contentStack.getChildren().clear();
        Label openPositionsLabel = new Label("Open Positions Section (Placeholder)");
        contentStack.getChildren().add(openPositionsLabel);
    }
}
