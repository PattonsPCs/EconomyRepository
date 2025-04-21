package com.anthony.commands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.Bukkit;
import org.jspecify.annotations.Nullable;
import org.jspecify.annotations.NullMarked;
import com.anthony.PlayerEcon;
import org.bukkit.entity.Player;

import java.util.Collection;

@NullMarked @SuppressWarnings("UnstableApiUsage")
public class Balance implements BasicCommand {

    private final PlayerEcon playerEcon;

    public Balance(PlayerEcon playerEcon){
        this.playerEcon = playerEcon;
    }

    @Override
    public void execute(CommandSourceStack source, String[] args) {
        if(args.length < 1) {
            source.getSender().sendMessage("Usage: /withdraw <player>");
            return;
        }

        if(source.getSender() instanceof Player player) {
            int balance = playerEcon.getCurrencyAmount();
            player.sendMessage("Your balance is " + balance);
        } else {
            source.getSender().sendMessage("Only players can use this command.");
        }
    }

    @Override
    public Collection<String> suggest(CommandSourceStack source, String[] args){
        if(args.length == 0) {
            return Bukkit.getOnlinePlayers().stream().map(Player::getName).toList();
        }
        return Bukkit.getOnlinePlayers().stream()
                .map(Player::getName)
                .filter(name -> name.toLowerCase().startsWith(args[args.length - 1].toLowerCase()))
                .toList();
    }

    @Override
    public @Nullable String permission(){
        return "econplugin.balance.use";
    }

}
