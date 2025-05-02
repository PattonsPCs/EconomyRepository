package com.anthony.commands;


import com.anthony.EconData;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NullMarked;

@NullMarked
@SuppressWarnings("UnstableApiUsage")
public class BalanceCommand implements BasicCommand {

  private final EconData econData;
  public BalanceCommand(EconData econData) {
    this.econData = econData;
  }

  @Override
  public void execute(CommandSourceStack source, String[] args) {
    CommandSender sender = source.getSender();
    if (!(sender instanceof Player player)) {
      sender.sendMessage("You must be a player to use this command.");
      return;
    }

    if(args.length == 0){
      player.sendRichMessage("<gold>Your current balance is <bold><yellow>$" + econData.getBalance(player.getUniqueId()) + "</yellow></bold>.</gold>");
    } else if(args.length == 1){
      if(!player.hasPermission("econplugin.balance.others")){
        player.sendRichMessage("<bold><red>You don't have permission to view another player's balance</bold>");
      } else{
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        player.sendRichMessage("<gold>"+ target.getName() + "'s current balance is <bold><yellow>$" + econData.getBalance(target.getUniqueId()) + "</yellow></bold>.</gold>");
      }
    }
  }

  
}
