package me.panda.nucleus.commands;

import joptsimple.internal.Strings;
import me.panda.nucleus.Nucleus;
import me.panda.nucleus.util.CC;
import net.luckperms.api.LuckPermsProvider;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class ReportCommand extends Command {

    private final Nucleus plugin = Nucleus.getInstance();

    public ReportCommand() {
        super("report");
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (!(commandSender instanceof ProxiedPlayer)) {
            commandSender.sendMessage(CC.translate("&cThis command only executable by Players."));
            return;
        }
        if (strings.length == 0) {
            commandSender.sendMessage(CC.translate("&cUsage: /report <player> <reason>"));
            return;
        }
        else if (strings.length == 1) {
            commandSender.sendMessage(CC.translate("&cUsage: /report " + strings[0] + " <reason>"));
            return;
        }
        ProxiedPlayer target = this.plugin.getProxy().getPlayer(strings[0]);
        if (target == null || !target.isConnected()) {
            commandSender.sendMessage(CC.translate("&cThis player is not connected."));
            return;
        }
        ProxiedPlayer player = (ProxiedPlayer) commandSender;
        StringBuilder reason = new StringBuilder(Strings.EMPTY);
        for (int i = 1; i < strings.length; i++) {
            reason.append(strings[i]).append(" ");
        }
        String playerPrefix = LuckPermsProvider.get().getUserManager().getUser(player.getUniqueId()).getCachedData().getMetaData().getPrefix() != null ? LuckPermsProvider.get().getUserManager().getUser(player.getUniqueId()).getCachedData().getMetaData().getPrefix() : "&r";
        String targetPrefix = LuckPermsProvider.get().getUserManager().getUser(target.getUniqueId()).getCachedData().getMetaData().getPrefix() != null ? LuckPermsProvider.get().getUserManager().getUser(target.getUniqueId()).getCachedData().getMetaData().getPrefix() : "&r";
        long timeLeft = System.currentTimeMillis() - this.plugin.getCooldownManager().getCooldown("report", player.getUniqueId());
        if (TimeUnit.MILLISECONDS.toSeconds(timeLeft) >= 60L) {
            for (ProxiedPlayer online : this.plugin.getProxy().getPlayers()) {
                if (online.hasPermission("nucleus.report.notify")) {
                    for (String line : Nucleus.getInstance().getConfig().getStringList("STAFF.REPORT")) {
                        online.sendMessage(CC.translate((line
                                .replace("%server%", player.getServer().getInfo().getName())
                                .replace("%prefix%", CC.translate(playerPrefix))
                                .replace("%name%", player.getName())
                                .replace("%player_report%", CC.translate(targetPrefix + target.getName()))
                                .replace("%reasons%", reason.toString()))));
                    }
                }
            }
            player.sendMessage(CC.translate("&aThank you! If any staff are online, they will respond shortly."));
            this.plugin.getCooldownManager().setCooldown("report", player.getUniqueId(), System.currentTimeMillis());
        }
        else {
            String theWait = String.valueOf(TimeUnit.MILLISECONDS.toSeconds(timeLeft) - 60L);
            player.sendMessage(CC.translate("&cYou have to wait " + theWait.replace("-", "") + " second(s) to use /report again!"));
        }
    }
}
