package me.panda.nucleus.commands;

import me.panda.nucleus.Nucleus;
import me.panda.nucleus.util.CC;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

/**
 * Created by DevSakio
 * Project: Nucleus
 * Date: 08/01/2021 @ 20:12
 * Class: ServerSendCommand
 */
public class ServerSendCommand extends Command {

    private final String label;

    public ServerSendCommand(String server) {
        super(server, Nucleus.getInstance().getConfig().getString("SERVER." + server + ".PERMISSION"));
        this.label = server;
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if(!(commandSender instanceof ProxiedPlayer)) {
            commandSender.sendMessage(CC.translate(Nucleus.getInstance().getConfig().getString("NO-PERMS")));
            return;
        }

        ProxiedPlayer player = (ProxiedPlayer) commandSender;
        ServerInfo server = ProxyServer.getInstance().getServerInfo(label);
        player.connect(server);
        player.sendMessage(CC.translate(Nucleus.getInstance().getConfig().getString("SERVER.MESSAGE")
                .replace("%server", server.getName())));
    }
}
