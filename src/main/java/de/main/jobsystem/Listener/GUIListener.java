package de.main.jobsystem.Listener;

import de.main.jobsystem.Jobsystem;
import de.main.jobsystem.booster.booster;
import de.main.jobsystem.gui.boosterGUI;
import de.main.jobsystem.gui.jobGUI;
import de.main.jobsystem.gui.rewardGUI;
import de.main.jobsystem.gui.statisticsGUI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
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
                e.getView().getTitle().equalsIgnoreCase("Statistic - Digger") ||
                e.getView().getTitle().equalsIgnoreCase("§6Rewards - Minerjob") ||
                e.getView().getTitle().equalsIgnoreCase("Booster")


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
            case ("open_rewards_miner"):
                p.getInventory().close();
                rewardGUI.createRewardGUI(p,"Minerjob");
                break;
            case ("open_booster"):
                p.getInventory().close();
                boosterGUI.createBoosterGUI(p);
                break;
            case ("start_booster_xp"):
                p.getInventory().close();
                booster.startBooster(p,"xp");
                break;
            case ("start_booster_geld"):
                p.getInventory().close();
                booster.startBooster(p,"geld");
                break;
            default:
                if (action.startsWith("claim_reward_"))
                {
                    String[] data = action.split("_");
                    claimReward(p,data);
                }
                break;



            }
        }
    }
    private void claimReward(Player p, String[] data) {

        String job = data[2];
        int rewardlevel = Integer.parseInt(data[3]);

        FileConfiguration cfg = Jobsystem.getInstance().getConfig();
        FileConfiguration cfgPlayerData = Jobsystem.getInstance().playerdata;
        String rewardDataPath = "players." + p.getUniqueId() + "." + job + ".rewards." + rewardlevel;

        String rewardsPath = "rewards." + job;

        // Prüft ob der Path exisitert
        if (cfg.getConfigurationSection(rewardsPath) == null) {
            p.sendMessage("§cFür diesen Job wurden keine Rewards gefunden!");
            return;
        }

        String rewardPath = null;

        // Alle Rewards durchsuchen
        for (String reward : cfg.getConfigurationSection(rewardsPath).getKeys(false)) {

            String path = rewardsPath + "." + reward;

            int level = cfg.getInt(path + ".level");

            if (level == rewardlevel) {
                rewardPath = path;
                break;
            }
        }

        // Falls kein Reward gefunden wurde
        if (rewardPath == null) {

            p.sendMessage("§cEs wurde kein Reward für Level " + rewardlevel + " gefunden!"
            );

            return;
        }
        String playerJobPath = "players." + p.getUniqueId() + "." + job;
        int playerJobLevel = cfgPlayerData.getInt(playerJobPath + ".level",1);

        if (playerJobLevel < rewardlevel)
        {
            p.sendMessage("§cDu benötigst mindestens Level " + rewardlevel);
            p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1f, 1f);
            return;
        }

        boolean alreadyClaimed = cfgPlayerData.getBoolean(rewardDataPath,false);
        if (alreadyClaimed)
        {
            p.sendMessage("§cDu hast diese Belohnung bereits eingesammelt!");
            return;
        }

        // Material aus Config
        String materialName = cfg.getString(rewardPath + ".material");

        if (materialName == null) {
            p.sendMessage("§cDer Reward besitzt kein Material!");
            return;
        }

        int amount = cfg.getInt(rewardPath + ".amount", 1);

        Material material;

        try {

            material = Material.valueOf(materialName.toUpperCase());

        } catch (IllegalArgumentException ex) {

            p.sendMessage("§cUngültiges Material: " + materialName);
            return;
        }


        ItemStack rewardItem = new ItemStack(material, amount);

        p.getInventory().addItem(rewardItem);
        cfgPlayerData.set(rewardDataPath,true);
        Jobsystem.getInstance().savePlayerdata();

        // Feedback
        p.sendMessage("§aDu hast erfolgreich eine Belohnung eingesammelt!");

        p.playSound(p.getLocation(), Sound.UI_TOAST_IN, 1f, 1f);
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
