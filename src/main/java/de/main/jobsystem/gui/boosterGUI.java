package de.main.jobsystem.gui;

import de.main.jobsystem.Jobsystem;
import de.main.jobsystem.manager.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
        FileConfiguration cfg = Jobsystem.getInstance().getConfig();

        String player_xp_booster_Path = "players." + p.getUniqueId() + ".booster.xp";
        String player_geld_booster_Path = "players." + p.getUniqueId() + ".booster.geld";

        String booster_path = "booster.active";


        // Holt sich alle Booster Infomationen
        List<String> currentBooster = cfg.getStringList(booster_path);

        int xp_booster = playerData.getInt(player_xp_booster_Path, 0);
        int geld_booster = playerData.getInt(player_geld_booster_Path, 0);

        // --- BOOSTER DISPLAY ITEM --- //
        ItemStack currentBoosterItem = inventory.getItem(15);
        if (currentBoosterItem != null && currentBoosterItem.hasItemMeta()) {

            ItemMeta currentBoostermeta = currentBoosterItem.getItemMeta();

            List<String> currentBoosterlore = currentBoostermeta.getLore();

            if (currentBoosterlore == null) {
                currentBoosterlore = new ArrayList<>();
            }

            if (!currentBooster.isEmpty()) {
                currentBoosterlore.add("§7Folgende Booster sind aktiv:");

                for (String booster : currentBooster) {
                    String test = "booster." + booster + ".endTime";
                    long endTime = cfg.getLong(test);

                    long remainingMillis = endTime - System.currentTimeMillis();

                    long remainingMinutes = remainingMillis / 1000 / 60;
                    currentBoosterlore.add(ChatColor.GOLD + booster.toUpperCase() + "-Booster " + ChatColor.RED + remainingMinutes + " Minuten");
                }
            }

            if (currentBooster.isEmpty()) {
                currentBoosterlore.add("§cEs ist kein Booster aktiv!");
            }
            currentBoostermeta.setLore(currentBoosterlore);
            currentBoosterItem.setItemMeta(currentBoostermeta);

            // Item aus dem Inventar holen
            ItemStack item = inventory.getItem(13);

            if (item != null && item.hasItemMeta()) {

                ItemMeta meta = item.getItemMeta();

                List<String> lore = meta.getLore();

                if (lore == null) {
                    lore = new ArrayList<>();
                }

                // Erstellt coole Lore
                lore.add("§e⭐ XP Booster: §6" + xp_booster);
                lore.add("§e💲 GELD Booster: §6" + geld_booster);


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

    public static void createBoosterAddGUI(Player p) {
        Inventory inventory = Bukkit.createInventory(null, 36, "Booster");

        ItemCreator itemCreator = new ItemCreator();
        itemCreator.autoGenItemsWithConfigGUI(inventory, "addBoosterGUI");

        FileConfiguration playerData = Jobsystem.getInstance().playerdata;

        FileConfiguration cfg = Jobsystem.getInstance().getConfig();
        String booster_path = "booster.active";
        // Holt sich alle Booster Infomationen
        List<String> currentBooster = cfg.getStringList(booster_path);

        String player_xp_booster_Path = "players." + p.getUniqueId() + ".booster.xp";
        String player_geld_booster_Path = "players." + p.getUniqueId() + ".booster.geld";

        int xp_booster = playerData.getInt(player_xp_booster_Path, 0);
        int geld_booster = playerData.getInt(player_geld_booster_Path, 0);

        // --- BOOSTER DISPLAY ITEM --- //
        ItemStack currentBoosterItem = inventory.getItem(22);
        if (currentBoosterItem != null && currentBoosterItem.hasItemMeta()) {

            ItemMeta meta = currentBoosterItem.getItemMeta();

            List<String> lore = meta.getLore();

            if (lore == null) {
                lore = new ArrayList<>();
            }

            if (!currentBooster.isEmpty()) {
                lore.add("§7Folgende Booster sind aktiv:");

                for (String booster : currentBooster) {
                    String test = "booster." + booster + ".endTime";
                    long endTime = cfg.getLong(test);

                    long remainingMillis = endTime - System.currentTimeMillis();

                    long remainingMinutes = remainingMillis / 1000 / 60;
                    lore.add(ChatColor.GOLD + booster.toUpperCase() + "-Booster " + ChatColor.RED + remainingMinutes + " Minuten");
                }
            }

            if (currentBooster.isEmpty()) {
                lore.add("§cEs ist kein Booster aktiv!");
            }
            meta.setLore(lore);
            currentBoosterItem.setItemMeta(meta);

            // Item aus dem Inventar holen
            // XP BOOSTER
            ItemStack xp_booster_item = inventory.getItem(10);

            if (xp_booster_item != null && xp_booster_item.hasItemMeta()) {

                ItemMeta XP_meta = xp_booster_item.getItemMeta();

                List<String> lore_geld_booster = XP_meta.getLore();

                if (lore_geld_booster == null) {
                    lore_geld_booster = new ArrayList<>();
                }

                // Erstellt coole Lore
                lore_geld_booster.add("§e⭐ XP Booster: §6" + xp_booster);


                XP_meta.setLore(lore_geld_booster);
                xp_booster_item.setItemMeta(XP_meta);
            }

            // Item aus dem Inventar holen
            // Geld BOOSTER
            ItemStack geld_booster_item = inventory.getItem(12);

            if (geld_booster_item != null && geld_booster_item.hasItemMeta()) {

                ItemMeta GELD_meta = geld_booster_item.getItemMeta();

                List<String> lore_xp_booster = GELD_meta.getLore();

                if (lore_xp_booster == null) {
                    lore_xp_booster = new ArrayList<>();
                }

                // Erstellt coole Lore
                lore_xp_booster.add("§e⭐ XP Booster: §6" + geld_booster);


                GELD_meta.setLore(lore);
                geld_booster_item.setItemMeta(GELD_meta);
            }


            p.openInventory(inventory);
            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
        }


    }
}
