package me.panda.nucleus.listeners;

import me.panda.nucleus.Nucleus;
import me.panda.nucleus.util.CC;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Listener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Project: Nucleus
 * Date: 10/04/2021 @ 22:15
 * Class: AutoMessageListener
 */
public class AutoMessageListener implements Listener {
    public static List<String> msglist;
    public static Random random;

    public AutoMessageListener() {
        ProxyServer.getInstance().getPluginManager().registerListener(Nucleus.getInstance(), this);
        this.Anuncion();
    }

    static {
        AutoMessageListener.msglist = new ArrayList<String>();
        AutoMessageListener.random = new Random();
    }

    public void Anuncion() {
        List<String> messages = (List<String>) Nucleus.getInstance().getConfig().getStringList("ANUNCIOS.ANUNCIOS_ACTIVADOS");
        for (String announcement : messages) {
            String message = Nucleus.getInstance().getConfig().getString("ANUNCIOS.ANUNCIOS." + announcement + ".MENSAJE");
            AutoMessageListener.msglist.add(CC.translate(message).replace("%NEWLINE%", "\n").replace("|", "\u2503").replace("%D_ARROW%", "\u00BB"));
        }

        Nucleus.getInstance().getProxy().getScheduler().schedule(Nucleus.getInstance(), () -> {
            BungeeCord.getInstance().broadcast("");
            BungeeCord.getInstance().broadcast(CC.translate(AutoMessageListener.msglist.get(Math.abs(AutoMessageListener.random.nextInt() % AutoMessageListener.msglist.size()))));
            BungeeCord.getInstance().broadcast("");
        }, (long)Nucleus.getInstance().getConfig().getInt("ANUNCIOS.COOLDOWN"), (long)Nucleus.getInstance().getConfig().getInt("ANUNCIOS.COOLDOWN"), TimeUnit.MINUTES);
    }
}
