package com.anthony;

import com.anthony.commands.*;
import com.anthony.configuration.ShopConfig;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

@SuppressWarnings("UnstableApiUsage")
public class Econ extends JavaPlugin{
    private Player player;
    @Getter
    private final UUID id;
    @Getter @Setter
    private int currencyAmount;

    public Econ(int currencyAmount){
        this.id = player.getUniqueId();
        this.currencyAmount = currencyAmount;
    }

    public Econ(Player player){
        this.player = player;
        this.id = player.getUniqueId();
    }

    public Econ(){
        this.id = null;
        this.currencyAmount = 0;
    }

    @Override
    public void onEnable() {
        ShopConfig shopConfig = new ShopConfig(this, "shop.yml");
        shopConfig.load();
        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS,
                event -> event.registrar().register("buy", new BuyCommand()));
        saveConfig();
        reloadConfig();
        getLogger().info("EconPlugin has been enabled!");
    }

}
