package de.main.jobsystem.manager;


import de.main.jobsystem.Jobsystem;
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

public class ItemCreator {

    public void autoGenItemsWithConfigGUI(Inventory inventoryToUse)
    {
        FileConfiguration cfg = Jobsystem.getInstance().getConfig();

        // Schau Config macht Sinn :)
        ConfigurationSection items = cfg.getConfigurationSection("items");

        // Geht durch jede Kategorie
        for (String key : items.getKeys(false)) {

            // Dynamischer Path
            String path = "items." + key;

            // Material Name
            String materialName = cfg.getString(path + ".material");

            // Slot index erstellen
            int slot = cfg.getInt(path + ".slot");

            // Item DisplayName
            String name = cfg.getString(path + ".name");

            // Item Lore
            List<String> lore = cfg.getStringList(path + ".lore");

            if(lore == null) lore = new ArrayList<>();

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

            meta.getPersistentDataContainer().set(
                    new NamespacedKey(Jobsystem.getInstance(), "action"),
                    PersistentDataType.STRING,
                    action
            );

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

    public void autoGenItemsWithConfigInventory(Inventory inventoryToUse)
    {
        inventoryToUse.clear();
        FileConfiguration cfg = Jobsystem.getInstance().getConfig();

        // Schau Config macht Sinn :)
        ConfigurationSection items = cfg.getConfigurationSection("inventory.items");

        if(items == null) return;

        // Geht durch jede Kategorie
        for (String key : items.getKeys(false)) {

            // Dynamischer Path
            String path = "inventory.items." + key;

            // Material Name
            String materialName = cfg.getString(path + ".material");

            // Slot index erstellen
            int slot = cfg.getInt(path + ".slot");

            // Item DisplayName
            String name = cfg.getString(path + ".name");

            // Item Lore
            List<String> lore = cfg.getStringList(path + ".lore");

            if(lore == null) lore = new ArrayList<>();

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

            if (action !=null)
            {
                meta.getPersistentDataContainer().set(
                        new NamespacedKey(Jobsystem.getInstance(), "action"),
                        PersistentDataType.STRING,
                        action
                );
            }

            item.setItemMeta(meta);

            inventoryToUse.setItem(slot, item);


        }

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
