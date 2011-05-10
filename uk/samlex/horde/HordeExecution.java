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

import java.util.Random;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

/*
 * Horde Execution Class
 * Made by Sam_Lex, 2011
 */

public class HordeExecution {

	//plugin variable
	public static Horde plugin;


	//plugin variable is instance of plugin
	public HordeExecution(Horde instance){

		plugin = instance;

		spawn();
	}

	//variable to check that players are in server
	private static boolean playerIn = false;

	public void spawn(){

		//gets list of all online players
		Player[] players = plugin.getServer().getOnlinePlayers(); 

		if(players.length == 0){

//			Horde.log.info("Null Pointer Stopper"); //debug print

		}else{

//			Horde.log.info("STARTING SPAWN"); //debug print

			//declare local variables
			int number = HordeDisk.getNumber(), count = 0, rndPlayer = 0, rndX = 0, rndY = 0, rndZ = 0, rndMob = 0, realX = 0, realY = 0, realZ = 0;
			int numberRnd = new Random().nextInt(10 - 0 + 1);
			int distance = HordeDisk.getDistance();
			int mdistance = 0 - distance;

			//more local variables
			int numPlayers = players.length;
			Player luckyPlayer = null;
			Block block;
			World playerWorld = null;

			//variable to let the selection time out if it is taking to long
			int timeout = 0;
			
			//variable to allow a timeout;
			boolean timedout = false;

			//variable for checking spawn location
			boolean spawnLocOK = false;

			//if the number in file is 0, choose a random number.
			if(number == 0){

				number = numberRnd;
			}

//			Horde.log.info("After choose number of times"); //debug print

			//continue doing this process until the selected number of times has been achieved 
			for (count = 0; count <= (number - 1); count++) {

				//chooses a random player to 'pray' on
				if(numPlayers != 1){ 

					rndPlayer = new Random().nextInt((numPlayers + 1));

					luckyPlayer = players[rndPlayer];

				}else if(numPlayers == 1){

					luckyPlayer = players[0];

				}

				//get the players world (does this give multi-world support?)
				playerWorld = luckyPlayer.getWorld();


//				Horde.log.info("Before coordinate select"); //debug print


				do{

					//set variable to false to stop spawn if location is bad
					spawnLocOK = false;

					//selects a random location for the monster to spawn
					rndX = new Random().nextInt((distance - mdistance + 1) + mdistance);
					rndY = new Random().nextInt((distance - mdistance + 1) + mdistance);
					rndZ = new Random().nextInt((distance - mdistance + 1) + mdistance);

					block = luckyPlayer.getLocation().getBlock().getRelative(rndX, rndY, rndZ);

					//checks if the block is suitable for a mob to spawn in
					if(block.getTypeId() == 0){ //block spawn in is air

						if(block.getRelative(0, -1, 0).getTypeId() != 0){ //block below is not air

							if(block.getRelative(0, -2, 0).getTypeId() != 0){ //block two below is not air

								if(block.getRelative(0, 1, 0).getTypeId() == 0){ //block above is air

									if(block.getRelative(0, 2, 0).getTypeId() == 0){ //block two above is air

										if(block.getRelative(1, 0, 0).getTypeId() == 0){ //block infront is air

											if(block.getRelative(2, 0, 0).getTypeId() == 0){ //block two infront is air

												if(block.getRelative(-1, 0, 0).getTypeId() == 0){ //block behind is air

													if(block.getRelative(-2, 0, 0).getTypeId() == 0){ //block two behind is air

														if(block.getRelative(0, 0, 1).getTypeId() == 0){ //block one side is air

															if(block.getRelative(0, 0, 2).getTypeId() == 0){ //block two one side is air

																if(block.getRelative(0, 0, -1).getTypeId() == 0){ //block other side is air

																	if(block.getRelative(0, 0, -2).getTypeId() == 0){ //block two other side is air

																		spawnLocOK = true;
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}

					timeout++;

					if(timeout == 1000){

						timedout = true;

//						Horde.log.info("Timed Out!"); //debug print

						break;
					}

				}while(spawnLocOK == false);

				if(timedout == false) {

//					Horde.log.info("After coordinate select"); //debug print

					//chooses a random monster to spawn
					rndMob = new Random().nextInt((Horde.mob.size()));

					//turns the random coordinates in to a location
					realX = luckyPlayer.getLocation().getBlock().getRelative(rndX, 0, 0).getX();
					realY = luckyPlayer.getLocation().getBlock().getRelative(rndY, 0, 0).getY();
					realZ = luckyPlayer.getLocation().getBlock().getRelative(rndZ, 0, 0).getZ();

					Location spawnloc = new Location(playerWorld, realX, realY, realZ);

					//spawns the randomly selected mob in the random location
					playerWorld.spawnCreature(spawnloc, (Horde.mob.get(rndMob)));

//					Horde.log.info(Horde.getPrefix() + " Spawning Mobs " + number + " " + count); //debug print
				}

//				Horde.log.info("End"); //debug print

			}
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
