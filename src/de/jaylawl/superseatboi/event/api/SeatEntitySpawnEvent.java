package de.jaylawl.superseatboi.event.api;

import de.jaylawl.superseatboi.seat.SeatEntity;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class SeatEntitySpawnEvent extends SeatEntityEvent {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    public SeatEntitySpawnEvent(@NotNull SeatEntity seatEntity) {
        super(seatEntity);
    }

    //

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

}
