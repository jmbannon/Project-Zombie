/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.dynamic_regions.utilities;

/**
 *
 * @author jbannon
 */
public class BlockOffset
{
    private final int x, y, z;
    
    public BlockOffset(final int x,
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
    
}