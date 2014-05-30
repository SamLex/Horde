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

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class HordeSpawner implements Runnable {
    // http://www.minecraftwiki.net/wiki/Spawn#Mob_spawning
    // http://www.minecraftwiki.net/wiki/File:Spawning_requirements.png
    private Plugin inst;
    private World world;
    private String worldName;
    private ArrayList<EntityType> mobs;
    private Random rand;
    private Player target;
    private boolean skip;
    private int xCooSet, yCooSet, zCooSet, numMob, max, min, trig, temp;
    private Block spawn;
    private EntityType spawnedCreature;
    private ArrayList<String> players, creatures;

    /*
     * Constructor that is passed the instance of the plugin, the world this
     * timer is going to be working on and whether the default setting shouldn't
     * be used
     */
    protected HordeSpawner(Plugin plugin, World w, boolean multi) {
        this.inst = plugin;
        this.world = w;
        if (multi)
            this.worldName = w.getName();
        else
            this.worldName = "Default";
        this.mobsList();
    }

    /*
     * Randomly spawns a number of creature in a random location near a random
     * player
     */
    public void run() {

        if (world.getPlayers().size() != 0) {
            rand = new Random(System.currentTimeMillis());
            skip = false;
            max = this.inst.getConfig().getInt(worldName + ".max distance");
            min = this.inst.getConfig().getInt(worldName + ".min distance");
            players = new ArrayList<String>(0);
            creatures = new ArrayList<String>(0);

            if (world.getTime() >= this.inst.getConfig().getInt(worldName + ".time start") && world.getTime() <= this.inst.getConfig().getInt(worldName + ".time stop")) {
                if (this.inst.getConfig().getInt(worldName + ".number of monsters") == 0)
                    numMob = rand.nextInt(this.inst.getConfig().getInt(worldName + ".random cap") + 1);
                else
                    numMob = this.inst.getConfig().getInt(worldName + ".number of monsters");
                for (int count = 0; count < numMob; count++) {
                    if (this.inst.getConfig().getBoolean("async")) {
                        while (true) {
                            skip = false;

                            target = world.getPlayers().get(rand.nextInt(world.getPlayers().size()));

                            xCooSet = (rand.nextBoolean() ? 1 : -1) * (rand.nextInt(max - min + 1) + min);
                            temp = (rand.nextInt(max - min + 1) + min);
                            trig = (int) Math.sqrt((temp * temp) - (xCooSet * xCooSet));
                            yCooSet = (rand.nextBoolean() ? 1 : -1) * trig;
                            temp = (rand.nextInt(max - min + 1) + min);
                            trig = (int) Math.sqrt((temp * temp) - (xCooSet * xCooSet));
                            zCooSet = (rand.nextBoolean() ? 1 : -1) * trig;

                            spawn = target.getLocation().getBlock().getRelative(xCooSet, yCooSet, zCooSet);
                            if (this.inst.getConfig().getString("checks").equals("high")) {
                                if (spawn.getType().equals(Material.AIR) && !spawn.getRelative(BlockFace.DOWN).getType().equals(Material.AIR) && !spawn.getRelative(BlockFace.UP).getType().equals(Material.AIR) && !spawn.getRelative(BlockFace.UP, 2).getType().equals(Material.AIR))
                                    break;
                            } else if (this.inst.getConfig().getString("checks").equals("low")) {
                                if (spawn.getTypeId() == 0 && spawn.getRelative(BlockFace.DOWN).getType().equals(Material.AIR))
                                    break;
                            } else if (this.inst.getConfig().getString("checks").equals("none")) {
                                break;
                            } else {
                                skip = true;
                                break;
                            }
                        }
                    } else {
                        for (byte tryCount = 0; tryCount <= 89; tryCount++) {
                            skip = false;

                            target = world.getPlayers().get(rand.nextInt(world.getPlayers().size()));

                            xCooSet = (rand.nextBoolean() ? 1 : -1) * (rand.nextInt(max - min + 1) + min);
                            temp = (rand.nextInt(max - min + 1) + min);
                            trig = (int) Math.sqrt((temp * temp) - (xCooSet * xCooSet));
                            yCooSet = (rand.nextBoolean() ? 1 : -1) * trig;
                            temp = (rand.nextInt(max - min + 1) + min);
                            trig = (int) Math.sqrt((temp * temp) - (xCooSet * xCooSet));
                            zCooSet = (rand.nextBoolean() ? 1 : -1) * trig;

                            spawn = target.getLocation().getBlock().getRelative(xCooSet, yCooSet, zCooSet);
                            if (this.inst.getConfig().getString("checks").equals("high")) {
                                if (spawn.getType().equals(Material.AIR) && !spawn.getRelative(BlockFace.DOWN).getType().equals(Material.AIR) && !spawn.getRelative(BlockFace.UP).getType().equals(Material.AIR) && !spawn.getRelative(BlockFace.UP, 2).getType().equals(Material.AIR))
                                    break;
                            } else if (this.inst.getConfig().getString("checks").equals("low")) {
                                if (spawn.getTypeId() == 0 && spawn.getRelative(BlockFace.DOWN).getType().equals(Material.AIR))
                                    break;
                            } else if (this.inst.getConfig().getString("checks").equals("none")) {
                                break;
                            } else {
                                skip = true;
                                break;
                            }
                        }
                    }
                    if (!skip) {
                        spawnedCreature = this.mobs.get(rand.nextInt(mobs.size()));
                        if (world.spawnEntity(spawn.getLocation(), spawnedCreature) != null) {
                            players.add(target.getName());
                            creatures.add(spawnedCreature.getName());
                            playerMessageSender(this.inst.getConfig().getString(worldName + ".player message"), spawnedCreature.getName(), target);
                        }
                    }
                }
            }
            serverMessageSender(this.inst.getConfig().getString(worldName + ".server message"), players, creatures, worldName);
        }
    }

    /*
     * Handles messages to be broadcast to the server on a Horde attack
     */
    private void serverMessageSender(String message, ArrayList<String> players, ArrayList<String> creatures, String spawnWorld) {
        if (message.length() * players.size() * creatures.size() == 0) {
            return;
        }

        String player = "", creature = "";
        ArrayList<String> playerTempHolder = new ArrayList<String>(0);

        for (String playerName : players) {
            if (!playerTempHolder.contains(playerName)) {
                playerTempHolder.add(playerName);
            }
        }

        int tempCount = 0;
        for (String playerName : playerTempHolder) {
            player += playerName + ", ";
            tempCount++;
        }

        tempCount = 0;
        for (String creatureName : creatures) {
            int count = 0;
            if (!creatureName.equals("!123456789!")) {
                while (creatures.contains(creatureName)) {
                    creatures.set(creatures.indexOf(creatureName), "!123456789!");
                    count++;
                }
                creature += count + " " + creatureName + (count == 1 ? "" : "s") + ", ";
            }
            tempCount++;
        }

        String tempString = "";
        player = player.substring(0, player.lastIndexOf(", "));
        if (player.lastIndexOf(", ") != -1) {
            tempString = player.substring(player.lastIndexOf(", "), player.length());
            player = player.replace(tempString, "");
            player += " and";
            tempString = tempString.trim();
            tempString = tempString.replace(",", "");
            player += tempString;
        }

        tempString = "";
        creature = creature.substring(0, creature.lastIndexOf(", "));
        if (creature.lastIndexOf(", ") != -1) {
            tempString = creature.substring(creature.lastIndexOf(", "), creature.length());
            creature = creature.replace(tempString, "");
            creature += " and";
            tempString = tempString.trim();
            tempString = tempString.replace(",", "");
            creature += tempString;
        }

        message = message.replace("$Player", player).replace("$Creature", creature).replace("$World", spawnWorld).replace("$p_was/were", (playerTempHolder.size() == 1 ? "was" : "were")).replace("$c_was/were", (tempCount == 1 ? "was" : "were"));

        this.inst.getServer().broadcastMessage(ChatColor.DARK_RED + message);

        return;
    }

    /*
     * Handles the message to be sent to the player after each creature spawn
     */
    private void playerMessageSender(String message, String creature, Player target) {
        if (message.length() * creature.length() == 0 || target == null) {
            return;
        }

        message = message.replace("$Player", target.getName()).replace("$Creature", creature);
        target.sendMessage(ChatColor.RED + message);

        return;
    }

    /*
     * Populates the list of creatures that this timer is allowed to spawn
     */
    private void mobsList() {
        this.mobs = new ArrayList<EntityType>(0);
        for (EntityType creature : EntityType.values()) {
            String name = creature.getName();
            if (name != null)
                if (this.inst.getConfig().getBoolean(worldName + ".creature." + name.toLowerCase())) {
                    this.mobs.add(creature);
                }
        }
        return;
    }
}
