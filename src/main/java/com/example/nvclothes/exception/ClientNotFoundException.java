package com.example.nvclothes.exception;

public class ClientNotFoundException extends Exception{
    public ClientNotFoundException(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public String toString() {
        return "Student wasn't found";
    }
}