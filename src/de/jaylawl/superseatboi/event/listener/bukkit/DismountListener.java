package de.jaylawl.superseatboi.event.listener.bukkit;

import de.jaylawl.superseatboi.seat.SeatEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.spigotmc.event.entity.EntityDismountEvent;

public class DismountListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onDismount(EntityDismountEvent event) {
        Entity entity = event.getDismounted();
        if (entity.getType() == EntityType.SILVERFISH) {
            if (entity.getScoreboardTags().contains(SeatEntity.SCOREBOARD_TAG_IDENTIFIER)) {
                entity.remove();
            }
        }
    }

}
