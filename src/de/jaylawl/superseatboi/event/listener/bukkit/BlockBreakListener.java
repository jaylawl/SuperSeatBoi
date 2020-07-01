package de.jaylawl.superseatboi.event.listener.bukkit;

import de.jaylawl.superseatboi.seat.SeatEntity;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {

        Block eventBlock = event.getBlock();
        if (!eventBlock.getType().toString().contains("STAIR")) {
            return;
        }

        Entity existingSeat = SeatEntity.findExisting(eventBlock.getLocation());
        if (existingSeat != null) {
            existingSeat.eject();
            existingSeat.remove();
        }

    }

}
