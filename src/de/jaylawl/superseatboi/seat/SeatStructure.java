package de.jaylawl.superseatboi.seat;

import de.jaylawl.superseatboi.SuperSeatBoi;
import de.jaylawl.superseatboi.event.PlayerInteractWithSeatStructureEvent;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class SeatStructure {

    private final Block seatBlock;
    private final Block controlBlock;

    private SeatStructure(@NotNull Block seatBlock, @NotNull Block controlBlock) {
        this.seatBlock = seatBlock;
        this.controlBlock = controlBlock;
    }

    public static @Nullable SeatStructure fromBlock(@NotNull Block seatBlock) {
        final SeatManager seatManager = SuperSeatBoi.getInstance().getSeatManager();

        if (!seatManager.isSeatBlockMaterial(seatBlock.getType())) {
            return null;
        }

        Block controlBlock = seatBlock.getRelative(BlockFace.DOWN);
        if (seatManager.requireControlBlock) {
            if (!seatManager.isControlBlockMaterial(controlBlock.getType())) {
                return null;
            }
        }

        if (seatBlock.getBlockData() instanceof Bisected bisected && bisected.getHalf() == Bisected.Half.TOP) {
            return null;
        }

        return new SeatStructure(seatBlock, controlBlock);
    }

    //

    public @NotNull Block getSeatBlock() {
        return this.seatBlock;
    }

    public @NotNull Block getControlBlock() {
        return this.controlBlock;
    }

    public void onPlayerInteract(@NotNull Player player) {
        PlayerInteractWithSeatStructureEvent playerInteractWithSeatStructureEvent = new PlayerInteractWithSeatStructureEvent(this, player);
        boolean cancelEvent = false;
        final SeatManager seatManager = SuperSeatBoi.getInstance().getSeatManager();
        switch (player.getGameMode()) {
            case CREATIVE -> cancelEvent = !seatManager.allowSeatingIfCreativeMode;
            case SPECTATOR -> cancelEvent = true;
        }
        if (!seatManager.allowWaterloggedSeats && this.seatBlock instanceof Waterlogged waterlogged && waterlogged.isWaterlogged()) {
            cancelEvent = true;
        } else if (!seatManager.allowSeatingIfSneaking && player.isSneaking()) {
            cancelEvent = true;
        } else if (!seatManager.allowSeatingIfFalling && player.getVelocity().getY() < -.08) {
            cancelEvent = true;
        } else if (!seatManager.allowSeatingIfFlying && player.isFlying()) {
            cancelEvent = true;
        }
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

    public Optional<SeatEntity> getSeatEntity() {
        return SuperSeatBoi.getInstance().getSeatManager().getSeatEntityInBlock(this.seatBlock);
    }

    public @NotNull SeatEntity getOrSpawnSeatEntity() {
        SeatManager seatManager = SuperSeatBoi.getInstance().getSeatManager();
        final SeatEntity seatEntity;
        Optional<SeatEntity> existentSeatEntity = seatManager.getSeatEntityInBlock(this.seatBlock);
        seatEntity = existentSeatEntity.orElseGet(() -> seatManager.spawnSeatEntity(this));
        return seatEntity;
    }

    public boolean hasSeatEntity() {
        return getSeatEntity().isPresent();
    }

}
