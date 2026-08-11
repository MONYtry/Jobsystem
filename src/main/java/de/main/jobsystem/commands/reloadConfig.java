package de.main.jobsystem.commands;

import de.main.jobsystem.Jobsystem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class reloadConfig implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        FileConfiguration cfgMessages = Jobsystem.getInstance().messages;
        String prefix = cfgMessages.getString("messages.prefix");
        String succsessfullyReloaded = cfgMessages.getString("messages.reload_succses");

        if (command.getName().equalsIgnoreCase("reloadconfig"))
        {
            Jobsystem.getInstance().reloadConfig();
            Jobsystem.getInstance().savePlayerdata();
            sender.sendMessage(prefix + succsessfullyReloaded);
        }
        return true;
    }
}
