package com.example.nvclothes.exception;

public class OrderProductNotFoundException extends Exception {
    public OrderProductNotFoundException(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public String toString() {
        return "OrderProductEntity wasn't found";
    }
}
