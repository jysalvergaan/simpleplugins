package com.grandmaxd.homes;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandTpa implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] ss) {

        Player tp = (Player) sender;
        tp.sendMessage(ChatColor.WHITE + "You have accepted the teleportation request.");

        return false;
    }
}