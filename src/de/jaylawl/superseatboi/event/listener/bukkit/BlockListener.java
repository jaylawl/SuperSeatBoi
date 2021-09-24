package de.jaylawl.superseatboi.event.listener.bukkit;

import de.jaylawl.superseatboi.SuperSeatBoi;
import de.jaylawl.superseatboi.seat.SeatEntity;
import de.jaylawl.superseatboi.seat.SeatStructure;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.jetbrains.annotations.NotNull;

public class BlockListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockBreak(@NotNull BlockBreakEvent event) {
        Block block = event.getBlock();
        SeatStructure seatStructure = SeatStructure.fromBlock(block);
        if (seatStructure != null) {
            SeatEntity existentSeatEntity = SuperSeatBoi.getSeatManager().getSeatEntityInBlock(block);
            if (existentSeatEntity != null) {
                Entity entity = existentSeatEntity.getEntity();
                entity.eject();
                entity.remove();
            }
        }
    }

}
