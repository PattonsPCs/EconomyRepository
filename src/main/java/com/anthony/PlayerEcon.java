package com.anthony;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;


import java.util.UUID;

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

    public void depositPlayer(Player player, int amount){
        currencyAmount += amount;
    }

    public void withdrawPlayer(Player player, int amount){
        currencyAmount -= amount;
    }


}
