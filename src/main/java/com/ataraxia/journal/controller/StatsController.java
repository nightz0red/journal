package com.ataraxia.journal.controller;

import com.ataraxia.journal.repository.BlockedEventRepository;
import com.ataraxia.journal.repository.TradeRepository;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/stats")
public class StatsController {

    private final BlockedEventRepository blockedRepo;
    private final TradeRepository tradeRepo;

    public StatsController(BlockedEventRepository blockedRepo, TradeRepository tradeRepo) {
        this.blockedRepo = blockedRepo;
        this.tradeRepo = tradeRepo;
    }

    // =========================
    // DTOs для нормального JSON
    // =========================
    public static class CountItem {
        public String label;
        public long count;

        public CountItem(String label, long count) {
            this.label = label;
            this.count = count;
        }
    }

    public static class WinrateItem {
        public String strategy;
        public long tp;
        public long totalClosed;
        public double winrate;

        public WinrateItem(String strategy, long tp, long totalClosed, double winrate) {
            this.strategy = strategy;
            this.tp = tp;
            this.totalClosed = totalClosed;
            this.winrate = winrate;
        }
    }

    // =========================
    // Rule failures (blocked_events)
    // UI ждёт: [{label:"...", count: 10}, ...]
    // =========================
    @GetMapping("/blocked/by-rule")
    public List<CountItem> blockedByRule() {
        List<Object[]> rows = blockedRepo.countByRule();
        List<CountItem> out = new ArrayList<>();
        for (Object[] r : rows) {
            String rule = String.valueOf(r[0]);
            long cnt = ((Number) r[1]).longValue();
            out.add(new CountItem(rule, cnt));
        }
        return out;
    }

    // (необязательно для графиков, но пусть будет)
    @GetMapping("/blocked/by-strategy")
    public List<CountItem> blockedByStrategy() {
        List<Object[]> rows = blockedRepo.countByStrategy();
        List<CountItem> out = new ArrayList<>();
        for (Object[] r : rows) {
            String strategy = String.valueOf(r[0]);
            long cnt = ((Number) r[1]).longValue();
            out.add(new CountItem(strategy, cnt));
        }
        return out;
    }

    // =========================
    // Winrate по стратегиям
    // UI ждёт: [{strategy,tp,totalClosed,winrate}, ...]
    // Считаем ТОЛЬКО закрытые: TP/SL/BE
    // =========================
    @GetMapping("/winrate/by-strategy")
    public List<WinrateItem> winrateByStrategy() {

        var trades = tradeRepo.findAll();

        // strategy -> [tp, totalClosed]
        Map<String, long[]> map = new LinkedHashMap<>();

        for (var t : trades) {
            String strategy = (t.getStrategy() == null || t.getStrategy().isBlank())
                    ? "UNKNOWN"
                    : t.getStrategy().toUpperCase();

            String status = (t.getStatus() == null)
                    ? ""
                    : t.getStatus().toUpperCase();

            // считаем только закрытые сделки
            if (!(status.equals("TP") || status.equals("SL") || status.equals("BE"))) {
                continue;
            }

            map.putIfAbsent(strategy, new long[]{0, 0});
            map.get(strategy)[1]++; // totalClosed

            if (status.equals("TP")) {
                map.get(strategy)[0]++; // tp
            }
        }

        List<WinrateItem> out = new ArrayList<>();
        for (var e : map.entrySet()) {
            long tp = e.getValue()[0];
            long totalClosed = e.getValue()[1];
            double wr = totalClosed == 0 ? 0 : (tp * 100.0 / totalClosed);
            out.add(new WinrateItem(e.getKey(), tp, totalClosed, wr));
        }

        return out;
    }
}
