package me.panda.nucleus.listeners;

import me.panda.nucleus.Nucleus;
import me.panda.nucleus.util.CC;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

/**
 * Created by DevSakio
 * Project: Nucleus
 * Date: 09/01/2021 @ 12:38
 * Class: BlockCommandListener
 */
public class FilterListener implements Listener {
    @EventHandler
    public void executeCommand(ChatEvent event){
        ProxiedPlayer player = (ProxiedPlayer) event.getSender();
        if (player.hasPermission("nucleus.bypass")){
            return;
        }
        if (!player.hasPermission("nucleus.bypass")){
            if (!(!event.isCommand() || !event.equals(Nucleus.getInstance().getConfig().getStringList("BLOCK-COMMAND")))){
                event.isCancelled();
                player.sendMessage(CC.translate("&cThis command has been block be console!"));
            }
        }
    }
}
