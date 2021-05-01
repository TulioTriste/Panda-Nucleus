package me.panda.nucleus.listeners;


import me.panda.nucleus.Nucleus;
import me.panda.nucleus.util.CC;
import net.luckperms.api.LuckPermsProvider;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.*;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class PlayerListener implements Listener {

    private final Nucleus plugin = Nucleus.getInstance();
    private final Map<ProxiedPlayer, String> leftServer = new HashMap<>();

    @EventHandler
    public void onPlayerDisconnect(PlayerDisconnectEvent event) {
        leftServer.remove(event.getPlayer());
    }

    @EventHandler
    public void onSwitch(ServerSwitchEvent event) {
        ProxiedPlayer player = event.getPlayer();
        if (player.hasPermission("nucleus.serverswitch")) {
            String prefix = Objects.requireNonNull(LuckPermsProvider.get().getUserManager().getUser(player.getUniqueId())).getCachedData().getMetaData().getPrefix() != null ? Objects.requireNonNull(LuckPermsProvider.get().getUserManager().getUser(player.getUniqueId())).getCachedData().getMetaData().getPrefix() : "&r";
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    plugin.getProxy().getPlayers().forEach(players -> {
                        if (players.hasPermission("nucleus.serverswitch")) {
                            if (leftServer.get(player) != null)
                                players.sendMessage(CC.translate(Nucleus.config.getString("STAFF.JOIN")
                                        .replace("%server%", player.getServer().getInfo().getName()))
                                        .replace("%name%", player.getName())
                                        .replace("%prefix%", CC.translate(prefix)));
                            else
                                players.sendMessage(CC.translate(Nucleus.config.getString("STAFF.JOIN")
                                        .replace("%server%", player.getServer().getInfo().getName()))
                                        .replace("%name%", player.getName())
                                        .replace("%prefix%", CC.translate(prefix)));

                        }
                    });
                }
            };
            this.plugin.getProxy().getScheduler().schedule(this.plugin, runnable, 1L, TimeUnit.MILLISECONDS);
        }
    }
    @EventHandler
    public void onDisconnect(PlayerDisconnectEvent event) {
        ProxiedPlayer player = event.getPlayer();
        if (player.hasPermission("nucleus.serverdisconnect")) {
            String prefix = Objects.requireNonNull(LuckPermsProvider.get().getUserManager().getUser(player.getUniqueId())).getCachedData().getMetaData().getPrefix() != null ? LuckPermsProvider.get().getUserManager().getUser(player.getUniqueId()).getCachedData().getMetaData().getPrefix() : "&r";
            this.plugin.getProxy().getPlayers().forEach(players -> {
                if (players.hasPermission("nucleus.serverdisconnect"))
                    players.sendMessage(CC.translate(Nucleus.getInstance().getConfig().getString("STAFF.LEAVE")
                            .replace("%name%", player.getName())
                            .replace("%prefix%", CC.translate(prefix))
                            .replace("%server%", player.getServer().getInfo().getName())));
            });
        }
    }
    @EventHandler
    public void connect(ServerConnectEvent e){
        ProxiedPlayer player = e.getPlayer();
        String prefix = Objects.requireNonNull(LuckPermsProvider.get().getUserManager().getUser(player.getUniqueId())).getCachedData().getMetaData().getPrefix() != null ? LuckPermsProvider.get().getUserManager().getUser(player.getUniqueId()).getCachedData().getMetaData().getPrefix() : "&r";
       if (Nucleus.getInstance().getConfig().getBoolean("VIP.JOIN.STATUS")){
           if (e.getTarget().getName().equalsIgnoreCase("SERVER.hub")){
               if (player.hasPermission("nucleus.vip.join")){
                   player.sendMessage(CC.translate(Nucleus.getInstance().getConfig().getString("VIP.JOIN.MESSAGE")
                           .replace("%server%", player.getServer().getInfo().getName()))
                           .replace("%name%", player.getName())
                           .replace("%prefix%", CC.translate(prefix)));
               }
           }
       }
    }
}
