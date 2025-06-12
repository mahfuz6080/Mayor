package com.mayorelection.models;

public class Mayor {

    public enum Perk {
        DOUBLE_MINING_XP,
        CHEAPER_SHOPS,
        EXTRA_MOB_DROPS,
        FASTER_CROPS,
        SERVER_BOOST
    }

    private final String name;
    private Perk perk;

    public Mayor(String name) {
        this.name = name;
        this.perk = Perk.DOUBLE_MINING_XP; // default perk
    }

    public Mayor(String name, Perk perk) {
        this.name = name;
        this.perk = perk;
    }

    public String getName() {
        return name;
    }

    public Perk getPerk() {
        return perk;
    }

    public void setPerk(Perk perk) {
        this.perk = perk;
    }

    public String getFormattedPerk() {
        switch (perk) {
            case DOUBLE_MINING_XP:
                return "Double Mining XP";
            case CHEAPER_SHOPS:
                return "Cheaper Shops";
            case EXTRA_MOB_DROPS:
                return "Extra Mob Drops";
            case FASTER_CROPS:
                return "Faster Crops";
            case SERVER_BOOST:
                return "Server Boost";
            default:
                return "Unknown Perk";
        }
    }
}
