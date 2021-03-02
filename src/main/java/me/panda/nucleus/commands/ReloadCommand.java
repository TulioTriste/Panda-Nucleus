package me.panda.nucleus.commands;

import me.panda.nucleus.Nucleus;
import me.panda.nucleus.util.CC;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

/**
 * Created by DevSakio
 * Project: Nucleus
 * Date: 08/01/2021 @ 12:49
 * Class: ReloadCommand
 */
public class ReloadCommand extends Command {
    public ReloadCommand() {
        super("reload", "nucleus.reload");
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (commandSender.hasPermission(getPermission()))
            commandSender.sendMessage(CC.translate("&cYou dont have permissions!"));

        commandSender.sendMessage(CC.translate("&4Config has been reload"));
        Nucleus.getInstance().reloadConfig();
    }
}
