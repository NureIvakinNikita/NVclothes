package com.example.nvclothes.model;

public enum Size {

    XXL("XXL"),
    XL("XL"),
    L("L"),
    M("M"),
    S("S"),
    XS("XS"),
    XXS("XXS"),
    US_8("US 8"),
    US_8_5("US 8,5"),
    US_9("US 9"),
    US_9_5("US 9,5"),
    US_10("US 10");

    private String displayName;

    private Size(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static Size fromDisplayName(String displayName) {
        for (Size size : values()) {
            if (size.getDisplayName().equals(displayName)) {
                return size;
            }
        }
        throw new IllegalArgumentException("No Brand with display name: " + displayName);
    }
}
