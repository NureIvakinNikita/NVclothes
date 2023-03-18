package com.example.nvclothes.exception;

public class TShirtNotFoundException  extends Exception {
    public TShirtNotFoundException(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public String toString() {
        return "T-shirt wasn't found";
    }
}