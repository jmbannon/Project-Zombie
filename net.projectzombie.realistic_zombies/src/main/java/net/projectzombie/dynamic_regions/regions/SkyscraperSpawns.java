/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.dynamic_regions.regions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import net.projectzombie.dynamic_regions.spawning.DynamicSpawning;
import net.projectzombie.dynamic_regions.spawning.MythicMobType;
import net.projectzombie.dynamic_regions.utilities.Coordinate;
import net.projectzombie.dynamic_regions.utilities.PlayerMethods;
import net.projectzombie.dynamic_regions.utilities.PlayerTrail;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

/**
 *
 * @author jbannon
 */
public class SkyscraperSpawns extends RegionModule
{
    static private final int
      FLOOR_HEIGHT = 6,
      SPAWN_HEIGHT = 10,
      P_WIDTH      = 20;
    
    static private final Coordinate[] SPAWN_REGION = getVerticalSquare();

    public SkyscraperSpawns(final String regionName,
                            final int frequency)
    {
        super(regionName, frequency);
    }
    
    @Override
    public boolean executeModule(Player player) {
        final Block playerBlock = player.getLocation().getBlock();
        final LinkedList<Coordinate> validSpawns = getValidSpawns(playerBlock);
        
        for (int i = 0; i < 10; i++)
            MythicMobType.TEST_ZOMBIE.spawnMythicMob(playerBlock, validSpawns.get(i));
        
        return true;
    }
    
    static private LinkedList<Coordinate> getValidSpawns(final Block playerLoc)
    {
        final LinkedList<Coordinate> validSpawns = new LinkedList<>();
        final ArrayList<Player> players = PlayerMethods.getOnlinePlayers();
        
        validSpawns.addAll(DynamicSpawning.getValidSpawnOffsets(playerLoc, SPAWN_REGION[0], SPAWN_REGION[1]));
        
        for (Coordinate spawn : validSpawns)
            if (!isPlayerSafe(playerLoc, players, spawn))
                validSpawns.remove(spawn);
        
        
        Collections.shuffle(validSpawns);
        
        return validSpawns;
    }
    
    static private boolean isPlayerSafe(final Block playerBlock,
                                        final ArrayList<Player> players,
                                        final Coordinate offset)
    { 
        for (Coordinate coord : PlayerTrail.getTrail())
        {
            if (inPlayerRange(playerBlock, coord.toBlock(playerBlock.getWorld())))
                return false;
        }
        for (Player player : players)
        {
            if (inPlayerRange(playerBlock, player.getLocation().getBlock()))
                return false;
        }
        return true;
    }
    
    static private boolean inPlayerRange(final Block playerBlock,
                                         final Block otherPlayerBlock)
    {
        
        return !(Math.abs(otherPlayerBlock.getX() - playerBlock.getX()) <= P_WIDTH
                && Math.abs(otherPlayerBlock.getZ() - playerBlock.getZ()) <= P_WIDTH
                && (otherPlayerBlock.getY() - playerBlock.getY()) > FLOOR_HEIGHT);
    }
    
    static private Coordinate[] getVerticalSquare()
    {
        final int verticalSum = FLOOR_HEIGHT+SPAWN_HEIGHT;
        
        return new Coordinate[] {
            new Coordinate(-P_WIDTH, FLOOR_HEIGHT, -P_WIDTH),
            new Coordinate (P_WIDTH, verticalSum,   P_WIDTH)
        };
    }
    
    
}
