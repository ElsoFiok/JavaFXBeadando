package com.beadando.oanda;

import com.oanda.v20.Context;
import com.oanda.v20.account.AccountID;
import com.oanda.v20.order.MarketOrderRequest;
import com.oanda.v20.order.OrderCreateRequest;
import com.oanda.v20.order.OrderCreateResponse;
import com.oanda.v20.primitives.InstrumentName;

public abstract class OpenPosition {
    static void Nyitás(Context ctx, AccountID accountId, String currencyPair, Integer amount, String direction) {
        System.out.println("Placing a Market Order");
        String instrumentName = currencyPair.replace("/", "_");
        InstrumentName instrument = new InstrumentName(instrumentName);
        try {
            OrderCreateRequest request = new OrderCreateRequest(accountId);
            MarketOrderRequest marketOrderRequest = new MarketOrderRequest();
            marketOrderRequest.setInstrument(instrument);
            int units;
            if (direction.equalsIgnoreCase("Vétel")) {
                units = Math.abs(amount);
            } else if (direction.equalsIgnoreCase("Eladás")) {
                units = -Math.abs(amount);
            } else {
                System.out.println("Invalid direction input: " + direction);
                return;
            }
            marketOrderRequest.setUnits(units);
            request.setOrder(marketOrderRequest);
            OrderCreateResponse response = ctx.order.create(request);
            System.out.println("Order successfully placed! Trade ID: " + response.getOrderFillTransaction().getId());
        } catch (Exception e) {
            System.out.println("Error placing market order: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}

