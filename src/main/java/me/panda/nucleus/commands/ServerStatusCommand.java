package me.panda.nucleus.commands;

import me.panda.nucleus.Nucleus;
import me.panda.nucleus.util.CC;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.stream.Collectors;

/**
 * Created by DevSakio
 * Project: Nucleus
 * Date: 08/01/2021 @ 23:12
 * Class: ServerStatusCommand
 */
public class ServerStatusCommand extends Command {
    public ServerStatusCommand() {
        super("serverstatus", "nucleus.status", "st");
    }
    ServerInfo server = (ServerInfo) ProxyServer.getInstance().getServers();

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if(!(commandSender instanceof ProxiedPlayer)) return;

        if (commandSender.hasPermission(getPermission()))
        commandSender.sendMessage(CC.translate("&cYou dont have permissions!"));

        for (String serverstatus : Nucleus.getInstance().getConfig().getStringList("STATUS-SERVER")
                .stream()
                .map(a -> a.replace("%server%", String.valueOf(server)))
                .map(a -> a.replace("%player_online%", String.valueOf(ProxyServer.getInstance().getOnlineCount())))
                .map(a -> a.replace("%server_info%", String.valueOf(ProxyServer.getInstance().getServerInfo(String.valueOf(server)))))
                .collect(Collectors.toList())){
            commandSender.sendMessage(CC.translate(serverstatus));
        }



    }
}
