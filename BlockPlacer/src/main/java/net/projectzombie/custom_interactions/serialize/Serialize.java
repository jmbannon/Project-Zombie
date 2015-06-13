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

package net.projectzombie.custom_interactions.serialize;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

/**
 *
 * @author jbannon
 */
public class Serialize implements Listener {

    private static final Server server = Bukkit.getServer();

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
        temp.append(',');
        temp.append(block.getX());
        temp.append(',');
        temp.append(block.getY());
        temp.append(',');
        temp.append(block.getZ());
        temp.append(',');
        temp.append(block.getType().toString());
        temp.append(',');
        temp.append(block.getData());
        temp.append('#');

        return temp.toString();
    }
    
    /**
     * Deserializes the string and sets the block in the specified world.
     * @param serializedString
     */
    public static void deserializeAndSet(final String serializedString) {
        String[] parts = serializedString.split(",");
        
        final Block theBlock = server.getWorld(parts[0]).getBlockAt(
                Integer.valueOf(parts[1]), 
                Integer.valueOf(parts[2]), 
                Integer.valueOf(parts[3]));
        
        BlockBreakEvent event = new BlockBreakEvent(theBlock, null);
        theBlock.setType(Material.valueOf(parts[4]));
        theBlock.setData(Byte.valueOf(parts[5]));
    }
    
    /**
     * Deserializes the location and returns its chunk.
     * @param serializedString
     * @return 
     */
    public static Chunk deserializeGetChunk(final String serializedString)
    {
        final String[] parts = serializedString.split(",");
        return server.getWorld(parts[0]).getBlockAt(Integer.valueOf(parts[1]), 
                                                    Integer.valueOf(parts[2]), 
                                                    Integer.valueOf(parts[3]))
                                                    .getChunk();
    }
}
