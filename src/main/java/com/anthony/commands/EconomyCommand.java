package com.anthony.commands;


import com.anthony.EconData;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import io.papermc.paper.command.brigadier.argument.resolvers.selector.PlayerSelectorArgumentResolver;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NullMarked;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

@SuppressWarnings("UnstableApiUsage")
public class EconomyCommand  {
    private EconData econData;


    public EconomyCommand(EconData econData) {
        this.econData = econData;
    }

    LiteralArgumentBuilder<CommandSourceStack> root = Commands.literal("economy")
            .then(Commands.literal("add")
                    .then(Commands.argument("target", ArgumentTypes.player())
                            .then(Commands.argument("amount", IntegerArgumentType.integer())
                                    .executes(ctx -> {
                                        PlayerSelectorArgumentResolver targetResolver = (PlayerSelectorArgumentResolver) ctx.getArgument("target", PlayerSelectorArgumentResolver.class);
                                        Player target = targetResolver.resolve(ctx.getSource()).getFirst();

                                        int amount = IntegerArgumentType.getInteger(ctx, "amount");
                                        econData.deposit(target.getUniqueId(), amount);
                                        return amount;
                                    })
                            )
                    )
            )
            .then(Commands.literal("remove")
                    .then)

}

