package com.grandmaxd.homes;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandHome implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] ss) {
        Player p = (Player) sender;

        int x = Integer.parseInt(Homes.homes.get(p.getName() + ".home" + ".x").toString());
        int y = Integer.parseInt(Homes.homes.get(p.getName() + ".home" + ".y").toString());
        int z = Integer.parseInt(Homes.homes.get(p.getName() + ".home" + ".z").toString());
        String worldS = Homes.homes.get(p.getName() + ".home" + ".world").toString();
        World world = Bukkit.getServer().getWorld(worldS);

        Location loc = new Location(world, (double) x, (double) y, (double) z);

        if (loc != null) {
            Homes.prevLocMap.putIfAbsent(p, p.getLocation());

            p.sendMessage(ChatColor.YELLOW + "Teleporting to your home...");
            p.teleport(loc);
        }

        return false;
    }
}
