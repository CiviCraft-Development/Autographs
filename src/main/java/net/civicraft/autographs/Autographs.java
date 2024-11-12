package net.civicraft.autographs;

import org.bukkit.plugin.java.JavaPlugin;

public final class Autographs extends JavaPlugin {
    private static Autographs instance;
    @Override
    public void onEnable() {
        saveDefaultConfig();
        instance = this;
        getCommand("autograph").setExecutor(new AutographCommand());
        getLogger().info("Autographs enabled!");
    }

    public static Autographs getInstance() {
        return instance;
    }

    @Override
    public void onDisable() {
        getLogger().info("Autographs disabled!");
    }
}
