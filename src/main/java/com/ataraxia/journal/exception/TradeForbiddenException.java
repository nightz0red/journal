package com.ataraxia.journal.exception;

import com.ataraxia.journal.rules.RuleCode;

public class TradeForbiddenException extends RuntimeException {

    private final RuleCode code;

    public TradeForbiddenException(RuleCode code, String message) {
        super(message);
        this.code = code;
    }

    public RuleCode getCode() {
        return code;
    }
}
