package com.anthony.events;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import com.anthony.EconData;

import java.util.UUID;

public class MobKillListener implements Listener {
    private final EconData econData;

    public MobKillListener(EconData econData){
        this.econData = econData;
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
        killer.sendActionBar(MiniMessage.miniMessage().deserialize("<bold><italic><dark_gray>You killed a <white>" + event.getEntity().getName() + "</white>. <green>+$" + reward + "</green></dark_gray></bold>"));
    }
}
