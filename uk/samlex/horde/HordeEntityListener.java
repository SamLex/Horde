/*
 * This file is part of the Bukkit plugin Horde
 * 
 * Copyright (C) 2011 <euan_hunt@hotmail.co.uk>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package uk.samlex.horde;

import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityListener;

/*
 * Horde Entity Listener
 * Made by Sam_Lex, 2011
 */

public class HordeEntityListener extends EntityListener{

	//variable plugin
	public static Horde plugin;

	//plugin variable is instance of plugin
	public HordeEntityListener(Horde instance){
		plugin = instance;
	}

	//events to catch
	public void onEntityCombust(EntityCombustEvent event){
		if(HordeDisk.isFire() == true){
			
			//stop mobs being set on fire by the sun
			event.setCancelled(true);
		}
	}
}
