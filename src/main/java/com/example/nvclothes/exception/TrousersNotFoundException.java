package com.example.nvclothes.exception;

public class TrousersNotFoundException   extends Exception {
    public TrousersNotFoundException(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public String toString() {
        return "Trainers weren't found";
    }
}