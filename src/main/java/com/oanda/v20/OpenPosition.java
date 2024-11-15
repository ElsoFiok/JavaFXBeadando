package com.oanda.v20;

import com.oanda.v20.account.AccountID;
import com.oanda.v20.order.MarketOrderRequest;
import com.oanda.v20.order.OrderCreateRequest;
import com.oanda.v20.order.OrderCreateResponse;
import com.oanda.v20.primitives.InstrumentName;
import com.oanda.v20.trade.Trade;
import com.oanda.v20.trade.TradeCloseRequest;
import com.oanda.v20.trade.TradeSpecifier;

import java.util.List;

public abstract class OpenPosition {
    static Context ctx;
    static AccountID accountId;
    public static void main(String[] args) throws ExecuteException, RequestException {
        ctx = new ContextBuilder(Config.URL).setToken(Config.TOKEN).setApplication("OpenPosition").build();
        accountId = Config.ACCOUNTID;
        if(false) Open();
        if(false) Close();
        if(true) DisplayOpenPositions();
        System.out.println("Done");
    }

    static void Open(){
        System.out.println("Place a Market Order");
        InstrumentName instrument = new InstrumentName("NZD_USD");
        try {
            OrderCreateRequest request = new OrderCreateRequest(accountId);
            MarketOrderRequest marketorderrequest = new MarketOrderRequest();
            marketorderrequest.setInstrument(instrument);
// If positive, then LONG, if negative, then SHORT:
            marketorderrequest.setUnits(-10);
            request.setOrder(marketorderrequest);
            OrderCreateResponse response = ctx.order.create(request);
            System.out.println("tradeId: "+response.getOrderFillTransaction().getId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static void Close(){
        System.out.println("Close a Trade");
        try {
            String tradeId="470";
            ctx.trade.close(new TradeCloseRequest(accountId, new TradeSpecifier(tradeId)));
        } catch (Exception e) {   throw new RuntimeException(e);   }
    }

    static void DisplayOpenPositions() throws ExecuteException, RequestException {
        System.out.println("Nyitott tradek:");
        List<Trade> trades = ctx.trade.listOpen(accountId).getTrades();
        for(Trade trade: trades)
            System.out.println(trade);
        for(Trade trade: trades)
            System.out.println(trade.getId()+"\t"+trade.getInstrument()+"\t"+trade.getOpenTime()+"\t"+trade.getCurrentUnits()+"\t"+trade.getPrice()+"\t"+trade.getUnrealizedPL());
    }

}
