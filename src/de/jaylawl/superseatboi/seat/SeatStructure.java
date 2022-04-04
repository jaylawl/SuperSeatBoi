package de.jaylawl.superseatboi.seat;

import de.jaylawl.superseatboi.SuperSeatBoi;
import de.jaylawl.superseatboi.event.api.PlayerInteractWithSeatStructureEvent;
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
        final ConfigurableSettings configurableSettings = SuperSeatBoi.getInstance().getConfigurableSettings();

        final String worldName = seatBlock.getWorld().getName();
        switch (configurableSettings.worldFilterMode) {
            case BLACKLIST -> {
                if (configurableSettings.blacklistedWorlds.contains(worldName)) {
                    return null;
                }
            }
            case WHITELIST -> {
                if (!configurableSettings.whitelistedWorlds.contains(worldName)) {
                    return null;
                }
            }
        }

        if (!configurableSettings.seatBlockMaterials.contains(seatBlock.getType())) {
            return null;
        }

        Block controlBlock = seatBlock.getRelative(BlockFace.DOWN);
        if (SuperSeatBoi.getInstance().getConfigurableSettings().requireControlBlock) {
            if (!configurableSettings.controlBlockMaterials.contains(controlBlock.getType())) {
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

    public Optional<SeatEntity> getSeatEntity() {
        return SuperSeatBoi.getInstance().getSeatManager().getSeatEntityInBlock(this.seatBlock);
    }

    public @NotNull SeatEntity getOrSpawnSeatEntity() {
        final SeatManager seatManager = SuperSeatBoi.getInstance().getSeatManager();
        return seatManager.getSeatEntityInBlock(this.seatBlock).orElseGet(() -> seatManager.spawnSeatEntity(this));
    }

    public boolean hasSeatEntity() {
        return getSeatEntity().isPresent();
    }

    //

    public void onInteract(final @NotNull Player player) {

        final ConfigurableSettings configurableSettings = SuperSeatBoi.getInstance().getConfigurableSettings();

        PlayerInteractWithSeatStructureEvent playerInteractWithSeatStructureEvent = new PlayerInteractWithSeatStructureEvent(this, player);

        final GameMode gameMode = player.getGameMode();

        if (!configurableSettings.allowSeatSwapping && player.getVehicle() != null) {
            playerInteractWithSeatStructureEvent.setCancelled(true);
        } else if (!configurableSettings.allowSeatingInCreativeMode && gameMode == GameMode.CREATIVE) {
            playerInteractWithSeatStructureEvent.setCancelled(true);
        } else if (gameMode == GameMode.SPECTATOR) {
            playerInteractWithSeatStructureEvent.setCancelled(true);
        } else if (!configurableSettings.allowWaterloggedSeats && this.seatBlock.getBlockData() instanceof Waterlogged waterlogged && waterlogged.isWaterlogged()) {
            playerInteractWithSeatStructureEvent.setCancelled(true);
        } else if (!configurableSettings.allowSeatingWhileSneaking && player.isSneaking()) {
            playerInteractWithSeatStructureEvent.setCancelled(true);
        } else if (!configurableSettings.allowSeatingWhileFalling && player.getVelocity().getY() < -.08) {
            playerInteractWithSeatStructureEvent.setCancelled(true);
        } else if (!configurableSettings.allowSeatingWhileFlying && player.isFlying()) {
            playerInteractWithSeatStructureEvent.setCancelled(true);
        } else if (!configurableSettings.allowSeatingWhileGliding && player.isGliding()) {
            playerInteractWithSeatStructureEvent.setCancelled(true);
        }
        Bukkit.getPluginManager().callEvent(playerInteractWithSeatStructureEvent);

        if (playerInteractWithSeatStructureEvent.isCancelled()) {
            return;
        }
        SeatEntity seatEntity = getOrSpawnSeatEntity();
        Entity entity = seatEntity.getEntity();
        if (entity.getPassengers().isEmpty()) {
            entity.addPassenger(player);
        }
    }

}
