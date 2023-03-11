package com.example.nvclothes.model;

public enum Attribute {
    SIZE("SIZE"),
    COST("COST"),
    BRAND("BRAND"),
    AMOUNT("AMOUNT"),
    NAME("NAME");

    private String displayName;

    private Attribute(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
