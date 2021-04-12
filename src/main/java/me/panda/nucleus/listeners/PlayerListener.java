package me.panda.nucleus.listeners;


import me.panda.nucleus.Nucleus;
import me.panda.nucleus.commands.manager.chat.AdminChatCommand;
import me.panda.nucleus.commands.manager.chat.StaffChatCommand;
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
//staffchat
        if (StaffChatCommand.toggle.contains(player.getUniqueId())) {
            String prefix = Objects.requireNonNull(LuckPermsProvider.get().getUserManager().getUser
                    (player.getUniqueId())).getCachedData().getMetaData().getPrefix()
                    != null ? Objects.requireNonNull(
                    LuckPermsProvider.get().getUserManager().getUser(player.getUniqueId()))
                    .getCachedData().getMetaData().getPrefix() : "&r";
            plugin.getProxy().getPlayers().forEach(online -> {
                if (player.hasPermission("nucleus.staffchat")) {
                    player.sendMessage(CC.translate(Nucleus.getInstance().getConfig().getString("CHAT.STAFF.FORMAT")).
                            replace("%name%", player.getName())
                            .replace("%server%", player.getServer().getInfo().getName())
                            .replace("%message%", event.getMessage())
                            .replace("%ranks%", CC.translate(prefix)));
                }
            });
            event.setCancelled(true);
            if (AdminChatCommand.toggle.contains(player.getUniqueId())) {
                plugin.getProxy().getPlayers().forEach(online -> {
                    if (player.hasPermission("nucleus.adminchat")) {
                        player.sendMessage(CC.translate(Nucleus.getInstance().getConfig().getString("CHAT.ADMIN.FORMAT")).
                                replace("%name%", player.getName())
                                .replace("%server%", player.getServer().getInfo().getName())
                                .replace("%message%", event.getMessage())
                                .replace("%ranks%", CC.translate(prefix)));
                    }
                }); //fin staffchat
                event.setCancelled(true);
            }
        }
    }
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
                        if (Nucleus.getInstance().getConfig().getBoolean("VIP.SERVER-SWITCH.STATUS")){
                            if (player.hasPermission("nucleus.vip.serverswitch")){
                                players.sendMessage(CC.translate(Nucleus.config.getString("VIP.SERVER-SWITCH.MESSAGE")
                                        .replace("%server%", player.getServer().getInfo().getName()))
                                        .replace("%name%", player.getName())
                                        .replace("%prefix%", CC.translate(prefix)));
                            }
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
}
