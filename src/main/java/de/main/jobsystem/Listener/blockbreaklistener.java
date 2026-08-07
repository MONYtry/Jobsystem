package de.main.jobsystem.Listener;

import de.main.jobsystem.Jobsystem;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class blockbreaklistener implements Listener {
    private final Map<Material,Integer> minerBlocks = Map.of(
            Material.STONE,1,
            Material.DEEPSLATE,1,
            Material.COAL_ORE,2,
            Material.IRON_ORE,2,
            Material.GOLD_ORE,2,
            Material.DIAMOND_ORE,2
    );

    private final Map<Material,Integer> diggerBlocks = Map.of(
            Material.SAND,1,
            Material.GRAVEL,5,
            Material.GRASS_BLOCK,2,
            Material.DIRT,1
    );



    @EventHandler
    public void OnBlockBreak(BlockBreakEvent e)
    {
        Material brokenBlock = e.getBlock().getType();
        Player p = e.getPlayer();
        UUID uuid = p.getUniqueId();



        String currentJob = "";
        String displayJob = "";

        if (minerBlocks.containsKey(brokenBlock))
        {
            minerJob(brokenBlock,currentJob,uuid,p,displayJob);
        }
        if (diggerBlocks.containsKey(brokenBlock))
        {
            diggerJob(brokenBlock,currentJob,uuid,p,displayJob);
        }

    }

    private void minerJob(Material brokenBlock,String currentJob,UUID uuid,Player p,String displayJob)
    {
        if (minerBlocks.containsKey(brokenBlock))
        {

            currentJob = "Minerjob";
            displayJob = "Miner";

            FileConfiguration cfg = Jobsystem.getInstance().getConfig();
            String prefix = cfg.getString("messages.prefix");

            List<Integer> levels = cfg.getIntegerList("Minerjob.level");

            String path = "players." + uuid +"."+ currentJob;
            String xpForLevelUpConfig = path + ".xpNeeded";
            String currentLevelConfig = path + ".level";

            // XP des abgebauten Blockes
            int xpToAdd = minerBlocks.get(brokenBlock);

            // Aktueller Stand der XP
            int xp = cfg.getInt(path + ".xp");

            int currentLevel = cfg.getInt(currentLevelConfig,1);

            // Aktuelle XP + XP des abgebauten Blockes
            xp += xpToAdd;
            if (levels.isEmpty())
            {
                p.sendMessage(prefix + "§cKein Level gefunden in Config.yml");
                return;
            }
            // Holt sich aus dem Array die needed XP
            int xpNeeded = levels.get(currentLevel - 1);

            // Falls genug XP für ein Levelup da sind
            if (xp >= xpNeeded)
            {
                if (currentLevel < levels.size())
                {
                    currentLevel += 1;
                    xp = 0;

                    xpNeeded = levels.get(currentLevel - 1);
                    sendLevelUp(p,currentLevel,currentJob,prefix);
                }
                else
                {
                    // Max Level erreicht!
                    xp = xpNeeded;
                    return;
                }

            }


            // Setzt es in den RAM
            cfg.set(path + ".xp",xp);
            cfg.set(currentLevelConfig,currentLevel);

            // Setzt es auf die Festplatte
            Jobsystem.getInstance().saveConfig();

            sendActionbar(p,xp,xpNeeded,currentLevel,displayJob,xpToAdd);
        }
    }
    private void diggerJob(Material brokenBlock,String currentJob,UUID uuid,Player p,String displayJob)
    {
        if (diggerBlocks.containsKey(brokenBlock))
        {
            currentJob = "Diggerjob";
            displayJob = "Gräber";
            FileConfiguration cfg = Jobsystem.getInstance().getConfig();
            String prefix = cfg.getString("messages.prefix");
            List<Integer> levels = cfg.getIntegerList("Diggerjob.level");

            String path = "players." + uuid +"."+ currentJob;
            String xpForLevelUpConfig = path + ".xpNeeded";
            String currentLevelConfig = path + ".level";

            // XP des abgebauten Blockes
            int xpToAdd = diggerBlocks.get(brokenBlock);

            // Aktueller Stand der XP
            int xp = cfg.getInt(path + ".xp");

            int currentLevel = cfg.getInt(currentLevelConfig,1);

            // Aktuelle XP + XP des abgebauten Blockes
            xp += xpToAdd;
            if (levels.isEmpty())
            {
                p.sendMessage("§cKein Level gefunden in Config.yml");
                return;
            }
            // Holt sich aus dem Array die needed XP
            int xpNeeded = levels.get(currentLevel - 1);

            // Falls genug XP für ein Levelup da sind
            if (xp >= xpNeeded)
            {
                if (currentLevel < levels.size())
                {
                    currentLevel += 1;
                    xp = 0;

                    xpNeeded = levels.get(currentLevel - 1);
                    sendLevelUp(p,currentLevel,currentJob,prefix);
                }
                else
                {
                    // Max Level erreicht!
                    xp = xpNeeded;
                    return;
                }

            }


            // Setzt es in den RAM
            cfg.set(path + ".xp",xp);
            cfg.set(currentLevelConfig,currentLevel);

            // Setzt es auf die Festplatte
            Jobsystem.getInstance().saveConfig();

            sendActionbar(p,xp,xpNeeded,currentLevel,displayJob,xpToAdd);
        }
    }


    private void sendLevelUp(Player p,int level,String displayJob,String prefix)
    {
        p.sendTitle("§d§lLEVEL UP","§fneues Level freigeschalten!");
        p.playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_DEATH,0.1f,1.0f);

        if (level >= 1)
        {
            Bukkit.broadcastMessage("§7»");
            Bukkit.broadcastMessage(prefix + " §e" + p.getName() + " ist nun Level " + level + " im §n" + displayJob + "§r§e!");
            Bukkit.broadcastMessage("§7»");
        }

    }

    private void sendActionbar(Player p,int xp,int xpNeeded,int currentLevel,String displayJob,int xpToAdd)
    {
        p.sendActionBar("§e+" +xpToAdd + " XP §8§l▪ §b" + displayJob + " §8§l▪ §7Level " + currentLevel + " §8▪ §7" + xp + "/" + xpNeeded + "XP");
    }
}
