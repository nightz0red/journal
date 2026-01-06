package com.ataraxia.journal.controller.dto;

import java.util.ArrayList;
import java.util.List;

public class ValidationResult {
    private boolean allowed;
    private List<String> reasons = new ArrayList<>();

    public ValidationResult() {}

    public ValidationResult(boolean allowed, List<String> reasons) {
        this.allowed = allowed;
        this.reasons = reasons;
    }

    public boolean isAllowed() { return allowed; }
    public void setAllowed(boolean allowed) { this.allowed = allowed; }

    public List<String> getReasons() { return reasons; }
    public void setReasons(List<String> reasons) { this.reasons = reasons; }
}
