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
            file.getParentFile().mkdirs();
            plugin.saveResource(fileName, false);
        }
        config = YamlConfiguration.loadConfiguration(file);
        onLoad();
    }

    public void save(){
        try{
            config.save(file);
        } catch (Exception e){
            plugin.getLogger().severe(e.getMessage());
        }
    }

    public void reload(){
        config = YamlConfiguration.loadConfiguration(file);
    }


    public String getString(String path){
        return config.getString(path);
    }

    public int getInt(String path){
        return config.getInt(path);
    }

    public void set(String path, Object value){
        config.set(path, value);
    }

    public void onLoad(){}

}
