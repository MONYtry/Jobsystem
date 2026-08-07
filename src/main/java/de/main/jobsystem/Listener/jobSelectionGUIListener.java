package de.main.jobsystem.Listener;

import de.main.jobsystem.Jobsystem;
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

public class jobSelectionGUIListener implements Listener {

    @EventHandler
    public void OnInventoryClick(InventoryClickEvent e) {
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

        if (action == null) return;
        switch (action) {
            case ("open_miner"):
                e.getWhoClicked().getInventory().close();
                ((Player) e.getWhoClicked()).playSound(e.getWhoClicked().getLocation(), Sound.BLOCK_ENDER_CHEST_CLOSE, 1f, 1f);
                e.getWhoClicked().sendMessage("§7GUI wurde §egeschlossen! (open_miner)");
                break;
            case ("open_woodcutter"):
                e.getWhoClicked().getInventory().close();
                ((Player) e.getWhoClicked()).playSound(e.getWhoClicked().getLocation(), Sound.BLOCK_ENDER_CHEST_CLOSE, 1f, 1f);
                e.getWhoClicked().sendMessage("§7GUI wurde §egeschlossen! (open_woodcutter)");
                break;
            case ("open_digger"):
                e.getWhoClicked().getInventory().close();
                ((Player) e.getWhoClicked()).playSound(e.getWhoClicked().getLocation(), Sound.BLOCK_ENDER_CHEST_CLOSE, 1f, 1f);
                e.getWhoClicked().sendMessage("§7GUI wurde §egeschlossen! (open_digger)");
                break;

        }
    }
}
