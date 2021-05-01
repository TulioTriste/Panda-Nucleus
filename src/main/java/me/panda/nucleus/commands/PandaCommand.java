package me.panda.nucleus.commands;

import me.panda.nucleus.Nucleus;
import me.panda.nucleus.util.CC;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

/**
 * Project: Nucleus
 * Date: 19/02/2021 @ 22:03
 * Class: PandaCommand
 */
public class PandaCommand extends Command {

    public PandaCommand(){
        super("panda", null, "nucleus");
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        commandSender.sendMessage(CC.translate("&c&lNucleus &7(1.1.8)"));
        commandSender.sendMessage(CC.translate("&cAuthor &7- Tulio / Sakio"));
        commandSender.sendMessage(CC.translate("&cDiscord &7- discord.gg/pandacommunity"));
        commandSender.sendMessage(CC.translate("&cWebsite &7- www.pandacommunity.org"));
        commandSender.sendMessage("");
        commandSender.sendMessage(CC.translate("&cVersion &7- " + Nucleus.getInstance().getDescription().getVersion()));
        commandSender.sendMessage(CC.translate("&cLast Update &7- 19/04/21"));
    }
}
