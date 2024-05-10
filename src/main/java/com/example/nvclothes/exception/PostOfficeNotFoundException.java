package com.example.nvclothes.exception;

public class PostOfficeNotFoundException extends Exception {
    public PostOfficeNotFoundException(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public String toString() {
        return "Post office wasn't found";
    }
}