package com.anthony.configuration;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class AbstractConfig {
    private final File file;
    @Getter @Setter
    private FileConfiguration config;
    private final String fileName;
    @Getter
    private final JavaPlugin plugin;

    public AbstractConfig(JavaPlugin plugin, String fileName) {
        this.plugin = plugin;
        this.fileName = fileName;
        this.file = new File(plugin.getDataFolder(), fileName);
    }

    public void load(){
        if(!file.exists()){
           if(!(file.getParentFile().mkdirs())){
               plugin.getLogger().severe("Failed to create config directory.");
           }
            plugin.saveResource(fileName, false);
        }
        config = YamlConfiguration.loadConfiguration(file);
        onLoad();
    }


    public void onLoad(){}

}
