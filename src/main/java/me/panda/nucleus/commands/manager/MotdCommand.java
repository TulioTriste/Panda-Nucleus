package me.panda.nucleus.commands.manager;

import me.panda.nucleus.Nucleus;
import me.panda.nucleus.util.CC;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;


/**
 * Created by DevSakio
 * Project: Nucleus
 * Date: 07/01/2021 @ 23:09
 * Class: MotdCommand
 */
public class MotdCommand extends Command {


    public MotdCommand() {
        super("bmotd", "nucleus.motd", "setmotd");
    }

    public void execute(CommandSender commandSender, final String[] args) {
        if (commandSender.hasPermission(getPermission())){
            commandSender.sendMessage(CC.translate(Nucleus.getInstance().getConfig().getString("NO-PERMS")));
        }
        if (args.length == 0) {
            commandSender.sendMessage(CC.translate("&cUsage: /bmotd <text> - %NEWLINE% new line"));
        }
        else {
            final StringBuilder message = new StringBuilder();
            for (int i = 0; i < args.length; ++i) {
                message.append(args[i]).append(" ");
            }
          Nucleus.getInstance().getConfig().set("MOTD-EDIT", (Object)message.toString());
            Nucleus.getInstance().saveConfig();
            commandSender.sendMessage(CC.translate("&eYou have updated motd to: " + CC.translate(message.toString())));
        }
        if (args[1].equalsIgnoreCase("status")){
            if(args.length > 2) {
                commandSender.sendMessage(CC.translate("&cIncorrect usage, Use: /bmotd status <true:false>"));
                return;
            }
            switch (args[0]) {
                case "true":
                    Nucleus.getInstance().getConfig().set("MOTD.STATUS", true);
                    Nucleus.getInstance().saveConfig();
                    Nucleus.getInstance().reloadConfig();
                    commandSender.sendMessage(CC.translate("&4&lBMotd: &aEnabled"));
                    break;
                case "false":
                    Nucleus.getInstance().getConfig().set("MOTD.STATUS", false);
                    Nucleus.getInstance().saveConfig();
                    Nucleus.getInstance().reloadConfig();
                    commandSender.sendMessage(CC.translate("&4&lBMotd: &cDisabled"));
                    break;
            }
        }
    }
}
