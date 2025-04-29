package com.anthony;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Account {
    @Getter
    private final UUID playerID;
    @Setter @Getter
    private int balance;

    public Account(Player player){
        this.playerID = player.getUniqueId();
        this.balance = 0;
    }
    public Account(UUID playerID, int balance){
        this.playerID = playerID;
        this.balance = balance;
    }
    public void withdraw(int amount){
        balance = balance - amount;
    }
}
