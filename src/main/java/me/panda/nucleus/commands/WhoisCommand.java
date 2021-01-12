package me.panda.nucleus.commands;

import me.panda.nucleus.Nucleus;
import me.panda.nucleus.util.CC;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.command.ConsoleCommandSender;
import net.md_5.bungee.config.Configuration;

import java.io.IOException;
import java.util.stream.Collectors;

/**
 * Created by DevSakio
 * Project: Nucleus
 * Date: 07/01/2021 @ 22:05
 * Class: WhoisCommand
 */
public class WhoisCommand extends Command {

    public WhoisCommand(){
        super("whois", "nucleus.whois", "debugg");
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (!commandSender.hasPermission(getPermission()) && !(commandSender instanceof ConsoleCommandSender)) {
            commandSender.sendMessage(CC.translate("&cNo permissions."));
            return;
        }

        if (strings.length == 0) {
            commandSender.sendMessage(CC.translate("&cUsage: /whois <player>"));
            return;
        }

        final String target = strings[0];
        final ProxiedPlayer targetP = Nucleus.getInstance().getProxy().getPlayer(target);
        if (targetP == null) {
            commandSender.sendMessage(CC.translate("&cThis player isnt online!"));
            return;
        }

        for (String whoismessage : Nucleus.getInstance().getConfig().getStringList("WHOIS-MESSAGE")
        .stream()
        .map(a -> a.replace("%server%", String.valueOf(targetP.getServer())))
        .map(a -> a.replace("%name%" , String.valueOf(targetP.getName())))
        .map(a -> a.replace("%ping%", String.valueOf(targetP.getPing())))
        .map(a -> a.replace("%uuid", String.valueOf(targetP.getUniqueId())))
        .map(a -> a.replace("%ip%", String.valueOf(targetP.getAddress())))
        .map(a -> a.replace("%customname%", String.valueOf(targetP.getDisplayName())))
        .map(a -> a.replace("%perms%", String.valueOf(targetP.getPermissions())))
        .collect(Collectors.toList())){
            commandSender.sendMessage(CC.translate(whoismessage));
        }
    }
}
