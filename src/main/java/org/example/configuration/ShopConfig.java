package org.example.configuration;

import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;


public class ShopConfig extends AbstractConfig{

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
        if(item != null && item.hasItemMeta()){
            item = new ItemStack(item);
        }
        getConfig().set("shop." + id + ".item", item);
        getConfig().set("shop." + id + ".price", price);
        save();
    }


}
