package com.anthony;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EconData {
  private final Map<UUID, Integer> balances = new HashMap<>();


  public void setBalance(UUID uuid, int balance) {
    balances.put(uuid, balance);
  }

  public int getBalance(UUID uuid) {
    return balances.getOrDefault(uuid, 0);
  }


  public Map<UUID, Integer> getAllBalances() {
    return balances;
  }

  public boolean canAfford(UUID uuid, int amount){
    return getBalance(uuid) >= amount;
  }

  public void deposit(UUID uuid, int amount){
    if(amount < 0) return;
    setBalance(uuid, getBalance(uuid) + amount);
  }

  public boolean withdraw(UUID uuid, int amount){
    if (!canAfford(uuid, amount)) return false;
    setBalance(uuid, getBalance(uuid) - amount);
    return true;
  }
}


