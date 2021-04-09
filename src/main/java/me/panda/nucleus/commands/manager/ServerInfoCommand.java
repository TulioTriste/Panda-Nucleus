package me.panda.nucleus.commands.manager;

import me.panda.nucleus.Nucleus;
import me.panda.nucleus.util.CC;
import net.luckperms.api.LuckPermsProvider;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.lang.reflect.Proxy;
import java.util.stream.Collectors;

/**
 * Project: Nucleus
 * Date: 09/04/2021 @ 16:10
 * Class: ServerInfoCommand
 */
public class ServerInfoCommand extends Command {
    public ServerInfoCommand() {
        super("server", "nucleus.servermanager", "sv");
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        ProxiedPlayer player = (ProxiedPlayer) commandSender;
        ServerInfo serverTarget = ProxyServer.getInstance().getServers().get(strings[2]);
        if (commandSender.hasPermission(getPermission())){
            commandSender.sendMessage(CC.translate("&cYou dont have permission"));
        }
        if (strings.length == 0) {
            commandSender.sendMessage(CC.translate("&cUsage: /server manager <server>"));
            return;
        }
        if (!ProxyServer.getInstance().getServers().containsKey(strings[2])) {
            commandSender.sendMessage(CC.translate("&cThis server doesn't exist"));
            return;
        }
        if (ProxyServer.getInstance().getServers().containsKey(strings[2])){
           // commandSender.sendMessage(CC.translate("&aInformation - <server>").replace("<server>", strings[2]));7
            for (String serverinfomessage : Nucleus.getInstance().getConfig().getStringList("SERVERINFO-MESSAGE")
                    .stream()
                    .map(a -> a.replace("%server%", serverTarget.getName()))
                    .map(a -> a.replace("%adress%", String.valueOf(serverTarget.getAddress())))
                    .map(a -> a.replace("%players_count%", String.valueOf(serverTarget.getPlayers())))
                    .collect(Collectors.toList())) {
                commandSender.sendMessage(CC.translate(serverinfomessage));
            }
        }
    }
}
