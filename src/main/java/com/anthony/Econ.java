package com.anthony;

import com.anthony.commands.*;
import com.anthony.configuration.ShopConfig;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SuppressWarnings("UnstableApiUsage")
public class Econ extends JavaPlugin{
    @Getter
    private final Map<UUID, Account> accounts = new HashMap<>();

    @Override
    public void onEnable() {
        ShopConfig shopConfig = new ShopConfig(this, "shop.yml");
        shopConfig.load();
        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS,
                event -> event.registrar().register("buy", new BuyCommand(this, shopConfig)));
        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS,
                event -> event.registrar().register("balance", new BalanceCommand(this)));
        saveConfig();
        reloadConfig();
        getLogger().info("EconPlugin has been enabled!");
    }

    public Account getAccount(Player player){
        return accounts.computeIfAbsent(player.getUniqueId(), key -> new Account(player));
    }

}
