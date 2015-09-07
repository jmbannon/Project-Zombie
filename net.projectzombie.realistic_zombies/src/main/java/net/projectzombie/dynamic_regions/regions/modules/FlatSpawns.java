/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.dynamic_regions.regions.modules;

import java.util.Collections;
import java.util.LinkedList;
import net.projectzombie.dynamic_regions.spawning.DynamicSpawning;
import net.projectzombie.dynamic_regions.spawning.MythicMobType;
import net.projectzombie.dynamic_regions.utilities.Coordinate;
import net.projectzombie.dynamic_regions.utilities.PlayerMethods;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

/**
 *
 * @author jbannon
 */
public class FlatSpawns extends RegionModule
{
    static private final int 
      P_WIDTH   = 70,
      Z_WIDTH   = 20,
      Z_HEIGHT  = 5;
    
    static private final Coordinate[] 
      RECT_1 = getHorizontalRectangle(true),
      RECT_2 = getHorizontalRectangle(false),
      RECT_3 = getVerticalRectangle(true),
      RECT_4 = getVerticalRectangle(false);
   
    
    public FlatSpawns(final String regionName)
    {
        super(regionName, 120);
    }

    @Override
    public boolean executeModule(final Player player)
    {
        
        final Block playerBlock = player.getLocation().getBlock();
        final LinkedList<Coordinate> validSpawns = getValidSpawns(playerBlock);
        
        for (int i = 0; i < 10; i++)
            MythicMobType.TEST_ZOMBIE.spawnMythicMob(playerBlock, validSpawns.get(i));
        
        return true;
        
    }
    
    
    static private LinkedList<Coordinate> getValidSpawns(final Block playerLoc)
    {
        final LinkedList<Coordinate> validSpawns = new LinkedList<>();
        
        validSpawns.addAll(DynamicSpawning.getValidSpawnOffsets(playerLoc, RECT_1[0], RECT_1[1]));
        validSpawns.addAll(DynamicSpawning.getValidSpawnOffsets(playerLoc, RECT_2[0], RECT_2[1]));
        validSpawns.addAll(DynamicSpawning.getValidSpawnOffsets(playerLoc, RECT_3[0], RECT_3[1]));
        validSpawns.addAll(DynamicSpawning.getValidSpawnOffsets(playerLoc, RECT_4[0], RECT_4[1]));
        
        final Player players[] = PlayerMethods.getOnlinePlayers();
        
        for (Coordinate spawn : validSpawns)
            if (!isPlayerSafe(playerLoc, players, spawn))
                validSpawns.remove(spawn);
        
        
        Collections.shuffle(validSpawns);
        
        return validSpawns;
    }
    
    static private boolean isPlayerSafe(final Block playerBlock,
                                        final Player players[],
                                        final Coordinate offset)
    {
        final Block blockOffset = offset.toBlockOffset(playerBlock);
        final Location blockLocation = blockOffset.getLocation();
        Location playerLoc;
        
        for (Player player : players)
        {
            playerLoc = player.getLocation();
            if (Math.abs(playerLoc.getBlockY() - blockOffset.getY()) <= Z_HEIGHT
                    && playerLoc.distance(blockLocation) <= P_WIDTH)
                return false;
        }
        return true;
    }
    
    static private Coordinate[] getHorizontalRectangle(final boolean left)
    {
        final int m = left ? 1 : -1;
        final int widthSum = P_WIDTH + Z_WIDTH;
        
        return new Coordinate[] {
            new Coordinate(m*widthSum, 0,       -widthSum),
            new Coordinate(m*P_WIDTH,  Z_HEIGHT, widthSum)
        };
    }
    
    static private Coordinate[] getVerticalRectangle(final boolean top)
    {
        final int m = top ? 1 : -1;
        final int widthSum = P_WIDTH + Z_WIDTH;
        
        return new Coordinate[] {
            new Coordinate(-P_WIDTH, 0,        m*P_WIDTH),
            new Coordinate( P_WIDTH, Z_HEIGHT, m*widthSum)
        };
    }
    
}
