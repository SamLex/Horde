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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;

/*
 * Horde Disk Class
 * Made by Sam_Lex, 2011
 */

public class HordeDisk {

	//variables
	private static long time;
	private static int number;
	private static int distance;
	private static boolean fire;
	
	//scanner method
	public void scanner() {
		
		//creates file object
		final File props = new File("HordeProperties.properties");

		try{
			
			//creates file if it does not exist and puts data in file
			if(props.createNewFile() == true){
				
				//displays message when creating file
				Horde.log.info(Horde.getPrefix() + " Properties file for Horde not found...creating new file");

				//creates writer object 
				final Writer output = new BufferedWriter(new FileWriter(props));
				
				//writes data
				output.write("30\n0\n50\ntrue");
				
				//closes file
				output.close(); 
			}
			
		}catch(IOException e){
			
			//displays message if file could not be created
			Horde.log.info(Horde.getPrefix() + " [WARNING] Could not create properties file for Horde");
		}

		try{
			
			//creates scanner object
			final Scanner diskScanner = new Scanner(props); 

			//gets data from file
			setTime(diskScanner.nextLong());
			setNumber(diskScanner.nextInt());
			setDistance(diskScanner.nextInt());
			setFire(diskScanner.nextBoolean());
			
			//closes file
			diskScanner.close();
			
		}catch(FileNotFoundException e){
			
			//displays message if file could not be found
			Horde.log.info(Horde.getPrefix() + " [WARNING] Horde Properties file could not be found");
		}
		
	}

	//getters and setters for variables
	public static long getTime() {
		return time;
	}

	public static void setTime(long time) {
		HordeDisk.time = time;
	}

	public static int getNumber() {
		return number;
	}

	public static void setNumber(int number) {
		HordeDisk.number = number;
	}

	public static int getDistance() {
		return distance;
	}

	public static void setDistance(int distance) {
		HordeDisk.distance = distance;
	}

	public static boolean isFire() {
		return fire;
	}

	public static void setFire(boolean fire) {
		HordeDisk.fire = fire;
	}
}
