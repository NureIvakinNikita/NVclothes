package com.example.nvclothes.exception;

public class HoodieNotFoundException extends Exception {
    public HoodieNotFoundException(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public String toString() {
        return "Hoodie wasn't found";
    }
}