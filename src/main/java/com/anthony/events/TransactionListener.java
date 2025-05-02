package com.anthony.events;

import com.anthony.transactions.PluginLogger;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;


public class TransactionListener implements Listener {
    private final PluginLogger logger;

    public TransactionListener(PluginLogger logger){
        this.logger = logger;
    }

    @EventHandler
    public void onWithdraw(PlayerCommandPreprocessEvent event){
        String command = event.getMessage();
        Player player = event.getPlayer();

        if(command.contains("/buy"))
            logger.log("Player " + player.getName() + " bought " + command.split("\\s+")[1] + ".");
    }

    @EventHandler
    public void onDepositFromServer(ServerCommandEvent event){
        String command = event.getCommand();
        String playerName = command.split("\\s+")[1];
        int amount = Integer.parseInt(command.split("\\s+")[2]);

        if(command.contains("addmoney")){
            logger.log("Server added " + amount + " to " + playerName + "'s balance.");
        }
    }




}
