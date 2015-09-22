/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.dynamic_regions.modules.spawn_mobs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import net.projectzombie.dynamic_regions.utilities.Coordinate;
import net.projectzombie.dynamic_regions.utilities.PlayerMethods;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

/**
 *
 * @author jbannon
 */
public class SpawnFlat extends SpawnArea
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
   
    
    public SpawnFlat(final String regionName,
                     final int frequency)
    {
        super(regionName, frequency, 16, null);
    }
    
    
    @Override
    public LinkedList<Coordinate> getValidSpawns(final Block playerBlock)
    {
        final LinkedList<Coordinate> validSpawns = new LinkedList<>();
        final ArrayList<Player> players = PlayerMethods.getOnlinePlayers();
        
        validSpawns.addAll(getValidSpawnOffsets(playerBlock, RECT_1[0], RECT_1[1]));
        validSpawns.addAll(getValidSpawnOffsets(playerBlock, RECT_2[0], RECT_2[1]));
        validSpawns.addAll(getValidSpawnOffsets(playerBlock, RECT_3[0], RECT_3[1]));
        validSpawns.addAll(getValidSpawnOffsets(playerBlock, RECT_4[0], RECT_4[1]));
        
        Iterator<Coordinate> iterator = validSpawns.iterator();
        while (iterator.hasNext())
        {
            if (!isPlayerSafe(playerBlock, players, iterator.next()))
                iterator.remove();
        }
        
        Collections.shuffle(validSpawns);
        
        return validSpawns;
    }
    
    @Override
    public boolean isPlayerSafe(final Block playerBlock,
                                final ArrayList<Player> players,
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
