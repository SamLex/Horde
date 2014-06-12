package uk.samlex.horde.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustByBlockEvent;
import org.bukkit.event.entity.EntityCombustByEntityEvent;
import org.bukkit.event.entity.EntityCombustEvent;

import uk.samlex.horde.config.ConfigStore;
import uk.samlex.horde.config.WorldConfig;

public class EntityBurnHandler implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityBurn(EntityCombustEvent ece) {
        if (ece instanceof EntityCombustByBlockEvent || ece instanceof EntityCombustByEntityEvent) {
            return;
        }

        String worldName = ece.getEntity().getWorld().getName();
        WorldConfig worldConfig = ConfigStore.instance().getWorldConfigMap().get(worldName);

        if (worldConfig.isSunBlock()) {
            ece.setCancelled(true);
        }
    }
}
