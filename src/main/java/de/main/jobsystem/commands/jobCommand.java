package de.main.jobsystem.commands;

import de.main.jobsystem.Jobsystem;
import de.main.jobsystem.gui.jobSelectionGUI;
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
                if (args.length < 2 || args[1].isBlank())
                {
                    p.sendMessage("Benutze /job booster <xp|geld>");
                    return true;
                }
                if (args[1].equalsIgnoreCase("xp"))
                {
                    giveBooster(p,1,"xp");
                }
                else
                {
                    giveBooster(p,1,"geld");
                }
            }

            if (args[0].equalsIgnoreCase("reload"))
            {
                reloadConfig(p);
            }
        }
        return true;
    }

    private void reloadConfig(Player p)
    {
        FileConfiguration cfgMessages = Jobsystem.getInstance().messages;


        String prefix = cfgMessages.getString("messages.prefix");
        String succsessfullyReloaded = cfgMessages.getString("messages.reload_succses");

        Jobsystem.getInstance().reloadConfig();
        Jobsystem.getInstance().savePlayerdata();
        Jobsystem.getInstance().reloadGUI();

        p.sendMessage(prefix + succsessfullyReloaded);
    }

    private void giveBooster(Player p,int amount,String boosterName)
    {
        FileConfiguration playerData = Jobsystem.getInstance().playerdata;

        String boosterPath = "players." + p.getUniqueId() + ".booster." + boosterName;

        int booster = playerData.getInt(boosterPath,0);
        booster += amount;

        p.sendMessage("§9Du hast " + amount + " Booster erhalten");
        p.sendMessage("§9Gesamt "+ boosterName +"-Booster: " + booster);

        playerData.set(boosterPath,booster);
        Jobsystem.getInstance().savePlayerdata();
    }
}
