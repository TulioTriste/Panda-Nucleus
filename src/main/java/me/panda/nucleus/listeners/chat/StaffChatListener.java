package me.panda.nucleus.listeners.chat;

import me.panda.nucleus.Nucleus;
import me.panda.nucleus.util.CC;
import me.panda.nucleus.util.ProfileManager;
import net.luckperms.api.LuckPermsProvider;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.Objects;


public class StaffChatListener implements Listener {
	@EventHandler
	public void onChat(ChatEvent event) {
		if (event.getMessage().startsWith("/")) {
            return;
        }
		if (!((ProxiedPlayer) event.getSender()).hasPermission(Nucleus.getInstance().getConfig().getString("CHAT.STAFF.PERMS"))) {
			return;
		}
		if (ProfileManager.getInstance().getProfile(((ProxiedPlayer) event.getSender()).getUniqueId()) == null) {
			return;
		}
		if (!ProfileManager.getInstance().getProfile(((ProxiedPlayer) event.getSender()).getUniqueId()).isvip()) {
			return;
		}
		ProxyServer.getInstance().getPlayers().forEach(staff -> {
			String prefix = Objects.requireNonNull(LuckPermsProvider.get().getUserManager().getUser(staff.getUniqueId())).getCachedData().getMetaData().getPrefix() != null ? Objects.requireNonNull(LuckPermsProvider.get().getUserManager().getUser(staff.getUniqueId())).getCachedData().getMetaData().getPrefix() : "&r";
			if (staff.hasPermission(Nucleus.getInstance().getConfig().getString("CHAT.STAFF.PERMS"))) {
				Nucleus.getInstance().getConfig().getStringList("CHAT.STAFF.FORMAT").forEach(message -> {
					staff.sendMessage(message.replace("%player%", ((ProxiedPlayer) event.getSender()).getName()).
							replace("%server%", ((ProxiedPlayer) event.getSender()).getServer().getInfo().getName()).
							replace("%message%", event.getMessage())
							.replace("%ranks%", CC.translate(prefix)));
				});
			}
		});
		event.setCancelled(true);
	}
}
