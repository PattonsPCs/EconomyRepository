package com.anthony.configuration;

import com.anthony.Account;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.Collection;

@Getter
@Setter
public class ShopConfig extends AbstractConfig {

  public ShopConfig(JavaPlugin plugin) {
    super(plugin, "shop.yml");
  }

  public boolean canAfford(Player player, Account account, String id) {
    int price = getItemPrice(id);
    if (account.getBalance() < price) {
      player.sendMessage("You do not have enough money to buy this item.");
      return false;
    } else {
      return true;
    }
  }

  public ItemStack getItem(String id) {
    String materialName = getConfig().getString("shop.items." + id + ".item");
    if (materialName == null) {
      getPlugin().getLogger().warning("Material name not found for item " + id.toLowerCase());
      return null;
    }

    Material material = Material.matchMaterial(materialName);

    if (material == null) {
      getPlugin().getLogger().warning("Material " + materialName + " not found for item " + id.toLowerCase());
      return null;
    }
    ItemStack item = new ItemStack(material);
    int amount = getAmount(id);
    if (amount > 0) {
      item.setAmount(amount);
    }
    return item;
  }

  @SuppressWarnings("DataFlowIssue")
  public Collection<String> getAllItems(){
    return getConfig().getConfigurationSection("shop.items").getKeys(false);
  }

  public int getAmount(String id) {
    String path = "shop.items." + id.toLowerCase() + ".amount";
    int amount = getConfig().getInt(path, 1);
    getPlugin().getLogger().info("Getting amount for " + id.toLowerCase() + " at path " + path + ": " + amount);
    // Also log the raw value to see what's actually in the config
    getPlugin().getLogger().info("Raw config value: " + getConfig().get(path));
    return amount;
  }

  public int getItemPrice(String id) {
    String path = "shop.items." + id.toLowerCase() + ".price";
    int price = getConfig().getInt(path, 0);
    getPlugin().getLogger().info("Getting price for " + id.toLowerCase() + " at path " + path + ": " + price);
    getPlugin().getLogger().info("Raw config value: " + getConfig().get(path));
    return price;
  }

}