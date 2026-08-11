package de.main.jobsystem.commands;

import de.main.jobsystem.Jobsystem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import java.security.KeyStore;

public class giveBooster implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (command.getName().equalsIgnoreCase("booster") && sender instanceof Player p)
        {
            FileConfiguration playerData = Jobsystem.getInstance().playerdata;
            String boosterPath = "players." + p.getUniqueId() + ".booster";
            int booster = playerData.getInt(boosterPath,0);


            p.sendMessage("Du hast " + booster + " Booster");
            booster++;
            p.sendMessage("Du hast " + booster + " Booster");

            if (booster == 0)
            {
                p.sendMessage("Du hast keine Booster");
                return true;
            }

            playerData.set(boosterPath,booster);
            Jobsystem.getInstance().savePlayerdata();
        }
        return true;
    }
}
