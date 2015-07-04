/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.block_buffer.buffer;

import java.io.File;
import java.io.FileWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;

/**
 *
 * @author jbannon
 */
public class Buffer
{
    private static File BLOCK_PLACED_BUFFER;
    private static File LIGHT_PLACED_BUFFER;
    private static File BLOCK_BROKE_BUFFER;
    private static File LIGHT_BROKE_BUFFER;
    
    private Buffer() { /* Do nothing */ }
    
    public static void initialize(final Plugin plugin)
    {
        Utilities.plugin = plugin;
        Buffer.BLOCK_PLACED_BUFFER = new File(Utilities.plugin.getDataFolder(), "BLOCK_PLACED_BUFFER");
        Buffer.LIGHT_PLACED_BUFFER = new File(Utilities.plugin.getDataFolder(), "LIGHT_PLACED_BUFFER");
        Buffer.BLOCK_BROKE_BUFFER  = new File(Utilities.plugin.getDataFolder(), "BLOCK_BROKE_BUFFER");
        Buffer.LIGHT_BROKE_BUFFER  = new File(Utilities.plugin.getDataFolder(), "LIGHT_BROKE_BUFFER");
    }
    
    public static String[] getBlockPlacedBuffer() { return Serialize.deserializeBuffer(Buffer.BLOCK_PLACED_BUFFER); }
    public static String[] getLightPlacedBuffer() { return Serialize.deserializeBuffer(Buffer.LIGHT_PLACED_BUFFER); }
    public static String[] getBlockBrokeBuffer()  { return Serialize.deserializeBuffer(Buffer.BLOCK_BROKE_BUFFER);  }
    public static String[] getLightBrokeBuffer()  { return Serialize.deserializeBuffer(Buffer.LIGHT_BROKE_BUFFER);  }
    
    public static boolean clearBlockPlacedBuffer() { return clearBuffer(Buffer.BLOCK_PLACED_BUFFER); }
    public static boolean clearLightPlacedBuffer() { return clearBuffer(Buffer.LIGHT_PLACED_BUFFER); }
    public static boolean clearBlockBrokeBuffer()  { return clearBuffer(Buffer.BLOCK_BROKE_BUFFER);  }
    public static boolean clearLightBrokeBuffer()  { return clearBuffer(Buffer.LIGHT_BROKE_BUFFER);  }
    
    public static boolean writeToPlacedBuffer(final Block theBlock)
    {
        return Utilities.isLight(theBlock) ? 
                writeToBuffer(LIGHT_PLACED_BUFFER, theBlock) 
              : writeToBuffer(BLOCK_PLACED_BUFFER, theBlock);
    }
    
    public static boolean writeToBrokeBuffer(final Block theBlock)
    {
        return Utilities.isLight(theBlock) ? 
                writeToBuffer(LIGHT_BROKE_BUFFER, theBlock) 
              : writeToBuffer(BLOCK_BROKE_BUFFER, theBlock);
    }
    
    private static boolean writeToBuffer(final File buffer,
                                         final Block theBlock)
    {
        try {
            final FileWriter blockWriter = new FileWriter(buffer, true);  
            blockWriter.append(Serialize.serialize(theBlock));
            blockWriter.close();
            return true;
        } catch (Exception ex) {
            Logger.getLogger(Buffer.class.getName()).log(Level.SEVERE, null, ex);
            return false;
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
            Logger.getLogger(Buffer.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
