package com.anthony.commands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.Bukkit;
import org.jspecify.annotations.Nullable;
import org.jspecify.annotations.NullMarked;
import com.anthony.PlayerEcon;
import org.bukkit.entity.Player;
import com.anthony.configuration.ShopConfig;


public class Buy implements BasicCommand {
    private final PlayerEcon playerEcon;
    public Buy(PlayerEcon playerEcon){
        this.playerEcon = playerEcon;
    }

    @Override
    public void execute(CommandSourceStack source, String[] args) {
        if(args.length < 1) {
            source.getSender().sendMessage("Usage: /buy <item>");
            return;
        }
        if(source.getSender() instanceof Player player){

        }
    }
}
