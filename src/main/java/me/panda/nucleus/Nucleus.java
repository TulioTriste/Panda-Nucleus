package me.panda.nucleus;


import lombok.Getter;
import me.panda.nucleus.commands.*;
import me.panda.nucleus.listeners.*;
import me.panda.nucleus.util.ConfigManager;
import me.panda.nucleus.util.CooldownManager;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;

@Getter
public class Nucleus extends Plugin {

    private static Nucleus instance;
    public static Configuration config;
    private CooldownManager cooldownManager;


    @Override
    public void onEnable() {
        this.getProxy().getConsole().sendMessage("FUnciono hijo de puta ");
        instance = this;
        this.onConfig();
        this.reloadConfig();
        registerManagers();
        registerCommands();
        registerListeners();
    }

    @Override
    public void onDisable() {
        cooldownManager.clearCooldowns();
    }

    private void registerCommands() {
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new AlertCommand());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new GlobalListCommand());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new SendCommand());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new ListCommand());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new RequestCommand());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new StaffChatCommand());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new ReportCommand());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new WhoisCommand());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new ReloadCommand());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new ServerInfoCommand());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new MotdCommand());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new MaintenanceCommand());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new SendCommand());
        Nucleus.getInstance().getConfig().getSection("SERVER").getKeys().forEach(commands ->
                ProxyServer.getInstance().getPluginManager().registerCommand(this, new ServerSendCommand(commands)));
    }

    private void registerListeners() {
        ProxyServer.getInstance().getPluginManager().registerListener(this, new PlayerListener());
            ProxyServer.getInstance().getPluginManager().registerListener(this, new MotdListener());
            ProxyServer.getInstance().getPluginManager().registerListener(this, new FilterListener());
            ProxyServer.getInstance().getPluginManager().registerListener(this, new MaintenanceListener());
            ProxyServer.getInstance().getPluginManager().registerListener(this, new PingListener());
    }

    public void onConfig() {
        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(ConfigManager.startConfig(this, "config.yml"));
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void saveConfig() {
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(Nucleus.getInstance().getConfig(), new File(this.getDataFolder(), "config.yml"));
        }
        catch (IOException e) {
            throw new RuntimeException("Unable to save configuration", e);
        }
    }

    public void reloadConfig() {
        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(this.getDataFolder(), "config.yml"));
        }
        catch (IOException e) {
            throw new RuntimeException("Unable to load configuration", e);
        }
    }


    private void registerManagers() {
        this.cooldownManager = new CooldownManager();
    }

    public static Nucleus getInstance() {
        return instance;
    }

    public CooldownManager getCooldownManager() {
        return cooldownManager;
    }

    public Configuration getConfig() { return config; }

}
