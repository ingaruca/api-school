package com.ingaruca.api.school.application.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse httpResponse = exchange.getResponse();
        setResponseStatus(httpResponse, ex);
        return httpResponse.writeWith(Mono.fromSupplier(() -> {
            DataBufferFactory bufferFactory = httpResponse.bufferFactory();
            try {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("message", ex.getMessage());
                HttpHeaders headers = httpResponse.getHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                return bufferFactory.wrap(new ObjectMapper().writeValueAsBytes(errorResponse));
            } catch (JsonProcessingException e) {
                return bufferFactory.wrap(new byte[0]);
            }
        }));
    }

    private void setResponseStatus(ServerHttpResponse httpResponse, Throwable ex) {
        if (ex instanceof InterruptedException) {
            httpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
        } else {
            httpResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}