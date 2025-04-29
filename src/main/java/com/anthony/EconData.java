package com.anthony;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EconData {
  private final Map<UUID, Integer> balances = new HashMap<>();


  public void saveAccount(UUID uuid, int balance) {
    balances.put(uuid, balance);
  }

  public int loadBalance(UUID uuid) {
    return balances.getOrDefault(uuid, 0);
  }


  public void loadAllAccounts(Map<UUID, Account> accounts) {
    for (Map.Entry<UUID, Integer> entry : balances.entrySet()) {
      UUID uuid = entry.getKey();
      int balance = entry.getValue();
      accounts.put(uuid, new Account(uuid, balance));
    }
  }
}
