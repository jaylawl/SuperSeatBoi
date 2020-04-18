package de.jaylawl.superseatboi.listeners;

import de.jaylawl.superseatboi.SuperSeatBoi;
import de.jaylawl.superseatboi.entity.SeatEntity;
import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class RightClickListener implements Listener {

    public RightClickListener() {
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onRightClick(PlayerInteractEvent event) {

        if (
                event.getAction() != Action.RIGHT_CLICK_BLOCK ||
                        event.getPlayer().getGameMode() == GameMode.SPECTATOR
        ) {
            return;
        }

        Block clickedBlock = event.getClickedBlock();
        if (clickedBlock == null) {
            return;
        }

        if (!clickedBlock.getType().toString().contains("STAIR")) {
            return;
        }

        Bisected bisected = ((Bisected) clickedBlock.getBlockData());
        if (bisected.getHalf() == Bisected.Half.TOP) {
            return; //mountable stairs can't be upside-down
        }

        Block controlBlock = clickedBlock.getLocation().add(new Vector(0, -1, 0)).getBlock();
        if (controlBlock.getType() != SuperSeatBoi.getControlMaterial()) {
            return; //block below seat must be of this material
        }

        Player player = event.getPlayer();
        if (player.isSneaking()) {
            return;
        }

        event.setCancelled(true);

        if (player.getVelocity().getY() < -0.08) {
            return; //prevent using seats to dodge fall damage
        }

        Entity existingSeat = SeatEntity.findExisting(clickedBlock.getLocation());
        if (existingSeat != null) {
            if (existingSeat.getPassengers().isEmpty()) {
                existingSeat.addPassenger(player);
            }
            return; //seat exists or is already taken
        }

        Entity seatEntity = SeatEntity.getNew(clickedBlock.getLocation());
        seatEntity.addPassenger(player);

        new BukkitRunnable() {
            @Override
            public void run() {

                BlockData blockData = clickedBlock.getBlockData();
                BlockFace facing = ((Directional) blockData).getFacing();
                Stairs.Shape shape = ((Stairs) blockData).getShape();

                Float yaw = null;
                switch (facing) {
                    case NORTH:
                        switch (shape) {
                            case STRAIGHT:
                                yaw = 0f;
                                break;
                            case INNER_LEFT:
                            case OUTER_LEFT:
                                yaw = -45f;
                                break;
                            case INNER_RIGHT:
                            case OUTER_RIGHT:
                                yaw = 45f;
                                break;
                        }
                        break;
                    case EAST:
                        switch (shape) {
                            case STRAIGHT:
                                yaw = 90f;
                                break;
                            case INNER_LEFT:
                            case OUTER_LEFT:
                                yaw = 45f;
                                break;
                            case INNER_RIGHT:
                            case OUTER_RIGHT:
                                yaw = 135f;
                                break;
                        }
                        break;
                    case SOUTH:
                        switch (shape) {
                            case STRAIGHT:
                                yaw = 180f;
                                break;
                            case INNER_LEFT:
                            case OUTER_LEFT:
                                yaw = 135f;
                                break;
                            case INNER_RIGHT:
                            case OUTER_RIGHT:
                                yaw = -135f;
                                break;
                        }
                        break;
                    case WEST:
                        switch (shape) {
                            case STRAIGHT:
                                yaw = -90f;
                                break;
                            case INNER_LEFT:
                            case OUTER_LEFT:
                                yaw = -135f;
                                break;
                            case INNER_RIGHT:
                            case OUTER_RIGHT:
                                yaw = -45f;
                                break;
                        }
                        break;
                }
                if (yaw != null) {
                    seatEntity.setRotation(yaw, 0f);
                }

            }
        }.runTaskLater(SuperSeatBoi.inst(), 5L);

    }

}
