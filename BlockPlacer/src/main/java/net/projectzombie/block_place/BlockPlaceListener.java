/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.block_place;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.projectzombie.block_placer.serialize.LocationSerialize;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.Plugin;

/**
 *
 * @author jbannon
 */
public class BlockPlaceListener implements Listener
{
    private final Plugin plugin;
    private final File blockBuffer;
    
    public BlockPlaceListener(final Plugin plugin)
    {
        this.plugin = plugin;
        blockBuffer = new File(this.plugin.getDataFolder(), "block_buffer");
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void BlockPlaceEvent(BlockPlaceEvent event)
    {
        final FileWriter blockWriter;
        final Player player = event.getPlayer();
        final Block placed = event.getBlockPlaced();
        
        if (player.getGameMode() == GameMode.SURVIVAL
                && !event.isCancelled())
        {
            if (isPlacable(placed.getType()))
            {
                try {
                    blockWriter = new FileWriter(blockBuffer, true);
                    blockWriter.append(LocationSerialize.serialize(placed.getLocation()));
                    blockWriter.close();
                } catch (IOException ex) {
                    Logger.getLogger(BlockPlaceListener.class.getName()).log(Level.SEVERE, null, ex);
                    player.sendMessage("An error has occured. Please consult an admin");
                    event.setCancelled(true);
                }
            } else
            {
                event.setCancelled(true);
            }
        }
    }
    
    public int removePlacedBlocks()
    {
        try {
            final BufferedReader reader = new BufferedReader(new FileReader(blockBuffer));
            final String buffer = reader.readLine();
            reader.close();
            
            if (buffer == null)
                return 0;
            
            final String[] locations = buffer.split("#");
            int restoreCount = 0;
            
            for (String loc : locations) {
                LocationSerialize.deserializeAndSet(loc);
                ++restoreCount;
            }
            
            final FileWriter blockWriter = new FileWriter(blockBuffer);
            blockWriter.write("");
            blockWriter.close();
            return restoreCount;
        } catch (IOException ex) {
            Logger.getLogger(BlockPlaceListener.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }
    
    public boolean isPlacable(Material type)
    {
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
}
