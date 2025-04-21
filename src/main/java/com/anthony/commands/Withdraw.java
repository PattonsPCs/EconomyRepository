package com.anthony.commands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.Bukkit;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.jspecify.annotations.NullMarked;
import net.kyori.adventure.text.Component;
import com.anthony.PlayerEcon;
import org.bukkit.entity.Player;

import java.util.Collection;

@NullMarked @SuppressWarnings("UnstableApiUsage")
public class Withdraw implements BasicCommand {
    private final PlayerEcon playerEcon;

    public Withdraw(PlayerEcon playerEcon){
        this.playerEcon = playerEcon;
    }

    @Override
    public void execute(CommandSourceStack source, @NonNull String[] args) {
        if(args.length < 2) {
            source.getSender().sendMessage("Usage: /withdraw <player> <amount>");
            return;
        }
        if(source.getSender() instanceof Player player) {
            int amount = Integer.parseInt(args[0]);
            playerEcon.withdrawPlayer(player, amount);
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
        return "econplugin.withdraw.use";
    }
}
