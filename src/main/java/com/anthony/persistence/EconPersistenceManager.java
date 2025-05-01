package com.anthony.persistence;

import com.anthony.EconData;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
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

}
