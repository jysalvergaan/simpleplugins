package com.grandmaxd.homes;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CommandWarp implements CommandExecutor {

    public Player p;
    public Inventory inv;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        p = (Player) sender;

        try {
            Homes.homes.load(Homes.file);
        }
        catch (Exception exc) {
            //p.sendMessage(ChatColor.RED + "Cannot load the homes file. j20" + exc.toString());
        }


        //SET
        if (args[0].equalsIgnoreCase("set") && args[1] != null) {
            Homes.homes.set(p.getName() + "." + args[1] + ".x", p.getLocation().getBlockX());
            Homes.homes.set(p.getName() + "." + args[1] + ".y", p.getLocation().getBlockY());
            Homes.homes.set(p.getName() + "." + args[1] + ".z", p.getLocation().getBlockZ());
            Homes.homes.set(p.getName() + "." + args[1] + ".world", p.getWorld().getName());
            try {
                Homes.homes.save(Homes.file);
                p.sendMessage(ChatColor.YELLOW + "Warp point added.");
            } catch (Exception exc) {
                p.sendMessage(ChatColor.RED + "Cannot load the homes file. j33" + exc.toString());
            }
            return false;
        }

        //REMOVE
        else if (args[0].equalsIgnoreCase("remove") && args[1] != null) {
            if(Homes.homes.contains(p.getName() + "." + args[1])) {
                Homes.homes.set(p.getName() + "." + args[1], null);
                Homes.homes.set(p.getName() + "." + args[1], null);
                Homes.homes.set(p.getName() + "." + args[1], null);
                Homes.homes.set(p.getName() + "." + args[1], null);

                try {
                    Homes.homes.save(Homes.file);
                    p.sendMessage(ChatColor.YELLOW + "Warp point removed.");
                }
                catch (Exception exc) {
                    p.sendMessage(ChatColor.RED + "Cannot load the homes file. j33" + exc.toString());
                }

            } else {
                p.sendMessage(ChatColor.RED + "No warp found.");
            }

            return false;
        }


        //TELEPORT
        int x;
        int y;
        int z;
        String worldS;

        if (Homes.homes.contains(p.getName() + "." +  args[0])) {
            x = Integer.parseInt(Homes.homes.get(p.getName() + "." +  args[0] + ".x").toString());
            y = Integer.parseInt(Homes.homes.get(p.getName() + "." +  args[0] + ".y").toString());
            z = Integer.parseInt(Homes.homes.get(p.getName() + "." +  args[0] + ".z").toString());
            worldS = Homes.homes.get(p.getName() + "." +  args[0] + ".world").toString();
        }
        else {
            p.sendMessage(ChatColor.RED + "No warp found.");
            return false;
        }

        World world = Bukkit.getServer().getWorld(worldS);
        Location loc = new Location(world, (double) x, (double) y, (double) z);

        inv = p.getInventory();

        if (inv.contains(Material.IRON_INGOT, 1)) {
            if (loc != null) {
                Homes.prevLocMap.putIfAbsent(p, p.getLocation());

                p.sendMessage(ChatColor.YELLOW + "Teleporting to the warp point...");
                p.teleport(loc);
                inv.removeItem(new ItemStack(Material.IRON_INGOT, 1));
                p.updateInventory();
            }
        } else {
            p.sendMessage(ChatColor.RED + "You lack 1 iron ingot to pay for the teleportation.");
            return false;
        }


        return false;
    }
}
