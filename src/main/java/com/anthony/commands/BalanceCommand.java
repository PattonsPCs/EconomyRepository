package com.anthony.commands;


import com.anthony.Account;
import com.anthony.Econ;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NullMarked;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@NullMarked
@SuppressWarnings("UnstableApiUsage")
public class BalanceCommand implements BasicCommand {

  private final Econ econ;
  private final Logger logger = LoggerFactory.getLogger(BalanceCommand.class);

  public BalanceCommand(Econ econ) {
    this.econ = econ;
  }

  @Override
  public void execute(CommandSourceStack source, String[] args) {
    Player player = (Player) source.getExecutor();
    if (player == null) {
      source.getExecutor().sendMessage("You must be a player to use this command.");
      logger.debug("Command executed by non-player.");
      return;
    }
    Account account = econ.getAccount(player);

    if (account == null) {
      player.sendMessage("Account not found.");
      logger.debug("Account not found for player.");
      return;
    }
    Component balanceMessage = Component.text("Your current balance is " + getBalance(account) + ".")
        .color(NamedTextColor.GOLD);
    source.getExecutor().sendMessage(balanceMessage);
  }

  private int getBalance(Account account) {
    return account.getBalance();
  }


}
