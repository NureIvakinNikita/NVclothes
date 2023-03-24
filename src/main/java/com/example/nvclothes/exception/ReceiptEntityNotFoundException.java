package com.example.nvclothes.exception;

public class ReceiptEntityNotFoundException extends Exception {
    public ReceiptEntityNotFoundException(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public String toString() {
        return "Receipt wasn't found";
    }
}
