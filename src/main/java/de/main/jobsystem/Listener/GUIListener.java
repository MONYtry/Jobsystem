package de.main.jobsystem.Listener;

import de.main.jobsystem.Jobsystem;
import de.main.jobsystem.gui.jobGUI;
import de.main.jobsystem.gui.statisticsGUI;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public class GUIListener implements Listener {

    @EventHandler
    public void OnInventoryClick(InventoryClickEvent e) {
        if (e.getView().getTitle().equalsIgnoreCase("JOBS") ||
                e.getView().getTitle().equalsIgnoreCase("Minerjob - GUI") ||
                e.getView().getTitle().equalsIgnoreCase("Woodcutter - GUI") ||
                e.getView().getTitle().equalsIgnoreCase("Digger - GUI") ||
                e.getView().getTitle().equalsIgnoreCase("Statistic - Miner - GUI") ||
                e.getView().getTitle().equalsIgnoreCase("Statistic - Woodcutter") ||
                e.getView().getTitle().equalsIgnoreCase("Statistic - Digger")
        )
        {
        e.setCancelled(true);

        // Config Varable erstellen!
        FileConfiguration cfg = JavaPlugin.getPlugin(Jobsystem.class).getConfig();

        // Variable für den Block der Getroffen wurde!
        ItemStack item = e.getCurrentItem();

        // Nullpointer verhindern
        if (item == null) return;
        if (!item.hasItemMeta()) return;

        ItemMeta itemMeta = item.getItemMeta();

        // Key erstellen
        String action = itemMeta.getPersistentDataContainer().get(
                new NamespacedKey(Jobsystem.getInstance(), "action"),
                PersistentDataType.STRING
        );
        Player p = ((Player) e.getWhoClicked());

        if (action == null) return;
        switch (action) {

            case ("open_miner"):
                p.getInventory().close();
                p.playSound(e.getWhoClicked().getLocation(), Sound.BLOCK_ENDER_CHEST_CLOSE, 1f, 1f);

                jobGUI.createJobGUI(p,"Minerjob");

                break;

            case ("open_woodcutter"):
                p.getInventory().close();
                p.playSound(e.getWhoClicked().getLocation(), Sound.BLOCK_ENDER_CHEST_CLOSE, 1f, 1f);

                jobGUI.createJobGUI(p,"Woodcutter");

                break;

            case ("open_digger"):
                p.getInventory().close();
                p.playSound(e.getWhoClicked().getLocation(), Sound.BLOCK_ENDER_CHEST_CLOSE, 1f, 1f);

                jobGUI.createJobGUI(p,"Digger");

                break;

            case ("open_statistics"):
                p.getInventory().close();
                p.playSound(p.getLocation(), Sound.BLOCK_ENDER_CHEST_CLOSE, 1f, 1f);
                openStatisticsGUI(p,e.getView().getTitle());
                break;





            }
        }
    }

    private void openStatisticsGUI(Player p,String inventory)
    {
        if (inventory.equalsIgnoreCase("Minerjob - GUI"))
        {
            statisticsGUI.createStaticsGUI(p,"Miner");
        }
        if (inventory.equalsIgnoreCase("Woodcutter - GUI"))
        {
            statisticsGUI.createStaticsGUI(p,"Woodcutter");
        }
        if (inventory.equalsIgnoreCase("Digger - GUI"))
        {
            statisticsGUI.createStaticsGUI(p,"Digger");
        }
    }
}
