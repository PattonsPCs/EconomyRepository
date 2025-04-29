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
  }

}
