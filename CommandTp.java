package com.grandmaxd.homes;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CommandTp implements CommandExecutor, Listener {
    public static Player p;
    public static Player tp;
    public static Inventory inv;
    public static boolean tpPays = false;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (args.length < 1) {
            p.sendMessage(ChatColor.RED + "No player specified.");
            return false;
        }

        p = (Player) sender;
        tp = Bukkit.getServer().getPlayer(args[0]);

        if (p.getName().equals(tp.getName())) {
            p.sendMessage(ChatColor.RED + "You cannot teleport to yourself.");
            return false;
        }

        if (tp == null) {
            tp.sendMessage(ChatColor.RED + "There is no such player online.");
            return false;
        }

        inv = p.getInventory();

        if (inv.contains(Material.IRON_INGOT, 1)) {
            tp.sendMessage(ChatColor.YELLOW + p.getName() + ChatColor.WHITE + " requests to teleport to you, type "
                    + ChatColor.GREEN + "/tpa" + ChatColor.WHITE + " to accept or " + ChatColor.RED + "/tpd" + ChatColor.WHITE + " to deny.");

            p.sendMessage(ChatColor.WHITE + "Teleportation request sent to " + ChatColor.YELLOW + tp.getName());
        } else {
            p.sendMessage(ChatColor.RED + "You lack 1 iron ingot to pay for the teleportation, the payment request sent to the destination player.");
            tp.sendMessage(ChatColor.YELLOW + p.getName() + ChatColor.WHITE + " requests to teleport to you " + ChatColor.YELLOW
                    + "(You pay)" + ChatColor.WHITE +  ", type "
                    + ChatColor.GREEN + "/tpa" + ChatColor.WHITE + " to accept or " + ChatColor.RED + "/tpd" + ChatColor.WHITE + " to deny.");
            tpPays = true;
        }

        return false;
    }

    @EventHandler
    public void onTpa(PlayerCommandPreprocessEvent event) {

        String com = event.getMessage().substring(1);
        String tpaName = event.getPlayer().getName();

        if (com.equalsIgnoreCase("tpa")) {

            if (inv.contains(Material.IRON_INGOT, 1)) {
                if (tpaName.equals(tp.getName())) {
                    p.sendMessage(ChatColor.WHITE + "Teleportation request " + ChatColor.GREEN + "accepted.");
                    Homes.prevLocMap.putIfAbsent(p, p.getLocation());
                    p.teleport(tp.getLocation());

                    inv.removeItem(new ItemStack(Material.IRON_INGOT, 1));
                    p.updateInventory();
                    Homes.homes.set("iron", Integer.parseInt(Homes.homes.get("iron").toString()) + 1);

                    try {
                        Homes.homes.save(Homes.file);
                    } catch (Exception exc) {
                        p.sendMessage(ChatColor.RED + "Cannot load the homes file. j74" + exc.toString());
                    }
                }
            } else if (tpPays && tp.getInventory().contains(Material.IRON_INGOT, 1)) {
                p.sendMessage(ChatColor.WHITE + "Teleportation request " + ChatColor.GREEN + "accepted.");
                Homes.prevLocMap.putIfAbsent(p, p.getLocation());
                p.teleport(tp.getLocation());

                tpPays = false;

                tp.getInventory().removeItem(new ItemStack(Material.IRON_INGOT, 1));
                tp.updateInventory();
                Homes.homes.set("iron", Integer.parseInt(Homes.homes.get("iron").toString()) + 1);

                try {
                    Homes.homes.save(Homes.file);
                } catch (Exception exc) {
                    p.sendMessage(ChatColor.RED + "Cannot load the homes file. j89" + exc.toString());
                }
            } else {
                p.sendMessage(ChatColor.RED + "You both lack 1 iron ingot to pay for the teleportation.");
                tp.sendMessage(ChatColor.RED + "Teleportation is cancelled.");
            }
        }
    }

    @EventHandler
    public void onTpd(PlayerCommandPreprocessEvent event) {

        String com = event.getMessage().substring(1);
        if (com.equalsIgnoreCase("tpd"))
            p.sendMessage(ChatColor.WHITE + "Teleportation request " + ChatColor.RED + "denied.");

    }
}