package com.mayorelection.models;

public enum Mayor {
    DIANA("Diana", "Extra pet drops"),
    PAUL("Paul", "Dungeon reward increase"),
    AATROX("Aatrox", "Slayer XP discount"),
    MARINA("Marina", "Fishing festival"),
    FOXXY("Foxxy", "Election bonus"),
    JERRY("Jerry", "Special perks"),
    COLE("Cole", "Mining fiesta"),
    DERPY("Derpy", "Double XP"),
    TECHNO("Technoblade", "Global bonuses");

    private final String name;
    private final String perk;

    Mayor(String name, String perk) {
        this.name = name;
        this.perk = perk;
    }

    public String getName() {
        return name;
    }

    public String getPerk() {
        return perk;
    }
}
