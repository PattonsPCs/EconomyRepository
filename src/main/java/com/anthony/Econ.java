package com.anthony;

import com.anthony.commands.EconomyCommand;
import com.anthony.configuration.ShopConfig;
import com.anthony.persistence.EconPersistenceManager;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.IOException;



@SuppressWarnings("UnstableApiUsage")
public class Econ extends JavaPlugin {
  @Getter
  private ShopConfig shopConfig;
    private EconPersistenceManager persistenceManager;


  @Override
  public void onEnable() {
    getLogger().info("EconPlugin is starting...");
    EconData econData = new EconData();
    EconomyCommand econCommand = new EconomyCommand(econData);
    persistenceManager = new EconPersistenceManager(econData, this);
    shopConfig = new ShopConfig(this);
    shopConfig.load();

    this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, commands -> {
      Commands commandRegistrar = commands.registrar();
      commandRegistrar.register(econCommand.build());
    });



    try{
      persistenceManager.initialize();
      persistenceManager.loadBalancesFromFile();
      persistenceManager.startAutoSaveTask();

      getLogger().info("Persistence manager initialized.");
    } catch (IOException e){
      getLogger().severe("Failed to initialize persistence manager.");
      getServer().getPluginManager().disablePlugin(this);
    }



    getLogger().info("EconPlugin has been enabled!");
    shopConfig.reload();
  }




  @Override
  public void onDisable() {
    persistenceManager.stopAutoSaveTask();
    persistenceManager.saveBalancesToFile();
    getLogger().info("EconPlugin has been disabled!");
  }
}
