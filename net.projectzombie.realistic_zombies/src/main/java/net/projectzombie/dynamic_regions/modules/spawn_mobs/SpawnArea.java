/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.dynamic_regions.modules.spawn_mobs;

import com.sk89q.worldguard.protection.managers.RegionManager;
import java.util.ArrayList;
import java.util.LinkedList;
import net.projectzombie.dynamic_regions.modules.RegionModule;
import static net.projectzombie.dynamic_regions.modules.spawn_mobs.CollisionOffsetTests.*;
import net.projectzombie.dynamic_regions.spawning.MythicMobType;
import net.projectzombie.dynamic_regions.utilities.Coordinate;
import net.projectzombie.dynamic_regions.world.DRWorld;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

/**
 *
 * @author jesse
 */
public abstract class SpawnArea extends RegionModule
{
    private final int spawnLimit;
    
    public SpawnArea(final String regionName,
                     final int frequency,
                     final int spawnCount,
                     final MythicMobType[] mobTypes)
    {
        super(regionName, frequency);
        this.spawnLimit = spawnCount;
    }
    
    @Override
    public boolean executeModule(final World world,
                                 final Player player)
    {
        final Block playerBlock = player.getLocation().getBlock();
        final LinkedList<Coordinate> validSpawns = getValidSpawns(playerBlock);
        
        int spawnCount = Math.min(validSpawns.size(), this.spawnLimit);
        int i = 0;
        while (i < spawnCount)
        {
            MythicMobType.TEST_ZOMBIE.spawnMythicMob(playerBlock, validSpawns.get(i));
            DRWorld.getPlugin().getLogger().info("Spawning mob at " + validSpawns.get(i).toString());
            ++i;
        }
        return true;
    }
    
    /**
     * Checks rectangle from two given BlockOffset corners relative to playerLocation
     * for valid spawns.
     * 
     * @param playerBlock
     * @param cornerOne
     * @param cornerTwo
     * @return 
     */
    public ArrayList<Coordinate> getValidSpawnOffsets(final Block playerBlock,
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
                    if (isCollisionSafe(iterator))
                        validSpawns.add(new Coordinate(x, y, z));
                }
        
        return validSpawns;
    }
   
    
    abstract public LinkedList<Coordinate> getValidSpawns(final Block playerBlock);
    
    
    abstract public boolean isPlayerSafe(final Block playerBlock,
                                         final ArrayList<Player> players,
                                         final Coordinate offset);
    
    private boolean isCollisionSafe(final Block spawnBlock)
    {
        return hasSolidGround(spawnBlock) && hasEmptySpace(spawnBlock);
    }
    
    private boolean hasSolidGround(final Block spawnBlock)
    {
        return !spawnBlock.getRelative(0, -2, 0).isEmpty();
    }
    
    private boolean hasEmptySpace(final Block spawnBlock)
    {
        return isEmpty(spawnBlock, COLLISION_RELATIVE_MAIN) &&
                  (isEmpty(spawnBlock, COLLITION_RELATIVE_TEST1)
                || isEmpty(spawnBlock, COLLITION_RELATIVE_TEST2)
                || isEmpty(spawnBlock, COLLITION_RELATIVE_TEST3)
                || isEmpty(spawnBlock, COLLITION_RELATIVE_TEST4));
    }
    
    private boolean isEmpty(final Block spawnBlock,
                           final Coordinate[] emptyOffsets)
    {
        for (Coordinate coord : emptyOffsets)
            if (!coord.toBlockOffset(spawnBlock).isEmpty())
                return false;
        return true;
    }
}
