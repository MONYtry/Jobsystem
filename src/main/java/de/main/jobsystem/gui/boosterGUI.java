package de.main.jobsystem.gui;

import de.main.jobsystem.Jobsystem;
import de.main.jobsystem.manager.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class boosterGUI {

    public static void createBoosterGUI(Player p) {

        Inventory inventory = Bukkit.createInventory(null, 36, "Booster");

        ItemCreator itemCreator = new ItemCreator();
        itemCreator.autoGenItemsWithConfigGUI(inventory, "boosterGUI");

        // Booster aus Playerdata holen
        FileConfiguration playerData = Jobsystem.getInstance().playerdata;
        String path = "players." + p.getUniqueId() + ".booster";
        int booster = playerData.getInt(path, 0);

        // Item aus dem Inventar holen
        ItemStack item = inventory.getItem(13);

        if (item != null && item.hasItemMeta()) {

            ItemMeta meta = item.getItemMeta();

            List<String> lore = meta.getLore();

            if (lore == null) {
                lore = new ArrayList<>();
            }

            lore.add("§7Booster: §e" + booster);

            meta.setLore(lore);
            item.setItemMeta(meta);
        }

        p.playSound(
                p.getLocation(),
                Sound.BLOCK_ENDER_CHEST_OPEN,
                1f,
                1f
        );

        p.openInventory(inventory);
    }
}
