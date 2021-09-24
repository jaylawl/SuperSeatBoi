package de.jaylawl.superseatboi.event.listener.bukkit;

import de.jaylawl.superseatboi.SuperSeatBoi;
import de.jaylawl.superseatboi.seat.SeatEntity;
import de.jaylawl.superseatboi.seat.SeatStructure;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.jetbrains.annotations.NotNull;

public class PlayerListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerInteract(@NotNull PlayerInteractEvent event) {
        if (event.getHand() == EquipmentSlot.HAND) {
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                Block clickedBlock = event.getClickedBlock();
                if (clickedBlock != null) {
                    SeatStructure seatStructure = SeatStructure.fromBlock(clickedBlock);
                    if (seatStructure != null) {
                        event.setCancelled(true);
                        seatStructure.interact(event.getPlayer());
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(@NotNull PlayerQuitEvent event) {
        Entity vehicle = event.getPlayer().getVehicle();
        if (vehicle != null) {
            SeatEntity seatEntity = SuperSeatBoi.getSeatManager().getSeatEntity(vehicle.getUniqueId());
            if (seatEntity != null) {
                vehicle.eject();
            }
        }
    }

}
