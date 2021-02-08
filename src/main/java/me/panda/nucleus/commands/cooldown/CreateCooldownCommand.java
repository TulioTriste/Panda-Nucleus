package me.panda.nucleus.commands.cooldown;

import me.panda.nucleus.Nucleus;
import me.panda.nucleus.util.CC;
import me.panda.nucleus.util.CooldownManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.command.ConsoleCommandSender;

import java.util.UUID;

/**
 * Created by DevSakio
 * Project: Nucleus
 * Date: 20/01/2021 @ 18:43
 * Class: CreateCooldownCommand
 */
public class CreateCooldownCommand extends Command {
    public CreateCooldownCommand() {
        super("createcooldown", "nucleus.cooldown.create", "ccoldown");
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        ProxiedPlayer player = (ProxiedPlayer) commandSender;
        UUID uuid = player.getUniqueId();

        if (!commandSender.hasPermission(getPermission()) && !(commandSender instanceof ConsoleCommandSender)) {
            commandSender.sendMessage(CC.translate("&cNo permissions."));
            return;
        }

        if (strings.length == 0) {
            commandSender.sendMessage(CC.translate("&cUsage: /createcooldown <name> <h:min:s>"));
            return;
        }
        CooldownManager cooldownManager = new CooldownManager();
        UUID playeruuid = player.getUniqueId();
        cooldownManager.setCooldown(strings[1], playeruuid , Long.parseLong(strings[2]));
    }
}
