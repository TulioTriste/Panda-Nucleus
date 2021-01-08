package me.panda.nucleus.commands;

import me.panda.nucleus.Nucleus;
import me.panda.nucleus.util.CC;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;

/**
 * Created by DevSakio
 * Project: Nucleus
 * Date: 07/01/2021 @ 23:09
 * Class: MotdCommand
 */
public class MotdCommand extends Command {

    private Configuration config = null;

    public MotdCommand() {
        super("bmotd", "nucleus.motd", "setmotd");
    }

    public void execute(CommandSender sender, final String[] args) {
        if (args.length == 0) {
            sender.sendMessage(CC.translate("&cUsage: /bmotd <text> - %NEWLINE% new line"));
        }
        else {
            try {
                config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(Nucleus.getInstance().getDataFolder(), "config.yml"));
            } catch (Exception ex) {
                Nucleus.getInstance().getLogger().fine("motd.edit in config.yml doesn't exist!");
                return;
            }
            final StringBuilder message = new StringBuilder();
            for (int i = 0; i < args.length; ++i) {
                message.append(args[i]).append(" ");
            }
          config.set("MOTD.EDIT", (Object)message.toString());
            sender.sendMessage(CC.translate("&eYou have updated motd to: " + CC.translate(message.toString())));
        }
    }
}
