package com.ataraxia.journal.rules;

public class MarketContext {

    // daily liquidity
    private boolean pdhTaken;
    private boolean pdlTaken;

    // trend alignment
    private boolean weeklyBullish;
    private boolean dailyBullish;
    private boolean h4Bullish;

    // daily confirmation
    private boolean dailyImbalanceUp;
    private boolean dailyOFUp;

    // h4 confirmation
    private boolean h4ImbalanceUp;
    private boolean h4OFUp;

    // OF specifics
    private boolean h4LiquidityStep1;
    private boolean h4LiquidityStep2;
    private boolean m15Bos;

    // Asia specifics
    private boolean nextDayModel;
    private boolean asiaOFAgainst;
    private boolean imbalanceAgainst;

    // getters/setters (генерируй в IntelliJ: Alt+Insert)
    // --- start
    public boolean isPdhTaken() { return pdhTaken; }
    public void setPdhTaken(boolean pdhTaken) { this.pdhTaken = pdhTaken; }

    public boolean isPdlTaken() { return pdlTaken; }
    public void setPdlTaken(boolean pdlTaken) { this.pdlTaken = pdlTaken; }

    public boolean isWeeklyBullish() { return weeklyBullish; }
    public void setWeeklyBullish(boolean weeklyBullish) { this.weeklyBullish = weeklyBullish; }

    public boolean isDailyBullish() { return dailyBullish; }
    public void setDailyBullish(boolean dailyBullish) { this.dailyBullish = dailyBullish; }

    public boolean isH4Bullish() { return h4Bullish; }
    public void setH4Bullish(boolean h4Bullish) { this.h4Bullish = h4Bullish; }

    public boolean isDailyImbalanceUp() { return dailyImbalanceUp; }
    public void setDailyImbalanceUp(boolean dailyImbalanceUp) { this.dailyImbalanceUp = dailyImbalanceUp; }

    public boolean isDailyOFUp() { return dailyOFUp; }
    public void setDailyOFUp(boolean dailyOFUp) { this.dailyOFUp = dailyOFUp; }

    public boolean isH4ImbalanceUp() { return h4ImbalanceUp; }
    public void setH4ImbalanceUp(boolean h4ImbalanceUp) { this.h4ImbalanceUp = h4ImbalanceUp; }

    public boolean isH4OFUp() { return h4OFUp; }
    public void setH4OFUp(boolean h4OFUp) { this.h4OFUp = h4OFUp; }

    public boolean isH4LiquidityStep1() { return h4LiquidityStep1; }
    public void setH4LiquidityStep1(boolean h4LiquidityStep1) { this.h4LiquidityStep1 = h4LiquidityStep1; }

    public boolean isH4LiquidityStep2() { return h4LiquidityStep2; }
    public void setH4LiquidityStep2(boolean h4LiquidityStep2) { this.h4LiquidityStep2 = h4LiquidityStep2; }

    public boolean isM15Bos() { return m15Bos; }
    public void setM15Bos(boolean m15Bos) { this.m15Bos = m15Bos; }

    public boolean isNextDayModel() { return nextDayModel; }
    public void setNextDayModel(boolean nextDayModel) { this.nextDayModel = nextDayModel; }

    public boolean isAsiaOFAgainst() { return asiaOFAgainst; }
    public void setAsiaOFAgainst(boolean asiaOFAgainst) { this.asiaOFAgainst = asiaOFAgainst; }

    public boolean isImbalanceAgainst() { return imbalanceAgainst; }
    public void setImbalanceAgainst(boolean imbalanceAgainst) { this.imbalanceAgainst = imbalanceAgainst; }
    // --- end
}
