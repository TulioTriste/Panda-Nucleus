package me.panda.nucleus.util;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.common.collect.Tables;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.*;

public class CooldownManager {

    private final Table<String, UUID, Long> cooldowns;

    public CooldownManager() {
        this.cooldowns = HashBasedTable.create();
    }

    public void setCooldown(String cooldown, UUID uuid, long time) {
        if (time < 1L) {
            this.cooldowns.remove(cooldown, uuid);
        }
        else {
            this.cooldowns.put(cooldown, uuid, time);
        }
    }

    public long getCooldown(String cooldown, UUID uuid) {
        return this.cooldowns.get(cooldown, uuid) != null ? this.cooldowns.get(cooldown, uuid) : 0;
    }

    public void clearCooldowns(String cooldown) {
        this.cooldowns.clear();
    }
}
