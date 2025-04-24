package com.anthony.commands;


import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.jspecify.annotations.NullMarked;
import com.anthony.Account;

@NullMarked @SuppressWarnings("UnstableApiUsage")
public class BalanceCommand implements BasicCommand{

    @Override
    public void execute(CommandSourceStack source, String[] args) {
        @SuppressWarnings("ConstantConditions")
        Account account = new Account(source.getExecutor().getUniqueId());
        // Logic to display the player's balance
        @SuppressWarnings("ConstantConditions")
        Component balanceMessage = Component.text("Your current balance is " + getBalance(account) + ".")
                .color(NamedTextColor.GOLD);
        source.getExecutor().sendMessage(balanceMessage);
    }

    private int getBalance(Account account) {
        return account.getBalance();
    }

    @Override
    public String permission() {
        return "econplugin.balance.use";
    }

}
