package de.main.jobsystem.booster;

import de.main.jobsystem.Jobsystem;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class booster {

    public static void startBooster(Player p, String boosterName)
    {
        // Spieler-Data
        FileConfiguration playerData = Jobsystem.getInstance().playerdata;
        String BoosterPath = "players." + p.getUniqueId() + ".booster." + boosterName;

        // Config
        FileConfiguration cfg = Jobsystem.getInstance().getConfig();

        // Aktive Booster
        String booster_path = "booster.active";
        List<String> currentBooster = cfg.getStringList(booster_path);

        int xpBooster = playerData.getInt(BoosterPath);

        // Keine Booster vorhanden
        if (xpBooster <= 0)
        {
            p.sendMessage("§cDu hast keine Booster");
            return;
        }

        // Booster bereits aktiv
        if (currentBooster.contains(boosterName))
        {
            p.sendMessage("§cEin Booster dieser Art ist bereits aktiv");
            return;
        }

        // Booster aus Inventar entfernen
        xpBooster--;
        currentBooster.add(boosterName);

        // 1 Stunde Ablaufzeit berechnen
        long endTime = System.currentTimeMillis() + (60 * 60 * 1000L);

        // Ablaufzeit speichern
        cfg.set("booster." + boosterName + ".endTime", endTime);

        // Aktive Booster speichern
        cfg.set(booster_path, currentBooster);

        // Spieler-Booster speichern
        playerData.set(BoosterPath, xpBooster);

        Jobsystem.getInstance().saveConfig();
        Jobsystem.getInstance().savePlayerdata();

        p.sendMessage("§aDu hast den " + boosterName.toUpperCase() + "-Booster gestartet!");

        // Spieler benachrichtigen
        for (Player player : Bukkit.getOnlinePlayers())
        {
            player.sendTitle("§f" + boosterName.toUpperCase() + "-BOOSTER AKTIVIERT", "§dvon " + p.getName());

            player.playSound(
                    player.getLocation(),
                    Sound.ENTITY_PLAYER_LEVELUP,
                    1,
                    1
            );
        }

        // Countdown
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                long remainingMillis = endTime - System.currentTimeMillis();

                // Booster abgelaufen
                if (remainingMillis <= 0)
                {
                    currentBooster.remove(boosterName);

                    cfg.set(booster_path, currentBooster);
                    cfg.set("booster." + boosterName + ".endTime", null);

                    Jobsystem.getInstance().saveConfig();
                    Bukkit.broadcastMessage("§cDer " + boosterName.toUpperCase() + "-Booster ist abgelaufen!");
                    cancel();
                    return;
                }

                // Verbleibende Minuten
                long remainingMinutes = remainingMillis / 1000 / 60;

                Bukkit.broadcastMessage("§e" + boosterName.toUpperCase() + "-Booster läuft noch §c" + remainingMinutes + " §eMinuten!");
            }

        }.runTaskTimer(Jobsystem.getInstance(), 0L, 1200L);
    }
}