package com.anthony;

import com.anthony.commands.*;
import com.anthony.configuration.ShopConfig;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SuppressWarnings("UnstableApiUsage")
public class Econ extends JavaPlugin{
    @Getter
    private final Map<UUID, Account> accounts = new HashMap<>();
    private Database database;
    private final ShopConfig shopConfig = new ShopConfig(this, "shop.yml");
    private Connection connection;

    @Override
    public void onEnable() {
        database = new Database();
        database.connect();
        database.createTable();

        shopConfig.load();
        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS,
                event ->{
                    event.registrar().register("buy", new BuyCommand(this, shopConfig));
                    event.registrar().register("balance", new BalanceCommand(this));
                });

        getLogger().info("EconPlugin has been enabled!");
    }

    public void connectDatabase(){
        try{
            File dbFile = new File(getDataFolder(), "database.db");

            if(!dbFile.exists()){
                getLogger().info("Database file not found, creating a new one.");
                if(!(dbFile.getParentFile().mkdirs())){
                    getLogger().severe("Failed to create database directory.");
                }
                if(!(dbFile.createNewFile())){
                    getLogger().severe("Failed to create database file.");
                }

                String url = "jdbc:sqlite:" + dbFile.getAbsolutePath();
                connection = DriverManager.getConnection(url);

                getLogger().info("Database connection established.");
            }
        } catch(SQLException | IOException e){
            getLogger().severe("Failed to connect to the database: " + e.getMessage());
        }

    }

    public Account getAccount(Player player){
        return accounts.computeIfAbsent(player.getUniqueId(), uuid -> {
            Account account = new Account(player);
            int balance = database.loadBalance(uuid);
            account.setBalance(balance);
            return account;
        });
    }

    @Override
    public void onDisable(){
        for(Account account : accounts.values()){
            database.saveAccount(account.getPlayerID(), account.getBalance());
        }
        database.close();
        getLogger().info("EconPlugin has been disabled!");
    }
}
