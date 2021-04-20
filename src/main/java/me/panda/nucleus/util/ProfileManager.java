package me.panda.nucleus.util;


import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.HashMap;
import java.util.UUID;

/**
 * Project: Nucleus
 * Date: 13/04/2021 @ 18:46
 * Class: ProfileManager
 */
public class ProfileManager implements Listener {

    private static ProfileManager instance;

    private HashMap<UUID, Profile> profileStorage;

    public ProfileManager() {
        ProfileManager.instance = this;

        this.profileStorage = new HashMap<UUID, Profile>();

    }

    public void startProfile(UUID uuid) {
        if (!this.profileStorage.containsKey(uuid)) {
            this.profileStorage.put(uuid, new Profile());
        }
    }

    public Profile getProfile(UUID uuid) {
        if (this.profileStorage.containsKey(uuid)) {
            return this.profileStorage.get(uuid);
        }
        return null;
    }

    @EventHandler
    public void onJoin(ServerConnectedEvent event) {
        this.startProfile(event.getPlayer().getUniqueId());
    }

    public HashMap<UUID, Profile> getProfileStorage() {
        return profileStorage;
    }

    public void setProfileStorage(HashMap<UUID, Profile> profileStorage) {
        this.profileStorage = profileStorage;
    }

    public static ProfileManager getInstance() {
        return instance;
    }
}

