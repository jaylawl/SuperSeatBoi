package de.jaylawl.superseatboi.event.listener.bukkit;

import de.jaylawl.superseatboi.seat.SeatEntity;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent event) {
        Entity vehicle = event.getPlayer().getVehicle();
        if (vehicle != null) {
            if (vehicle.getScoreboardTags().contains(SeatEntity.SCOREBOARD_TAG_IDENTIFIER)) {
                vehicle.eject();
            }
        }
    }

}
