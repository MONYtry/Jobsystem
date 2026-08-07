package de.main.jobsystem.gui;

import de.main.jobsystem.manager.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class jobSelectionGUI {

    public static void createSelectionGUI(Player p)
    {
        Inventory selectionGUI = Bukkit.createInventory(null,36,"JOBS");

        ItemCreator itemCreator = new ItemCreator();
        itemCreator.autoGenItemsWithConfigGUI(selectionGUI);

        p.playSound(p.getLocation(), Sound.BLOCK_ENDER_CHEST_OPEN,1,1);
        p.openInventory(selectionGUI);
    }
}
