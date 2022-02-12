package de.jaylawl.superseatboi.seat;

import de.jaylawl.superseatboi.SuperSeatBoi;
import de.jaylawl.superseatboi.event.PlayerInteractWithSeatStructureEvent;
import de.jaylawl.superseatboi.util.ConfigurableSettings;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected;
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
        if (SuperSeatBoi.getInstance().getConfigurableSettings().requireControlBlock) {
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
        final ConfigurableSettings configurableSettings = SuperSeatBoi.getInstance().getConfigurableSettings();

        PlayerInteractWithSeatStructureEvent playerInteractWithSeatStructureEvent = new PlayerInteractWithSeatStructureEvent(this, player);
        boolean cancelEvent = false;

        final String worldName = player.getWorld().getName();
        switch (configurableSettings.worldFilterMode) {
            case BLACKLIST -> {
                if (configurableSettings.blacklistedWorlds.contains(worldName)) {
                    cancelEvent = true;
                }
            }
            case WHITELIST -> {
                if (!configurableSettings.whitelistedWorlds.contains(worldName)){
                    cancelEvent = true;
                }
            }
        }

        if (player.getVehicle() != null) {
            cancelEvent = true;
        }

        final GameMode gameMode = player.getGameMode();
        if (!configurableSettings.allowSeatingIfCreativeMode && gameMode == GameMode.CREATIVE) {
            cancelEvent = true;
        } else if (gameMode == GameMode.SPECTATOR) {
            cancelEvent = true;
        } else if (!configurableSettings.allowWaterloggedSeats && this.seatBlock.getBlockData() instanceof Waterlogged waterlogged && waterlogged.isWaterlogged()) {
            cancelEvent = true;
        } else if (!configurableSettings.allowSeatingIfSneaking && player.isSneaking()) {
            cancelEvent = true;
        } else if (!configurableSettings.allowSeatingIfFalling && player.getVelocity().getY() < -.08) {
            cancelEvent = true;
        } else if (!configurableSettings.allowSeatingIfFlying && player.isFlying()) {
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
        return seatManager.getSeatEntityInBlock(this.seatBlock).orElseGet(() -> seatManager.spawnSeatEntity(this));
    }

    public boolean hasSeatEntity() {
        return getSeatEntity().isPresent();
    }

}
