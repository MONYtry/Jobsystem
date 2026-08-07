package de.main.jobsystem.commands;

import de.main.jobsystem.Jobsystem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class reloadConfig implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (command.getName().equalsIgnoreCase("reloadconfig"))
        {
            Jobsystem.getInstance().reloadConfig();
            sender.sendMessage("Config was reloaded");
        }
        return true;
    }
}
