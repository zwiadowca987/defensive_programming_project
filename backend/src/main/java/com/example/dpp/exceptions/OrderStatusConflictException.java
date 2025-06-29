package com.example.dpp.exceptions;

public class OrderStatusConflictException extends RuntimeException {
    public OrderStatusConflictException(String message) {
        super(message);
    }
}
