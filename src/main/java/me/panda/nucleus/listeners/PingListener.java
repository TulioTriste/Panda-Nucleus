package me.panda.nucleus.listeners;

import me.panda.nucleus.Nucleus;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

/**
 * Created by DevSakio
 * Project: Nucleus
 * Date: 15/01/2021 @ 22:10
 * Class: PingListener
 */
public class PingListener implements Listener {
    @EventHandler
    public void onPing(ProxyPingEvent e){
        if(Nucleus.getInstance().getConfig().getBoolean("MAINTENANCE.ENABLE")){
            ServerPing ping = e.getResponse();
            ping.setVersion(new ServerPing.Protocol(Nucleus.getInstance().getConfig().getString("MAINTENANCE.PING"), 9999));
            e.setResponse(ping);
        }
    }
}
