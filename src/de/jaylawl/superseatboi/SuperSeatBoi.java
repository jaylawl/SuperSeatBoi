package de.jaylawl.superseatboi;

import de.jaylawl.superseatboi.event.listener.bukkit.BlockBreakListener;
import de.jaylawl.superseatboi.event.listener.bukkit.DismountListener;
import de.jaylawl.superseatboi.event.listener.bukkit.QuitListener;
import de.jaylawl.superseatboi.event.listener.bukkit.RightClickListener;
import de.jaylawl.superseatboi.seat.SeatStructure;
import org.bukkit.Material;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class SuperSeatBoi extends JavaPlugin {

    private static SuperSeatBoi INSTANCE;

    private final static Material CONTROL_MATERIAL = Material.REDSTONE_BLOCK;

    @Override
    public void onEnable() {

        INSTANCE = this;

        PluginManager pluginManager = getServer().getPluginManager();

        pluginManager.registerEvents(new BlockBreakListener(), this);
        pluginManager.registerEvents(new DismountListener(), this);
        pluginManager.registerEvents(new RightClickListener(), this);
        pluginManager.registerEvents(new QuitListener(), this);

        SeatStructure.init();

    }

    public static SuperSeatBoi inst() {
        return INSTANCE;
    }

    public static Material getControlMaterial() {
        return CONTROL_MATERIAL;
    }

}
