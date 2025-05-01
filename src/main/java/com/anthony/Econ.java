package com.anthony;

import com.anthony.commands.*;
import com.anthony.configuration.ShopConfig;
import com.anthony.events.MobKillListener;
import com.anthony.persistence.EconPersistenceManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.IOException;


@SuppressWarnings("UnstableApiUsage")
public class Econ extends JavaPlugin {
  @Getter
  private ShopConfig shopConfig;
  private EconData econData;
  private EconPersistenceManager persistenceManager;

  @Override
  public void onEnable() {
    getLogger().info("EconPlugin is starting...");
    econData = new EconData();
    persistenceManager = new EconPersistenceManager(econData, this);
    shopConfig = new ShopConfig(this);
    shopConfig.load();

    try{
      persistenceManager.initialize();
      persistenceManager.loadBalancesFromFile();
      persistenceManager.startAutoSaveTask();
      getLogger().info("Persistence manager initialized.");
    } catch (IOException e){
      getLogger().severe("Failed to initialize persistence manager.");
      getServer().getPluginManager().disablePlugin(this);
    }

    getServer().getPluginManager().registerEvents(new MobKillListener(econData), this);

    this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS,
        event -> {
          event.registrar().register("buy", "Buy something from the shop.", new BuyCommand(this, econData));
          event.registrar().register("balance", "Check your balance.", new BalanceCommand(econData));
          event.registrar().register("addmoney", "Add money to an account", new AddMoney(econData));
          event.registrar().register("shop", "See items in the shop", new ShopCommand(this));
          event.registrar().register("save", "Saves balances", new SaveCommand(persistenceManager));
        });

    getLogger().info("EconPlugin has been enabled!");
    shopConfig.reload();
    shopConfig.debugConfig();
  }


  @Override
  public void onDisable() {
    persistenceManager.stopAutoSaveTask();
    persistenceManager.saveBalancesToFile();
    getLogger().info("EconPlugin has been disabled!");
  }
}
