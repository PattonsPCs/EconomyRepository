package com.anthony;

import com.anthony.commands.*;
import com.anthony.configuration.ShopConfig;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SuppressWarnings("UnstableApiUsage")
public class Econ extends JavaPlugin{
    @Getter
    private final Map<UUID, Account> accounts = new HashMap<>();
    private Database database;
    private final ShopConfig shopConfig = new ShopConfig(this, "shop.yml");


    @Override
    public void onEnable() {
        database = new Database();
        database.connect();
        database.createTable();

        shopConfig.load();
        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS,
                event ->{
                    event.registrar().register("buy", new BuyCommand(this, shopConfig));
                    event.registrar().register("balance", new BalanceCommand(this));
                });

        getLogger().info("EconPlugin has been enabled!");
    }

    public Account getAccount(Player player){
        return accounts.computeIfAbsent(player.getUniqueId(), uuid -> {
            Account account = new Account(player);
            int balance = database.loadBalance(uuid);
            account.setBalance(balance);
            return account;
        });
    }

    @Override
    public void onDisable(){
        for(Account account : accounts.values()){
            database.saveAccount(account.getPlayerID(), account.getBalance());
        }
        database.close();
        getLogger().info("EconPlugin has been disabled!");
    }
}
