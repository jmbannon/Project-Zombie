/*
 * BlockPlacer
 *
 * Version:     0.5
 * MC Build:    1.8.3
 * Date:        06-10-2015
 *
 * Authur:      Jesse Bannon
 * Server:      Project Zombie
 * Website:     www.projectzombie.net
 * 
 * Allows players to place and break particular blocks within a WorldGuard
 * region whos build flag is set to allowed. Stores these blocks within two
 * buffers: blocks and lights. Restores all blocks by iterating through the
 * buffer and setting the blocks to air.  For light blocks, a player must
 * send the command to be able to teleport to each light block location to
 * remove it allowing light to update correctly.
 *
 */

package net.projectzombie.block_buffer.buffer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.Listener;

/**
 *
 * @author jbannon
 */
public class Serialize implements Listener {

    
    protected static final String INFO_SEPERATOR = "`";
    protected static final String BLOCK_SEPERATOR = "#";
    private Serialize() { /* Do nothing */ }
    
    /**
     * Serializes blocks in the form of "world_name,x,y,z,type,data,\n"
     *
     * @param block
     * @return 
     */
    public static String serialize(final Block block) {
        final StringBuilder temp = new StringBuilder();
        
        temp.append(block.getWorld().getName());
        temp.append(INFO_SEPERATOR);
        temp.append(block.getX());
        temp.append(INFO_SEPERATOR);
        temp.append(block.getY());
        temp.append(INFO_SEPERATOR);
        temp.append(block.getZ());
        temp.append(INFO_SEPERATOR);
        temp.append(block.getType().toString());
        temp.append(INFO_SEPERATOR);
        temp.append(block.getData());
        temp.append(BLOCK_SEPERATOR);

        return temp.toString();
    }
    
    /**
     * Deserializes the string and sets the block in the specified world.
     * @param serializedString
     */
    public static void deserializeAndSet(final String serializedString) {
        String[] parts = serializedString.split(INFO_SEPERATOR);
        
        final Block theBlock = deserializeGetBlock(parts);
        
        theBlock.setType(Material.valueOf(parts[4]));
        theBlock.setData(Byte.valueOf(parts[5]));
    }
    
    public static void deserializeAndSetToAir(final String serializedString) {
        String[] parts = serializedString.split(INFO_SEPERATOR);
        
        final Block theBlock = deserializeGetBlock(parts);
        theBlock.setType(Material.AIR);
    }
    
    /**
     * Deserializes the location and returns its chunk.
     * @param serializedString
     * @return 
     */
    public static Chunk deserializeGetChunk(final String serializedString)
    {
        final String[] parts = serializedString.split(INFO_SEPERATOR);
        return Utilities.SERVER.getWorld(parts[0]).getBlockAt(Integer.valueOf(parts[1]), 
                                                    Integer.valueOf(parts[2]), 
                                                    Integer.valueOf(parts[3]))
                                                    .getChunk();
    }
    
    public static String[] deserializeBuffer(final File file)
    {
        try
        {
            final BufferedReader reader = new BufferedReader(new FileReader(file));
            final String buffer = reader.readLine();
            reader.close();

            if (buffer == null)
                return null;

            return buffer.split(BLOCK_SEPERATOR);
        } 
        catch (Exception ex)
        {
            Logger.getLogger(Buffer.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    static private Block deserializeGetBlock(final String[] parts)
    {
        return Utilities.SERVER.getWorld(parts[0]).getBlockAt(
                Integer.valueOf(parts[1]), 
                Integer.valueOf(parts[2]), 
                Integer.valueOf(parts[3]));
    }
}
