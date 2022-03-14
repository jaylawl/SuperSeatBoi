package de.jaylawl.superseatboi.event.event;

import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class SuperSeatBoiPostReloadEvent extends SuperSeatBoiEvent {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    public SuperSeatBoiPostReloadEvent() {
    }

    //

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

}
