package com.grandmaxd.homes;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSetHome implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] ss) {
        Player p = (Player) sender;

        try {
            Homes.homes.load(Homes.file);
        }
        catch (Exception exc) {
            //p.sendMessage(ChatColor.RED + "Cannot load the homes file. j20" + exc.toString());
        }

        Homes.homes.set(p.getName() + ".home" + ".x", p.getLocation().getBlockX());
        Homes.homes.set(p.getName() + ".home" + ".y", p.getLocation().getBlockY());
        Homes.homes.set(p.getName() + ".home" + ".z", p.getLocation().getBlockZ());
        Homes.homes.set(p.getName() + ".home" + ".world", p.getWorld().getName());

        try {
            Homes.homes.save(Homes.file);
            p.sendMessage(ChatColor.YELLOW + "Your home point is set.");
        }
        catch (Exception exc) {
            p.sendMessage(ChatColor.RED + "Cannot load the homes file. j33" + exc.toString());
        }


        return false;
    }
}
