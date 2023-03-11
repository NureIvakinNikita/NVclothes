package com.example.nvclothes.model;

public enum Brand {
    NIKE("Nike"),
    ADIDAS("Adidas"),
    THE_NORTH_FACE("The North Face "),
    TOMMY_HILFIGER("Tommy Hilfiger"),
    CHAMPION("Champion"),
    PUMA("Puma"),
    VANS("Vans"),
    CONVERSE("Converse"),
    NEW_BALANCE("New Balance"),
    LEVIS("Levi's"),
    GUESS("Guess"),
    STONE_ISLAND("Stone Island"),
    ARMANI("Armani"),
    DICKIES("Dickies"),
    FADED_FUTURE("Faded Future"),
    CLASSICS_77("Classics 77"),
    JEEMPERS_PEEPERS("Jeepers Peepers"),
    NEW_ERA_9TWENTY("New Era 9Twenty"),
    CALVIN_KLEIN_JEANS("Calvin Klein Jeans"),
    POLO_RALPH_LAUREN("Polo Ralph Lauren"),
    HUGO("HUGO"),
    EA7("EA7"),
    ASICS("Asics"),
    COLLUSION("COLLUSION"),
    NEW_LOOK("New Look"),
    CARHARTT("Carhartt");


    private String displayName;

    private Brand(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static Brand fromDisplayName(String displayName) {
        for (Brand brand : values()) {
            if (brand.getDisplayName().equals(displayName)) {
                return brand;
            }
        }
       throw new IllegalArgumentException("No Brand with display name: " + displayName);
    }


}
