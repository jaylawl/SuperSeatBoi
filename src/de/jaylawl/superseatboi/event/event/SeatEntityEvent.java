package de.jaylawl.superseatboi.event.event;

import de.jaylawl.superseatboi.seat.SeatEntity;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

public abstract class SeatEntityEvent extends Event {

    private final SeatEntity seatEntity;

    public SeatEntityEvent(@NotNull SeatEntity seatEntity) {
        this.seatEntity = seatEntity;
    }

    //

    public @NotNull SeatEntity getSeatEntity() {
        return this.seatEntity;
    }

}
