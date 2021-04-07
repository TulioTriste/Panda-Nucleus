package me.panda.nucleus.listeners;


import me.panda.nucleus.Nucleus;
import me.panda.nucleus.commands.manager.StaffChatCommand;
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
    public void onChat(ChatEvent event) {
        if (!(event.getSender() instanceof ProxiedPlayer)) return;

        if (event.getMessage().startsWith("/")) return;

        ProxiedPlayer player = (ProxiedPlayer) event.getSender();
        if (StaffChatCommand.toggle.contains(player.getUniqueId())) {
            String prefix = Objects.requireNonNull(LuckPermsProvider.get().getUserManager().getUser(player.getUniqueId())).getCachedData().getMetaData().getPrefix() != null ? Objects.requireNonNull(LuckPermsProvider.get().getUserManager().getUser(player.getUniqueId())).getCachedData().getMetaData().getPrefix() : "&r";
            String playerString = prefix + player.getName();
            this.plugin.getProxy().getPlayers().forEach(players -> {
                if (players.hasPermission("nucleus.staffchat"))
                    player.sendMessage(CC.translate(Nucleus.getInstance().getConfig().getString("STAFF.CHAT-FORMAT")).
                            replace("%name%", playerString)
                            .replace("%server%", player.getServer().getInfo().getName())
                            .replace("message%", event.getMessage())
                            .replace("%ranks%", prefix));            });
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDisconnect(PlayerDisconnectEvent event) {
        leftServer.remove(event.getPlayer());
    }

    @EventHandler
    public void onServerDisconnect(ServerDisconnectEvent event) {
        leftServer.put(event.getPlayer(), event.getTarget().getName());
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
                                players.sendMessage(CC.translate(Nucleus.config.getString("STAFF.LEAVE")
                                        .replace("%server%", player.getServer().getInfo().getName()))
                                        .replace("%name%", player.getName())
                                        .replace("%prefix%", prefix));
                            else
                                players.sendMessage(CC.translate(Nucleus.config.getString("STAFF.JOIN")
                                        .replace("%server%", player.getServer().getInfo().getName()))
                                        .replace("%name%", player.getName())
                                        .replace("%prefix%", prefix));
                        }
                        if (player.hasPermission("nucleus.vip.serverswitch")){
                            players.sendMessage(CC.translate(Nucleus.config.getString("VIP.JOIN")
                                    .replace("%server%", player.getServer().getInfo().getName()))
                                    .replace("%name%", player.getName())
                                    .replace("%prefix%", prefix));
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
                    players.sendMessage(CC.translate(Nucleus.getInstance().getConfig().getString("STAFF.DISCONNECT")
                            .replace("%name%", player.getName())
                            .replace("%prefix%", prefix)));
            });
        }
    }
}
