package com.anthony.commands;

import com.anthony.persistence.EconPersistenceManager;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("UnstableApiUsage")
public class SaveCommand implements BasicCommand {
    private final EconPersistenceManager persistenceManager;

    public SaveCommand(EconPersistenceManager persistanceManager){
        this.persistenceManager = persistanceManager;
    }

    @Override
    public void execute(CommandSourceStack source, String @NotNull [] args){
        CommandSender sender = source.getSender();

        if(!(sender instanceof Player player)){
            sender.sendMessage("You must be a player to use this command.");
            return;
        }

        if(args.length == 0){
            persistenceManager.saveBalanceToFile(player.getUniqueId());
            player.sendMessage("Your balance has been saved.");
        } else if(args.length == 1){
            if(!player.hasPermission("econplugin.save.others")){
                player.sendRichMessage("<bold><red>You don't have permission to save other player's balances.</bold>");
            }

            OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
            persistenceManager.saveBalanceToFile(target.getUniqueId());
            player.sendMessage("Saved balance for " + target.getName());
        } else{
            player.sendRichMessage("<bold><red>Invalid usage. /save or /save <player> (Admin Only)</bold>");
        }

    }

}
