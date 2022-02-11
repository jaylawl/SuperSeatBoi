package de.jaylawl.superseatboi.util;

import de.jaylawl.superseatboi.SuperSeatBoi;
public class ReloadScript extends IReloadScript {

    public ReloadScript() {
        super(SuperSeatBoi.getInstance());
    }

    //

    @Override
    public void initialSyncTasks() {
    }

    @Override
    public void asyncTasks() {
    }

    @Override
    public void finalSyncTasks() {
    }

    @Override
    public void finish() {
        SuperSeatBoi superSeatBoi = (SuperSeatBoi) this.pluginInstance;

        this.logger.info("Reload completed within " + this.elapsedSeconds + " s. and with " + this.totalWarnings + " warning(s)");

        notifySubscribers(getSubscriberNotification());
    }

}
