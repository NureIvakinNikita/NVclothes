package com.example.nvclothes.model;

public enum ProductType {
    ACCESSORY("Accessories"),
    HOODIE("Hoodies"),
    TROUSERS("Trousers"),
    TSHIRT("T-shirts"),
    TRAINERS("Trainers");
    private String displayName;

    private ProductType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
