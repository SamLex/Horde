package uk.samlex.horde;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;

import org.bukkit.plugin.java.JavaPlugin;

import uk.samlex.horde.config.WorldZone;

public class Horde extends JavaPlugin {

    private static Horde INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;
    }

    @Override
    public void onDisable() {

    }

    public static Horde instance() {
        return INSTANCE;
    }

    public void checkDatabase() {
        try {
            getDatabase().find(WorldZone.class).findRowCount();
        } catch (PersistenceException pe) {
            getLogger().info("Installing database due to first time use");
            installDDL();
        }
    }

    @Override
    public List<Class<?>> getDatabaseClasses() {
        ArrayList<Class<?>> dbClasses = new ArrayList<Class<?>>(1);
        dbClasses.add(WorldZone.class);
        return dbClasses;
    }
}
