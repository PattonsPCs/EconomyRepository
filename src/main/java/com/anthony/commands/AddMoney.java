package com.anthony.commands;


import com.anthony.Econ;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NullMarked;

import java.util.Collection;
import java.util.Collections;

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
            sender.sendRichMessage("<red>Player not found.");
            return;
        }
        econ.getAccount(player).deposit(amount);
        sender.sendRichMessage("<gold>You have added <yellow>" + amount + "</yellow> to your account.</gold>");
    }

    public Collection<String> suggest(final CommandSourceStack commandSourceStack, final String[] args) {
        if (args.length == 1) {
          return Bukkit.getOnlinePlayers().stream()
              .map(Player::getName)
              .filter(name -> name.startsWith(args[0]))
              .toList();
        }
        return Collections.emptyList();
    }

    @Override
    public boolean canUse(CommandSender sender) {
        return sender.hasPermission("econplugin.addmoney.use");
    }
}
