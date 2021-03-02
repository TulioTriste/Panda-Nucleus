package me.panda.nucleus.commands.manager;

import me.panda.nucleus.Nucleus;
import me.panda.nucleus.util.CC;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Command;


/**
 * Created by DevSakio
 * Project: Nucleus
 * Date: 09/01/2021 @ 12:53
 * Class: ServerInfoCommand
 */
public class ServerInfoCommand extends Command {
    public ServerInfoCommand() {
        super("server", "nucleus.serverinfo", "network");
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        final ProxyServer server = (ProxyServer) Nucleus.getInstance().getProxy().getServers();
        String servers = Nucleus.getInstance().getConfig().getString("SERVER.");
        if (!commandSender.hasPermission(getPermission()))
            return;

        if (strings[1].equals(ProxyServer.getInstance().getServers())){
            commandSender.sendMessage(CC.translate("&4Server - %server%").
                    replace("%server%" , Nucleus.getInstance().getConfig().getString("SERVER." + servers)));
            commandSender.sendMessage(CC.translate("&4Versions - %versions%").
                    replace("%versions%" , ProxyServer.getInstance().getVersion()));
        }
    }
}
