package me.panda.nucleus.commands.manager;

import me.panda.nucleus.Nucleus;
import me.panda.nucleus.util.CC;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

/**
 * Project: Nucleus
 * Date: 28/02/2021 @ 21:07
 * Class: SetMaxSlotsCommand
 */
public class SetMaxSlotsCommand extends Command implements Listener {

    public SetMaxSlotsCommand(){
        super("setmaxslots", "nucleus.setmaxslots", "maxslots");
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (commandSender.hasPermission(getPermission())){
            commandSender.sendMessage(CC.translate("&cYou dont have permission"));
        }
        if (strings.length == 0) {
            commandSender.sendMessage(CC.translate("&cUsage: /setmaxslots <server> <slots>"));
        }
        ServerInfo serverTarget = ProxyServer.getInstance().getServers().get(strings[1]);
    }
}
