package com.mayorelection;

import com.mayorelection.models.Mayor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.stream.Collectors;

public class MayorElection extends JavaPlugin {

    private List<Mayor> candidates = new ArrayList<>();
    private Map<UUID, String> votes = new HashMap<>();
    private Mayor currentMayor;
    private Mayor runnerUp;
    private long electionStartTime;
    private int currentYear = 1;
    private int electionDurationMinutes = 5; // default

    @Override
    public void onEnable() {
        saveDefaultConfig();
        electionDurationMinutes = getConfig().getInt("election-duration", 5);

        getCommand("vote").setExecutor(this);
        getCommand("electioninfo").setExecutor(this);
        getCommand("addcandidate").setExecutor(this);

        startElection();
    }

    private void startElection() {
        votes.clear();
        electionStartTime = System.currentTimeMillis();
        long durationTicks = electionDurationMinutes * 60L * 20L;

        Bukkit.broadcastMessage(ChatColor.GOLD + "§lMayor Election has started! §r§6Year " + currentYear);

        Bukkit.getScheduler().runTaskLater(this, this::endElection, durationTicks);
    }

    private void endElection() {
        Map<String, Integer> results = tallyVotes();

        if (results.isEmpty()) {
            Bukkit.broadcastMessage(ChatColor.RED + "No votes were cast. No mayor elected.");
            startElection();
            return;
        }

        List<Map.Entry<String, Integer>> sorted = results.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toList());

        currentMayor = findCandidate(sorted.get(0).getKey());
        runnerUp = sorted.size() > 1 ? findCandidate(sorted.get(1).getKey()) : null;

        Bukkit.broadcastMessage("§aElection Results:");
        Bukkit.broadcastMessage("§eMayor: §6" + currentMayor.getName());
        if (runnerUp != null) {
            Bukkit.broadcastMessage("§eClerk: §6" + runnerUp.getName());
        }

        currentYear++;
        startElection();
    }

    private Map<String, Integer> tallyVotes() {
        Map<String, Integer> counts = new HashMap<>();
        for (String vote : votes.values()) {
            counts.put(vote, counts.getOrDefault(vote, 0) + 1);
        }
        return counts;
    }

    private Mayor findCandidate(String name) {
        return candidates.stream()
                .filter(c -> c.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    private String formatTimeRemaining() {
        long endMillis = electionStartTime + electionDurationMinutes * 60L * 1000L;
        long remaining = endMillis - System.currentTimeMillis();
        if (remaining <= 0) return "00:00";
        long seconds = remaining / 1000;
        long minutes = seconds / 60;
        seconds %= 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        switch (cmd.getName().toLowerCase()) {
            case "vote":
                if (!(sender instanceof Player)) {
                    sender.sendMessage("Only players can vote.");
                    return true;
                }
                if (args.length != 1) {
                    sender.sendMessage("§cUsage: /vote <candidate>");
                    return true;
                }
                String choice = args[0];
                if (findCandidate(choice) == null) {
                    sender.sendMessage("§cNo such candidate.");
                    return true;
                }
                votes.put(((Player) sender).getUniqueId(), choice);
                sender.sendMessage("§aVote recorded for §e" + choice);
                return true;

            case "addcandidate":
                if (args.length != 1) {
                    sender.sendMessage("§cUsage: /addcandidate <name>");
                    return true;
                }
                String name = args[0];
                Mayor candidate = new Mayor(name);
                candidates.add(candidate);
                sender.sendMessage("§aCandidate §e" + name + " §aadded.");
                return true;

            case "electioninfo":
                if (currentMayor != null)
                    sender.sendMessage("§6Current Mayor: §e" + currentMayor.getName());
                if (runnerUp != null)
                    sender.sendMessage("§6Clerk: §e" + runnerUp.getName());
                sender.sendMessage("§6Year: §e" + currentYear);
                sender.sendMessage("§6Time Remaining: §e" + formatTimeRemaining());
                return true;
        }
        return false;
    }
}
