package com.anthony.commands;

import com.anthony.Account;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.jspecify.annotations.NullMarked;
import com.anthony.configuration.ShopConfig;

@NullMarked @SuppressWarnings("UnstableApiUsage")
public class BuyCommand implements BasicCommand {

    private final Account account;
    private final ShopConfig shopConfig;

    public BuyCommand(){
        this.account = new Account(null);
        this.shopConfig = new ShopConfig(null, "shop.yml");
    }



    @Override
    public void execute(CommandSourceStack source, String[] args){
        if(args.length < 1){
            @SuppressWarnings("ConstantConditions")
            Component errorMessage = Component.text("Usage: /buy <item>")
                    .color(NamedTextColor.RED);
            source.getExecutor().sendMessage(errorMessage);
        }else{
            account.pay(shopConfig.getItemPrice(args[0]));
        }
    }

    @Override
    public String permission(){
        return "econplugin.buy.use";
    }
}
