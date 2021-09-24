package de.jaylawl.superseatboi.seat;

import de.jaylawl.superseatboi.SuperSeatBoi;
import de.jaylawl.superseatboi.event.PlayerInteractWithSeatStructureEvent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SeatStructure {

    private final Block seatBlock;
    private final Block controlBlock;

    private SeatStructure(@NotNull Block seatBlock, @NotNull Block controlBlock) {
        this.seatBlock = seatBlock;
        this.controlBlock = controlBlock;
    }

    public static @Nullable SeatStructure fromBlock(@NotNull Block seatBlock) {
        SeatManager seatManager = SuperSeatBoi.getSeatManager();
        check:
        {
            if (seatManager.isSeatBlockMaterial(seatBlock.getType())) {
                Block controlBlock = seatBlock.getRelative(BlockFace.DOWN);
                if (seatManager.isControlBlockMaterial(controlBlock.getType())) {

                    BlockData blockData = seatBlock.getBlockData();

                    if (blockData instanceof Bisected bisected) {
                        if (bisected.getHalf() == Bisected.Half.TOP) {
                            break check;
                        }
                    }
                    if (blockData instanceof Waterlogged waterlogged) {
                        if (waterlogged.isWaterlogged()) {
                            break check;
                        }
                    }

                    return new SeatStructure(seatBlock, controlBlock);
                }
            }
        }
        return null;
    }

    //

    public @NotNull Block getSeatBlock() {
        return this.seatBlock;
    }

    public @NotNull Block getControlBlock() {
        return this.controlBlock;
    }

    public void interact(@NotNull Player player) {
        PlayerInteractWithSeatStructureEvent playerInteractWithSeatStructureEvent = new PlayerInteractWithSeatStructureEvent(this, player);
        boolean cancelEvent = player.isSneaking() || player.getGameMode() == GameMode.SPECTATOR || player.getVelocity().getY() < -.08;
        playerInteractWithSeatStructureEvent.setCancelled(cancelEvent);
        Bukkit.getPluginManager().callEvent(playerInteractWithSeatStructureEvent);

        if (!playerInteractWithSeatStructureEvent.isCancelled()) {
            SeatEntity seatEntity = getOrSpawnSeatEntity();
            Entity entity = seatEntity.getEntity();
            if (entity.getPassengers().isEmpty()) {
                entity.addPassenger(player);
            }
        }
    }

    public @Nullable SeatEntity getSeatEntity() {
        return SuperSeatBoi.getSeatManager().getSeatEntityInBlock(this.seatBlock);
    }

    public @NotNull SeatEntity getOrSpawnSeatEntity() {
        SeatManager seatManager = SuperSeatBoi.getSeatManager();
        SeatEntity seatEntity = seatManager.getSeatEntityInBlock(this.seatBlock);
        if (seatEntity == null) {
            seatEntity = SuperSeatBoi.getSeatManager().spawnSeatEntity(this);
        }
        return seatEntity;
    }

    public boolean hasSeatEntity() {
        return getSeatEntity() != null;
    }

}
