package de.jaylawl.superseatboi.event.event;

import de.jaylawl.superseatboi.seat.SeatStructure;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class PlayerInteractWithSeatStructureEvent extends SeatStructureEvent implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();
    private boolean cancelled = false;

    private final Player player;

    public PlayerInteractWithSeatStructureEvent(@NotNull SeatStructure seatStructure, @NotNull Player player) {
        super(seatStructure);
        this.player = player;
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

    //

    public @NotNull Player getPlayer() {
        return this.player;
    }

}
