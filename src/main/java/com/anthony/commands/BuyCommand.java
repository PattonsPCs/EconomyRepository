package com.anthony.commands;

import com.anthony.Account;
import com.anthony.Econ;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.NullMarked;
import com.anthony.configuration.ShopConfig;

@NullMarked @SuppressWarnings("UnstableApiUsage")
public class BuyCommand implements BasicCommand {

    private final Econ econ;
    private final ShopConfig shopConfig;

    public BuyCommand(Econ econ, ShopConfig shopConfig){
        this.econ = econ;
        this.shopConfig = shopConfig;
    }



    @Override
    public void execute(CommandSourceStack source, String[] args){
        if(!(source.getExecutor() instanceof Player player)){
            if(source.getExecutor() != null){
                source.getExecutor().sendMessage("You must be a player to use this command.");
            }
            return;
        }
        if(args.length < 1){
            Component errorMessage = Component.text("Usage: /buy <item>")
                    .color(NamedTextColor.RED);
            source.getExecutor().sendMessage(errorMessage);
            return;
        }

        String itemId = args[0];
        Account account = econ.getAccount(player);

        if(!shopConfig.canAfford(player, account, itemId)){
            return;
        }

        int price = shopConfig.getItemPrice(itemId);
        account.withdraw(price);

        ItemStack item = shopConfig.getItem(itemId);
        if(item != null){
            player.getInventory().addItem(item.clone());
            player.sendMessage(Component.text("You have bought " + item.getType().name() + " for " + price + ".")
                    .color(NamedTextColor.GREEN));
        }
        else{
            player.sendMessage(Component.text("The item you requested could not be found.")
                    .color(NamedTextColor.RED));
        }
    }

    @Override
    public String permission(){
        return "econplugin.buy.use";
    }
}
