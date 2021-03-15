package com.grandmaxd.homes;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Chest;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;


public class Homes extends JavaPlugin {
    //public File folder = this.getDataFolder();
    public static File file = new File("homes.yml");
    public static YamlConfiguration homes = new YamlConfiguration();
    public static HashMap<Player, Location> prevLocMap = new HashMap<Player, Location>();
    public static final int BASE_IRON_COST = 1;
    public static long totalIron;
    public static int chestsNum = 0;
    public static int doubleChestsNum = 0;
    public static ArrayList<Chest> invListChests = new ArrayList<>();
    public static ArrayList<Inventory> invListPlayers = new ArrayList<>();


    @Override
    public void onEnable () {

        CommandSwitchSleeping commandSwitchSleeping = new CommandSwitchSleeping();
        CommandSetHome commandSetHome = new CommandSetHome();
        CommandHome commandHome = new CommandHome();
        CommandBack commandBack = new CommandBack();
        CommandSpawn commandSpawn = new CommandSpawn();
        CommandTp commandTp = new CommandTp();
        CommandTpa commandTpa = new CommandTpa();
        CommandTpd commandTpd = new CommandTpd();
        CommandPm commandPm = new CommandPm();
        CommandWarp commandWarp = new CommandWarp();
        CommandWarps commandWarps = new CommandWarps();
        IronCounter ironCounter = new IronCounter();

        this.getCommand("whoissleeping").setExecutor(commandSwitchSleeping);
        this.getCommand("sethome").setExecutor(commandSetHome);
        this.getCommand("home").setExecutor(commandHome);
        this.getCommand("back").setExecutor(commandBack);
        this.getCommand("spawn").setExecutor(commandSpawn);
        this.getCommand("tp").setExecutor(commandTp);
        this.getCommand("tpa").setExecutor(commandTpa);
        this.getCommand("tpd").setExecutor(commandTpd);
        this.getCommand("pm").setExecutor(commandPm);
        this.getCommand("warp").setExecutor(commandWarp);
        this.getCommand("warps").setExecutor(commandWarps);

        getServer().getPluginManager().registerEvents(commandTp, this);
        getServer().getPluginManager().registerEvents(commandSwitchSleeping, this);
        getServer().getPluginManager().registerEvents(commandPm, this);
        getServer().getPluginManager().registerEvents(ironCounter, this);


        try {
            file.createNewFile();
        }
        catch (Exception exc) {
            Bukkit.broadcastMessage(ChatColor.RED + "Cannot create the homes file. j46; " + exc.toString());
        }

        try {
            homes.load(file);
        }
        catch (Exception exc) {
            Bukkit.broadcastMessage(ChatColor.RED + "Cannot load the homes file. j53; " + exc.toString());
        }

        if (homes.get("iron") == null) {
            homes.set("iron", 0);
        }

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                //Bukkit.broadcastMessage(ChatColor.YELLOW + "Chests counted: " + invList.size());
                totalIron = IronCounter.countChestsIron() + IronCounter.countPlayersInventoriesIron();
                //Bukkit.broadcastMessage(ChatColor.YELLOW + "The inflation level has been counted.");
                homes.set("total_iron", totalIron);
                try {
                    Homes.homes.save(Homes.file);
                }
                catch (Exception exc) {

                }
            }
        }, 600L, 6000L);

    }

}
