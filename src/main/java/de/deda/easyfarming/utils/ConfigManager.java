package de.deda.easyfarming.utils;

import de.deda.easyfarming.Easyfarming;
import org.bukkit.configuration.file.FileConfiguration;

import java.nio.charset.StandardCharsets;

public class ConfigManager {

    private static FileConfiguration getConfig() {
        return Easyfarming.getPlugin().getConfig();
    }

    public String getString(String path) {
        return new String(getConfig().getString(path).getBytes(), StandardCharsets.UTF_8).replace('&','§');
    }

    public String getPrefixString(String path) {
        return new String(getConfig().getString(path).getBytes(), StandardCharsets.UTF_8).replace('&','§').replace("%PREFIX%", getString("Prefix"));
    }

    public boolean getBoolean(String path) {
        return getConfig().getBoolean(path);
    }

    public Object getObject(String path) {
        return getConfig().get(path);
    }

}
