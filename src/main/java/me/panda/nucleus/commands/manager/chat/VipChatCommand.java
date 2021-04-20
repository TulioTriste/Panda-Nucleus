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


public class VipChatCommand extends Command {


	public VipChatCommand() {
		super("VipChat", null , "vp");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (sender instanceof ConsoleCommandSender) {
			return;
		}
		if (!sender.hasPermission(Nucleus.getInstance().getConfig().getString("CHAT.VIP.PERMS"))) {
			sender.sendMessage(CC.translate(Nucleus.getInstance().getConfig().getString("NO-PERMS")));
			return;
		}
		if (args.length != 0) {
			StringBuilder builder = new StringBuilder();
			
			for (int i = 0; i < args.length; ++i) {
                builder.append(args[i]).append(" ");
            }
			
			ProxyServer.getInstance().getPlayers().forEach(vip -> {
				String prefix = Objects.requireNonNull(LuckPermsProvider.get().getUserManager().getUser(vip.getUniqueId())).getCachedData().getMetaData().getPrefix() != null ? Objects.requireNonNull(LuckPermsProvider.get().getUserManager().getUser(vip.getUniqueId())).getCachedData().getMetaData().getPrefix() : "&r";
				if (vip.hasPermission(Nucleus.getInstance().getConfig().getString("CHAT.VIP.PERMS"))) {
					Nucleus.getInstance().getConfig().getStringList("CHAT.VIP.FORMAT").forEach(message -> {
						vip.sendMessage(message
								.replace("%name%", sender.getName())
								.replace("%server%", ((ProxiedPlayer) sender).getServer().getInfo().getName())
								.replace("%message%", builder.toString())
								.replace("%ranks%", CC.translate(prefix)));
					});
				}
			});
			return;
		}
		if (ProfileManager.getInstance().getProfile(((ProxiedPlayer) sender).getUniqueId()) == null) {
			return;
		}
		if (ProfileManager.getInstance().getProfile(((ProxiedPlayer) sender).getUniqueId()).isvip()) {
			ProfileManager.getInstance().getProfile(((ProxiedPlayer) sender).getUniqueId()).setvip(false);
			sender.sendMessage(CC.translate(Nucleus.getInstance().getConfig().getString("CHAT.VIP.DISABLE-CHAT")));
			return;
		}
		ProfileManager.getInstance().getProfile(((ProxiedPlayer) sender).getUniqueId()).setvip(true);
		sender.sendMessage(CC.translate(Nucleus.getInstance().getConfig().getString("CHAT.VIP.ENABLE-CHAT")));
	}
}
