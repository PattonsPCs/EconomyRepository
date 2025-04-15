package com.anthony;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import com.anthony.configuration.ShopConfig;

public class Shop {
    public void setupShop(JavaPlugin plugin){
        ShopConfig shopConfig = new ShopConfig(plugin, "shop.yml");

        shopConfig.onLoad();

        ItemStack diamondSword = new ItemStack(Material.DIAMOND_SWORD, 1);
        shopConfig.addItem("diamond_sword", diamondSword, 500.0);
    }
}
