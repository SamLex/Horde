package uk.samlex.horde.config;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import org.bukkit.entity.EntityType;

public class EntityGroup {

    private HashMap<EntityType, Integer> entityCount;

    public EntityGroup() {
        this.entityCount = new HashMap<EntityType, Integer>();
    }

    public void addToGroup(EntityType type, int number) {
        entityCount.put(type, number);
    }

    public Set<Entry<EntityType, Integer>> getGroup() {
        return entityCount.entrySet();
    }
    
    public static EntityGroup parseGroupString(String groupString) {
        return null;
    }
}
