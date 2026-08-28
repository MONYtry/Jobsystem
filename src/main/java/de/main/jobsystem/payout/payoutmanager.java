package de.main.jobsystem.payout;

import de.main.jobsystem.Jobsystem;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class payoutmanager {

    private static final List<String> JOBS = List.of(
            "Minerjob",
            "Woodcutter",
            "Diggerjob"
    );

    public static void sendMoney() {

        Bukkit.getScheduler().runTaskTimer(
                Jobsystem.getInstance(),
                () -> {

                    FileConfiguration playerData = Jobsystem.getInstance().playerdata;
                    FileConfiguration cfg = Jobsystem.getInstance().getConfig();

                    // Holt alle Spieler die Online sind
                    for (Player p : Bukkit.getOnlinePlayers()) {

                        UUID uuid = p.getUniqueId();

                        // Jeden Job des Spielers überprüfen
                        for (String job : JOBS) {

                            String displayJob = "";
                            switch (job)
                            {
                                case ("Minerjob"):
                                    displayJob = "Miner";
                                    break;
                                case ("Diggerjob"):
                                    displayJob = "Gräber";
                                    break;
                                case ("Woodcutter"):
                                    displayJob = "Holzfäller";
                                    break;
                            }
                            // Dynamischer Path
                            String path = "players." + uuid + "." + job;

                            // Abgebaute Blöcke
                            String brokenBlocksPath = path + ".brokenblocks";

                            double brokenBlocks = playerData.getDouble(brokenBlocksPath, 0);

                            // Nichts gearbeitet
                            if (brokenBlocks <= 0) {
                                continue;
                            }
                            int level = playerData.getInt(path + ".level",1);

                            // Geld pro Block aus config.yml
                            String moneyPath = "jobsettings." + job + ".money." + level;

                            if (!cfg.contains(moneyPath)) {
                                p.sendMessage("§7Es wurde kein verfügbares Level gefunden! (§cKontaktiere umgehend einen Admin!§7)");
                                continue;
                            }

                            double moneyPerBlock = cfg.getDouble(moneyPath,0.0);

                            if (moneyPerBlock <= 0) {
                                p.sendMessage("§7Das Geld für dieses Level ist ungültig! (§cKontaktiere umgehend einen Admin!§7)");
                                return;
                            }

                            String booster_path = "booster.active";

                            List<String> currentBooster = cfg.getStringList(booster_path);

                            double money = brokenBlocks * moneyPerBlock;
                            if (currentBooster.contains("geld"))
                            {
                                money *= 2;
                            }

                            // Beispiel: 0,50$ pro Block

                            // Sendet gesammeltes Geld
                            Jobsystem.getEconomy().depositPlayer(p,money);

                            p.sendMessage("§7Du hast als §e" + displayJob + " §7deinen Lohn von §a" + Math.round(money) + "$ §7erhalten.");

                            // Für diesen Job zurücksetzen
                            playerData.set(brokenBlocksPath, 0);
                        }
                    }

                    Jobsystem.getInstance().savePlayerdata();

                },

                20L * 30, // erste Auszahlung nach 30 Sekunden
                20L * 30  // danach alle 30 Sekunden
        );
    }
}