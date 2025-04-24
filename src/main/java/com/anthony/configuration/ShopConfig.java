package com.anthony.configuration;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import com.anthony.Econ;

@Getter @Setter
public class ShopConfig extends AbstractConfig{

    private FileConfiguration shopConfig;
    public ShopConfig(JavaPlugin plugin, String fileName) {
        super(plugin, fileName);
        shopConfig = getConfig();
    }


    @Override
    public void onLoad() {
        if(!getConfig().contains("shop")) {
            getPlugin().getLogger().info("Creating default shop config");
            getConfig().createSection("shop");
        }

    }

    public boolean canAfford(Player player, Econ econ, String id){
        int price = getItemPrice(id);
        if(econ.getCurrencyAmount() < price) {
            player.sendMessage("You do not have enough money to buy this item.");
            return false;
        } else {
            return true;
        }
    }

    public ItemStack getItem(String id){
        return shopConfig.getItemStack("shop.items." + id + ".item");
    }


    public int getItemPrice(String id){
        return shopConfig.getInt("shop.items." + id + ".price");
    }

}
