package de.jaylawl.superseatboi.util;

import de.jaylawl.superseatboi.SuperSeatBoi;
import de.jaylawl.superseatboi.event.api.SuperSeatBoiPostReloadEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;

import java.io.*;
import java.util.*;

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
        FileUtil.createDefaultDataFolder();
        FileUtil.createDefaultConfigFile();

        final SuperSeatBoi superSeatBoi = (SuperSeatBoi) this.pluginInstance;

        File configFile = FileUtil.getConfigFile();
        if (configFile == null) {
            this.logger.warning("Unable to find config file \"config.yml\"");
            this.totalWarnings++;
            return;
        }

        YamlConfiguration yaml = new YamlConfiguration();
        try {
            yaml.load(configFile);
        } catch (IOException | InvalidConfigurationException exception) {
            this.logger.warning("Exception occurred while trying to read \"config.yml\" as yaml configuration:");
            exception.printStackTrace();
            this.totalWarnings++;
            return;
        }

        // Configuration file integrity:
        {
            if (yaml.get("PlayerInteractionCooldown") == null) {
                yaml.set("PlayerInteractionCooldown", ConfigurableSettings.DEFAULT_PLAYER_INTERACTION_COOLDOWN);
            }
            if (yaml.get("WorldFilterMode") == null) {
                yaml.set("WorldFilterMode", ConfigurableSettings.DEFAULT_WORLD_FILTER_MODE.toString());
            }
            if (yaml.get("BlacklistedWorlds") == null) {
                yaml.set("BlacklistedWorlds", new ArrayList<>());
            }
            if (yaml.get("WhitelistedWorlds") == null) {
                yaml.set("WhitelistedWorlds", new ArrayList<>());
            }
            if (yaml.get("RequireControlBlock") == null) {
                yaml.set("RequireControlBlock", ConfigurableSettings.DEFAULT_REQUIRE_CONTROL_BLOCK);
            }
            if (yaml.get("SeatBlockMaterials") == null) {
                yaml.set("SeatBlockMaterials", ConfigurableSettings.DEFAULT_SEAT_BLOCK_MATERIAL_NAMES);
            }
            if (yaml.get("ControlBlockMaterials") == null) {
                yaml.set("ControlBlockMaterials", ConfigurableSettings.DEFAULT_CONTROL_BLOCK_MATERIAL_NAMES);
            }
            if (yaml.get("AllowWaterloggedSeats") == null) {
                yaml.set("AllowWaterloggedSeats", ConfigurableSettings.DEFAULT_ALLOW_WATERLOGGED_SEATS);
            }
            if (yaml.get("AllowSeatingWhileFalling") == null) {
                yaml.set("AllowSeatingWhileFalling", ConfigurableSettings.DEFAULT_ALLOW_SEATING_WHILE_FALLING);
            }
            if (yaml.get("AllowSeatingWhileSneaking") == null) {
                yaml.set("AllowSeatingWhileSneaking", ConfigurableSettings.DEFAULT_ALLOW_SEATING_WHILE_SNEAKING);
            }
            if (yaml.get("AllowSeatingWhileFlying") == null) {
                yaml.set("AllowSeatingWhileFlying", ConfigurableSettings.DEFAULT_ALLOW_SEATING_WHILE_FLYING);
            }
            if (yaml.get("AllowSeatingWhileGliding") == null) {
                yaml.set("AllowSeatingWhileGliding", ConfigurableSettings.DEFAULT_ALLOW_SEATING_WHILE_GLIDING);
            }
            if (yaml.get("AllowSeatingInCreativeMode") == null) {
                yaml.set("AllowSeatingInCreativeMode", ConfigurableSettings.DEFAULT_ALLOW_SEATING_IN_CREATIVE_MODE);
            }
            if (yaml.get("AllowSeatSwapping") == null) {
                yaml.set("AllowSeatSwapping", ConfigurableSettings.DEFAULT_ALLOW_SEAT_SWAPPING);
            }

            try {
                yaml.save(configFile);
                PluginDescriptionFile pluginDescriptionFile = SuperSeatBoi.getInstance().getDescription();
                FileInputStream fileInputStream = new FileInputStream(configFile);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
                StringBuilder contents = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    if (!line.contains("#") && !line.equals("")) {
                        contents.append(line).append("\n");
                    }
                }

                StringBuilder fileComments = new StringBuilder("# \n");

                InputStream inputStream = getClass().getResourceAsStream("/de/jaylawl/superseatboi/resources/config_file_comments.txt");
                if (inputStream != null) {
                    BufferedReader commentsBufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    for (String commentLine : commentsBufferedReader.lines().toList()) {
                        commentLine = commentLine
                                .replace("%plugin_name%", pluginDescriptionFile.getName())
                                .replace("%plugin_version%", pluginDescriptionFile.getVersion())
                                .replace("%spigot_api_version%", pluginDescriptionFile.getAPIVersion() != null ? pluginDescriptionFile.getAPIVersion() : "null")
                                .replace("%authors%", String.join(",", pluginDescriptionFile.getAuthors()))
                                .replace("%github_link%", "https://github.com/jaylawl/SuperSeatBoi")
                                .replace("%spigot_link%", "https://www.spigotmc.org/resources/superseatboi.77321/");
                        fileComments.append("# ").append(commentLine).append("\n");
                    }
                }
                fileComments.append("\n");

                contents.insert(0, fileComments);
                FileOutputStream fileOutputStream = new FileOutputStream(configFile);
                fileOutputStream.write(contents.toString().getBytes());
                fileOutputStream.flush();

            } catch (IOException exception) {
                this.logger.warning("Exception occurred while trying to save to \"config.yml\":");
                exception.printStackTrace();
                this.totalWarnings++;
            }
        }

        ConfigurableSettings configurableSettings = superSeatBoi.getConfigurableSettings();
        configurableSettings.applyDefaultValues();

        // Apply data from YamlConfiguration to ConfigurableSettings
        {
            configurableSettings.playerInteractionCooldown = yaml.getInt("PlayerInteractionCooldown");

            // World filter mode:
            {
                String worldFilterModeString = yaml.getString("WorldFilterMode");
                try {
                    configurableSettings.worldFilterMode = WorldFilterMode.valueOf(worldFilterModeString);
                } catch (IllegalArgumentException exception) {
                    this.logger.warning("\"" + worldFilterModeString + "\" is not a valid WorldFilterMode.class constant");
                    this.logger.warning("Must be either of " + Arrays.toString(WorldFilterMode.values()));
                    this.totalWarnings++;
                }
            }

            configurableSettings.blacklistedWorlds = new LinkedList<>(yaml.getStringList("BlacklistedWorlds"));
            configurableSettings.whitelistedWorlds = new LinkedList<>(yaml.getStringList("WhitelistedWorlds"));

            // Seat and control block materials:
            {
                Collection<Material> seatBlockMaterials = new ArrayList<>();
                Collection<Material> controlBlockMaterials = new ArrayList<>();

                for (int i = 0; i < 2; i++) {

                    Collection<Material> list;
                    String yamlNode;
                    if (i == 0) {
                        list = seatBlockMaterials;
                        yamlNode = "SeatBlockMaterials";
                    } else {
                        list = controlBlockMaterials;
                        yamlNode = "ControlBlockMaterials";
                    }

                    for (String inputString : yaml.getStringList(yamlNode)) {
                        if (inputString.toLowerCase().contains("tag:")) {
                            String tagName = inputString.replace("tag:", "").toLowerCase();
                            Tag<Material> tag = Bukkit.getTag("blocks", NamespacedKey.minecraft(tagName), Material.class);
                            if (tag == null) {
                                this.logger.warning("\"" + inputString + "\" is not a valid material tag set");
                                this.totalWarnings++;
                            } else {
                                list.addAll(tag.getValues());
                            }
                        } else {
                            String materialName = inputString.toUpperCase();
                            Material material;
                            try {
                                material = Material.valueOf(materialName);
                            } catch (IllegalArgumentException exception) {
                                this.logger.warning("\"" + inputString + "\" is not a valid material");
                                this.totalWarnings++;
                                continue;
                            }
                            list.add(material);
                        }
                    }

                }

                configurableSettings.seatBlockMaterials = seatBlockMaterials.isEmpty() ? EnumSet.noneOf(Material.class) : EnumSet.copyOf(seatBlockMaterials);
                configurableSettings.controlBlockMaterials = controlBlockMaterials.isEmpty() ? EnumSet.noneOf(Material.class) : EnumSet.copyOf(controlBlockMaterials);
            }

            configurableSettings.requireControlBlock = yaml.getBoolean("RequireControlBlock");
            configurableSettings.allowWaterloggedSeats = yaml.getBoolean("AllowWaterloggedSeats");
            configurableSettings.allowSeatingWhileFalling = yaml.getBoolean("AllowSeatingWhileFalling");
            configurableSettings.allowSeatingWhileSneaking = yaml.getBoolean("AllowSeatingWhileSneaking");
            configurableSettings.allowSeatingWhileFlying = yaml.getBoolean("AllowSeatingWhileFlying");
            configurableSettings.allowSeatingWhileGliding = yaml.getBoolean("AllowSeatingWhileGliding");
            configurableSettings.allowSeatingInCreativeMode = yaml.getBoolean("AllowSeatingInCreativeMode");
            configurableSettings.allowSeatSwapping = yaml.getBoolean("AllowSeatSwapping");

            if (configurableSettings.requireControlBlock && configurableSettings.controlBlockMaterials.isEmpty()) {
                this.logger.warning("RequireControlBlocks is set to true, but no control block materials have been found");
                this.totalWarnings++;
            }
            if (configurableSettings.seatBlockMaterials.isEmpty()) {
                this.logger.warning("No seat block materials have been found");
                this.totalWarnings++;
            }

        }
    }

    @Override
    public void finalSyncTasks() {
    }

    @Override
    public void finish() {
        final SuperSeatBoi superSeatBoi = (SuperSeatBoi) this.pluginInstance;
        final ConfigurableSettings configurableSettings = superSeatBoi.getConfigurableSettings();

        this.logger.info("Reload completed within " + this.elapsedSeconds + " s. and with " + this.totalWarnings + " warning(s)");
        this.logger.info("+ Loaded " + configurableSettings.controlBlockMaterials.size() + " control block material(s)");
        this.logger.info("+ Loaded " + configurableSettings.seatBlockMaterials.size() + " seat block material(s)");

        notifySubscribers(getSubscriberNotification());

        Bukkit.getPluginManager().callEvent(new SuperSeatBoiPostReloadEvent());
    }

}
