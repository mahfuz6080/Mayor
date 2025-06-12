package com.mayorelection.commands;

import com.mayorelection.MayorElection;
import com.mayorelection.models.Mayor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;

public class ElectionCommand implements CommandExecutor {
    private final MayorElection plugin;

    public ElectionCommand(MayorElection plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;

        Player player = (Player) sender;
        Inventory gui = Bukkit.createInventory(null, 9, "Vote for Mayor");

        for (Mayor mayor : plugin.getCandidates()) {
            ItemStack item = new ItemStack(Material.NAME_TAG);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.GOLD + mayor.getName());
            meta.setLore(java.util.Arrays.asList(ChatColor.GRAY + mayor.getPerk()));
            item.setItemMeta(meta);
            gui.addItem(item);
        }

        player.openInventory(gui);
        return true;
    }
}
