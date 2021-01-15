package me.panda.nucleus.listeners;

import me.panda.nucleus.Nucleus;
import me.panda.nucleus.util.CC;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.List;

/**
 * Created by DevSakio
 * Project: Nucleus
 * Date: 09/01/2021 @ 12:38
 * Class: BlockCommandListener
 */
public class FilterListener implements Listener {
    List<String> commands = Nucleus.getInstance().getConfig().getStringList("BLOCK-COMMAND");
    String string = "";
    @EventHandler
    public void executeCommand(ChatEvent event){
        ProxiedPlayer player = (ProxiedPlayer) event.getSender();
        if (player.hasPermission("nucleus.bypass")) {
            return;
        }
        for (String s : commands)
        {
            string += s + "\t";
        }
        if (!player.hasPermission("nucleus.bypass")){
            if (event.isCommand() || event.equals(string)){
                event.setCancelled(true);
                player.sendMessage(CC.translate("&cThis command has been block be console!"));
            }
        }
    }
}
