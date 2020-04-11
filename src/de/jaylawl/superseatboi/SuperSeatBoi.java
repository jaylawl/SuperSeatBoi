package de.jaylawl.superseatboi;

import de.jaylawl.superseatboi.listeners.RightClickListener;
import org.bukkit.Material;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class SuperSeatBoi extends JavaPlugin {

    private static SuperSeatBoi inst;
    private final static Material CONTROL_MATERIAL = Material.REDSTONE_BLOCK;
    private final static String SCOREBOARD_TAG_IDENTIFIER = "SuperSeatBoiSeatEntity";

    @Override
    public void onEnable() {

        inst = this;

        PluginManager pluginManager = getServer().getPluginManager();

        pluginManager.registerEvents(new RightClickListener(), this);

    }

    public static SuperSeatBoi inst() {
        return inst;
    }

    public static Material getControlMaterial() {
        return CONTROL_MATERIAL;
    }

    public static String getScoreboardTagIdentifier() {
        return SCOREBOARD_TAG_IDENTIFIER;
    }

}
