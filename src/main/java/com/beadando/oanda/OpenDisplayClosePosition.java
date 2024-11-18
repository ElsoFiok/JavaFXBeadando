package com.beadando.oanda;

import com.oanda.v20.Context;
import com.oanda.v20.ContextBuilder;
import com.oanda.v20.ExecuteException;
import com.oanda.v20.RequestException;
import com.oanda.v20.account.AccountID;
import com.oanda.v20.order.MarketOrderRequest;
import com.oanda.v20.order.OrderCreateRequest;
import com.oanda.v20.order.OrderCreateResponse;
import com.oanda.v20.primitives.InstrumentName;
import com.oanda.v20.trade.Trade;
import com.oanda.v20.trade.TradeCloseRequest;
import com.oanda.v20.trade.TradeSpecifier;
public abstract class OpenDisplayClosePosition {
    static void Nyitás(Context ctxxd, AccountID accountIdxd, String currencyPair, Integer amount, String direction) {
        System.out.println("Placing a Market Order");

        // Convert the currency pair (e.g., EUR/USD) to EUR_USD format
        String instrumentName = currencyPair.replace("/", "_");
        InstrumentName instrument = new InstrumentName(instrumentName);

        try {
            // Create a request for the market order
            OrderCreateRequest request = new OrderCreateRequest(accountIdxd);
            MarketOrderRequest marketOrderRequest = new MarketOrderRequest();
            marketOrderRequest.setInstrument(instrument);

            // Check direction (Vétel for Buy, Eladás for Sell)
            int units;
            if (direction.equalsIgnoreCase("Vétel")) {
                // If direction is "Vétel" (Buy), set the units as positive
                units = Math.abs(amount);  // Positive units for buy
            } else if (direction.equalsIgnoreCase("Eladás")) {
                // If direction is "Eladás" (Sell), set the units as negative
                units = -Math.abs(amount); // Negative units for sell
            } else {
                // Handle invalid direction input if necessary
                System.out.println("Invalid direction input: " + direction);
                return;
            }

            marketOrderRequest.setUnits(units);
            request.setOrder(marketOrderRequest);

            // Send the order request to the OANDA API
            OrderCreateResponse response = ctxxd.order.create(request);

            // Print the trade ID of the order placed
            System.out.println("Order successfully placed! Trade ID: " + response.getOrderFillTransaction().getId());
        } catch (Exception e) {
            // Handle any errors that occur during the order creation process
            System.out.println("Error placing market order: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


}

