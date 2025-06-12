private String perk;

public Mayor(String name) {
    this.name = name;
    this.perk = "Default Perk"; // or whatever logic
}

public String getPerk() {
    return perk;
}

public void setPerk(String perk) {
    this.perk = perk;
}
