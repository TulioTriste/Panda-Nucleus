package me.panda.nucleus.listeners;

import me.panda.nucleus.Nucleus;
import me.panda.nucleus.util.CC;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import net.md_5.bungee.event.EventHandler;

import java.io.File;

/**
 * Created by DevSakio
 * Project: Nucleus
 * Date: 08/01/2021 @ 12:07
 * Class: MotdListener
 */
public class MotdListener implements Listener {
    private Configuration config = null;
    @EventHandler(priority = 64)
    public void onServerListPing(final ProxyPingEvent event, String[] args){
        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(Nucleus.getInstance().getDataFolder(), "config.yml"));
        } catch (Exception ex) {
            Nucleus.getInstance().getLogger().fine("motd.edit in config.yml doesn't exist!");
            return;
        }
        final StringBuilder message = new StringBuilder();
        for (int i = 0; i < args.length; ++i) {
            message.append(args[i]).append(" ");
        }
        if (config.getString("MOTD.EDIT") == null){
            return;
        }
        final ServerPing ping = event.getResponse();
        String motd = CC.translate(config.getString("MOTD.EDIT").replace("︱", "\u2503").replace("%ARROW_1", "\u27a5"));
        motd = motd.replace("%NEWLINE%", "\n");
        motd = motd.replace("%D_ARROW%", "\u00BB");
        ping.setDescription(motd);
        event.setResponse(ping);
    }
}
