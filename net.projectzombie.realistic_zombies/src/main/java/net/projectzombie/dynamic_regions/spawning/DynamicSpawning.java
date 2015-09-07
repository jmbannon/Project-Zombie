/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.dynamic_regions.spawning;


import java.util.ArrayList;
import net.projectzombie.dynamic_regions.utilities.Coordinate;
import org.bukkit.Location;
import org.bukkit.block.Block;

/**
 *
 * @author jbannon
 */
public class DynamicSpawning
{
    
    /**
     * Checks rectangle from two given BlockOffset corners relative to playerLocation
     * for valid spawns.
     * 
     * @param playerBlock
     * @param cornerOne
     * @param cornerTwo
     * @return 
     */
    static public ArrayList<Coordinate> getValidSpawnOffsets(final Block playerBlock,
                                                             final Coordinate cornerOne,
                                                             final Coordinate cornerTwo)
    {
        final ArrayList<Coordinate> validSpawns = new ArrayList<>();
        
        Block iterator;
        for (int x = Math.min(cornerOne.getX(), cornerTwo.getX()); x <= Math.max(cornerOne.getX(), cornerTwo.getX()); x++)
            for (int y = Math.min(cornerOne.getY(), cornerTwo.getY()); y <= Math.max(cornerOne.getY(), cornerTwo.getY()); y++)
                for (int z = Math.min(cornerOne.getZ(), cornerTwo.getZ()); z <= Math.max(cornerOne.getZ(), cornerTwo.getZ()); z++)
                {
                    iterator = playerBlock.getRelative(x, y, z);
                    if (isValidSpawn(iterator))
                        validSpawns.add(new Coordinate(x, y, z));
                }
        
        return validSpawns;
    }
    
    /**
     * Returns true if current block and block underneath are air with a solid block underneath both.
     * @param spawnBlock
     * @return 
     */
    static public boolean isValidSpawn(final Block spawnBlock)
    {
        return spawnBlock.isEmpty() 
                && spawnBlock.getRelative(0, -1, 0).isEmpty()
                && !spawnBlock.getRelative(0, -2, 0).isEmpty();
    }
}
