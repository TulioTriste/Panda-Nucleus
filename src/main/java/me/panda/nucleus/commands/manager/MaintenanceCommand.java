package me.panda.nucleus.commands.manager;

import me.panda.nucleus.Nucleus;
import me.panda.nucleus.util.CC;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

/**
 * Created by DevSakio
 * Project: Nucleus
 * Date: 12/01/2021 @ 19:01
 * Class: MaintenanceCommand
 */
public class MaintenanceCommand extends Command {

    public MaintenanceCommand() {
        super("maintenance", "nucleus.manager");
    }


    @Override
    public void execute(CommandSender commandSender, String[] args) {
        if (!commandSender.hasPermission(getPermission())){
            commandSender.sendMessage(CC.translate(Nucleus.getInstance().getConfig().getString("NO-PERMS")));
            return;
        }
        if (!(commandSender instanceof ProxiedPlayer)) {
            if(args.length == 0) {
                commandSender.sendMessage(CC.translate("&cIncorrect usage, Use: /maintenance <true:false>"));
                return;
            }
            if(args.length > 1) {
                commandSender.sendMessage(CC.translate("&cIncorrect usage, Use: /maintenance <true:false>"));
                return;
            }
            switch (args[0]) {
                case "true":
                    Nucleus.getInstance().getConfig().set("MAINTENANCE.ENABLED", true);
                    Nucleus.getInstance().saveConfig();
                    (Nucleus.getInstance()).reloadConfig();
                    commandSender.sendMessage(CC.translate("&4&LMaintenance: &aEnabled"));
                    break;
                case "false":
                    Nucleus.getInstance().getConfig().set("MAINTENANCE.ENABLED", false);
                    Nucleus.getInstance().saveConfig();
                    Nucleus.getInstance().reloadConfig();
                    commandSender.sendMessage(CC.translate("&4&LMaintenance: &cDisabled"));
                    break;
            }
            return;
        }

        ProxiedPlayer player = (ProxiedPlayer)commandSender;
        if(player.hasPermission(getPermission())) {
            if(args.length == 0) {
                commandSender.sendMessage(CC.translate("&cIncorrect usage, Use: /maintenance <true:false>"));
                return;
            }
            if(args.length > 1) {
                commandSender.sendMessage(CC.translate("&cIncorrect usage, Use: /maintenance <true:false>"));
                return;
            }
            switch (args[0]) {
                case "true":
                    Nucleus.getInstance().getConfig().set("MAINTENANCE.ENABLED", true);
                    Nucleus.getInstance().getConfig().set("MAINTENANCE-MOTD.STATUS", true);
                    Nucleus.getInstance().getConfig().set("MOTD.STATUS", false);
                    Nucleus.getInstance().saveConfig();
                    Nucleus.getInstance().reloadConfig();
                    commandSender.sendMessage(CC.translate("&4&LMaintenance: &aEnabled"));
                    break;
                case "false":
                    Nucleus.getInstance().getConfig().set("MAINTENANCE.ENABLED", false);
                    Nucleus.getInstance().getConfig().set("MAINTENANCE-MOTD.STATUS", false);
                    Nucleus.getInstance().getConfig().set("MOTD.STATUS", true);
                    Nucleus.getInstance().saveConfig();
                    Nucleus.getInstance().reloadConfig();
                    commandSender.sendMessage(CC.translate("&4&LMaintenance: &cDisabled"));
                    break;
            }
        } else {
            player.sendMessage(CC.translate("&cNope!"));
        }
    }
}
