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
    private final ShopConfig shopConfig = new ShopConfig(this, "shop.yml");
    private EconData econData;

    @Override
    public void onEnable() {

        getLogger().info("EconPlugin is starting...");
        econData = new EconData();
        shopConfig.load();

        econData.loadAllBalances(accounts);
        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS,
                event ->{
                    event.registrar().register("buy", new BuyCommand(this, shopConfig, econData));
                    event.registrar().register("balance", new BalanceCommand(this));
                });

        getLogger().info("EconPlugin has been enabled!");
    }


    public Account getAccount(Player player){
        return accounts.computeIfAbsent(player.getUniqueId(), uuid -> {
            Account account = new Account(player);
            int balance = econData.loadBalance(uuid);
            account.setBalance(balance);
            return account;
        });
    }

    @Override
    public void onDisable(){
        for(Account account : accounts.values()){
            econData.saveAccount(account.getPlayerID(), account.getBalance());
        }
        getLogger().info("EconPlugin has been disabled!");
    }
}
