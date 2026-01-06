package com.ataraxia.journal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TradeForbiddenException.class)
    public ResponseEntity<Map<String, Object>> handleForbidden(TradeForbiddenException ex) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", 403);
        body.put("error", "FORBIDDEN");
        body.put("code", ex.getCode().name());
        body.put("message", ex.getMessage());

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(body);
    }
}
