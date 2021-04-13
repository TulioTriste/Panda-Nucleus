package me.panda.nucleus.commands.manager.chat;

import me.panda.nucleus.Nucleus;
import me.panda.nucleus.util.CC;
import me.panda.nucleus.util.ProfileManager;
import net.luckperms.api.LuckPermsProvider;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.command.ConsoleCommandSender;

import java.util.Objects;


public class StaffChatCommand extends Command {


	public StaffChatCommand() {
		super("staffchat", null , "sc");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (sender instanceof ConsoleCommandSender) {
			return;
		}
		if (!sender.hasPermission(Nucleus.getInstance().getConfig().getString("CHAT.STAFF.PERMS"))) {
			sender.sendMessage(CC.translate(Nucleus.getInstance().getConfig().getString("NO-PERMS")));
			return;
		}
		if (args.length != 0) {
			StringBuilder builder = new StringBuilder();
			
			for (int i = 0; i < args.length; ++i) {
                builder.append(args[i]).append(" ");
            }
			
			ProxyServer.getInstance().getPlayers().forEach(staff -> {
				String prefix = Objects.requireNonNull(LuckPermsProvider.get().getUserManager().getUser(staff.getUniqueId())).getCachedData().getMetaData().getPrefix() != null ? Objects.requireNonNull(LuckPermsProvider.get().getUserManager().getUser(staff.getUniqueId())).getCachedData().getMetaData().getPrefix() : "&r";
				if (staff.hasPermission(Nucleus.getInstance().getConfig().getString("CHAT.STAFF.PERMS"))) {
					Nucleus.getInstance().getConfig().getStringList("CHAT.VIP.FORMAT").forEach(message -> {
						staff.sendMessage(message
								.replace("%name%", sender.getName()).
										replace("%server%", ((ProxiedPlayer) sender).getServer().getInfo().getName())
								.replace("%message%", builder.toString())
								.replace("%prefix%", CC.translate(prefix)));
					});
				}
			});
			return;
		}
		if (ProfileManager.getInstance().getProfile(((ProxiedPlayer) sender).getUniqueId()) == null) {
			return;
		}
//		if (ProfileManager.getInstance().getProfile(((ProxiedPlayer) sender).getUniqueId()).isHeadStaff()) {
//			sender.sendMessage(CC.translate(Nucleus.getInstance().getConfig().getString()));
//			return;
//		}
		if (ProfileManager.getInstance().getProfile(((ProxiedPlayer) sender).getUniqueId()).isStaff()) {
			ProfileManager.getInstance().getProfile(((ProxiedPlayer) sender).getUniqueId()).setStaff(false);
			sender.sendMessage(CC.translate(Nucleus.getInstance().getConfig().getString("CHAT.STAFF.DISABLE")));
			return;
		}
		ProfileManager.getInstance().getProfile(((ProxiedPlayer) sender).getUniqueId()).setStaff(true);
		sender.sendMessage(CC.translate(Nucleus.getInstance().getConfig().getString("CHAT.STAFF.ENABLE")));
	}
}
