package com.example.nvclothes.exception;

public class OrderEntityNotFoundException extends Exception {
    public OrderEntityNotFoundException(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public String toString() {
        return "Order wasn't found";
    }
}
