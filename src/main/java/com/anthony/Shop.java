package com.anthony;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import com.anthony.configuration.ShopConfig;
import org.bukkit.event.player.PlayerInteractEvent;

public class Shop {
    public void setupShop(JavaPlugin plugin){
        ShopConfig shopConfig = new ShopConfig(plugin, "shop.yml");

        shopConfig.onLoad();

        ItemStack food = new ItemStack(Material.BREAD, 10);
        ItemStack miscellaneous = new ItemStack(Material.ELYTRA,  1);
        shopConfig.addItem("&bSuper Skibidi Bread!!!", food, 10.0);
        shopConfig.addItem("&fOhio Gyatt Sigma Item!!!", miscellaneous, 20.0);
    }

    public void onPlayerClick(PlayerInteractEvent event, ItemStack clickedItem){


    }



}
