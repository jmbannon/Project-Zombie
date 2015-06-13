/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.listeners;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.projectzombie.serialize.LocationSerialize;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;

/**
 *
 * @author jbannon
 */
public class Utilities
{
    protected static File blockBuffer;
    protected static File lightBuffer;
    public static Plugin plugin;
    
    private Utilities() { /* Do nothing */ }
    
    public static void initialize(final Plugin plugin)
    {
        Utilities.plugin = plugin;
        Utilities.blockBuffer = new File(Utilities.plugin.getDataFolder(), "block_buffer");
        Utilities.lightBuffer = new File(Utilities.plugin.getDataFolder(), "light_buffer");
    }
    
    public static boolean writeToBuffer(final Block theBlock)
    {
        try {
            final FileWriter blockWriter 
                    = new FileWriter(isLight(theBlock) ? lightBuffer : blockBuffer, true);
            
            blockWriter.append(LocationSerialize.serialize(theBlock));
            blockWriter.close();
            return true;
        } catch (Exception ex) {
            Logger.getLogger(BlockListener.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public static String[] getBlockBuffer()
    {
        return getBuffer(Utilities.blockBuffer);
    }
    
    public static String[] getLightBuffer()
    {
        return getBuffer(Utilities.lightBuffer);
    }
    
    public static boolean clearBlockBuffer()
    {
        return clearBuffer(Utilities.blockBuffer);
    }
    
    public static boolean clearLightBuffer()
    {
        return clearBuffer(Utilities.lightBuffer);
    }
    
    private static String[] getBuffer(final File file)
    {
        try
        {
            final BufferedReader reader = new BufferedReader(new FileReader(file));
            final String buffer = reader.readLine();
            reader.close();

            if (buffer == null)
                return null;

            return buffer.split("#");
        } 
        catch (Exception ex)
        {
            Logger.getLogger(BlockListener.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    private static boolean clearBuffer(final File file)
    {
        try
        {
            final FileWriter blockWriter = new FileWriter(file);
            blockWriter.write("");
            blockWriter.close();
            return true;
        }
        catch (Exception ex)
        {
            Logger.getLogger(BlockListener.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
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
                || type.equals(Material.STAINED_GLASS)
                || type.equals(Material.STAINED_GLASS_PANE)
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
