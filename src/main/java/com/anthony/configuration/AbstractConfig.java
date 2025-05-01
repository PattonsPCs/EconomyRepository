package com.anthony.configuration;

import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;


@Getter
public class AbstractConfig {

  private final File file;
  private final String fileName;
  private final JavaPlugin plugin;
  private FileConfiguration config;

  public AbstractConfig(JavaPlugin plugin, String fileName) {
    this.plugin = plugin;
    this.fileName = fileName;
    this.file = new File(plugin.getDataFolder(), fileName);
  }

  public void load() {
    if (!file.exists()) {
      if (!(file.getParentFile().mkdirs())) {
        plugin.getLogger().severe("Failed to create config directory.");
      }
      plugin.saveResource(fileName, false);
    }
    if( !file.exists() ) {
      plugin.saveResource(fileName, false);
      plugin.getLogger().severe("Failed to save config file.");
    }
    config = YamlConfiguration.loadConfiguration(file);
    plugin.getLogger().info("Loaded config file " + fileName + " from disk.");

    getPlugin().getLogger().info("Config file exist: " + file.exists());
    getPlugin().getLogger().info("Config file path: " + file.getAbsolutePath());

  }

  public void reload(){
    load();
  }

}
