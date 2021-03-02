package me.panda.nucleus.commands;

import me.panda.nucleus.Nucleus;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

/**
 * Project: Nucleus
 * Date: 02/03/2021 @ 19:42
 * Class: PingCommand
 */
public class PingCommand extends Command {
    public PingCommand(){
        super("ping", null, "");
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        ProxiedPlayer player = (ProxiedPlayer) commandSender;
        ProxiedPlayer target = Nucleus.getInstance().getProxy().getPlayer(strings[0]);
        commandSender.sendMessage(Nucleus.getInstance().getConfig().getString("MESSAGE.PING")
                .replace("%ping%", String.valueOf(player.getPing())));
        if (strings[1].equals(target)){
            commandSender.sendMessage(Nucleus.getInstance().getConfig().getString("MESSAGE.PING")
                    .replace("%ping%", String.valueOf(player.getPing())
                            .replace("name", target.getName())));
        }
    }
}
