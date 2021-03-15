package com.grandmaxd.homes;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSpawn implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] ss) {

        Player p = (Player) sender;
        p.sendMessage(ChatColor.YELLOW + "Teleporting to the spawn...");
        Homes.prevLocMap.putIfAbsent(p, p.getLocation());
        p.teleport(Bukkit.getServer().getWorld("Try2").getSpawnLocation());

        return false;
    }
}