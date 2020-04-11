package de.jaylawl.superseatboi.entity;

import de.jaylawl.superseatboi.SuperSeatBoi;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Silverfish;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class SeatEntity {

    @SuppressWarnings("deprecation")
    public static Entity getNew(Location location) {

        location.add(new Vector(0.5, 0, 0.5));
        Silverfish seatEntity = ((Silverfish) location.getWorld().spawnEntity(location, EntityType.SILVERFISH));

        seatEntity.setAI(false);
        seatEntity.setInvulnerable(true);
        seatEntity.setSilent(true);
        seatEntity.setPersistent(false);
        seatEntity.setRemoveWhenFarAway(true);

        AttributeInstance maxHealth = seatEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        if (maxHealth != null) {
            maxHealth.setBaseValue(0.01d);
        }

        seatEntity.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 2147483647, 0, true, false, false));

        seatEntity.addScoreboardTag(SuperSeatBoi.getScoreboardTagIdentifier());

        return seatEntity;

    }

}
