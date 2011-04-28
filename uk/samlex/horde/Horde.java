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
//TODO [FROZEN] Add more monsters to spawn and add directory and add AntiMobSpawn support
import java.util.Timer;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Horde
 * @author Sam_Lex
 */

public class Horde extends JavaPlugin {

	//event listeners
	private final HordeEntityListener entityListener = new HordeEntityListener(this);
	private final HordePlayerListener playerListener = new HordePlayerListener(this);

	//timer
	private Timer hordeTime = new Timer();

	//variable object
	HordeDisk props = new HordeDisk();

	//variable for prefix
	private static String prefix = "";

	//what to do on disable
	@Override
	public void onDisable() {

		//goodbye message
		System.out.println( prefix() + " Disabled, GoodBye!" );
	}

	//what to do on enable
	@Override
	public void onEnable() {	

		//create instance of Plugin Manager
		PluginManager pm = getServer().getPluginManager();

		//register events
		pm.registerEvent(Event.Type.ENTITY_COMBUST, entityListener, Event.Priority.Highest, this);
		pm.registerEvent(Event.Type.PLAYER_JOIN, playerListener, Event.Priority.Monitor, this);
		pm.registerEvent(Event.Type.PLAYER_QUIT, playerListener, Event.Priority.Monitor, this);

		//allows the prefix to be used in scanner
		prefix();

		//get data from file
		props.scanner();

		//activates timer
		hordeTimerCall();

		//welcome message
		System.out.println( prefix() + " Enabled and ready to cause havok!" );
	}

	//make a prefix class for messages
	public String prefix(){

		//get info from plguin.yml
		PluginDescriptionFile desc = this.getDescription();

		//make and return prefix
		prefix = "[" + desc.getName() + " v" + desc.getVersion() + "]";

		return prefix;
	}

	//getter for prefix
	public static String getPrefix() {
		return prefix;
	}

	public void hordeTimerCall(){

		//time variable for timer
		long time = (HordeDisk.getTime() * 1000);

		try{
			
		//sets timer going
		hordeTime.scheduleAtFixedRate((new HordeExecution(this)), time, time);
		
		}catch(NullPointerException e) {}
		
	}
}
