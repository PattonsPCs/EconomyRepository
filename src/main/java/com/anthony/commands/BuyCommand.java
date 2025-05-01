package com.anthony.commands;

import com.anthony.Account;
import com.anthony.Econ;
import com.anthony.EconData;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.NullMarked;

@NullMarked
@SuppressWarnings("UnstableApiUsage")
public class BuyCommand implements BasicCommand {

  private final Econ econ;
  private final EconData econData;

  public BuyCommand(Econ econ, EconData econData) {
    this.econ = econ;
    this.econData = econData;
  }

  @Override
  public void execute(CommandSourceStack source, String[] args) {
    CommandSender sender = source.getSender();
    Player player = Bukkit.getPlayerExact((sender.getName()));
    if (args.length < 1) {
      sender.sendRichMessage("<red>Usage: /buy <item>");
      return;
    }

    String itemId = args[0];
    if(player == null){
      sender.sendRichMessage("<red>Player not found.");
      return;
    }
    Account account = econ.getAccount(player);

    if (!econ.getShopConfig().canAfford(player, account, itemId)) {
      return;
    }

    int price = econ.getShopConfig().getItemPrice(itemId);
    account.withdraw(price);
    econData.setBalance(account.getPlayerID(), account.getBalance());

    ItemStack item = econ.getShopConfig().getItem(itemId);
    if (item != null) {
      player.getInventory().addItem(item.clone());
      sender.sendRichMessage("<green>You have bought <gold>" + econ.getShopConfig().getAmount(itemId) + " " +item.getType().name() + "</gold> for <light_purple>" + price + "</light_purple>.</green>");
    } else {
      sender.sendRichMessage("<red>Item not found in shop.</red>");
    }
  }

}
