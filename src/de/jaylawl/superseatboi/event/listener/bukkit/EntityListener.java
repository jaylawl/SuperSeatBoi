package de.jaylawl.superseatboi.event.listener.bukkit;

import com.destroystokyo.paper.event.entity.EntityRemoveFromWorldEvent;
import de.jaylawl.superseatboi.event.SeatEntityDismountEvent;
import de.jaylawl.superseatboi.event.SeatEntityMountEvent;
import de.jaylawl.superseatboi.seat.SeatEntity;
import de.jaylawl.superseatboi.seat.SeatManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;
import org.spigotmc.event.entity.EntityDismountEvent;
import org.spigotmc.event.entity.EntityMountEvent;

public class EntityListener implements Listener {

    private final SeatManager seatManager;

    public EntityListener(@NotNull SeatManager seatManager) {
        this.seatManager = seatManager;
    }

    //

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityRemoveFromWorld(@NotNull EntityRemoveFromWorldEvent event) {
        this.seatManager.unregisterSeatEntity(event.getEntity().getUniqueId());
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityMount(@NotNull EntityMountEvent event) {
        Entity mountedEntity = event.getMount();
        SeatEntity seatEntity = this.seatManager.getSeatEntity(mountedEntity.getUniqueId());
        if (seatEntity != null) {
            SeatEntityMountEvent seatEntityMountEvent = new SeatEntityMountEvent(seatEntity);
            Bukkit.getPluginManager().callEvent(seatEntityMountEvent);
            if (seatEntityMountEvent.isCancelled()) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityDismount(@NotNull EntityDismountEvent event) {
        Entity dismountedEntity = event.getDismounted();
        SeatEntity seatEntity = this.seatManager.getSeatEntity(dismountedEntity.getUniqueId());
        if (seatEntity != null) {
            SeatEntityDismountEvent seatEntityDismountEvent = new SeatEntityDismountEvent(seatEntity);
            Bukkit.getPluginManager().callEvent(seatEntityDismountEvent);
            if (seatEntityDismountEvent.isCancelled()) {
                event.setCancelled(true);
            } else {
                Entity passenger = dismountedEntity.getPassengers().get(0);
                dismountedEntity.remove();
                Location passengerLocation = passenger.getLocation();
                passenger.teleport(passengerLocation.add(0, 1, 0));
            }
        }
    }

}
