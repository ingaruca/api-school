package com.ingaruca.api.school.application.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;

@ControllerAdvice
public class ValidationHandler {

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<List<Map<String, String>>> handleException(WebExchangeBindException e) {
        return ResponseEntity.badRequest()
                .body(e.getFieldErrors()
                        .stream()
                        .map(fieldError -> {
                            Map<String, String> mapException = new HashMap<>();
                            mapException.put("field", fieldError.getField());
                            mapException.put("message",  fieldError.getDefaultMessage());
                            return mapException;
                        })
                        .collect(Collectors.toList()));
    }

}