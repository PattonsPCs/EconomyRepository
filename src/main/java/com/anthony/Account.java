package com.anthony;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Account {
    @Getter
    private final UUID playerID;
    @Setter @Getter
    private int balance = 0;

    public Account(Player player){
        this.playerID = player.getUniqueId();
    }
    public void withdraw(int amount){
        balance -= amount;
    }
}
