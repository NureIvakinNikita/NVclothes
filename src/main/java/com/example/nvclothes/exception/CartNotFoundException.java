package com.example.nvclothes.exception;

public class CartNotFoundException  extends Exception {
    public CartNotFoundException(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public String toString() {
        return "Cart wasn't found";
    }
}
