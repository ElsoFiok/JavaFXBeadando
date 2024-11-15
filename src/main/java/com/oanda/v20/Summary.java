package com.oanda.v20;

import com.oanda.v20.account.AccountID;
import com.oanda.v20.account.AccountSummary;
public class Summary {
    public static void main(String[] args) {
        Context ctx = new Context("https://api-fxpractice.oanda.com","671b81420c1e7e9020aa39a044555251-23066734340c434485b049f98ee82a83");
        try {
            AccountSummary summary = ctx.account.summary(new AccountID("101-004-27055954-001")).getAccount();
            System.out.println(summary);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
