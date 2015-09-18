/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.block_buffer.controller;

import net.projectzombie.block_buffer.buffer.Buffer;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import net.projectzombie.block_buffer.buffer.Serialize;
import net.projectzombie.block_buffer.buffer.Utilities;
import net.projectzombie.block_buffer.main.Main;
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
    private static boolean CURRENTLY_RESTORING;
    
    private static int removePlacedBlocks()  { return resetBlocks(Buffer.getBlockPlacedBuffer(), true); }
    private static int restoreBrokenBlocks() { return resetBlocks(Buffer.getBlockBrokeBuffer(), false);  }
    
    private static int removePlacedLights(final Player player)  { return resetLights(player, Buffer.getLightPlacedBuffer(), true); }
    private static int restoreBrokenLights(final Player player) { return resetLights(player, Buffer.getLightBrokeBuffer(), false);  }
    
    public static void restoreAll(final Player sender)
    {
        tickDelay = 0;
        CURRENTLY_RESTORING = true;
        final Location senderLocation = sender.getLocation();
        
        final int placedBlocksRemoved = removePlacedBlocks();
        final int placedLightsRemoved = removePlacedLights(sender);
        final int brokeBlocksRestored = restoreBrokenBlocks();
        final int brokeLightsRestored = restoreBrokenLights(sender);
        
        teleportPlayerBack(sender, senderLocation);
        clearBuffers();
        broadcastMetrics(placedBlocksRemoved,
                         placedLightsRemoved,
                         brokeBlocksRestored,
                         brokeLightsRestored);
    }
    
    /**
     * Iterates through the buffer array file backwards and restores all blocks.
     *
     * @param serializedBlocks Array of serialized blocks from buffer.
     * @return Blocks restored
     */
    private static int resetBlocks(final String[] serializedBlocks,
                                   final boolean toAir)
    {
        if (serializedBlocks == null || serializedBlocks.length == 0)
            return 0;
        
        Utilities.plugin.getServer().getScheduler().scheduleSyncDelayedTask(Utilities.plugin, new Runnable()
        {
            @Override
            public void run()
            {        
                if (CURRENTLY_RESTORING)
                {
                    for (int i = serializedBlocks.length - 1; i >= 0; i--)
                    {
                        if (toAir)
                            Serialize.deserializeAndSetToAir(serializedBlocks[i]);
                        else
                            Serialize.deserializeAndSet(serializedBlocks[i]);
                    }
                }
            }
        }, tickDelay);
        
        return serializedBlocks.length;
    }
    
    /**
     * Iterates through the light_buffer file and teleports the command sender
     * to each chunk that contains placed lights, waits 20 ticks, and sets the
     * torch block to air.
     * 
     * @param sender Command sender
     * @return Lights removed
     */
    private static int resetLights(final Player sender,
                                   final String[] serializedLights,
                                   final boolean toAir)
    {
        final HashMap<Chunk, LinkedList<String>> hash = new HashMap<>();

        if (serializedLights == null 
                || serializedLights.length == 0 
                || !CURRENTLY_RESTORING)
            return 0;

        Chunk tempChunk;
        for (String block : serializedLights)
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
                    {
                        CURRENTLY_RESTORING = false;
                        return;
                    }

                    sender.teleport(entry.getKey().getBlock(8, 30, 8).getLocation());
                    Utilities.plugin.getServer().getScheduler().scheduleSyncDelayedTask(Utilities.plugin, new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            if (CURRENTLY_RESTORING)
                            {
                                while (!entry.getValue().isEmpty())
                                {
                                    if (toAir)
                                        Serialize.deserializeAndSetToAir(entry.getValue().removeFirst());
                                    else
                                        Serialize.deserializeAndSet(entry.getValue().removeFirst());
                                }
                            }
                        }

                    }, 20);
                }
            }, tickDelay);
            tickDelay+=40;
        }
        
        return serializedLights.length;
    }
    
    private static void teleportPlayerBack(final Player player,
                                           final Location originalLocation)
    {
        Utilities.plugin.getServer().getScheduler().scheduleSyncDelayedTask(Utilities.plugin, new Runnable()
        {
            @Override
            public void run()
            {
                if (player.isOnline())
                    player.teleport(originalLocation);
            }
        }, tickDelay);
    }
    
    private static void clearBuffers()
    {
        Utilities.plugin.getServer().getScheduler().scheduleSyncDelayedTask(Utilities.plugin, new Runnable()
        {
            @Override
            public void run()
            {
                if (CURRENTLY_RESTORING)
                {
                    Main.deleteBuffers();
                    Main.createBuffers();
//                    Buffer.clearBlockBrokeBuffer();
//                    Buffer.clearLightBrokeBuffer();
//                    Buffer.clearBlockPlacedBuffer();
//                    Buffer.clearLightPlacedBuffer();
                }
            }
        }, tickDelay);
    }
    
    private static void broadcastMetrics(final int placedBlocksRemoved,
                                         final int placedLightsRemoved,
                                         final int brokenBlocksRestored,
                                         final int brokenLightsRestored)
    {
        Utilities.plugin.getServer().getScheduler().scheduleSyncDelayedTask(Utilities.plugin, new Runnable()
        {
            @Override
            public void run()
            {
                if (CURRENTLY_RESTORING)
                {
                    Utilities.SERVER.broadcastMessage("Block Reset Statistics:");
                    Utilities.SERVER.broadcastMessage("Placed blocks removed:  " + placedBlocksRemoved);
                    Utilities.SERVER.broadcastMessage("Placed lights removed:  " + placedLightsRemoved);
                    Utilities.SERVER.broadcastMessage("Broken blocks restored: " + brokenBlocksRestored);
                    Utilities.SERVER.broadcastMessage("Broken lights restored: " + brokenLightsRestored);
                    CURRENTLY_RESTORING = false;
                }
            }
        }, tickDelay);
    }
    
    public static boolean isRestoring()
    {
        return CURRENTLY_RESTORING;
    }
}
