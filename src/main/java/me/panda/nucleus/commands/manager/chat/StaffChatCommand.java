package me.panda.nucleus.commands.manager.chat;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import joptsimple.internal.Strings;
import me.panda.nucleus.Nucleus;
import me.panda.nucleus.util.CC;
import net.luckperms.api.LuckPermsProvider;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.*;

public class StaffChatCommand extends Command {

    private final Nucleus plugin = Nucleus.getInstance();
    public static final Set<UUID> toggle = Sets.newHashSet();

    public StaffChatCommand() {
        super("staffchat", "nucleus.staffchat", "sc", "staffc");
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (!(commandSender instanceof ProxiedPlayer)) {
            commandSender.sendMessage(CC.translate("&cThis command only executable by Players."));
            return;
        }
        ProxiedPlayer player = (ProxiedPlayer) commandSender;
        if (strings.length == 0) {
            if (!toggle.contains(player.getUniqueId())) {
                toggle.add(player.getUniqueId());
                commandSender.sendMessage(CC.translate(Nucleus.getInstance().getConfig().getString("CHAT.STAFF.ENABLE-CHAT")));
            } else {
                toggle.remove(player.getUniqueId());
                commandSender.sendMessage(CC.translate(Nucleus.getInstance().getConfig().getString("CHAT.STAFF.DISABLE-CHAT")));
            }
            return;
        }
        StringBuilder message = new StringBuilder(Strings.EMPTY);
        for (String string : strings)
            message.append(string).append(" ");
        String prefix = LuckPermsProvider.get().getUserManager().getUser(player.getUniqueId()).getCachedData().getMetaData().getPrefix() != null ? LuckPermsProvider.get().getUserManager().getUser(player.getUniqueId()).getCachedData().getMetaData().getPrefix() : "&r";
        String playerString = player.getName();
        this.plugin.getProxy().getPlayers().forEach(players -> {
            if (players.hasPermission(getPermission())){
                player.sendMessage(CC.translate(Nucleus.getInstance().getConfig().getString("CHAT.STAFF.FORMAT")).
                        replace("%name%", playerString)
                        .replace("%server%", player.getServer().getInfo().getName())
                        .replace("%message%", message.toString())
                        .replace("%ranks%", CC.translate(prefix)));
            }
        });
    }
}