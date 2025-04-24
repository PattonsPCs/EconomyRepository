package com.anthony;

import lombok.Getter;
import java.util.UUID;

public class Account {
    private final UUID playerID;
    @Getter
    private int balance;

    public Account(UUID playerID){
        this.playerID = playerID;
        this.balance = 0;
    }
    public String getPlayerID(){
        return playerID.toString();
    }
    public void pay(int amount){
        balance -= amount;
    }
}
