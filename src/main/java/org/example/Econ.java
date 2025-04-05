package org.example;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.economy.Economy;

public class Econ extends JavaPlugin{
    Economy economy = null;
    JavaPlugin plugin = null;

    @Override
    public void onEnable(){
        getLogger().info("Economy plugin enabled");
        if(!setEconomy()){
            getLogger().severe("Disabled due to no Vault dependency found!");
            getServer().getPluginManager().disablePlugin(this);
        }

        getLogger().info("Economy plugin enabled");
    }


    public boolean setEconomy(){
        if(getServer().getPluginManager().getPlugin("Vault") == null){
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if(rsp == null){
            return false;
        }

        economy = rsp.getProvider();
        return true;
    }

    public static Economy getEconomy(Economy economy){
        return economy;
    }

    public void registerEconomy(){
        getServer().getServicesManager().register(Economy.class, economy, this, ServicePriority.Normal);
    }

    public void setBalance(Player player, double balance){
        economy.depositPlayer(player, balance);
    }

    public double getBalance(Player player){
        return economy.getBalance(player);
    }

    public void withdrawPlayer(Player player, double amount){
        economy.withdrawPlayer(player, amount);
    }

    public void depositPlayer(Player player, double amount){
        economy.depositPlayer(player, amount);
    }



    @Override
    public void onDisable(){
        getLogger().info("Economy plugin disabled");
    }
}
