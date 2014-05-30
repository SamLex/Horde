package uk.samlex.horde.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.World;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.Vector;

import uk.samlex.horde.Horde;
import uk.samlex.horde.config.WorldZone;

public abstract class GenericCommand implements CommandExecutor, TabCompleter {

    protected static List<String> checkPartialArgument(String partialArgument, String[] possiblities) {
        ArrayList<String> morePossible = new ArrayList<>();
        for (String s : possiblities) {
            if (partialArgument.length() < s.length())
                if (partialArgument.startsWith(s.substring(0, partialArgument.length())))
                    morePossible.add(s);
        }
        return morePossible;
    }

    protected static String[] getWorldNames() {
        World[] worlds = Horde.instance().getServer().getWorlds().toArray(new World[0]);
        String[] worldNames = new String[worlds.length];

        for (int i = 0; i < worldNames.length; i++) {
            worldNames[i] = worlds[i].getName();
        }

        return worldNames;
    }
    
    protected static String[] getZoneNames(String worldName) {
        WorldZone[] zones = Horde.instance().getDatabase().find(WorldZone.class).where().ieq("worldName", worldName).findList().toArray(new WorldZone[0]);
        String[] zoneNames = new String[zones.length];

        for (int i = 0; i < zoneNames.length; i++) {
            zoneNames[i] = zones[i].getZoneName();
        }
        
        return zoneNames;
    }
    
    protected static String blockVectorString(Vector blockVector) {
        return String.format("%d,%d,%d",  blockVector.getBlockX(), blockVector.getBlockY(), blockVector.getBlockZ());
    }
}
