package de.main.jobsystem.gui;

import de.main.jobsystem.Jobsystem;
import de.main.jobsystem.manager.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class statisticsGUI {

    public static void createStaticsGUI(Player p, String job) {
        Material[] blocks = {};
        FileConfiguration cfg = Jobsystem.getInstance().playerdata;

        UUID uuid = p.getUniqueId();

        Inventory statisticsGUI = Bukkit.createInventory(null, 36, "Statistic - " + job);

        ItemCreator itemCreator = new ItemCreator();
        if (job.equals("Miner"))
        {
            blocks = new Material[]{
                    Material.STONE,
                    Material.COAL_ORE,
                    Material.IRON_ORE,
                    Material.GOLD_ORE,
                    Material.REDSTONE_ORE,
                    Material.DIAMOND_ORE
            };
        }
        if (job.equals("Woodcutter"))
        {
            blocks = new Material[]{
                    Material.OAK_LOG,
                    Material.SPRUCE_LOG,
                    Material.BIRCH_LOG,
            };
        }
        if (job.equals("Digger"))
        {
            blocks = new Material[]{
                    Material.SAND,
                    Material.GRASS_BLOCK,
                    Material.DIRT,
                    Material.GRAVEL
            };
        }
        // Alle Blöcke, die angezeigt werden sollen


        // Start-Slot
        int slot = 0;

        // Geht jeden Block durch
        for (Material block : blocks) {

            // Config-Pfad erstellen
            String path = "players." + uuid + ".statistics.blocks." + block.name();

            // Anzahl aus der Config holen
            int amount = cfg.getInt(path, 0);

            // Item-Name
            String itemName = "§b" + block.name().replace("_"," ");

            // Lore erstellen
            List<String> lore = new ArrayList<>();

            lore.add("");
            lore.add("§7Abgebaut: §e" + amount);
            lore.add("");

            // Item erstellen
            itemCreator.createItemWith(
                    block,
                    itemName,
                    lore,
                    slot,
                    p,
                    statisticsGUI,
                    false,
                    "none"
            );

            // Nächster Slot
            slot++;
        }

        p.playSound(p.getLocation(), Sound.BLOCK_ENDER_CHEST_OPEN, 1, 1);

        p.openInventory(statisticsGUI);
    }
}
