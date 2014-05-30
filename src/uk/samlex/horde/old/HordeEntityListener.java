/*
 * Horde, a plugin for the Minecraft server modification Bukkit. Provides extra
 * in game creature spawns
 * 
 * Copyright (C) 2011 Euan James Hunter <euanhunter117@gmail.com>
 * 
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any
 * later version. This program is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details. You should have received
 * a copy of the GNU General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */

package uk.samlex.horde.old;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.plugin.Plugin;

public class HordeEntityListener implements Listener {

    private Plugin inst;

    /*
     * Constructor that is passed the instance of the plugin
     */
    protected HordeEntityListener(Plugin plugin) {
        this.inst = plugin;
    }

    /*
     * Handles creatures bursting into flames thanks to the sun. Checks the
     * configuration file and will stop the event if it is to be stopped
     */
    @EventHandler(priority = EventPriority.LOW)
    public void onEntityCombust(EntityCombustEvent ece) {
        if (this.inst.getConfig().contains(ece.getEntity().getLocation().getWorld().getName())) {
            if (!this.inst.getConfig().getBoolean(ece.getEntity().getLocation().getWorld().getName() + ".sunburn")) {
                ece.setCancelled(true);
            }
        } else {
            if (!this.inst.getConfig().getBoolean("Default.sunburn")) {
                ece.setCancelled(true);
            }
        }
    }
}
