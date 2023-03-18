package com.example.nvclothes.exception;

public class AccessoryNotFoundException  extends Exception {
    public AccessoryNotFoundException(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public String toString() {
        return "Accessory wasn't found";
    }
}