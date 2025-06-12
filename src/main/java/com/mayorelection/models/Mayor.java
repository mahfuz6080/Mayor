package com.mayorelection.models;

public class Mayor {
    private final String name;
    private final String perk;

    public Mayor(String name, String perk) {
        this.name = name;
        this.perk = perk;
    }

    public String getName() { return name; }

    public String getPerk() { return perk; }
}
