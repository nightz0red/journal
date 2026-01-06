package com.ataraxia.journal.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "blocked_events")
public class BlockedEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdAt = LocalDateTime.now();

    private String pair;
    private String strategy;
    private String ruleCode;
    private String message;

    public Long getId() { return id; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public String getPair() { return pair; }
    public void setPair(String pair) { this.pair = pair; }

    public String getStrategy() { return strategy; }
    public void setStrategy(String strategy) { this.strategy = strategy; }

    public String getRuleCode() { return ruleCode; }
    public void setRuleCode(String ruleCode) { this.ruleCode = ruleCode; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
