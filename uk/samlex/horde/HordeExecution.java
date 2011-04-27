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

import java.util.ArrayList;
import java.util.Random;
import java.util.TimerTask;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Player;
import java.lang.ArrayIndexOutOfBoundsException;

/**
 * Horde Execution Class
 * @author Sam_Lex
 */

public class HordeExecution extends TimerTask {

	//plugin variable
	public static Horde plugin;


	//plugin variable is instance of plugin
	public HordeExecution(Horde instance){
		plugin = instance;
	}

	//creates list of monsters (not yet filled)
	private static ArrayList<CreatureType> mob = new ArrayList<CreatureType>();

	//variable to check that players are in server
	private static boolean playerIn = false;

	//another check for number of times done
	private static boolean done = false;
	
	//what the timer is to run
	@Override
	public void run() {

		done = false;
		//System.out.println("Run"); //debug print

		//As long as there is a player in the game, start process
		while(isPlayerIn() == true && done == false){

			//declare local variables
			int number = 0, count = 0, rndPlayer = 0, rndX = 0, rndY = 0, rndZ = 0, rndMob = 0, realX = 0, realY = 0, realZ = 0;
			int numberRnd = (int) (Math.random() * 10);
			int distance = HordeDisk.getDistance();
			int mdistance = 0 - distance;
			Player[] players = null;

			//gets list of all online players
			players = plugin.getServer().getOnlinePlayers();

			//more local variables
			int numPlayers = players.length;
			Player luckyPlayer = null;
			boolean midAir, feet, head;
			Block block;

			//fills list with monster types (could be done with enum?)
			mob.add(CreatureType.CREEPER);
			mob.add(CreatureType.SKELETON);
			mob.add(CreatureType.SPIDER);
			mob.add(CreatureType.ZOMBIE);

			//if the number in file is 0, chose a random number. If a number use number
			if(HordeDisk.getNumber() == 0){

				number = numberRnd;
			}else if((HordeDisk.getNumber()) > 0){

				number = HordeDisk.getNumber();
			}

			//			System.out.println("After choose number of times"); //debug print

			//continue doing this process until the selected number of times has been achieved 
			for (count =1; count <= number; count++) {

				//chooses a random player to 'pray' on
				if(numPlayers > 1){

					rndPlayer = new Random().nextInt((numPlayers + 0 + 1 ) + 0);
					try{
					luckyPlayer = players[rndPlayer];
					}catch(ArrayIndexOutOfBoundsException e){}
				}else{
					try{
					luckyPlayer = players[0];
					}catch(ArrayIndexOutOfBoundsException e) {}
				}
				//get the players world (does this give multi-world support?)
				World playerWorld = luckyPlayer.getWorld();

				//				System.out.println("Before coordinate select"); //debug print

				do{

					//selects a random location for the monster to spawn
					rndX = new Random().nextInt((distance - mdistance + 1) + mdistance);
					rndY = new Random().nextInt((distance - mdistance + 1) + mdistance);
					rndZ = new Random().nextInt((distance - mdistance + 1) + mdistance);

					block = luckyPlayer.getLocation().getBlock().getRelative(rndX, rndY, rndZ);

					//checks the block is not in mid air
					if(block.getRelative(0, -1, 0).getType().equals(Material.AIR)){

						midAir = true;
					}else{

						midAir = false;
					}

					//checks that the monster will have head room
					if(block.getRelative(0, 1, 0).getType().equals(Material.AIR)){

						head = true;
					}else{

						head = false;
					}

					//checks the monster is not trying to spawn in anything other than air
					if(block.getType().equals(Material.AIR)){

						feet = true;
					}else{

						feet = false;
					}

				}while(midAir == true || head == false || feet == false);

				//				System.out.println("After coordinate select"); //debug print

				//chooses a random monster to spawn
				rndMob = (int) (Math.random() * 4);

				//turns the random coordinates in to a location
				realX = luckyPlayer.getLocation().getBlock().getRelative(rndX, 0, 0).getX();
				realY = luckyPlayer.getLocation().getBlock().getRelative(rndY, 0, 0).getY();
				realZ = luckyPlayer.getLocation().getBlock().getRelative(rndZ, 0, 0).getZ();
				
				Location spawnloc = new Location(playerWorld, realX, realY, realZ);

				//spawns the randomly selected mob in the random location
				playerWorld.spawnCreature(spawnloc, (mob.get(rndMob)));

				//System.out.println(Horde.getPrefix() + " Spawning Mobs"); //debug print

				//				System.out.println("End"); //debug print
			}
			
			done = true;
		}
	}

	//getter and setter for playerIn
	public static boolean isPlayerIn() {
		return playerIn;
	}

	public static void setPlayerIn(boolean playerIn) {
		HordeExecution.playerIn = playerIn;
	}
}
