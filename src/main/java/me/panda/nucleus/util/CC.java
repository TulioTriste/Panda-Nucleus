package me.panda.nucleus.util;

import net.md_5.bungee.api.ChatColor;

import java.util.List;

public class  CC {

    public static String translate(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static List<String> translate(List<String> message) {
        message.forEach(lines -> {
            ChatColor.translateAlternateColorCodes('&', lines);
        });
        return message;
    }
}
