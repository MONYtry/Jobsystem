package de.main.jobsystem;

import de.main.jobsystem.Listener.blockbreaklistener;
import de.main.jobsystem.Listener.jobSelectionGUIListener;
import de.main.jobsystem.commands.jobCommand;
import de.main.jobsystem.commands.reloadConfig;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Jobsystem extends JavaPlugin {

    private static Jobsystem instance;


    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        saveDefaultConfig();

        // LISTENER
        Bukkit.getPluginManager().registerEvents(new blockbreaklistener(),this);
        Bukkit.getPluginManager().registerEvents(new jobSelectionGUIListener(),this);
        // COMMANDS
        getCommand("job").setExecutor(new jobCommand());
        getCommand("reloadconfig").setExecutor(new reloadConfig());

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
