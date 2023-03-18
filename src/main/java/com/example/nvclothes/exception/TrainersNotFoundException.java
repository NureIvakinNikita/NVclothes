package com.example.nvclothes.exception;

public class TrainersNotFoundException  extends Exception {
    public TrainersNotFoundException(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public String toString() {
        return "Trainers weren't found";
    }
}