package de.jaylawl.superseatboi.event.listener.bukkit;

import de.jaylawl.superseatboi.seat.SeatManager;
import de.jaylawl.superseatboi.seat.SeatStructure;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.jetbrains.annotations.NotNull;

public class BlockListener implements Listener {

    private final SeatManager seatManager;

    public BlockListener(@NotNull SeatManager seatManager) {
        this.seatManager = seatManager;
    }

    //

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockBreak(@NotNull BlockBreakEvent event) {
        Block block = event.getBlock();
        SeatStructure seatStructure = SeatStructure.fromBlock(block);
        if (seatStructure != null) {
            this.seatManager.getSeatEntityInBlock(block).ifPresent(seatEntity -> {
                Entity entity = seatEntity.getEntity();
                entity.eject();
                entity.remove();
            });
        }
    }

}
