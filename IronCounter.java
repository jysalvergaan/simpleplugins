package com.grandmaxd.homes;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import com.grandmaxd.homes.Homes;

import java.util.Collection;

public class IronCounter implements Listener {
    public Inventory invChest;
    public static Plugin plugin = Bukkit.getPluginManager().getPlugin("Homes");


    public static long countChestsIron() {
        long totalIronCur = 0;
        for (Chest ch : Homes.invListChests) {

            if (ch.getInventory().contains(Material.IRON_INGOT))
            {
                ItemStack[] items = ch.getInventory().getContents();

                for (ItemStack item : items)
                {
                    if ((item != null) && (item.getType() == Material.IRON_INGOT) && (item.getAmount() > 0))
                    {
                        totalIronCur += item.getAmount();
                    }
                }
            }
        }
        return totalIronCur;
    }

    public static long countPlayersInventoriesIron () {
        long totalIronCur = 0;

        final Collection<? extends Player> onlinePlayers = Bukkit.getOnlinePlayers();
        for (Player player : onlinePlayers) {
            if (player.getInventory().contains(Material.IRON_INGOT))
            {
                ItemStack[] items = player.getInventory().getContents();
                for (ItemStack item : items)
                {
                    if ((item != null) && (item.getType() == Material.IRON_INGOT) && (item.getAmount() > 0))
                    {
                        totalIronCur += item.getAmount();
                    }
                }
            }
        }

        return totalIronCur;
    }

    @EventHandler
    public void onOpenChest (InventoryOpenEvent event) {

        //Player p = (Player) event.getPlayer();
        boolean add = true;
        boolean addLeft = true;
        boolean addRight = true;

        if (event.getInventory().getHolder() instanceof Chest) {

            Chest chest = (Chest) event.getInventory().getHolder();

            if (event.getInventory().contains(Material.IRON_INGOT)) {

                for (Chest ch : Homes.invListChests) {
                    if (ch.getMetadata("iron").get(0).asInt() == chest.getMetadata("iron").get(0).asInt()) {
                        add = false;
                    }
                }

                if (add) {
                    chest.setMetadata("iron", new FixedMetadataValue(plugin, Homes.chestsNum + 1));
                    Homes.chestsNum += 1;
                }


            }
        }

        if (event.getInventory().getHolder() instanceof DoubleChest) {

            DoubleChest doubleChest = (DoubleChest) event.getInventory().getHolder();
            Chest left = (Chest) doubleChest.getLeftSide();
            Chest right = (Chest) doubleChest.getRightSide();

            if (event.getInventory().contains(Material.IRON_INGOT)) {

                //LEFT
                for (Chest ch : Homes.invListChests) {
                    if (ch.getMetadata("iron").get(0).asInt() == left.getMetadata("iron").get(0).asInt()) {
                        addLeft = false;
                    }
                }

                if (addLeft) {
                    left.setMetadata("iron", new FixedMetadataValue(plugin, Homes.chestsNum + 1));
                    Homes.chestsNum += 1;
                }

                //RIGHT
                for (Chest ch : Homes.invListChests) {
                    if (ch.getMetadata("iron").get(0).asInt() == right.getMetadata("iron").get(0).asInt()) {
                        addRight = false;
                    }
                }

                if (addRight) {
                    right.setMetadata("iron", new FixedMetadataValue(plugin, Homes.chestsNum + 1));
                    Homes.chestsNum += 1;
                }

            }
        }

    }

}
