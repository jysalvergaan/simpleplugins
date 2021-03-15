package com.grandmaxd.homes;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;

public class CommandSwitchSleeping implements CommandExecutor, Listener {
    boolean checkForSleeping = true;

    @Override
    public boolean onCommand(CommandSender cs, Command c, String s, String [] args) {
        checkForSleeping = !checkForSleeping;

        if (checkForSleeping)
            Bukkit.broadcastMessage("Checking for sleeping players is enabled");
        else
            Bukkit.broadcastMessage("Checking for sleeping players is disabled");

        return false;
    }

    @EventHandler
    public void PlayerEnterBed (PlayerBedEnterEvent event) {
        if (checkForSleeping) {
            Player p = event.getPlayer();
            String playerName = p.getPlayerListName();

            if (event.getBedEnterResult() == PlayerBedEnterEvent.BedEnterResult.OK) {
                Bukkit.broadcastMessage(ChatColor.YELLOW + playerName + ChatColor.WHITE + " is sleeping, zzz..");
            }
        }
    }
}