package com.mayorelection;

import com.mayorelection.commands.ElectionCommand;
import com.mayorelection.commands.ElectionAdminCommand;
import com.mayorelection.listeners.GuiClickListener;
import com.mayorelection.models.Mayor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class MayorElection extends JavaPlugin {

    private final Map<UUID, String> votes = new HashMap<>();
    private final List<Mayor> candidates = new ArrayList<>();
    private Mayor currentMayor = null;
    private long electionEndTime;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getCommand("election").setExecutor(new ElectionCommand(this));
        getCommand("electionadmin").setExecutor(new ElectionAdminCommand(this));
        Bukkit.getPluginManager().registerEvents(new GuiClickListener(this), this);
        loadMayors();
        startElection();
    }

    public void loadMayors() {
        candidates.clear();
        candidates.add(new Mayor("Diana", "Mythological creatures spawn."));
        candidates.add(new Mayor("Aatrox", "Slayer XP bonus and cheaper."));
        candidates.add(new Mayor("Cole", "Boosted mining XP."));
        candidates.add(new Mayor("Derpy", "50% XP, disables auction."));
        candidates.add(new Mayor("Paul", "Buffs to dungeons."));
    }

    public void startElection() {
        votes.clear();
        electionEndTime = System.currentTimeMillis() + 3600_000L; // 1 hour
        Bukkit.getScheduler().runTaskLater(this, this::endElection, 3600 * 20L);
    }

    public void endElection() {
        Map<String, Integer> voteCount = new HashMap<>();
        for (String mayor : votes.values()) {
            voteCount.put(mayor, voteCount.getOrDefault(mayor, 0) + 1);
        }
        String winner = Collections.max(voteCount.entrySet(), Map.Entry.comparingByValue()).getKey();
        currentMayor = candidates.stream().filter(m -> m.getName().equals(winner)).findFirst().orElse(null);
        Bukkit.broadcastMessage("§aMayor Election Over! Winner: §e" + currentMayor.getName());
        Bukkit.broadcastMessage("§7Perk: " + currentMayor.getPerk());
        startElection(); // auto-restart
    }

    public List<Mayor> getCandidates() { return candidates; }

    public Map<UUID, String> getVotes() { return votes; }

    public Mayor getCurrentMayor() { return currentMayor; }
}
