/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.custom_interactions.listeners;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import net.projectzombie.custom_interactions.serialize.Serialize;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 *
 * @author jbannon
 */
public class RestoreController
{    
    private static int tickDelay;
    
    public int restoreAll(final Player sender)
    {
        tickDelay = 0;
        final int lightsRestored = this.removePlacedLights(sender);
      
        Utilities.plugin.getServer().getScheduler().scheduleSyncDelayedTask(Utilities.plugin, new Runnable()
        {
            @Override
            public void run()
            {
                final int blocksRestored = restoreBlocks();
                sender.sendMessage("Restored "
                        + (lightsRestored + blocksRestored)
                        + " blocks.");
            }
        }, tickDelay+=40);
        return 1;
    }
    
    /**
     * Iterates through the block_buffer file backwards and restores all blocks.
     * 
     * @return Blocks restored
     */
    private int restoreBlocks()
    {     
        final String[] locations = Utilities.getBlockBuffer();
        if (locations == null)
            return 0;

        for (int i = locations.length - 1; i >= 0; i--)
            Serialize.deserializeAndSet(locations[i]);

        Utilities.clearBlockBuffer();

        return locations.length; 
    }
    
    /**
     * Iterates through the light_buffer file and teleports the command sender
     * to each chunk that contains placed lights, waits 20 ticks, and sets the
     * torch block to air.
     * 
     * @param sender Command sender
     * @return Lights removed
     */
    private int removePlacedLights(final Player sender)
    {
        final HashMap<Chunk, LinkedList<String>> hash = new HashMap<>();
        final Location originalLocation = sender.getLocation();
        final String[] serializedBlocks = Utilities.getLightBuffer();

        if (serializedBlocks == null)
            return 0;
        
        tickDelay = 0;
        Chunk tempChunk;

        for (String block : serializedBlocks)
        {
            tempChunk = Serialize.deserializeGetChunk(block);
            if (hash.get(tempChunk) == null)
                hash.put(tempChunk, new LinkedList<String>());

            hash.get(tempChunk).add(block);
        }

        for (final Map.Entry<Chunk, LinkedList<String>> entry: hash.entrySet())
        {
            Utilities.plugin.getServer().getScheduler().scheduleSyncDelayedTask(Utilities.plugin, new Runnable()
            {
                @Override
                public void run()
                {
                    // Make sure sender is online to actually teleport and remove
                    if (!sender.isOnline())
                        return;

                    sender.teleport(entry.getKey().getBlock(8, 30, 8).getLocation());
                    Utilities.plugin.getServer().getScheduler().scheduleSyncDelayedTask(Utilities.plugin, new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            while (!entry.getValue().isEmpty())
                                Serialize.deserializeAndSet(entry.getValue().removeFirst());
                        }

                    }, 20);
                }
            }, tickDelay);
            tickDelay+=40;
        }

        Utilities.plugin.getServer().getScheduler().scheduleSyncDelayedTask(Utilities.plugin, new Runnable()
        {
            @Override
            public void run()
            {
                sender.teleport(originalLocation);
            }
        }, tickDelay);

        // Make sure sender is online to remove buffer to assure blocks have
        // been properly removed.
        if (sender.isOnline())
            Utilities.clearLightBuffer();
        
        return serializedBlocks.length;
    }
}
