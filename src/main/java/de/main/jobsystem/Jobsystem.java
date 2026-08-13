package de.main.jobsystem;

import de.main.jobsystem.Listener.*;
import de.main.jobsystem.booster.booster;
import de.main.jobsystem.commands.jobCommand;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.yaml.snakeyaml.constructor.SafeConstructor;

import java.io.File;
import java.io.IOException;

public final class Jobsystem extends JavaPlugin {

    private static Jobsystem instance;
    public FileConfiguration messages;
    public FileConfiguration playerdata;
    public FileConfiguration gui;
    private File playerdataFile;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        saveDefaultConfig();
        saveResource("messages.yml", false);
        saveResource("playerdata.yml",false);
        saveResource("gui.yml",false);

        gui = YamlConfiguration.loadConfiguration(new File(getDataFolder(),"gui.yml"));
        messages = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "messages.yml"));
        playerdata = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "playerdata.yml"));
        playerdataFile = new File(getDataFolder(), "playerdata.yml");

        if (!playerdataFile.exists()) {
            try {
                playerdataFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        playerdata = YamlConfiguration.loadConfiguration(playerdataFile);

        // LISTENER
        Bukkit.getPluginManager().registerEvents(new blockbreaklistener(),this);
        Bukkit.getPluginManager().registerEvents(new GUIListener(),this);



        // COMMANDS
        getCommand("job").setExecutor(new jobCommand());


        // Aktive Booster nach Serverstart wieder laden
        booster.loadActiveBoosters();



    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Jobsystem getInstance()
    {
        return instance;
    }
    public void savePlayerdata() {
        try {
            playerdata.save(playerdataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reloadGUI() {
        File guiFile = new File(getDataFolder(), "gui.yml");

        if (!guiFile.exists()) {
            saveResource("gui.yml", false);
        }

        gui = YamlConfiguration.loadConfiguration(guiFile);
    }
}
