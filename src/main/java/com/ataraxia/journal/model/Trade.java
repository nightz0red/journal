package com.ataraxia.journal.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "trades")
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // === META ===
    private LocalDateTime createdAt = LocalDateTime.now();

    // === BASIC INFO ===
    private String pair;        // EURUSD
    private String strategy;    // ORDERFLOW / ASIA_LIQUIDITY
    private String session;     // ASIA / LONDON / NY
    private String direction;   // LONG / SHORT
    private String status;      // OPEN / TP / SL / BE
    private double rr;

    // === DAILY LIQUIDITY ===
    private boolean pdhTaken;
    private boolean pdlTaken;

    // === HTF TREND ===
    private boolean weeklyBullish;
    private boolean dailyBullish;
    private boolean h4Bullish;

    // === DAILY CONFIRMATION ===
    private boolean dailyImbalanceUp;
    private boolean dailyOFUp;

    // === H4 CONFIRMATION ===
    private boolean h4ImbalanceUp;
    private boolean h4OFUp;

    // === ORDERFLOW (OF SETUP) ===
    private boolean h4LiquidityStep1;
    private boolean h4LiquidityStep2;
    private boolean m15Bos;

    // === ASIA MODEL ===
    private boolean nextDayModel;
    private boolean asiaHighTaken;
    private boolean asiaLowTaken;
    private boolean asiaOFAgainst;
    private boolean imbalanceAgainst;

    // === COMPUTED ===
    public boolean isH4Valid() {
        return weeklyBullish
                && dailyBullish
                && h4Bullish
                && (dailyImbalanceUp || dailyOFUp)
                && (h4ImbalanceUp || h4OFUp);
    }

    // ===== GETTERS & SETTERS =====

    public Long getId() { return id; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public String getPair() { return pair; }
    public void setPair(String pair) { this.pair = pair; }

    public String getStrategy() { return strategy; }
    public void setStrategy(String strategy) { this.strategy = strategy; }

    public String getSession() { return session; }
    public void setSession(String session) { this.session = session; }

    public String getDirection() { return direction; }
    public void setDirection(String direction) { this.direction = direction; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public double getRr() { return rr; }
    public void setRr(double rr) { this.rr = rr; }

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

    public boolean isAsiaHighTaken() { return asiaHighTaken; }
    public void setAsiaHighTaken(boolean asiaHighTaken) { this.asiaHighTaken = asiaHighTaken; }

    public boolean isAsiaLowTaken() { return asiaLowTaken; }
    public void setAsiaLowTaken(boolean asiaLowTaken) { this.asiaLowTaken = asiaLowTaken; }

    public boolean isAsiaOFAgainst() { return asiaOFAgainst; }
    public void setAsiaOFAgainst(boolean asiaOFAgainst) { this.asiaOFAgainst = asiaOFAgainst; }

    public boolean isImbalanceAgainst() { return imbalanceAgainst; }
    public void setImbalanceAgainst(boolean imbalanceAgainst) { this.imbalanceAgainst = imbalanceAgainst; }
}
