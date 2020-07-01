package de.jaylawl.superseatboi.event.listener.bukkit;

import de.jaylawl.superseatboi.SuperSeatBoi;
import de.jaylawl.superseatboi.seat.SeatEntity;
import de.jaylawl.superseatboi.seat.SeatStructure;
import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class RightClickListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onRightClick(PlayerInteractEvent event) {

        if (event.getAction() != Action.RIGHT_CLICK_BLOCK || event.getPlayer().getGameMode() == GameMode.SPECTATOR) {
            return;
        }

        Block clickedBlock = event.getClickedBlock();
        if (clickedBlock == null) {
            return;
        }

        if (!SeatStructure.SEAT_MATERIALS.contains(clickedBlock.getType())) {
            return;
        }

        BlockData blockData = clickedBlock.getBlockData();

        if (blockData instanceof Bisected && ((Bisected) blockData).getHalf() == Bisected.Half.TOP) {
            return; //mountable stairs can't be upside-down
        }

        if (!SeatStructure.SEAT_VALIDATION_MATERIALS.contains(clickedBlock.getRelative(BlockFace.DOWN).getType())) {
            return; //block below seat must be of this material
        }

        if (blockData instanceof Waterlogged && ((Waterlogged) blockData).isWaterlogged()) {
            return; //no waterlogged seating
        }

        Player player = event.getPlayer();
        if (player.isSneaking()) {
            return;
        }

        event.setCancelled(true);

        if (player.getVelocity().getY() < -.08) {
            return; //prevent using seats to dodge fall damage
        }

        Entity existingSeat = SeatEntity.findExisting(clickedBlock.getLocation());
        if (existingSeat != null) {
            if (existingSeat.getPassengers().isEmpty()) {
                existingSeat.addPassenger(player);
            }
            return; //seat exists or is already taken
        }

        Entity seatEntity = SeatEntity.spawnNew(clickedBlock.getLocation());
        seatEntity.addPassenger(player);

        new BukkitRunnable() {
            @Override
            public void run() {
                Float yaw = null;
                switch (((Directional) blockData).getFacing()) {
                    case NORTH:
                        yaw = 0f;
                        break;
                    case EAST:
                        yaw = 90f;
                        break;
                    case SOUTH:
                        yaw = 180f;
                        break;
                    case WEST:
                        yaw = 270f;
                        break;
                }
                if (yaw != null) {
                    switch (((Stairs) blockData).getShape()) {
                        case INNER_LEFT:
                        case OUTER_LEFT:
                            yaw -= 45f;
                            break;
                        case INNER_RIGHT:
                        case OUTER_RIGHT:
                            yaw += 45f;
                            break;
                    }
                    seatEntity.setRotation(yaw, 0f);
                }
            }
        }.runTaskLater(SuperSeatBoi.inst(), 5L);

    }

}
