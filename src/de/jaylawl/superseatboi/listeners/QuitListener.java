package de.jaylawl.superseatboi.listeners;

import de.jaylawl.superseatboi.SuperSeatBoi;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {

    public QuitListener() {
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Entity vehicle = player.getVehicle();
        if (vehicle != null) {
            if (vehicle.getScoreboardTags().contains(SuperSeatBoi.getScoreboardTagIdentifier())) {
                vehicle.eject();
            }
        }
    }

}
