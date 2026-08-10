package de.main.jobsystem.gui;

import de.main.jobsystem.manager.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class jobGUI {

    public static void createJobGUI(Player p, String job) {

        Inventory inventory = Bukkit.createInventory(null, 36, job + " - GUI");

        ItemCreator itemCreator = new ItemCreator();

        switch (job.toLowerCase()) {

            case "minerjob":
                itemCreator.autoGenItemsWithConfigGUI(inventory, "Minerjob");
                break;

            case "woodcutter":
                itemCreator.autoGenItemsWithConfigGUI(inventory, "Woodcutter");
                break;

            case "digger":
                itemCreator.autoGenItemsWithConfigGUI(inventory, "Digger");
                break;

            default:
                Bukkit.getLogger().warning( "Unbekannter Job: " + job);
                return;
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
