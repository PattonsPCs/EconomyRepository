package com.anthony.configuration;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import com.anthony.PlayerEcon;
import org.bukkit.inventory.PlayerInventory;

@Getter @Setter
public class ShopConfig extends AbstractConfig{

    private double price;
    public ShopConfig(JavaPlugin plugin, String fileName) {
        super(plugin, fileName);
    }


    @Override
    public void onLoad() {
        // Custom logic for loading the config can be added here
        // For example, setting default values or validating the config
        if(!getConfig().contains("shop")) {
            getPlugin().getLogger().info("Creating default shop config");
            getConfig().createSection("shop");
        }

    }

    public void addItem(String id, ItemStack item, double price){
        System.out.println("Items in stack: " + item.getAmount());
        if(item.hasItemMeta()){
            item = new ItemStack(item);
        }
        getConfig().set("shop." + id + ".item", item);
        getConfig().set("shop." + id + ".price", price);
        setPrice(price);
        save();
    }

    public ItemStack getItem(String id){
        return getConfig().getItemStack("shop." + id + ".item");
    }

    public double getItemPrice(String id){
        return getConfig().getDouble("shop." + id + ".price");
    }





}
