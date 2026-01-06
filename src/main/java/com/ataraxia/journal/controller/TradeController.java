package com.ataraxia.journal.controller;

import com.ataraxia.journal.controller.dto.ValidationResult;
import com.ataraxia.journal.exception.TradeForbiddenException;
import com.ataraxia.journal.model.Trade;
import com.ataraxia.journal.rules.ContextMapper;
import com.ataraxia.journal.rules.StrategyType;
import com.ataraxia.journal.service.RuleEngineService;
import com.ataraxia.journal.service.TradeService;
import org.springframework.web.bind.annotation.*;
import com.ataraxia.journal.controller.dto.UpdateStatusRequest;

import java.util.List;

@RestController
@RequestMapping("/trades")
public class TradeController {

    private final TradeService tradeService;
    private final RuleEngineService ruleEngine;

    public TradeController(TradeService tradeService,
                           RuleEngineService ruleEngine) {
        this.tradeService = tradeService;
        this.ruleEngine = ruleEngine;
    }

    // ======================================================
    // ✅ СОЗДАНИЕ СДЕЛКИ (С ЗАПРЕТОМ)
    // ======================================================
    @PostMapping
    public Trade createTrade(@RequestBody Trade trade) {
        return tradeService.create(trade);
    }

    // ======================================================
    // ✅ ПРОВЕРКА СДЕЛКИ (БЕЗ СОХРАНЕНИЯ)
    // ======================================================
    @PostMapping("/validate")
    public ValidationResult validateTrade(@RequestBody Trade trade) {

        try {
            StrategyType strategy =
                    StrategyType.valueOf(trade.getStrategy().toUpperCase());

            var ctx = ContextMapper.fromTrade(trade);

            ruleEngine.validate(strategy, ctx);

            return new ValidationResult(
                    true,
                    List.of("ALLOW")
            );

        } catch (TradeForbiddenException ex) {

            return new ValidationResult(
                    false,
                    List.of(ex.getCode().name() + ": " + ex.getMessage())
            );

        } catch (Exception ex) {

            return new ValidationResult(
                    false,
                    List.of("ERROR: " + ex.getMessage())
            );
        }
    }

    // ======================================================
    // ✅ ПОЛУЧИТЬ ВСЕ СДЕЛКИ
    // ======================================================
    @GetMapping
    public List<Trade> getAllTrades() {
        return tradeService.all();
    }

    // ======================================================
    // ✅ ФИЛЬТРЫ
    // /trades/filter?strategy=ORDERFLOW
    // /trades/filter?status=TP
    // /trades/filter?pair=EURUSD
    // ======================================================
    @GetMapping("/filter")
    public List<Trade> filterTrades(
            @RequestParam(required = false) String strategy,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String pair) {

        if (strategy != null) {
            return tradeService.byStrategy(strategy);
        }
        if (status != null) {
            return tradeService.byStatus(status);
        }
        if (pair != null) {
            return tradeService.byPair(pair);
        }
        return tradeService.all();
    }

    @PutMapping("/{id}/status")
    public Trade updateTradeStatus(@PathVariable Long id,
                                   @RequestBody UpdateStatusRequest req) {
        return tradeService.updateStatus(id, req.getStatus());
    }



}
