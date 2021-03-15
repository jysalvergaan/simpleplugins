package com.grandmaxd.homes;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandBack implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] ss) {

        Player p = (Player) sender;
        if (Homes.prevLocMap.containsKey(p)) {
            p.sendMessage(ChatColor.YELLOW + "Teleporting to your previous location...");
            p.teleport(Homes.prevLocMap.get(p));
        }
        else
            p.sendMessage(ChatColor.RED + "No previous location identified.");

        return false;
    }
}
