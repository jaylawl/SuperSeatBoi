package de.jaylawl.superseatboi.seat;

import de.jaylawl.superseatboi.SuperSeatBoi;
import de.jaylawl.superseatboi.event.api.SeatEntitySpawnEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Silverfish;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SeatManager {

    private final ConcurrentHashMap<UUID, SeatEntity> seatEntities = new ConcurrentHashMap<>();

    public SeatManager() {
    }

    //

    public @NotNull SeatEntity spawnSeatEntity(@NotNull SeatStructure seatStructure) {
        Block seatBlock = seatStructure.getSeatBlock();
        Location location = seatBlock.getLocation().toBlockLocation().add(.5, 0, .5);
        Silverfish entity = (Silverfish) location.getWorld().spawnEntity(location, SeatEntity.ENTITY_TYPE);
        entity.setAI(false);
        entity.setInvulnerable(true);
        entity.setSilent(true);
        entity.setPersistent(false);
        entity.setRemoveWhenFarAway(true);
        AttributeInstance maxHealth = entity.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        if (maxHealth != null) {
            maxHealth.setBaseValue(.01);
        }
        entity.addScoreboardTag(SeatEntity.SCOREBOARD_TAG_IDENTIFIER);
        entity.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0, true, false, false));

        BlockData blockData = seatBlock.getBlockData();
        if (blockData instanceof Directional directional) {
            Float yaw = switch (directional.getFacing()) {
                case NORTH -> 0f;
                case EAST -> 90f;
                case SOUTH -> 180f;
                case WEST -> 270f;
                default -> null;
            };
            if (yaw != null) {
                if (blockData instanceof Stairs stairs) {
                    switch (stairs.getShape()) {
                        case INNER_LEFT, OUTER_LEFT -> yaw -= 45f;
                        case INNER_RIGHT, OUTER_RIGHT -> yaw += 45f;
                    }
                    final float finalizedYaw = yaw;
                    final UUID entityUniqueId = entity.getUniqueId();
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            Entity entity1 = Bukkit.getEntity(entityUniqueId);
                            if (entity1 != null) {
                                entity1.setRotation(finalizedYaw, 0f);
                            }
                        }
                    }.runTaskLater(SuperSeatBoi.getInstance(), 5L);
                }
            }
        }

        SeatEntity seatEntity = new SeatEntity(entity);
        registerSeatEntity(seatEntity);
        Bukkit.getPluginManager().callEvent(new SeatEntitySpawnEvent(seatEntity));
        return seatEntity;
    }

    public void registerSeatEntity(@NotNull SeatEntity seatEntity) {
        this.seatEntities.put(seatEntity.getUniqueId(), seatEntity);
    }

    public void unregisterSeatEntity(@NotNull UUID uniqueId) {
        this.seatEntities.remove(uniqueId);
    }

    public @NotNull UUID[] getSeatEntityUniqueIds() {
        return this.seatEntities.keySet().toArray(new UUID[0]);
    }

    public @NotNull SeatEntity[] getSeatEntities() {
        return this.seatEntities.values().toArray(new SeatEntity[0]);
    }

    public Optional<SeatEntity> getSeatEntity(@NotNull UUID uniqueId) {
        return Optional.ofNullable(this.seatEntities.get(uniqueId));
    }

    public Optional<SeatEntity> getSeatEntityInBlock(@NotNull Block block) {
        World blockWorld = block.getWorld();
        Vector blockMinVector = block.getLocation().toBlockLocation().toVector();
        BoundingBox blockBoundingBox = BoundingBox.of(blockMinVector, blockMinVector.clone().add(new Vector(1, 1, 1)));
        for (final SeatEntity seatEntity : getSeatEntities()) {
            final Entity entity = seatEntity.getEntity();
            final Location entityLocation = entity.getLocation();
            if (blockWorld.equals(entityLocation.getWorld())) {
                if (blockBoundingBox.overlaps(entity.getBoundingBox())) {
                    return Optional.of(seatEntity);
                }
            }
        }
        return Optional.empty();
    }

}
