package de.main.jobsystem.commands;

import de.main.jobsystem.gui.jobSelectionGUI;
import de.main.jobsystem.manager.ItemCreator;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class jobCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (command.getName().equalsIgnoreCase("job") && sender instanceof Player p)
        {
            jobSelectionGUI.createSelectionGUI(p);
        }
        return true;
    }
}
