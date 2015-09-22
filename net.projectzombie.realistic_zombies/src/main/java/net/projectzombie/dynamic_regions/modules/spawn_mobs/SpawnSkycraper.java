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
import net.projectzombie.dynamic_regions.utilities.PlayerTrail;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

/**
 *
 * @author jbannon
 */
public class SpawnSkycraper extends SpawnArea
{
    static private final int
      FLOOR_HEIGHT = 6,
      SPAWN_HEIGHT = 10,
      P_WIDTH      = 20;
    
    static private final Coordinate[] SPAWN_REGION = getVerticalSquare();

    public SpawnSkycraper(final String regionName,
                          final int frequency)
    {
        super(regionName, frequency, 6, null);
    }
    
    @Override
    public LinkedList<Coordinate> getValidSpawns(final Block playerBlock)
    {
        final LinkedList<Coordinate> validSpawns = new LinkedList<>();
        final ArrayList<Player> players = PlayerMethods.getOnlinePlayers();
        
        validSpawns.addAll(getValidSpawnOffsets(playerBlock, SPAWN_REGION[0], SPAWN_REGION[1]));
        
        Iterator<Coordinate> iterator = validSpawns.iterator();
        while (iterator.hasNext())
            if (!isPlayerSafe(playerBlock, players, iterator.next()))
                iterator.remove();
        
        Collections.shuffle(validSpawns);
        return validSpawns;
    }
    
    @Override
    public boolean isPlayerSafe(final Block playerBlock,
                                 final ArrayList<Player> players,
                                 final Coordinate offset)
    { 
        Coordinate coord;
        for (Object coordObject : PlayerTrail.getTrail())
        {
            coord = (Coordinate)coordObject;
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
        
        return (Math.abs(otherPlayerBlock.getX() - playerBlock.getX()) <= P_WIDTH
                && Math.abs(otherPlayerBlock.getZ() - playerBlock.getZ()) <= P_WIDTH
                && (otherPlayerBlock.getY() - playerBlock.getY()) > FLOOR_HEIGHT);
    }
    
    static private Coordinate[] getVerticalSquare()
    {
        final int verticalSum = FLOOR_HEIGHT+SPAWN_HEIGHT;
        
        return new Coordinate[] {
            new Coordinate(-P_WIDTH, FLOOR_HEIGHT, -P_WIDTH),
            new Coordinate( P_WIDTH, verticalSum,   P_WIDTH)
        };
    }
    
    
}
