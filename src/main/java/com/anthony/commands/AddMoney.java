package com.anthony.commands;


import com.anthony.Econ;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NullMarked;

@NullMarked
@SuppressWarnings("UnstableApiUsage")
public class AddMoney implements BasicCommand{
    private final Econ econ;

    public AddMoney(Econ econ) {
        this.econ = econ;
    }

    @Override
    public void execute(CommandSourceStack source, String[] args) {
        if (!(source.getExecutor() instanceof Player)) {
            if (source.getExecutor() != null) {
                source.getExecutor().sendMessage("You must be an operator to use this command.");
            }

            if(args.length < 2) {
                Component errorMessage = Component.text("Usage: /addmoney <player> <amount>").color(NamedTextColor.RED);
                if(source.getExecutor() != null) {
                    source.getExecutor().sendMessage(errorMessage);
                }
                return;
            }

            String playerName = args[0];
            int amount = Integer.parseInt(args[1]);
            Player player = econ.getServer().getPlayer(playerName);
            if(source.getExecutor() != null){
                if(player == null) {
                    source.getExecutor().sendMessage("Player not found.");
                    return;
                }
                econ.getAccount(player).deposit(amount);
                source.getExecutor().sendMessage(Component.text("You have added " + amount + " to your account.")
                        .color(NamedTextColor.GREEN));
            }
        }
    }


    @Override
    public boolean canUse(CommandSender sender){
        return sender.hasPermission("econplugin.addmoney.use");
    }
}
