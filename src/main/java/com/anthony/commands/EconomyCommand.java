package com.anthony.commands;


import com.anthony.EconData;
import com.anthony.configuration.ShopConfig;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import io.papermc.paper.command.brigadier.argument.resolvers.selector.PlayerSelectorArgumentResolver;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;

@SuppressWarnings("UnstableApiUsage")
public class EconomyCommand  {
    private EconData econData;
    private int defaultBalance;
    private ShopConfig shopConfig;


    public EconomyCommand(EconData econData) {
        this.econData = econData;
        this.defaultBalance = 100;
    }

    LiteralArgumentBuilder<CommandSourceStack> root = Commands.literal("economy")
            .executes(ctx -> {
                ctx.getSource().getSender().sendMessage("Usage: /economy <add|remove|set|view|reset|shop>");
                return Command.SINGLE_SUCCESS;
            })
            .then(Commands.literal("add")
                    .then(Commands.argument("target", ArgumentTypes.player())
                            .then(Commands.argument("amount", IntegerArgumentType.integer())
                                    .executes(ctx -> {
                                        PlayerSelectorArgumentResolver targetResolver =  ctx.getArgument("target", PlayerSelectorArgumentResolver.class);
                                        Player target = targetResolver.resolve(ctx.getSource()).getFirst();

                                        int amount = IntegerArgumentType.getInteger(ctx, "amount");
                                        if(amount <= 0) {
                                            ctx.getSource().getSender().sendMessage("Amount must be positive.");
                                            return Command.SINGLE_SUCCESS;
                                        }
                                        econData.deposit(target.getUniqueId(), amount);
                                        ctx.getSource().getSender().sendMessage("Deposited " + amount + " to " + target.getName());
                                        return Command.SINGLE_SUCCESS;
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
                                        if(amount <= 0){
                                            ctx.getSource().getSender().sendMessage("Amount must be positive.");
                                            return Command.SINGLE_SUCCESS;
                                        }
                                        econData.withdraw(target.getUniqueId(), amount);
                                        ctx.getSource().getSender().sendMessage("Removed " + amount + " to " + target.getName());
                                        return Command.SINGLE_SUCCESS;
                                    })
                            )
                    )
            )
            .then(Commands.literal("view")
                    .then(Commands.argument("target", ArgumentTypes.player())
                            .executes(ctx -> {
                                PlayerSelectorArgumentResolver targetResolver =  ctx.getArgument("target", PlayerSelectorArgumentResolver.class);
                                Player target = targetResolver.resolve(ctx.getSource()).getFirst();

                                ctx.getSource().getSender().sendMessage(target.getName() + "'s balance is " + econData.getBalance(target.getUniqueId()));
                                return Command.SINGLE_SUCCESS;
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
                                        if(amount < 0){
                                            ctx.getSource().getSender().sendMessage("Amount must be positive.");
                                            return Command.SINGLE_SUCCESS;
                                        }
                                        econData.setBalance(target.getUniqueId(), amount);

                                        ctx.getSource().getSender().sendMessage("Set " + amount + " to " + target.getName());
                                        return Command.SINGLE_SUCCESS;
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
                                if(amount < 0){
                                    ctx.getSource().getSender().sendMessage("Amount must be positive.");
                                    return Command.SINGLE_SUCCESS;
                                }
                                econData.setBalance(target.getUniqueId(), amount);
                                ctx.getSource().getSender().sendMessage("Reset " + amount + " to " + target.getName());
                                return Command.SINGLE_SUCCESS;
                            })
                    )
            )
            .then(Commands.literal("shop")
                    .executes(ctx -> {
                        Collection<String> items = shopConfig.getAllItems();
                        ctx.getSource().getSender().sendMessage("Available items:");
                        for(String item: items){
                            ctx.getSource().getSender().sendMessage("- " + item);
                            ctx.getSource().getSender().sendMessage("  - Price: " + shopConfig.getItemPrice(item));
                        }
                        return Command.SINGLE_SUCCESS;
                    })
            )
            .then(Commands.literal("buy")
                    .then(Commands.argument("item", ArgumentTypes.itemStack())
                            .executes(ctx -> {
                                ItemStack item = shopConfig.getItem(ctx.getArgument("item", String.class));
                                if(item == null){
                                    ctx.getSource().getSender().sendMessage("Item not found.");
                                    return Command.SINGLE_SUCCESS;
                                }
                                Player player = (Player) ctx.getSource().getSender();
                                int price = shopConfig.getItemPrice(item.getType().name());
                                if(!econData.canAfford(player.getUniqueId(), price)){
                                    ctx.getSource().getSender().sendMessage("You cannot afford this item.");
                                    return Command.SINGLE_SUCCESS;
                                }
                                econData.withdraw(player.getUniqueId(), price);
                                player.getInventory().addItem(item.clone());
                                ctx.getSource().getSender().sendMessage("Bought " + item.getAmount() + " of " + item.getType().name() + " for " + price);
                                return Command.SINGLE_SUCCESS;
                            })
                    )
            );


    public LiteralCommandNode<CommandSourceStack> build(){
        return root.build();
    }

}

