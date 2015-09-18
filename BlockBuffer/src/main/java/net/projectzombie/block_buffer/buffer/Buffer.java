/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.block_buffer.buffer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.projectzombie.block_buffer.main.Main;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;

/**
 *
 * @author jbannon
 */
public class Buffer
{
    
    private Buffer() { /* Do nothing */ }
    
    public static void initialize(final Plugin plugin)
    {
        Utilities.plugin = plugin;
    }
    
    public static String[] getBlockPlacedBuffer() { return Serialize.deserializeBuffer(Main.BLOCK_PLACED_BUFFER); }
    public static String[] getLightPlacedBuffer() { return Serialize.deserializeBuffer(Main.LIGHT_PLACED_BUFFER); }
    public static String[] getBlockBrokeBuffer()  { return Serialize.deserializeBuffer(Main.BLOCK_BROKE_BUFFER);  }
    public static String[] getLightBrokeBuffer()  { return Serialize.deserializeBuffer(Main.LIGHT_BROKE_BUFFER);  }
    
    public static boolean clearBlockPlacedBuffer() { return clearBuffer(Main.BLOCK_PLACED_BUFFER); }
    public static boolean clearLightPlacedBuffer() { return clearBuffer(Main.LIGHT_PLACED_BUFFER); }
    public static boolean clearBlockBrokeBuffer()  { return clearBuffer(Main.BLOCK_BROKE_BUFFER);  }
    public static boolean clearLightBrokeBuffer()  { return clearBuffer(Main.LIGHT_BROKE_BUFFER);  }
    
    public static boolean writeToPlacedBuffer(final Block theBlock)
    {
        return Utilities.isLight(theBlock) ? 
                writeToBuffer(Main.LIGHT_PLACED_BUFFER, theBlock) 
              : writeToBuffer(Main.BLOCK_PLACED_BUFFER, theBlock);
    }
    
    public static boolean writeToBrokeBuffer(final Block theBlock)
    {
        return Utilities.isLight(theBlock) ? 
                writeToBuffer(Main.LIGHT_BROKE_BUFFER, theBlock) 
              : writeToBuffer(Main.BLOCK_BROKE_BUFFER, theBlock);
    }
    
    private static boolean writeToBuffer(final File buffer,
                                         final Block theBlock)
    {
        try
        {
            final FileWriter blockWriter = new FileWriter(buffer, true);  
            blockWriter.append(Serialize.serialize(theBlock));
            blockWriter.close();
            return true;
        }
        catch (Exception ex)
        {
            Logger.getLogger(Buffer.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    private static boolean clearBuffer(final File file)
    {
        try
        {
            final FileOutputStream blockWriter = new FileOutputStream(file);
            blockWriter.write("".getBytes());
            blockWriter.close();
            return true;
        }
        catch (Exception ex)
        {
            Logger.getLogger(Buffer.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
