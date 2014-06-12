package uk.samlex.horde;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;

import uk.samlex.bukkitcommon.BukkitPlugin;
import uk.samlex.bukkitcommon.config.WorldZone;
import uk.samlex.horde.config.ConfigStore;

public class Horde extends BukkitPlugin {

    private static Horde INSTANCE;

    public Horde() {
        INSTANCE = this;
    }

    @Override
    public void onEnable() {
        new ConfigStore();
    }

    @Override
    public void onDisable() {

    }

    @Override
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

    public static Horde instance() {
        return INSTANCE;
    }
}
