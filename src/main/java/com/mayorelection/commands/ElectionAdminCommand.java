package com.mayorelection.commands;

import com.mayorelection.MayorElection;
import org.bukkit.command.*;

public class ElectionAdminCommand implements CommandExecutor {
    private final MayorElection plugin;

    public ElectionAdminCommand(MayorElection plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("mayorelection.admin")) {
            sender.sendMessage("§cNo permission.");
            return true;
        }

        if (args.length == 1 && args[0].equalsIgnoreCase("start")) {
            plugin.startElection();
            sender.sendMessage("§aElection restarted.");
            return true;
        }

        sender.sendMessage("§cUsage: /electionadmin start");
        return true;
    }
}
