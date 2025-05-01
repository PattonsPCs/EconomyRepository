package com.anthony.persistence;

import com.anthony.EconData;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Getter
public class EconPersistenceManager {
    private final EconData econData;
    private final JavaPlugin plugin;
    private final File file;
    private final FileConfiguration config;
    private BukkitTask autoSaveTask;

    public EconPersistenceManager(EconData econData, JavaPlugin plugin) {
        this.econData = econData;
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), "accounts.yml");
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public void initialize() throws IOException {
        if(!file.exists()){
            if(file.createNewFile()){
                plugin.getLogger().info("Created new accounts.yml file.");
            }
        }
    }

    public void loadBalancesFromFile(){
        for(String id : config.getKeys(false)){
            UUID uuid = UUID.fromString(id);
            int balance = config.getInt(id, 0);
            econData.setBalance(uuid, balance);
            plugin.getLogger().info("Loaded account " + uuid + " with balance " + balance);
        }
    }

    public void saveBalancesToFile(){
        Map<UUID, Integer> accounts = econData.getAllBalances();
        accounts.forEach((uuid, balance) -> config.set(uuid.toString(), balance));
        try {
            config.save(file);
            plugin.getLogger().info("Saved " + accounts.size() + " accounts to accounts.yml file.");
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to save accounts.yml file.");
        }
    }

    public void startAutoSaveTask(){
        if(autoSaveTask != null && !autoSaveTask.isCancelled()){
            autoSaveTask.cancel();
        }

        autoSaveTask = Bukkit.getScheduler().runTaskTimer(
                plugin,
                this::saveBalancesToFile,
                0L,
                20L * 60L * 3L
        );
        plugin.getLogger().info("Saving balances...");
    }

    public void stopAutoSaveTask(){
        if(autoSaveTask != null && !autoSaveTask.isCancelled()){
            autoSaveTask.cancel();
        }
        plugin.getLogger().info("Stopped saving balances.");
    }



}
