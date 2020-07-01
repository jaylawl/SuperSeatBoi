package de.jaylawl.superseatboi.seat;

import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Silverfish;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

public class SeatEntity {

    public final static String SCOREBOARD_TAG_IDENTIFIER = "SuperSeatBoiSeatEntity";
    private final static PotionEffect INVISIBILITY_POTION = new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0, true, false, false);

    @SuppressWarnings("deprecation")
    public static Entity spawnNew(Location location) {
        location.setX(location.getBlockX() + .5);
        location.setY(location.getBlockY());
        location.setZ(location.getBlockZ() + .5);
        Silverfish seatEntity = (Silverfish) location.getWorld().spawnEntity(location, EntityType.SILVERFISH);

        seatEntity.setAI(false);
        seatEntity.setInvulnerable(true);
        seatEntity.setSilent(true);
        seatEntity.setPersistent(false);
        seatEntity.setRemoveWhenFarAway(true);

        AttributeInstance maxHealth = seatEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        if (maxHealth != null) {
            maxHealth.setBaseValue(.01);
        }

        seatEntity.addScoreboardTag(SCOREBOARD_TAG_IDENTIFIER);
        seatEntity.addPotionEffect(INVISIBILITY_POTION);

        return seatEntity;
    }

    public static Entity findExisting(Location location) {
        Vector blockCoordinate = new Vector(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        BoundingBox voxelBounds = BoundingBox.of(blockCoordinate, blockCoordinate.clone().add(new Vector(1, 1, 1)));
        for (Entity entity : location.getWorld().getNearbyEntities(voxelBounds)) {
            if (entity instanceof Silverfish && entity.getScoreboardTags().contains(SCOREBOARD_TAG_IDENTIFIER)) {
                return entity;
            }
        }
        return null;
    }

}
