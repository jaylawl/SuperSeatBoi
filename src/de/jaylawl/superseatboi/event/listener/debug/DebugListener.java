package de.jaylawl.superseatboi.event.listener.debug;

import de.jaylawl.superseatboi.event.event.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public class DebugListener implements Listener {

    public DebugListener() {
    }

    //

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerInteractIWthSeatStructure(@NotNull PlayerInteractWithSeatStructureEvent event) {
        System.out.println(event.getClass().getSimpleName());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onSeatEntityDismount(@NotNull SeatEntityDismountEvent event) {
        System.out.println(event.getClass().getSimpleName());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onSeatEntityMount(@NotNull SeatEntityMountEvent event) {
        System.out.println(event.getClass().getSimpleName());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onSeatEntitySpawn(@NotNull SeatEntitySpawnEvent event) {
        System.out.println(event.getClass().getSimpleName());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPreReload(@NotNull SuperSeatBoiPreReloadEvent event) {
        System.out.println(event.getClass().getSimpleName());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPostReload(@NotNull SuperSeatBoiPostReloadEvent event) {
        System.out.println(event.getClass().getSimpleName());
    }

}
