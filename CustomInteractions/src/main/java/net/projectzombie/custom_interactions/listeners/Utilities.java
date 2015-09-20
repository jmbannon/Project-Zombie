/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.custom_interactions.listeners;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.projectzombie.custom_interactions.serialize.Serialize;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;

/**
 *
 * @author jbannon
 */
public class Utilities
{
    public static Plugin plugin;
    
    private Utilities() { /* Do nothing */ }
    
    public static void initialize(final Plugin plugin)
    {
        Utilities.plugin = plugin;
    }
    
    /**
     * Returns whether the block can be placed or broke.
     * 
     * @param theBlock
     * @return True if it's placable/breakable.
     */
    public static boolean canPlaceBreak(final Block theBlock)
    {
        final Material type = theBlock.getType();
        return (type.equals(Material.TORCH) 
                || type.equals(Material.SOUL_SAND)
                || type.equals(Material.PACKED_ICE)
                || type.equals(Material.QUARTZ_BLOCK)
                || type.equals(Material.HARD_CLAY)
                || type.equals(Material.COAL_BLOCK)
                || type.equals(Material.EMERALD_BLOCK)
                || type.equals(Material.TRAPPED_CHEST));
    }
    
    public static boolean isLight(final Block theBlock)
    {
        return theBlock.getType().equals(Material.TORCH);
    }
}
