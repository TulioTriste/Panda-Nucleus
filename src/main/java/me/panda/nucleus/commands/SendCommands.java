package me.panda.nucleus.commands;

import me.panda.nucleus.util.CC;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.command.ConsoleCommandSender;

import java.lang.reflect.Proxy;

/**
 * Created by DevSakio
 * Project: Nucleus
 * Date: 12/01/2021 @ 19:18
 * Class: SendCommands
 */
public class SendCommands extends Command {
    public SendCommands() {
        super("servercommand", "nucleus.servercommand", "scommand");
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (!commandSender.hasPermission(getPermission()) && commandSender instanceof ConsoleCommandSender){
            commandSender.sendMessage(CC.translate("&cNo permissions."));
            return;
        }
        if (strings.length == 0) {
            commandSender.sendMessage(CC.translate("&cUsage: /servercommand <server> </commands>"));
            return;
        }
        if (!ProxyServer.getInstance().getServers().containsKey(strings[1])) {
            commandSender.sendMessage(CC.translate("&cThis server doesn't exist"));
            return;
        }
        if (strings.length > 1){
            commandSender.sendMessage(CC.translate("&c/servercommand" + strings[1] + "/commands"));
            return;
        }
        ProxyServer.getInstance().getServers().containsKey(strings[1]);{
            BungeeCord.getInstance().getConsole().sendMessage(strings[2]);
        }
    }
}
