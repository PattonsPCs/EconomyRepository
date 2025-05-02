package com.anthony.events;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import com.anthony.EconData;
import com.anthony.transactions.PluginLogger;

import java.util.UUID;

public class MobKillListener implements Listener {
    private final EconData econData;
    private final PluginLogger logger;

    public MobKillListener(EconData econData, PluginLogger logger){
        this.econData = econData;
        this.logger = logger;
    }

    @EventHandler
    public void onKill(EntityDeathEvent event) {
        Player killer = event.getEntity().getKiller();
        if(killer == null){
            return;
        }
        UUID uuid = killer.getUniqueId();
        int currentBalance = econData.getBalance(uuid);
        int reward = 15;
        econData.setBalance(uuid, currentBalance + reward);

        logger.log("Player " + killer.getName() + " earned " + reward + " for killing " + event.getEntity().getName());
        killer.sendActionBar(MiniMessage.miniMessage().deserialize("<bold><italic><dark_gray>You killed a <white>" + event.getEntity().getName() + "</white>. <green>+$" + reward + "</green></dark_gray></bold>"));
    }
}
