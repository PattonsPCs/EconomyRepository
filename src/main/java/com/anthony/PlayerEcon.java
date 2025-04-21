package com.anthony;

import com.anthony.commands.*;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

@SuppressWarnings("UnstableApiUsage")
public class PlayerEcon extends JavaPlugin{
    private Player player;
    @Getter
    private final UUID id;
    @Getter @Setter
    private int currencyAmount;

    public PlayerEcon(int currencyAmount){
        this.id = player.getUniqueId();
        this.currencyAmount = currencyAmount;
    }

    public PlayerEcon(Player player){
        this.player = player;
        this.id = player.getUniqueId();
    }

    public PlayerEcon(){
        this.id = null;
        this.currencyAmount = 0;
    }

    @Override
    public void onEnable() {
        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS,
                event -> event.registrar().register("balance", new Balance(this)));
        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS,
                event -> event.registrar().register("deposit", new Deposit(this)));
        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS,
                event -> event.registrar().register("withdraw", new Withdraw(this)));
        getLogger().info("EconPlugin has been enabled!");
    }



    public void depositPlayer(Player player, int amount){
        currencyAmount += amount;
    }

    public void withdrawPlayer(Player player, int amount){
        currencyAmount -= amount;
    }

}
