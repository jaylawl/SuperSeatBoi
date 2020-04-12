package de.jaylawl.superseatboi.util;

import de.jaylawl.superseatboi.SuperSeatBoi;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.BoundingBox;

public class FindSeatEntity {

    public static Entity get(Location location) {

        location.setX(Math.floor(location.getX()));
        location.setY(Math.floor(location.getY()));
        location.setZ(Math.floor(location.getZ()));
        BoundingBox scanBox = new BoundingBox(
                location.getX(), location.getY(), location.getZ(),
                (location.getX() + 1), (location.getY() + 1), (location.getZ() + 1));
        for (Entity e : location.getWorld().getNearbyEntities(scanBox)) {
            if (e.getScoreboardTags().contains(SuperSeatBoi.getScoreboardTagIdentifier())) {
                return e;
            }
        }
        return null;
    }

}
