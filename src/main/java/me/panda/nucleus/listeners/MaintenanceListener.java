package me.panda.nucleus.listeners;

import me.panda.nucleus.Nucleus;
import me.panda.nucleus.util.CC;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

/**
 * Created by DevSakio
 * Project: Nucleus
 * Date: 12/01/2021 @ 18:56
 * Class: MaintenanceListener
 */
public class MaintenanceListener implements Listener {
    @EventHandler
    public void onProxyPing(final ProxyPingEvent event) {
        if (!Nucleus.getInstance().getConfig().getBoolean("MAINTENANCE.ENABLED")) {
            return;
        }
        final ServerPing ping = event.getResponse();
        ping.setVersion(new ServerPing.Protocol(CC.translate("&4Maintenance"), 1));
    }

    @EventHandler
    public void postLogin(final PostLoginEvent event) {
        final ProxiedPlayer player = event.getPlayer();
        if (Nucleus.getInstance().getConfig().getBoolean("MAINTENANCE.MOTD.ENABLED") && !player.hasPermission("nucleus.maintenance")) {
            player.disconnect(CC.translate(
                    Nucleus.getInstance().getConfig().getString("MAINTENANCE.KICK_MESSAGE").replace("%NEWLINE%", "\n")));
        }
    }
    @EventHandler(priority = 64)
    public void onServerListPing(final ProxyPingEvent event) {
        if (Nucleus.getInstance().getConfig().getBoolean("MAINTENANCE.MOTD.STATUS")) {
            if (Nucleus.getInstance().getConfig().getString("MOTD-EDIT") == null) {
                return;
            }
            final ServerPing ping = event.getResponse();
            String motd = CC.translate(Nucleus.getInstance().getConfig().getString("MAINTENANCE.MOTD.MOTD-EDIT").replace("︱", "\u2503").replace("%ARROW_1", "\u27a5"));
            motd = motd.replace("%NEWLINE%", "\n");
            motd = motd.replace("%D_ARROW%", "\u00BB");
            ping.setDescription(motd);
            event.setResponse(ping);
        }
    }
}
