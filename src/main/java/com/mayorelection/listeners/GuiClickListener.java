package com.mayorelection.listeners;

import com.mayorelection.MayorElection;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class GuiClickListener implements Listener {
    private final MayorElection plugin;

    public GuiClickListener(MayorElection plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().equals("Vote for Mayor")) return;
        event.setCancelled(true);
        if (!(event.getWhoClicked() instanceof Player)) return;

        Player player = (Player) event.getWhoClicked();
        ItemStack clicked = event.getCurrentItem();
        if (clicked == null || !clicked.hasItemMeta()) return;

        String mayorName = ChatColor.stripColor(clicked.getItemMeta().getDisplayName());
        plugin.getVotes().put(player.getUniqueId(), mayorName);
        player.sendMessage("§aYou voted for §e" + mayorName);
        player.closeInventory();
    }
}
