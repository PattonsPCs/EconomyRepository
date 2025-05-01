package com.anthony;

import com.anthony.commands.AddMoney;
import com.anthony.commands.BalanceCommand;
import com.anthony.commands.BuyCommand;
import com.anthony.commands.ShopCommand;
import com.anthony.configuration.ShopConfig;
import com.anthony.events.MobKillListener;
import com.anthony.persistence.EconPersistenceManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SuppressWarnings("UnstableApiUsage")
public class Econ extends JavaPlugin {
  @Getter
  private final Map<UUID, Account> accounts = new HashMap<>();
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
      getLogger().severe("Failed to initialze persistence manager.");
      getServer().getPluginManager().disablePlugin(this);
    }

    getServer().getPluginManager().registerEvents(new MobKillListener(econData), this);

    this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS,
        event -> {
          event.registrar().register("buy", "Buy something from the shop.", new BuyCommand(this, econData));
          event.registrar().register("balance", "Check your balance.", new BalanceCommand(this));
          event.registrar().register("addmoney", "Add money to an account", new AddMoney(this));
          event.registrar().register("shop", "See items in the shop", new ShopCommand(this));
        });

    getLogger().info("EconPlugin has been enabled!");
    shopConfig.reload();
    shopConfig.debugConfig();
  }


  public Account getAccount(Player player) {
    return accounts.computeIfAbsent(player.getUniqueId(), uuid -> {
      Account account = new Account(player);
      int balance = econData.getBalance(uuid);
      account.setBalance(balance);
      return account;
    });
  }

  @Override
  public void onDisable() {
    for (Account account : accounts.values()) {
      econData.setBalance(account.getPlayerID(), account.getBalance());
    }
    persistenceManager.stopAutoSaveTask();
    persistenceManager.saveBalancesToFile();
    getLogger().info("EconPlugin has been disabled!");
  }
}
