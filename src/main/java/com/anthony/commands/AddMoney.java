package com.anthony.commands;


import com.anthony.Econ;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NullMarked;

@NullMarked
@SuppressWarnings("UnstableApiUsage")
public class AddMoney implements BasicCommand {
    private final Econ econ;

    public AddMoney(Econ econ) {
        this.econ = econ;
    }

    @Override
    public void execute(CommandSourceStack source, String[] args) {
        CommandSender sender = source.getSender();
        if (args.length != 2) {
            sender.sendRichMessage("<red>Usage: /addmoney <player> <amount>");
            return;
        }

        String playerName = args[0];
        int amount = Integer.parseInt(args[1]);
        Player player = Bukkit.getPlayerExact(playerName);
        if (player == null) {
            source.getSender().sendRichMessage("<red>Player not found.");
            return;
        }
        econ.getAccount(player).deposit(amount);
        source.getExecutor().sendRichMessage("<gold>You have added <yellow>" + amount + "</yellow> to your account.</gold>");
    }


    @Override
    public boolean canUse(CommandSender sender) {
        return sender.hasPermission("econplugin.addmoney.use");
    }
}
