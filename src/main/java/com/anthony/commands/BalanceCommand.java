package com.anthony.commands;


import com.anthony.EconData;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NullMarked;

import java.util.UUID;

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

    UUID uuid = player.getUniqueId();
    int balance = econData.getBalance(uuid);
    player.sendRichMessage("<gold>Your current balance is <bold><yellow>$" + balance + "</yellow></bold>.</gold>");
  }
}
