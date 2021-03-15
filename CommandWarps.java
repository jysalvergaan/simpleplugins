package com.grandmaxd.homes;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

public class CommandWarps implements CommandExecutor {

    public Player p;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        p = (Player) sender;

        try {
            Homes.homes.load(Homes.file);
        }
        catch (Exception exc) {
            //p.sendMessage(ChatColor.RED + "Cannot load the homes file. j20" + exc.toString());
        }

        Map<String, Object> warpsMap;
        warpsMap = Homes.homes.getConfigurationSection(p.getName()).getValues(true);

        String keys = "";
        boolean first = true;

        for (String key : warpsMap.keySet()) {
            if (!key.contains(".") && !key.equals("home")) {
                if (first) {
                    keys = keys + key;
                    first = false;
                } else
                    keys = keys + ", " + key;
            }
        }

        //String[] warpsList = new String[warpsMap.keySet().size()];
        //warpsList = warpsMap.keySet().toArray(warpsList);
        p.sendMessage(ChatColor.WHITE + "Your warps: " + keys);

        return false;
    }
}