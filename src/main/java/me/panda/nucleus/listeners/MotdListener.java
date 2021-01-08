package me.panda.nucleus.listeners;

import me.panda.nucleus.Nucleus;
import me.panda.nucleus.util.CC;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;


/**
 * Created by DevSakio
 * Project: Nucleus
 * Date: 08/01/2021 @ 12:07
 * Class: MotdListener
 */
public class MotdListener implements Listener{
    @EventHandler(priority = 64)
    public void onServerListPing(final ProxyPingEvent event){

        if (Nucleus.getInstance().getConfig().getString("MOTD-EDIT") == null){
            return;
        }
        final ServerPing ping = event.getResponse();
        String motd = CC.translate(Nucleus.getInstance().getConfig().getString("MOTD-EDIT").replace("︱", "\u2503").replace("%ARROW_1", "\u27a5"));
        motd = motd.replace("%NEWLINE%", "\n");
        motd = motd.replace("%D_ARROW%", "\u00BB");
        ping.setDescription(motd);
        event.setResponse(ping);
    }
}
