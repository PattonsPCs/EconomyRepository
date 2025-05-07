package com.anthony.commands;


import com.anthony.EconData;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import io.papermc.paper.command.brigadier.argument.resolvers.selector.PlayerSelectorArgumentResolver;
import org.bukkit.entity.Player;

@SuppressWarnings("UnstableApiUsage")
public class EconomyCommand  {
    private EconData econData;
    private int defaultBalance;


    public EconomyCommand(EconData econData) {
        this.econData = econData;
        this.defaultBalance = 100;
    }

    LiteralArgumentBuilder<CommandSourceStack> root = Commands.literal("economy")
            .then(Commands.literal("add")
                    .then(Commands.argument("target", ArgumentTypes.player())
                            .then(Commands.argument("amount", IntegerArgumentType.integer())
                                    .executes(ctx -> {
                                        PlayerSelectorArgumentResolver targetResolver =  ctx.getArgument("target", PlayerSelectorArgumentResolver.class);
                                        Player target = targetResolver.resolve(ctx.getSource()).getFirst();

                                        int amount = IntegerArgumentType.getInteger(ctx, "amount");
                                        econData.deposit(target.getUniqueId(), amount);
                                        return amount;
                                    })
                            )
                    )
            )
            .then(Commands.literal("remove")
                    .then(Commands.argument("target", ArgumentTypes.player())
                            .then(Commands.argument("amount", IntegerArgumentType.integer())
                                    .executes(ctx -> {
                                        PlayerSelectorArgumentResolver targetResolver =  ctx.getArgument("target", PlayerSelectorArgumentResolver.class);
                                        Player target = targetResolver.resolve(ctx.getSource()).getFirst();
                                        int amount = IntegerArgumentType.getInteger(ctx, "amount");
                                        econData.withdraw(target.getUniqueId(), amount);
                                        return amount;
                                    })
                            )
                    )
            )
            .then(Commands.literal("view")
                    .then(Commands.argument("target", ArgumentTypes.player())
                            .executes(ctx -> {
                                PlayerSelectorArgumentResolver targetResolver =  ctx.getArgument("target", PlayerSelectorArgumentResolver.class);
                                Player target = targetResolver.resolve(ctx.getSource()).getFirst();

                                return econData.getBalance(target.getUniqueId());
                            })
                    )
            )
            .then(Commands.literal("set")
                    .then(Commands.argument("target", ArgumentTypes.player())
                            .then(Commands.argument("amount", IntegerArgumentType.integer())
                                    .executes(ctx -> {
                                        PlayerSelectorArgumentResolver targetResolver =  ctx.getArgument("target", PlayerSelectorArgumentResolver.class);
                                        Player target = targetResolver.resolve(ctx.getSource()).getFirst();
                                        int amount = IntegerArgumentType.getInteger(ctx, "amount");
                                        econData.setBalance(target.getUniqueId(), amount);
                                        return econData.getBalance(target.getUniqueId());
                                    })
                            )
                    )
            )
            .then(Commands.literal("reset")
                    .then(Commands.argument("target", ArgumentTypes.player())
                            .executes(ctx -> {
                                PlayerSelectorArgumentResolver targetResolver =  ctx.getArgument("target", PlayerSelectorArgumentResolver.class);
                                Player target = targetResolver.resolve(ctx.getSource()).getFirst();

                                int amount = defaultBalance;
                                econData.setBalance(target.getUniqueId(), amount);
                                return econData.getBalance(target.getUniqueId());
                            })
                    )
            );

    public LiteralCommandNode<CommandSourceStack> build(){
        return root.build();
    }

}

