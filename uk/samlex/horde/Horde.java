/*
 * Horde, a plugin for the Minecraft server modification Bukkit. Provides extra in game creature spawns
 * 
 * Copyright (C) 2011 Euan James Hunter <euanhunter117@gmail.com>
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
//TODO Let the testing begin
package uk.samlex.horde;

import java.util.Iterator;
import java.util.logging.Logger;

import org.bukkit.World;
import org.bukkit.event.Event;
import org.bukkit.plugin.java.JavaPlugin;

public class Horde extends JavaPlugin {

	private HordeEntityListener hel;
	private HordeCommand hc;

	/*
	 * Runs when the plugin is enabled. Instances of the entity listener and the command handler are created
	 * and then registered. The timers used for the spawns are then started. A ready message is then printed
	 */
	@Override
	public void onEnable(){
		hel = new HordeEntityListener(this);
		hc = new HordeCommand(this);

		this.config();
		this.getServer().getPluginManager().registerEvent(Event.Type.ENTITY_COMBUST, hel, Event.Priority.Low, this);
		this.getCommand("horde").setExecutor(hc);
		this.spawnTimers();
		this.print("Enabled. Ready to cause havok");
	}

	/*
	 * Runs just as the plugin is disabled. Cancels the timers to stop them from running after the plugin has been disabled.
	 * Saves any changes to the configuration file to disk and then prints a goodbye message
	 */
	@Override
	public void onDisable(){
		this.getServer().getScheduler().cancelTasks(this);
		this.saveConfig();
		this.print("Disabled. Goodbye");
		hel = null;
		hc = null;
	}

	/*
	 * Provides an easy function to do the printing 
	 */
	private void print(String message){
		Logger.getLogger("Minecraft").info('[' + this.getDescription().getFullName() + "] " + message);
		return;
	}

	/*
	 * Checks that all the worlds loaded are in the configuration file and if not they are added. The configuration file is then saved to disk
	 */
	private void config(){
		this.getConfig().options().copyDefaults(true);

		Iterator<World> iter = this.getServer().getWorlds().iterator();
		while(iter.hasNext()){
			String w = iter.next().getName();
			if(!this.getConfig().contains(w+".timer"))
				this.getConfig().set(w+".timer", 30);
			if(!this.getConfig().contains(w+".number of monsters"))
				this.getConfig().set(w+".number of monsters", 0);
			if(!this.getConfig().contains(w+".random cap"))
				this.getConfig().set(w+".random cap", 10);
			if(!this.getConfig().contains(w+".min distance"))
				this.getConfig().set(w+".min distance", 20);
			if(!this.getConfig().contains(w+".max distance"))
				this.getConfig().set(w+".max distance", 50);
			if(!this.getConfig().contains(w+".sunburn"))
				this.getConfig().set(w+".sunburn", false);
			if(!this.getConfig().contains(w+".time start"))
				this.getConfig().set(w+".time start", 0);
			if(!this.getConfig().contains(w+".time stop"))
				this.getConfig().set(w+".time stop", 24000);
			if(!this.getConfig().contains(w+".server message"))
				this.getConfig().set(w+".server message", "$Player $p_was/were targeted by $Creature on $World");
			if(!this.getConfig().contains(w+".player message"))
				this.getConfig().set(w+".player message", "You have been targeted by a $Creature");
			if(!this.getConfig().contains(w+".creature.blaze"))
				this.getConfig().set(w+".creature.blaze", true);
			if(!this.getConfig().contains(w+".creature.cave spider"))
				this.getConfig().set(w+".creature.cave spider", true);
			if(!this.getConfig().contains(w+".creature.chicken"))
				this.getConfig().set(w+".creature.chicken", true);
			if(!this.getConfig().contains(w+".creature.cow"))
				this.getConfig().set(w+".creature.cow", true);
			if(!this.getConfig().contains(w+".creature.creeper"))
				this.getConfig().set(w+".creature.creeper", true);
			if(!this.getConfig().contains(w+".creature.ender dragon"))
				this.getConfig().set(w+".creature.ender dragon", false);
			if(!this.getConfig().contains(w+".creature.enderman"))
				this.getConfig().set(w+".creature.enderman", true);
			if(!this.getConfig().contains(w+".creature.ghast"))
				this.getConfig().set(w+".creature.ghast", true);
			if(!this.getConfig().contains(w+".creature.giant"))
				this.getConfig().set(w+".creature.giant", true);
			if(!this.getConfig().contains(w+".creature.monster"))
				this.getConfig().set(w+".creature.monster", true);
			if(!this.getConfig().contains(w+".creature.muchroom cow"))
				this.getConfig().set(w+".creature.muchroom cow", true);
			if(!this.getConfig().contains(w+".creature.pig"))
				this.getConfig().set(w+".creature.pig", true);
			if(!this.getConfig().contains(w+".creature.pig zombie"))
				this.getConfig().set(w+".creature.pig zombie", true);
			if(!this.getConfig().contains(w+".creature.sheep"))
				this.getConfig().set(w+".creature.sheep", true);
			if(!this.getConfig().contains(w+".creature.silverfish"))
				this.getConfig().set(w+".creature.silverfish", true);
			if(!this.getConfig().contains(w+".creature.skeleton"))
				this.getConfig().set(w+".creature.skeleton", true);
			if(!this.getConfig().contains(w+".creature.slime"))
				this.getConfig().set(w+".creature.slime", true);
			if(!this.getConfig().contains(w+".creature.spider"))
				this.getConfig().set(w+".creature.spider", true);
			if(!this.getConfig().contains(w+".creature.squid"))
				this.getConfig().set(w+".creature.squid", true);
			if(!this.getConfig().contains(w+".creature.villager"))
				this.getConfig().set(w+".creature.villager", true);
			if(!this.getConfig().contains(w+".creature.wolf"))
				this.getConfig().set(w+".creature.wolf", true);
			if(!this.getConfig().contains(w+".creature.zombie"))
				this.getConfig().set(w+".creature.zombie", true);
			w=null;
		}
		iter=null;
		this.saveConfig();
		return;
	}

	/*
	 * Sets up and starts the timers that execute the spawns
	 */
	private void spawnTimers(){
		HordeSpawner hordeSpawner;
		if(this.getConfig().getBoolean("async")){
			if(this.getConfig().getBoolean("multi")){
				Iterator<World> iter = this.getServer().getWorlds().iterator();
				while(iter.hasNext()){
					World w = iter.next();
					if(this.getConfig().contains(w.getName())){
						int time = this.getConfig().getInt(w.getName()+".timer")*20;
						hordeSpawner = new HordeSpawner(this,w,true);
						this.getServer().getScheduler().scheduleAsyncRepeatingTask(this, hordeSpawner, time, time);
						hordeSpawner = null;
					}else{
						this.print(w.getName() + " not found in configuration file - using defaults for this world");
						int time = this.getConfig().getInt("Default.timer")*20;
						hordeSpawner = new HordeSpawner(this,w,false);
						this.getServer().getScheduler().scheduleAsyncRepeatingTask(this, hordeSpawner, time, time);
						hordeSpawner = null;
					}
					w = null;
				}
				iter = null;
			}else{
				Iterator<World> iter = this.getServer().getWorlds().iterator();
				while(iter.hasNext()){
					World w = iter.next();
					int time = this.getConfig().getInt("Default.timer")*20;
					hordeSpawner = new HordeSpawner(this,w,false);
					this.getServer().getScheduler().scheduleAsyncRepeatingTask(this, hordeSpawner, time, time);
					hordeSpawner = null;
					w = null;
				}
				iter = null;
			}
			return;
		}else{
			if(this.getConfig().getBoolean("multi")){
				Iterator<World> iter = this.getServer().getWorlds().iterator();
				while(iter.hasNext()){
					World w = iter.next();
					if(this.getConfig().contains(w.getName())){
						int time = this.getConfig().getInt(w.getName()+".timer")*20;
						hordeSpawner = new HordeSpawner(this,w,true);
						this.getServer().getScheduler().scheduleSyncRepeatingTask(this, hordeSpawner, time, time);
						hordeSpawner = null;
					}else{
						this.print(w.getName() + " not found in configuration file - using defaults for this world");
						int time = this.getConfig().getInt("Default.timer")*20;
						hordeSpawner = new HordeSpawner(this,w,false);
						this.getServer().getScheduler().scheduleSyncRepeatingTask(this, hordeSpawner, time, time);
						hordeSpawner = null;
					}
					w = null;
				}
				iter = null;
			}else{
				Iterator<World> iter = this.getServer().getWorlds().iterator();
				while(iter.hasNext()){
					World w = iter.next();
					int time = this.getConfig().getInt("Default.timer")*20;
					hordeSpawner = new HordeSpawner(this,w,false);
					this.getServer().getScheduler().scheduleSyncRepeatingTask(this, hordeSpawner, time, time);
					hordeSpawner = null;
					w = null;
				}
				iter = null;
			}
			return;
		}
	}
}
