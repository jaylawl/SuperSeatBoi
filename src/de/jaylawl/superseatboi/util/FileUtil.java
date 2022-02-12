package de.jaylawl.superseatboi.util;

import de.jaylawl.superseatboi.SuperSeatBoi;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;

public final class FileUtil {

    private FileUtil() {
    }

    //

    public static boolean createDefaultDataFolder() {
        File dataFolder = SuperSeatBoi.getInstance().getDataFolder();
        if (!dataFolder.exists() || !dataFolder.isDirectory()) {
            return dataFolder.mkdirs();
        }
        return true;
    }

    public static boolean createDefaultConfigFile() {
        File configFile = new File(SuperSeatBoi.getInstance().getDataFolder() + "/config.yml");
        if (!configFile.exists() || !configFile.isFile()) {
            try {
                if (configFile.createNewFile()) {
                    return false;
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return true;
    }

    public static @Nullable File getConfigFile() {
        File configFile = new File(SuperSeatBoi.getInstance().getDataFolder() + "/config.yml");
        if (!configFile.exists() || !configFile.isFile()) {
            return null;
        }
        return configFile;
    }

}
