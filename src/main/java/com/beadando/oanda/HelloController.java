package com.beadando.oanda;

import com.oanda.v20.ContextBuilder;
import com.oanda.v20.ExecuteException;
import com.oanda.v20.RequestException;
import com.oanda.v20.instrument.CandlestickGranularity;
import com.oanda.v20.instrument.InstrumentCandlesRequest;
import com.oanda.v20.pricing.ClientPrice;
import com.oanda.v20.pricing.PricingGetRequest;
import com.oanda.v20.pricing.PricingGetResponse;
import com.oanda.v20.primitives.InstrumentName;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.oanda.v20.Context;
import com.oanda.v20.account.AccountID;
import com.oanda.v20.account.AccountSummary;
import com.oanda.v20.instrument.Candlestick;
import com.oanda.v20.instrument.InstrumentCandlesResponse;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import com.oanda.v20.trade.Trade;
import com.oanda.v20.trade.TradeCloseRequest;
import com.oanda.v20.trade.TradeSpecifier;

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
    private Label closeStatus;
    @FXML
    private ComboBox<String> currencyPairComboBox2;
    @FXML
    private TableView<String[]> historicalPricesTable;
    @FXML
    private TableColumn<String[], String> dateColumn;
    @FXML
    private TableColumn<String[], String> priceColumn;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private ComboBox<String> currencyPairComboBox3;
    @FXML
    private ComboBox<Integer> amountComboBox;
    @FXML
    private ComboBox<String> directionComboBox;
    @FXML
    private  TableView<String[]> positionsTable;
    @FXML
    private TableColumn<String[], String> positionsidColumn;
    @FXML
    private TableColumn<String[], String> positionsinstrumentColumn;
    @FXML
    private TableColumn<String[], String> positionsopenTimeColumn;
    @FXML
    private TableColumn<String[], String> positionscurrentUnitsColumn;
    @FXML
    private TableColumn<String[], String> positionspriceColumn;
    @FXML
    private TableColumn<String[], String> positionsunrealizedPLColumn;
    @FXML
    private TextField positionIdTextField;
    @FXML
    protected void initialize() {
        positionsidColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[0]));
        positionsinstrumentColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[1]));
        positionsopenTimeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[2]));
        positionscurrentUnitsColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[3]));
        positionspriceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[4]));
        positionsunrealizedPLColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[5]));
        positionsidColumn.prefWidthProperty().bind(positionsTable.widthProperty().multiply(0.1));
        positionsinstrumentColumn.prefWidthProperty().bind(positionsTable.widthProperty().multiply(0.2));
        positionsopenTimeColumn.prefWidthProperty().bind(positionsTable.widthProperty().multiply(0.2));
        positionscurrentUnitsColumn.prefWidthProperty().bind(positionsTable.widthProperty().multiply(0.2));
        positionspriceColumn.prefWidthProperty().bind(positionsTable.widthProperty().multiply(0.15));
        positionsunrealizedPLColumn.prefWidthProperty().bind(positionsTable.widthProperty().multiply(0.15));
        dateColumn.prefWidthProperty().bind(historicalPricesTable.widthProperty().multiply(0.5));
        priceColumn.prefWidthProperty().bind(historicalPricesTable.widthProperty().multiply(0.5));
        currencyPairComboBox2.setItems(FXCollections.observableArrayList("EUR/USD", "USD/JPY", "GBP/USD", "AUD/USD", "USD/CAD"));
        dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[0]));
        priceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[1]));
        currencyPairComboBox.setItems(FXCollections.observableArrayList("EUR/USD", "USD/JPY", "GBP/USD", "AUD/USD", "USD/CAD"));
        currencyPairComboBox3.setItems(FXCollections.observableArrayList("EUR/USD", "USD/JPY", "GBP/USD", "AUD/USD", "USD/CAD"));
        amountComboBox.setItems(FXCollections.observableArrayList(1, 10, 100, 1000));
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
            idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[0]));
            aliasColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[1]));
            currencyColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[2]));
            balanceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[3]));
            plColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[4]));
            marginRateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[5]));
            marginAvailableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[6]));
            withdrawalLimitColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[7]));
            accountSummaryTable.getColumns().addAll(idColumn, aliasColumn, currencyColumn, balanceColumn, plColumn, marginRateColumn, marginAvailableColumn, withdrawalLimitColumn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void onClosePositionClick() {
        String positionId = positionIdTextField.getText();
        if (positionId.isEmpty()) {
            System.out.println("A pozíció azonosítója kötelező!");
            return;
        }
        try {
            Context ctx = new Context("https://api-fxpractice.oanda.com", Config.TOKEN);
            ctx.trade.close(new TradeCloseRequest(Config.ACCOUNTID, new TradeSpecifier(positionId)));
            closeStatus.setText("Sikeres törlés!");

        } catch (Exception e) {
            System.out.println("Hiba történt a pozíció bezárásakor: " + e.getMessage());
            closeStatus.setText("Valami baj van. Biztos létezik ez a position?");
        }
    }
    @FXML
    private void onShowOpenPositionsClick() {
        try {
            Context ctx = new ContextBuilder(Config.URL)
                    .setToken(Config.TOKEN)
                    .setApplication("OpenPositionsApp")
                    .build();
            AccountID accountId = Config.ACCOUNTID;
            NyitotttradekKiír(ctx, accountId);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error fetching open positions: " + e.getMessage());
        }
    }
    void NyitotttradekKiír(Context ctx, AccountID accountId) throws ExecuteException, RequestException {
        ObservableList<String[]> tradeData = FXCollections.observableArrayList();
        try{
            List<Trade> trades = ctx.trade.listOpen(accountId).getTrades();
            for (Trade trade : trades) {
                String[] tradeDataArray = new String[6];
                tradeDataArray[0] = String.valueOf(trade.getId());
                tradeDataArray[1] = trade.getInstrument().toString();
                tradeDataArray[2] = trade.getOpenTime().toString();
                tradeDataArray[3] = String.valueOf(trade.getCurrentUnits());
                tradeDataArray[4] = String.valueOf(trade.getPrice());
                tradeDataArray[5] = String.valueOf(trade.getUnrealizedPL());
                tradeData.add(tradeDataArray);
            }
            positionsTable.setItems(tradeData);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private void onOpenPositionButtonClick() {
        String currencyPair = currencyPairComboBox3.getValue();
        Integer amount = amountComboBox.getValue();
        String direction = directionComboBox.getValue();
        if (currencyPair == null || amount == null || direction == null) {
            System.out.println("Minden mező kitöltése szükséges!");
            return;
        }
        Context ctx = new ContextBuilder(Config.URL)
                .setToken(Config.TOKEN)
                .setApplication("HistorikusAdatok")
                .build();
        OpenPosition.Nyitás(ctx, Config.ACCOUNTID, currencyPair, amount, direction);
    }
    public void openGraphWindow(String currencyPair, LocalDate startDate, LocalDate endDate) {
        Stage graphStage = new Stage();
        graphStage.setTitle("Grafikon");
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Dátum");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Ár");
        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(currencyPair != null ? currencyPair : "Nincs adat");
        if (currencyPair == null || startDate.toString().isEmpty() || endDate.toString().isEmpty()) {
            System.out.println("Nincs megadva bemenet.Így üres a grafikon. :(");
        } else {
            try {
                List<String[]> historicalData = fetchHistoricalPrices(currencyPair, startDate, endDate);
                for (String[] entry : historicalData) {
                    String date = entry[0];
                    double price = Double.parseDouble(entry[1]);
                    series.getData().add(new XYChart.Data<>(date, price));
                }
            } catch (Exception e) {
                System.out.println("Valami baj van: " + e.getMessage());
            }
        }
        lineChart.getData().add(series);
        Scene scene = new Scene(lineChart, 800, 600);
        graphStage.setScene(scene);
        graphStage.show();
    }
    @FXML
    private void onGrafikonButtonClick() {
        String currencyPair = currencyPairComboBox2.getValue();
        LocalDate  startDate = startDatePicker.getValue();
        LocalDate  endDate = endDatePicker.getValue();
        openGraphWindow(currencyPair, startDate, endDate);
    }
    @FXML
    private void onHistoricalButtonClick() {
        String currencyPair = currencyPairComboBox2.getValue();
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();
        if (currencyPair == null || startDate == null || endDate == null) {
            System.out.println("Valami kimaradt :))))))))))");
            return;
        }
        historicalPricesTable.setItems(fetchHistoricalPrices(currencyPair, startDate, endDate));
    }
    private ObservableList<String[]> fetchHistoricalPrices(String currencyPair, LocalDate  startDate, LocalDate  endDate) {
        String instrumentName = currencyPair.replace("/", "_");
        String from = startDate.atStartOfDay(ZoneOffset.UTC).toInstant().toString();
        String to = endDate.atStartOfDay(ZoneOffset.UTC).plusDays(1).toInstant().toString();
        Context ctx = new ContextBuilder(Config.URL)
                .setToken(Config.TOKEN)
                .setApplication("HistorikusAdatok")
                .build();
        try {
            InstrumentCandlesRequest request = new InstrumentCandlesRequest(new InstrumentName(instrumentName));
            request.setFrom(from);
            request.setTo(to);
            request.setGranularity(CandlestickGranularity.H1);
            InstrumentCandlesResponse resp = ctx.instrument.candles(request);
            ObservableList<String[]> data = FXCollections.observableArrayList();
            for (Candlestick candle : resp.getCandles()) {
                String time = candle.getTime().toString();
                String closePrice = candle.getMid().getC().toString();
                data.add(new String[]{time, closePrice});
            }
            return data;
        } catch (Exception e) {
            System.out.println("Valami baj van: " + e.getMessage());
            e.printStackTrace();
        }
        List<String[]> emptylist = FXCollections.observableArrayList();
        return (ObservableList<String[]>) emptylist;
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
}
