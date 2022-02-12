package de.jaylawl.superseatboi.event.event;

import de.jaylawl.superseatboi.SuperSeatBoi;
import org.bukkit.event.server.PluginEvent;
import org.jetbrains.annotations.NotNull;

public abstract class SuperSeatBoiEvent extends PluginEvent {

    public SuperSeatBoiEvent() {
        super(SuperSeatBoi.getInstance());
    }

    @Override
    public @NotNull SuperSeatBoi getPlugin() {
        return ((SuperSeatBoi) super.getPlugin());
    }

}
