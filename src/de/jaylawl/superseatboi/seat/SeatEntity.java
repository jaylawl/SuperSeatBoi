package de.jaylawl.superseatboi.seat;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class SeatEntity {

    public final static String SCOREBOARD_TAG_IDENTIFIER = "SuperSeatBoiSeatEntity";
    public final static EntityType ENTITY_TYPE = EntityType.SILVERFISH;

    private final UUID uniqueId;
    private final Entity entity;

    protected SeatEntity(@NotNull Entity entity) {
        this.uniqueId = entity.getUniqueId();
        this.entity = entity;
    }

    //

    public @NotNull UUID getUniqueId() {
        return this.uniqueId;
    }

    public @NotNull Entity getEntity() {
        return this.entity;
    }

}
