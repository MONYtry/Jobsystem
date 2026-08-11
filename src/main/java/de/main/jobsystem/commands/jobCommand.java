package de.main.jobsystem.commands;

import de.main.jobsystem.Jobsystem;
import de.main.jobsystem.gui.jobSelectionGUI;
import de.main.jobsystem.manager.ItemCreator;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class jobCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (command.getName().equalsIgnoreCase("job") && sender instanceof Player p)
        {
            if (args.length == 0)
            {
                jobSelectionGUI.createSelectionGUI(p);
                return true;
            }
            if (args[0].equalsIgnoreCase("booster"))
            {
                giveBooster(p,1);
            }
        }
        return true;
    }


    private void giveBooster(Player p,int amount)
    {
        FileConfiguration playerData = Jobsystem.getInstance().playerdata;
        String boosterPath = "players." + p.getUniqueId() + ".booster";
        int booster = playerData.getInt(boosterPath,0);

        booster += amount;
        p.sendMessage("Du hast " + booster + " Booster erhalten");


        playerData.set(boosterPath,booster);
        Jobsystem.getInstance().savePlayerdata();
    }
}
