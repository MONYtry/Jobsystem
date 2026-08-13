package de.main.jobsystem.manager;


import de.main.jobsystem.Jobsystem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ItemCreator {

    public void autoGenItemsWithConfigGUI(Inventory inventoryToUse,String categoryName)
    {
        // selectionGUI
        FileConfiguration cfg = Jobsystem.getInstance().gui;

        // Schau Config macht Sinn :)
        ConfigurationSection items = cfg.getConfigurationSection(categoryName);

        // Geht durch jede Kategorie
        for (String key : items.getKeys(false)) {

            // Dynamischer Path
            String path = categoryName + "." + key;

            // Material Name
            String materialName = cfg.getString(path + ".material");

            // Slot index erstellen
            int slot = cfg.getInt(path + ".slot");

            // Item DisplayName
            String name = cfg.getString(path + ".name");

            // Item Lore
            List<String> lore = cfg.getStringList(path + ".lore");

            if(lore == null) lore = new ArrayList<>();
            String rankingJob = cfg.getString(path + ".ranking");

            if (rankingJob != null) {
                lore = getTop10Lore(rankingJob);
            }


            // Ignorieren
            Material material;
            try {
                // Versucht das material zu switchen
                material = Material.valueOf(materialName);
            } catch (Exception e) {
                // Falls Error
                material = Material.STONE;
                System.out.print("error Critical!");
            }


            // Basic Stuff
            ItemStack item = new ItemStack(material);
            ItemMeta meta = item.getItemMeta();

            meta.setDisplayName(name);
            meta.setLore(lore);

            String action = cfg.getString(path + ".action");
            if (action != null)
            {
                meta.getPersistentDataContainer().set(
                        new NamespacedKey(Jobsystem.getInstance(), "action"),
                        PersistentDataType.STRING,
                        action
                );
            }
            // Für Server Detection
            String server = cfg.getString(path + ".server");

            if (server != null) {
                meta.getPersistentDataContainer().set(
                        new NamespacedKey(Jobsystem.getInstance(), "server"),
                        PersistentDataType.STRING,
                        server
                );
            }

            if (meta == null) {
                System.out.print("error: ItemMeta is null!");
                continue;
            }

            item.setItemMeta(meta);

            inventoryToUse.setItem(slot, item);
        }
    }






    private List<String> getTop10Lore(String job) {

        FileConfiguration config = Jobsystem.getInstance().playerdata;

        // Holt den Bereich "players" aus der Config
        ConfigurationSection playersSection =
                config.getConfigurationSection("players");

        // Hier speichern wir später alle Lore-Zeilen
        List<String> lore = new ArrayList<>();

        lore.add("§8§m----------------");
        lore.add("");

        // Falls es keine Spieler gibt, brechen wir hier ab
        if (playersSection == null) {
            lore.add("§cKeine Spieler gefunden.");
            return lore;
        }

        // Hier speichern wir die Spieler für die Rangliste
        List<String> ranking = new ArrayList<>();

        // Geht jeden Spieler in der Config durch
        for (String uuidString : playersSection.getKeys(false)) {

            // Erstellt den Pfad zum jeweiligen Job des Spielers
            String playerJobPath = "players." + uuidString + "." + job;

            // Falls der Spieler diesen Job nicht besitzt, überspringen
            if (!config.contains(playerJobPath)) {
                continue;
            }

            // Level des Spielers aus der Config holen
            int playerLevel = config.getInt(playerJobPath + ".level", 1);

            // UUID in einen Bukkit-Spieler umwandeln und Namen holen
            String playerName = Bukkit.getOfflinePlayer(UUID.fromString(uuidString)).getName();

            // Falls kein Name gefunden wurde
            if (playerName == null) {
                playerName = "Unbekannt";
            }

            int globalXP = config.getInt(playerJobPath + ".globalxp",0);


            // Level und Name zusammen speichern
            // Beispiel: "20;Steve"
            ranking.add(playerLevel + ";" + globalXP + ";" + playerName);
        }

        // Rangliste nach Level sortieren
        // Höchstes Level kommt zuerst
        ranking.sort((spielerA, spielerB) -> {

            // Trennt z.B. "20;Steve"
            // [0] = Level
            // [1] = Name

            int levelA = Integer.parseInt(spielerA.split(";")[0]);

            int levelB = Integer.parseInt(spielerB.split(";")[0]);

            // Level B zuerst = höchstes Level zuerst
            return Integer.compare(levelB, levelA);
        });

        // Startet bei Platz 1
        int platz = 1;

        // Geht die sortierte Rangliste durch
        for (String spieler : ranking) {

            // Nur die Top 10 anzeigen
            if (platz > 10) {
                break;
            }

            // Spieler wieder in Level und Name aufteilen
            // Beispiel: "20;Steve"
            String[] spielerDaten = spieler.split(";");

            // Level aus den Daten holen
            int playerLevel = Integer.parseInt(spielerDaten[0]);

            int globalXP = Integer.parseInt(spielerDaten[1]);

            // Namen aus den Daten holen
            String playerName = spielerDaten[2];


            lore.add("§e#" + platz + " §f" + playerName + " §8» §bLevel " + playerLevel + " | " + globalXP + " XP");

            platz++;
        }

        lore.add("");
        lore.add("§8§m----------------");

        // Fertige Lore zurückgeben
        return lore;
    }

    public void createItemWith(Material newMaterial, String itemName, List<String> newLore, int slotIndex, Player p, Inventory inventoryToUse, boolean unbreakable,String action)
    {
        // Erstellen von Item
        ItemStack newItem = new ItemStack(newMaterial);

        // ItemMeta erstellen
        ItemMeta newItemMeta = newItem.getItemMeta();

        // Meta Data verbinden
        newItemMeta = newItem.getItemMeta();



        // Display Namen setzen
        newItemMeta.setDisplayName(itemName);

        // Lore setzen
        newItemMeta.setLore(newLore);
        newItemMeta.setUnbreakable(unbreakable);


        if(action != null)
        {
            newItemMeta.getPersistentDataContainer().set(
                    new NamespacedKey(Jobsystem.getInstance(), "action"),
                    PersistentDataType.STRING,
                    action
            );
        }

        // Meta Data verbinden
        newItem.setItemMeta(newItemMeta);

        // Item ablegen
        inventoryToUse.setItem(slotIndex,newItem);

    }
}
