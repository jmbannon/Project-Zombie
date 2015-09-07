/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.dynamic_regions.utilities;

import org.bukkit.World;
import org.bukkit.block.Block;

/**
 *
 * @author jbannon
 */
public class Coordinate
{
    private final int x, y, z;
    
    public Coordinate(final int x,
                       final int y,
                       final int z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public int getX() { return x; }
    public int getY() { return y; }
    public int getZ() { return z; }
    
    
    public Block toBlock(final World world)
    {
        return world.getBlockAt(x, y, z);
    }
    
    public Block toBlockOffset(final Block block)
    {
        return block.getRelative(x, y, z);
    }
}