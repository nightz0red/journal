package com.ataraxia.journal.rules;

import com.ataraxia.journal.model.Trade;

public class ContextMapper {

    private ContextMapper() {}

    public static MarketContext fromTrade(Trade t) {
        MarketContext ctx = new MarketContext();

        ctx.setPdhTaken(t.isPdhTaken());
        ctx.setPdlTaken(t.isPdlTaken());

        ctx.setWeeklyBullish(t.isWeeklyBullish());
        ctx.setDailyBullish(t.isDailyBullish());
        ctx.setH4Bullish(t.isH4Bullish());

        ctx.setDailyImbalanceUp(t.isDailyImbalanceUp());
        ctx.setDailyOFUp(t.isDailyOFUp());

        ctx.setH4ImbalanceUp(t.isH4ImbalanceUp());
        ctx.setH4OFUp(t.isH4OFUp());

        ctx.setH4LiquidityStep1(t.isH4LiquidityStep1());
        ctx.setH4LiquidityStep2(t.isH4LiquidityStep2());
        ctx.setM15Bos(t.isM15Bos());

        ctx.setNextDayModel(t.isNextDayModel());
        ctx.setAsiaOFAgainst(t.isAsiaOFAgainst());
        ctx.setImbalanceAgainst(t.isImbalanceAgainst());

        return ctx;
    }
}
