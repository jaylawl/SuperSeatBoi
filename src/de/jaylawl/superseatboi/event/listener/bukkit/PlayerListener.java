package de.jaylawl.superseatboi.event.listener.bukkit;

import de.jaylawl.superseatboi.seat.SeatManager;
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

    private final SeatManager seatManager;

    public PlayerListener(@NotNull SeatManager seatManager) {
        this.seatManager = seatManager;
    }

    //

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerInteract(@NotNull PlayerInteractEvent event) {
        if (event.getHand() == EquipmentSlot.HAND) {
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                Block block = event.getClickedBlock();
                if (block != null) {
                    String worldName = block.getWorld().getName();
                    for (String blackListedWorldName : this.seatManager.blacklistedWorldNames) {
                        if (worldName.equals(blackListedWorldName)) {
                            return;
                        }
                    }
                    SeatStructure seatStructure = SeatStructure.fromBlock(block);
                    if (seatStructure != null) {
                        event.setCancelled(true);
                        seatStructure.onPlayerInteract(event.getPlayer());
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(@NotNull PlayerQuitEvent event) {
        Entity vehicle = event.getPlayer().getVehicle();
        if (vehicle != null) {
            if (this.seatManager.getSeatEntity(vehicle.getUniqueId()).isPresent()) {
                vehicle.eject();
            }
        }
    }

}
