package com.anthony;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Account {
    private final UUID playerID;
    private final Player player;
    @Setter @Getter
    private int balance = 0;

    public Account(Player player){
        this.player = player;
        this.playerID = player.getUniqueId();
    }
    public String getPlayerID(){
        return playerID.toString();
    }
    public void pay(int amount){
        balance -= amount;
    }
}
