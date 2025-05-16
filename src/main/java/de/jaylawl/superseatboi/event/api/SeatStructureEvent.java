package de.jaylawl.superseatboi.event.api;

import de.jaylawl.superseatboi.seat.SeatStructure;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

public abstract class SeatStructureEvent extends Event {

    private final SeatStructure seatStructure;

    public SeatStructureEvent(@NotNull SeatStructure seatStructure) {
        this.seatStructure = seatStructure;
    }

    //

    public @NotNull SeatStructure getSeatStructure() {
        return this.seatStructure;
    }

}
