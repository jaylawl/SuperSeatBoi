package de.jaylawl.superseatboi.event.listener.bukkit;

import de.jaylawl.superseatboi.SuperSeatBoi;
import de.jaylawl.superseatboi.seat.SeatManager;
import de.jaylawl.superseatboi.seat.SeatStructure;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.UUID;

public class PlayerListener implements Listener {

    private final SeatManager seatManager;
    private final HashMap<UUID, Integer> lastPlayerInteractionTicks = new HashMap<>();

    public PlayerListener(@NotNull SeatManager seatManager) {
        this.seatManager = seatManager;
    }

    //

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerInteract(@NotNull PlayerInteractEvent event) {

        if (event.getHand() != EquipmentSlot.HAND) {
            return;
        }
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        final Player player = event.getPlayer();
        final UUID playerUniqueId = player.getUniqueId();

        final int currentTick = Bukkit.getCurrentTick();
        if (this.lastPlayerInteractionTicks.containsKey(playerUniqueId)) {
            int lastPlayerInteractionTick = this.lastPlayerInteractionTicks.get(playerUniqueId);
            int playerInteractionCooldown = SuperSeatBoi.getInstance().getConfigurableSettings().playerInteractionCooldown;
            if (currentTick - lastPlayerInteractionTick < playerInteractionCooldown) {
                return;
            }
        }
        this.lastPlayerInteractionTicks.put(playerUniqueId, currentTick);

        Block clickedBlock = event.getClickedBlock();
        if (clickedBlock == null) {
            return;
        }

        SeatStructure seatStructure = SeatStructure.fromBlock(clickedBlock);
        if (seatStructure == null) {
            return;
        }

        event.setCancelled(true);
        seatStructure.onInteract(player);

    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerQuit(@NotNull PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Entity vehicle = player.getVehicle();
        if (vehicle != null) {
            if (this.seatManager.getSeatEntity(vehicle.getUniqueId()).isPresent()) {
                vehicle.eject();
            }
        }
        this.lastPlayerInteractionTicks.remove(player.getUniqueId());
    }

}
