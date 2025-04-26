package com.anthony.configuration;

import com.anthony.Account;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
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

    public boolean canAfford(Player player, Account account, String id){
        int price = getItemPrice(id);
        if(account.getBalance() < price) {
            player.sendMessage("You do not have enough money to buy this item.");
            return false;
        } else {
            return true;
        }
    }

    public ItemStack getItem(String id){
        String materialName = shopConfig.getString("shop.items." + id + ".item");
        if(materialName == null) {
            getPlugin().getLogger().warning("Material name not found for item " + id);
            return null;
        }

        Material material = Material.matchMaterial(materialName);

        if(material == null) {
            getPlugin().getLogger().warning("Material " + materialName + " not found for item " + id);
            return null;
        }

        return new ItemStack(material);
    }



    public int getItemPrice(String id){
        return shopConfig.getInt("shop.items." + id + ".price");
    }

}
