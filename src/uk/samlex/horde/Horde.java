package uk.samlex.horde;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;

import uk.samlex.bukkitcommon.BukkitPlugin;
import uk.samlex.bukkitcommon.command.ConfigReloadCommand;
import uk.samlex.bukkitcommon.zone.command.ZoneCreateCommand;
import uk.samlex.bukkitcommon.zone.command.ZoneListCommand;
import uk.samlex.bukkitcommon.zone.command.ZonePreviewCommand;
import uk.samlex.bukkitcommon.zone.command.ZoneRemoveCommand;
import uk.samlex.bukkitcommon.zone.command.ZoneSetCommand;
import uk.samlex.bukkitcommon.zone.config.WorldZone;
import uk.samlex.horde.config.ConfigStore;

public class Horde extends BukkitPlugin {

    private static Horde INSTANCE;

    public Horde() {
        INSTANCE = this;
    }

    @Override
    public void onEnable() {
        new ConfigStore();

        new ZoneCreateCommand(instance(), "horde-create");
        new ZoneListCommand(instance(), "horde-list");
        new ZonePreviewCommand(instance(), "horde-preview");
        new ConfigReloadCommand(instance(), "horde-reload");
        new ZoneRemoveCommand(instance(), "horde-remove");
        new ZoneSetCommand(instance(), "horde-set");
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

    @Override
    public void reloadConfig() {
        ConfigStore.instance().reloadConfig();
    }
}
