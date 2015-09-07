/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.dynamic_regions.spawning;

import net.projectzombie.dynamic_regions.utilities.Coordinate;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;

/**
 *
 * @author jbannon
 */
public enum MythicMobType
{
    TEST_ZOMBIE("test_zombie");
    
    private final String mobID;
    
    MythicMobType(final String mobID)
    {
        this.mobID = mobID;
    }
    
    public String getID() { return this.mobID; }
    
    public void spawnMythicMob(final Block playerBlock,
                               final Coordinate offset)
    {
        final Block spawnBlock = offset.toBlockOffset(playerBlock);
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), this.spawnMythicMobCommand(spawnBlock));
    }
    
    private String spawnMythicMobCommand(final Block spawnBlock)
    {
        return "mm mobs spawn " + this.mobID + ":0 1 " + MythicMobType.toCoordinate(spawnBlock);
    }
    
    static private String toCoordinate(final Block spawnBlock)
    {
        return spawnBlock.getWorld().getName() 
                + "," + spawnBlock.getX() 
                + "," + spawnBlock.getY() 
                + "," + spawnBlock.getZ();
    }
}
