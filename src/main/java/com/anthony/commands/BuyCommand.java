package com.anthony.commands;

import com.anthony.Account;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.jspecify.annotations.Nullable;
import org.jspecify.annotations.NullMarked;
import com.anthony.Econ;
import org.bukkit.entity.Player;
import com.anthony.configuration.ShopConfig;

@NullMarked @SuppressWarnings("UnstableApiUsage")
public class BuyCommand implements BasicCommand {

    private Account account;
    private ShopConfig shopConfig;
    private Player player;

    public BuyCommand(){
        super();
    }

    @Override
    public void execute(CommandSourceStack source, String[] args){
        if(args.length < 1){
            player.sendMessage("Invalid syntax. /buy <item id>");
        }else{
            account.pay(shopConfig.getItemPrice(args[0]));
        }
    }

    @Override
    public String permission(){
        return "econplugin.buy.use";
    }
}
