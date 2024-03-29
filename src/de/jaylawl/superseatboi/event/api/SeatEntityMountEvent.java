package de.jaylawl.superseatboi.event.api;

import de.jaylawl.superseatboi.seat.SeatEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class SeatEntityMountEvent extends SeatEntityEvent implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();
    private boolean cancelled = false;

    public SeatEntityMountEvent(@NotNull SeatEntity seatEntity) {
        super(seatEntity);
    }

    //

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

}
