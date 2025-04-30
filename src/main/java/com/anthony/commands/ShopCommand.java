package com.anthony.commands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.command.CommandSender;
import com.anthony.Econ;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("UnstableApiUsage")
public class ShopCommand implements BasicCommand{

    private final Econ econ;

    public ShopCommand(Econ econ){
        this.econ = econ;
    }

    @Override
    public void execute(CommandSourceStack source, String @NotNull [] args){
        CommandSender sender = source.getSender();

        sender.sendRichMessage("<bold><gold>Shop Menu</gold></bold>");
        sender.sendRichMessage("<green>Buy items from the shop!</green>");
        sender.sendRichMessage("<yellow>To buy an item, type /buy <item></yellow>");
        sender.sendRichMessage("<yellow>To check your balance, type /balance</yellow>");

        econ.getShopConfig().getAllItems().forEach(id -> {
            sender.sendRichMessage("<green>Item: " + id + "</green>");
            sender.sendRichMessage("<yellow>Price: " + econ.getShopConfig().getItemPrice(id) + "</yellow>");
        });
    }
}
