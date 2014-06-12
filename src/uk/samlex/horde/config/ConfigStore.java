package uk.samlex.horde.config;

import java.util.HashMap;

import org.bukkit.World;

import uk.samlex.bukkitcommon.config.Config;
import uk.samlex.horde.Horde;

public class ConfigStore extends Config {

    private static ConfigStore INSTANCE;

    public static ConfigStore instance() {
        return INSTANCE;
    }

    private boolean debug = false;

    private HashMap<String, WorldConfig> worldConfigMap;

    public ConfigStore() {
        super(Horde.instance());

        INSTANCE = this;

        Horde.instance().checkDatabase();

        debug = getConfigBoolean("", "debug", debug);
        worldConfigMap = new HashMap<String, WorldConfig>();

        for (World world : Horde.instance().getServer().getWorlds()) {
            worldConfigMap.put(world.getName(), new WorldConfig(world.getName()));
        }

        Horde.instance().saveConfig();
    }

    public HashMap<String, WorldConfig> getWorldConfigMap() {
        return worldConfigMap;
    }

    public boolean isDebug() {
        return debug;
    }

    public void reloadConfig() {
        Horde.instance().reloadConfig();
        super.reloadConfig(Horde.instance());

        debug = getConfigBoolean("", "debug", debug);
        worldConfigMap = new HashMap<String, WorldConfig>();

        for (World world : Horde.instance().getServer().getWorlds()) {
            worldConfigMap.put(world.getName(), new WorldConfig(world.getName()));
        }

        Horde.instance().saveConfig();
    }
}
