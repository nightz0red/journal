package com.ataraxia.journal.service;

import com.ataraxia.journal.exception.TradeForbiddenException;
import com.ataraxia.journal.rules.MarketContext;
import com.ataraxia.journal.rules.RuleCode;
import com.ataraxia.journal.rules.StrategyType;
import org.springframework.stereotype.Service;

@Service
public class RuleEngineService {

    public void validate(StrategyType strategy, MarketContext ctx) {

        /*
         ======================================================
         1. DAILY LIQUIDITY RULE
         ======================================================
         Если PDH или PDL уже снят — торговать сегодня нельзя
         */
        if (ctx.isPdhTaken() || ctx.isPdlTaken()) {
            throw new TradeForbiddenException(
                    RuleCode.DAILY_LIQUIDITY_TAKEN,
                    "Daily liquidity already taken (PDH/PDL)"
            );
        }

        /*
         ======================================================
         2. HTF ALIGNMENT (W → D → H4)
         ======================================================
         Недельный, дневной и 4H тренды должны быть синхронизированы
         */
        if (!(ctx.isWeeklyBullish()
                && ctx.isDailyBullish()
                && ctx.isH4Bullish())) {
            throw new TradeForbiddenException(
                    RuleCode.HTF_NOT_ALIGNED,
                    "HTF trend not aligned (Weekly → Daily → H4)"
            );
        }

        /*
         ======================================================
         3. DAILY CONFIRMATION
         ======================================================
         Дневка должна подтверждать идею
         (либо imbalance, либо orderflow)
         */
        if (!(ctx.isDailyImbalanceUp() || ctx.isDailyOFUp())) {
            throw new TradeForbiddenException(
                    RuleCode.NO_DAILY_CONFIRMATION,
                    "No Daily confirmation (IMB or OF)"
            );
        }

        /*
         ======================================================
         4. H4 CONFIRMATION OF DAILY IDEA
         ======================================================
         4H должен подтверждать дневную идею
         */
        if (!(ctx.isH4ImbalanceUp() || ctx.isH4OFUp())) {
            throw new TradeForbiddenException(
                    RuleCode.H4_NOT_CONFIRMING_DAILY,
                    "H4 does not confirm Daily idea"
            );
        }

        /*
         ======================================================
         5. STRATEGY-SPECIFIC RULES
         ======================================================
         */

        /*
         ----------------------
         ORDERFLOW STRATEGY
         ----------------------
         */
        if (strategy == StrategyType.ORDERFLOW) {

            // OF должен быть подтверждён:
            // 2 ступени ликвидности + BOS на 15m
            if (!(ctx.isH4LiquidityStep1()
                    && ctx.isH4LiquidityStep2()
                    && ctx.isM15Bos())) {
                throw new TradeForbiddenException(
                        RuleCode.OF_NOT_CONFIRMED,
                        "OrderFlow not fully confirmed (2-step liquidity + 15m BOS)"
                );
            }
        }

        /*
         ----------------------
         ASIA LIQUIDITY STRATEGY
         ----------------------
         */
        if (strategy == StrategyType.ASIA_LIQUIDITY) {

            // Next Day Model обязателен
            if (!ctx.isNextDayModel()) {
                throw new TradeForbiddenException(
                        RuleCode.NOT_NEXT_DAY_MODEL,
                        "Not a Next Day Model"
                );
            }

            // Нельзя, если есть OF против нас
            if (ctx.isAsiaOFAgainst()) {
                throw new TradeForbiddenException(
                        RuleCode.ASIA_OF_AGAINST,
                        "Asia OrderFlow against the trade"
                );
            }

            // Нельзя, если есть имбаланс против нас
            if (ctx.isImbalanceAgainst()) {
                throw new TradeForbiddenException(
                        RuleCode.IMBALANCE_AGAINST,
                        "Imbalance against the trade"
                );
            }
        }

        /*
         ======================================================
         ЕСЛИ МЫ ДОШЛИ СЮДА — СДЕЛКА РАЗРЕШЕНА
         ======================================================
         */
    }
}
