package com.ataraxia.journal.service;

import com.ataraxia.journal.exception.TradeForbiddenException;
import com.ataraxia.journal.model.BlockedEvent;
import com.ataraxia.journal.model.Trade;
import com.ataraxia.journal.repository.BlockedEventRepository;
import com.ataraxia.journal.repository.TradeRepository;
import com.ataraxia.journal.rules.ContextMapper;
import com.ataraxia.journal.rules.StrategyType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeService {

    private final TradeRepository tradeRepository;
    private final RuleEngineService ruleEngineService;
    private final BlockedEventRepository blockedEventRepository;

    public TradeService(
            TradeRepository tradeRepository,
            RuleEngineService ruleEngineService,
            BlockedEventRepository blockedEventRepository
    ) {
        this.tradeRepository = tradeRepository;
        this.ruleEngineService = ruleEngineService;
        this.blockedEventRepository = blockedEventRepository;
    }

    // ======================================================
    // СОЗДАНИЕ СДЕЛКИ (С ПРОВЕРКОЙ ПРАВИЛ)
    // ======================================================
    public Trade create(Trade trade) {

        try {
            // 1️⃣ Определяем стратегию
            StrategyType strategy =
                    StrategyType.valueOf(trade.getStrategy().toUpperCase());

            // 2️⃣ Строим MarketContext из Trade
            var context = ContextMapper.fromTrade(trade);

            // 3️⃣ RULE ENGINE (может выбросить 403)
            ruleEngineService.validate(strategy, context);

            // 4️⃣ Если всё ок — сохраняем сделку
            return tradeRepository.save(trade);

        } catch (TradeForbiddenException ex) {

            // 🔴 ЛОГИРУЕМ ЗАПРЕТ
            BlockedEvent event = new BlockedEvent();
            event.setPair(trade.getPair());
            event.setStrategy(trade.getStrategy());
            event.setRuleCode(ex.getCode().name());
            event.setMessage(ex.getMessage());

            blockedEventRepository.save(event);

            // 🔴 Пробрасываем дальше (вернётся 403 клиенту)
            throw ex;
        }
    }

    public Trade updateStatus(Long id, String newStatus) {
        Trade trade = tradeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trade not found: " + id));

        trade.setStatus(newStatus);
        return tradeRepository.save(trade);
    }



    // ======================================================
    // ПОЛУЧЕНИЕ ДАННЫХ
    // ======================================================
    public List<Trade> all() {
        return tradeRepository.findAll();
    }

    public List<Trade> byStrategy(String strategy) {
        return tradeRepository.findByStrategyIgnoreCase(strategy);
    }

    public List<Trade> byStatus(String status) {
        return tradeRepository.findByStatusIgnoreCase(status);
    }

    public List<Trade> byPair(String pair) {
        return tradeRepository.findByPairIgnoreCase(pair);
    }
}
