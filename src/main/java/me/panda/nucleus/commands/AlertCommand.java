package me.panda.nucleus.commands;


import joptsimple.internal.Strings;
import me.panda.nucleus.Nucleus;
import me.panda.nucleus.util.CC;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.command.ConsoleCommandSender;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class AlertCommand extends Command {

    public AlertCommand(){
        super("alert", "nucleus.alert", "announcement");
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (!commandSender.hasPermission(getPermission()) && !(commandSender instanceof ConsoleCommandSender)) {
            commandSender.sendMessage(CC.translate("&cNo permissions."));
            return;
        }

        if (strings.length == 0) {
            commandSender.sendMessage(CC.translate("&cUsage: /alert <message>"));
            return;
        }
        StringBuilder message = new StringBuilder(Strings.EMPTY);
        for (String string : strings) {
            message.append(string).append(" ");
        }
        ProxyServer.getInstance().broadcast(CC.translate(Nucleus.getInstance().getConfig().getString("ALERT-PREFIX") + " &f" + message.toString()));
    }
}
