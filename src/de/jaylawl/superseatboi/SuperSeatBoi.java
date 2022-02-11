package de.jaylawl.superseatboi;

import de.jaylawl.superseatboi.event.listener.bukkit.BlockListener;
import de.jaylawl.superseatboi.event.listener.bukkit.EntityListener;
import de.jaylawl.superseatboi.event.listener.bukkit.PlayerListener;
import de.jaylawl.superseatboi.seat.SeatManager;
import org.bukkit.Material;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.logging.Logger;

public class SuperSeatBoi extends JavaPlugin {

    private static SuperSeatBoi INSTANCE;

    private SeatManager seatManager;

    @Override
    public void onEnable() {

        INSTANCE = this;

        this.seatManager = new SeatManager();

        final Logger logger = getLogger();

        InputStream inputStream = getClass().getResourceAsStream("/de/jaylawl/superseatboi/resources/ascii_startup_art.txt");
        if (inputStream != null) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            for (String line : bufferedReader.lines().toList()) {
                logger.info(line);
            }
        }

        final PluginManager pluginManager = getServer().getPluginManager();

        pluginManager.registerEvents(new BlockListener(), this);
        pluginManager.registerEvents(new EntityListener(), this);
        pluginManager.registerEvents(new PlayerListener(), this);

        initiateSeatManagerSettings();

    }

    //

    public static SuperSeatBoi getInstance() {
        return INSTANCE;
    }

    public static SeatManager getSeatManager() {
        return INSTANCE.seatManager;
    }

    //

    private void initiateSeatManagerSettings() {
        Collection<Material> seatMaterials = new ArrayList<>();
        for (Material material : Material.values()) {
            if (material.toString().contains("STAIR")) {
                seatMaterials.add(material);
            }
        }
        this.seatManager.setSeatBlockMaterials(seatMaterials);
        this.seatManager.setControlBlockMaterials(Collections.singletonList(Material.REDSTONE_BLOCK));
    }

}
