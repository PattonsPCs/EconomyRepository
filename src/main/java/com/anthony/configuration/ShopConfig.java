package com.anthony.configuration;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import com.anthony.Econ;
import org.bukkit.inventory.PlayerInventory;


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

    public void setPrice(double price){
        this.price = price;
    }

    public double getPrice(){
        return this.price;
    }

    public void buy(ItemStack item, Econ econ, Player player){
        double price = getPrice();
        System.out.println("Items in stack: " + item.getAmount());
        if(econ.getBalance(player) >= price){
            econ.withdrawPlayer(player, price);
            player.getInventory().addItem(item);
            getPlugin().getLogger().info("Player " + player.getName() + " bought an item for " + price);
        }
    }

    public void sell(ItemStack item, Econ econ, PlayerInventory playerInventory, Player player){
        double price = getPrice();
        System.out.println("Items in stack: " + item.getAmount());
        if(playerInventory.contains(item)){
            playerInventory.removeItem(item);
            econ.depositPlayer(player, price);
            getPlugin().getLogger().info("Player " + player.getName() + " sold an item for " + price);
        } else {
            player.sendMessage("You do not have that item.");
        }
    }



}
