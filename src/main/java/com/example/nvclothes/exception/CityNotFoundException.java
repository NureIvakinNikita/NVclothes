package com.example.nvclothes.exception;

public class CityNotFoundException extends Exception {
    public CityNotFoundException(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public String toString() {
        return "City wasn't found";
    }
}
