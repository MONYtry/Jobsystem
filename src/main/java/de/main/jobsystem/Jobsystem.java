package de.main.jobsystem;

import de.main.jobsystem.Listener.blockbreaklistener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Jobsystem extends JavaPlugin {

    private static Jobsystem instance;


    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(new blockbreaklistener(),this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Jobsystem getInstance()
    {
        return instance;
    }
}
