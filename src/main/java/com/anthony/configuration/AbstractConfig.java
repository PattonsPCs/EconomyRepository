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

    config = YamlConfiguration.loadConfiguration(file);
    plugin.getLogger().info("Loaded config file " + fileName + " from disk.");

    getPlugin().getLogger().info("Config file exist: " + file.exists());
    getPlugin().getLogger().info("Config file path: " + file.getAbsolutePath());

  }

  public void reload() {
    // If file exists, reload it; otherwise create it
    if (file.exists()) {
      config = YamlConfiguration.loadConfiguration(file);
      plugin.getLogger().info("Reloaded existing config file " + fileName);
    } else {
      // Handle missing file on reload
      plugin.getLogger().warning("Config file " + fileName + " missing, recreating...");
      if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
        plugin.getLogger().severe("Failed to create config directory for " + fileName);
      }
      plugin.saveResource(fileName, true); // Force overwrite
      if (file.exists()) {
        config = YamlConfiguration.loadConfiguration(file);
        plugin.getLogger().info("Created and loaded config file " + fileName);
      } else {
        plugin.getLogger().severe("Failed to create config file " + fileName);
      }
    }
  }

}
