package com.anthony.configuration;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.Collection;
import org.bukkit.configuration.file.YamlConfiguration;


@Getter
@Setter
public class ShopConfig extends AbstractConfig {
  private FileConfiguration config;
  private AbstractConfig abstractConfig;
  public ShopConfig(JavaPlugin plugin) {
    super(plugin, "shop.yml");
    this.config = YamlConfiguration.loadConfiguration(abstractConfig.getFile());
  }
  public ItemStack getItem(String id) {
    String materialName = getConfig().getString("shop.items." + id.toLowerCase() + ".item");
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
    int amount = getBuyAmount(id);
    if (amount > 0) {
      item.setAmount(amount);
    }
    return item;
  }

  @SuppressWarnings("DataFlowIssue")
  public Collection<String> getAllItems(){
    return getConfig().getConfigurationSection("shop.items").getKeys(false);
  }

  public int getBuyAmount(String id) {
    String path = "shop.items." + id.toLowerCase() + ".on_buy_amount";
    int amount = getConfig().getInt(path, 1);
    getPlugin().getLogger().info("Getting amount for " + id.toLowerCase() + " at path " + path + ": " + amount);
    // Also log the raw value to see what's actually in the config
    getPlugin().getLogger().info("Raw config value: " + getConfig().get(path));
    return amount;
  }

  public int getStockAmount(String id){
    String path = "shop.items." + id.toLowerCase() + ".stock_amount";
    return getConfig().getInt(path, 5);
  }

  public int getItemPrice(String id) {
    String path = "shop.items." + id.toLowerCase() + ".price";
    int price = getConfig().getInt(path, 0);
    getPlugin().getLogger().info("Getting price for " + id.toLowerCase() + " at path " + path + ": " + price);
    getPlugin().getLogger().info("Raw config value: " + getConfig().get(path));
    return price;
  }

  @SuppressWarnings("DataFlowIssue")
  public void debugConfig(){
    getPlugin().getLogger().info("Shop config sections:");
    if(getConfig().contains("shop")){
      getPlugin().getLogger().info("-shop section exists");
      if(getConfig().contains("shop.items")){
        getPlugin().getLogger().info("-shop.items section exists");

        for(String key : getConfig().getConfigurationSection("shop.items").getKeys(false)){
          getPlugin().getLogger().info(" - Item: " + key);
          getPlugin().getLogger().info("   - Price: " + getConfig().getInt("shop.items." + key + ".price"));
          getPlugin().getLogger().info("   - Amount: " + getConfig().getInt("shop.items." + key + ".amount"));
          getPlugin().getLogger().info("   - Material: " + getConfig().getString("shop.items." + key + ".item"));
        }
      } else{
        getPlugin().getLogger().info("-shop.items section does not exist");
      }
    } else{
      getPlugin().getLogger().info("-shop section does not exist");
    }
  }


  public void updateYAML(String id) {
    String path = "shop.items" + id + "stock_amount";
    int currentAmount = getStockAmount(id);
    int loss = getBuyAmount(id);
    config.set(path, currentAmount - loss);
  }



}