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
 * Project: Nucleus
 * Date: 29/01/2021 @ 23:52
 * Class: ClearCooldownCommand
 */
public class ClearCooldownCommand extends Command{
    public ClearCooldownCommand() {
        super("clearcooldown", "nucleus.cooldown.clear", "cclear");
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
            commandSender.sendMessage(CC.translate("&cUsage: /clearcooldown <name> "));
            return;
        }
        Nucleus.getInstance().getCooldownManager().clearCooldowns(strings[1]);
        commandSender.sendMessage(CC.translate("&3Cooldowns Clears!"));
    }
}
