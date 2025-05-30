package de.jaylawl.superseatboi.event.listener.bukkit;

import com.destroystokyo.paper.event.entity.EntityRemoveFromWorldEvent;
import de.jaylawl.superseatboi.event.api.SeatEntityDismountEvent;
import de.jaylawl.superseatboi.event.api.SeatEntityMountEvent;
import de.jaylawl.superseatboi.seat.SeatManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDismountEvent;
import org.bukkit.event.entity.EntityMountEvent;
import org.jetbrains.annotations.NotNull;

public class EntityListener implements Listener {

    private final SeatManager seatManager;

    public EntityListener(@NotNull SeatManager seatManager) {
        this.seatManager = seatManager;
    }

    //

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityRemoveFromWorld(final @NotNull EntityRemoveFromWorldEvent event) {
        this.seatManager.unregisterSeatEntity(event.getEntity().getUniqueId());
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityMount(final @NotNull EntityMountEvent event) {
        Entity mountedEntity = event.getMount();
        this.seatManager.getSeatEntity(mountedEntity.getUniqueId()).ifPresent(seatEntity -> {
            SeatEntityMountEvent seatEntityMountEvent = new SeatEntityMountEvent(seatEntity);
            Bukkit.getPluginManager().callEvent(seatEntityMountEvent);
            if (seatEntityMountEvent.isCancelled()) {
                event.setCancelled(true);
            }
        });
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityDismount(final @NotNull EntityDismountEvent event) {
        Entity dismountedEntity = event.getDismounted();
        this.seatManager.getSeatEntity(dismountedEntity.getUniqueId()).ifPresent(seatEntity -> {
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
        });
    }

}
