package com.grandmaxd.homes;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;

public class CommandPm implements CommandExecutor, Listener {
    public Player p;
    public Player pm;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (args.length < 1) {
            p.sendMessage(ChatColor.RED + "No player specified.");
            return false;
        }

        p = (Player) sender;
        pm = Bukkit.getServer().getPlayer(args[0]);


        if (pm.isOnline()) {
            pm.sendTitle("", ChatColor.YELLOW + p.getName() + ChatColor.WHITE + " mentioned you", 5, 60, 10);
        }
        else if (pm == null) {
            p.sendMessage(ChatColor.RED + "No such player found.");
        }
        else {
            p.sendMessage(ChatColor.RED + "The player is offline.");
        }

        return false;
    }

    @EventHandler
    public void onMention(AsyncPlayerChatEvent event) {
        String name = event.getMessage();
        Player ps = event.getPlayer();

        for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
            if (name.equalsIgnoreCase(pl.getName()))
                pl.sendTitle("", ChatColor.YELLOW + ps.getName() + ChatColor.WHITE + " mentioned you", 5, 60, 10);
        }
    }
}
