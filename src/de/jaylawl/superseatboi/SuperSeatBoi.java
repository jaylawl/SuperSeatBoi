package de.jaylawl.superseatboi;

import de.jaylawl.superseatboi.command.CommandMaster;
import de.jaylawl.superseatboi.event.listener.bukkit.BlockListener;
import de.jaylawl.superseatboi.event.listener.bukkit.EntityListener;
import de.jaylawl.superseatboi.event.listener.bukkit.PlayerListener;
import de.jaylawl.superseatboi.seat.SeatManager;
import de.jaylawl.superseatboi.util.ConfigurableSettings;
import de.jaylawl.superseatboi.util.ReloadScript;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Logger;

public class SuperSeatBoi extends JavaPlugin {

    private static SuperSeatBoi INSTANCE;

    private final SeatManager seatManager = new SeatManager();

    private final ConfigurableSettings configurableSettings = new ConfigurableSettings();
    private ReloadScript latestReloadScript = null;

    @Override
    public void onEnable() {

        INSTANCE = this;

        final Logger logger = getLogger();

        InputStream inputStream = getClass().getResourceAsStream("/de/jaylawl/superseatboi/resources/ascii_startup_art.txt");
        if (inputStream != null) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            for (String line : bufferedReader.lines().toList()) {
                logger.info(line);
            }
        }

        final PluginManager pluginManager = getServer().getPluginManager();

        PluginCommand masterCommand = getCommand("superseatboi");
        if (masterCommand == null) {
            logger.severe("Failed to enable master command!");
            logger.severe("Disabling plugin...");
            pluginManager.disablePlugin(this);
            return;
        } else {
            CommandMaster commandMaster = new CommandMaster();
            masterCommand.setTabCompleter(commandMaster);
            masterCommand.setExecutor(commandMaster);
        }

        pluginManager.registerEvents(new BlockListener(this.seatManager), this);
        pluginManager.registerEvents(new EntityListener(this.seatManager), this);
        pluginManager.registerEvents(new PlayerListener(this.seatManager), this);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (!SuperSeatBoi.this.reload()) {
                    logger.warning("Initial reload of plugin data failed");
                    logger.warning("Try running the plugin's reload command manually");
                }
            }
        }.runTaskLater(this, 1L);

    }

    @Override
    public void onDisable() {
    }

    public boolean reload() {
        return reload(getServer().getConsoleSender());
    }

    public boolean reload(@NotNull CommandSender issuer) {
        if (this.latestReloadScript != null && !this.latestReloadScript.isConcluded()) {
            if (issuer instanceof Player player) {
                this.latestReloadScript.addSubscriber(player.getUniqueId());
            }
            return false;
        }
        this.latestReloadScript = new ReloadScript();
        if (issuer instanceof Player player) {
            this.latestReloadScript.addSubscriber(player.getUniqueId());
        }
        this.latestReloadScript.run();
        return true;
    }

    //

    public static SuperSeatBoi getInstance() {
        return INSTANCE;
    }

    public SeatManager getSeatManager() {
        return this.seatManager;
    }

    public ConfigurableSettings getConfigurableSettings() {
        return this.configurableSettings;
    }

}
