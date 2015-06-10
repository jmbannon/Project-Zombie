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

package net.projectzombie.block_place;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.Plugin;
import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.LocalPlayer;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import net.projectzombie.block_placer.serialize.LocationSerialize;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;


/**
 *
 * @author jbannon
 */
public class BlockPlaceListener implements Listener
{
    private final Plugin plugin;
    private final File blockBuffer;
    private final File lightBuffer;
    private static int tickDelay;

    public BlockPlaceListener(final Plugin plugin)
    {
        this.plugin = plugin;      
        blockBuffer = new File(this.plugin.getDataFolder(), "block_buffer");
        lightBuffer = new File(this.plugin.getDataFolder(), "light_buffer");
    }
    
    /**
     * Allows players to place blocks with material types found within
     * isPlacable method in regions where build flag is allowed.  Does not
     * allow players to place against snow blocks. Torch locations saved in
     * separate file for light updating on restore.
     * 
     * @param event Block Place Event
     */
    @EventHandler(priority = EventPriority.MONITOR)
    public void BlockPlaceEvent(BlockPlaceEvent event)
    {
        final Player player = event.getPlayer();
        final Block blockPlaced = event.getBlockPlaced();
        final FileWriter blockWriter;
        
        LocalPlayer localPlayer = WGBukkit.getPlugin().wrapPlayer(player);
        ApplicableRegionSet set = WGBukkit.getPlugin()
                .getRegionManager(player.getWorld())
                .getApplicableRegions(blockPlaced.getLocation());

        if (player.getGameMode() == GameMode.SURVIVAL
                && canPlaceBreak(blockPlaced.getType())
                && set.testState(localPlayer, DefaultFlag.BUILD) == true)
        {
            if (event.getBlockReplacedState().getType().equals(Material.SNOW))
            {
                player.sendMessage(ChatColor.RED + "Sorry, you cannot place against snow!");
                event.setCancelled(true);
            }
            else
            {
                try 
                {
                    event.setCancelled(false);
                    blockWriter = (isLight(blockPlaced.getType())) ? 
                            new FileWriter(lightBuffer, true) : new FileWriter(blockBuffer, true);
                    
                    blockWriter.append(LocationSerialize.serialize(blockPlaced));
                    blockWriter.close();
                } 
                catch (IOException ex)
                {
                    event.setCancelled(true);
                    Logger.getLogger(BlockPlaceListener.class.getName()).log(Level.SEVERE, null, ex);
                    player.sendMessage("An error has occured. Please consult an admin");

                }
            }
        }
        else if (player.getGameMode() == GameMode.CREATIVE)
            event.setCancelled(false);
        else
        {
            event.setCancelled(true);
        }
    }
    
    /**
     * If a region does not have an owner and it is of the placable block set,
     * let the player bypass the WorldGuard regions to break it.
     * 
     * @param event Block Break Event
     */
    @EventHandler(priority = EventPriority.MONITOR)
    public void BlockBreakEvent(BlockBreakEvent event)
    {
        final Player player = event.getPlayer();      
        final LocalPlayer localPlayer = WGBukkit.getPlugin().wrapPlayer(player);
        final Block theBlock = event.getBlock();
        final ApplicableRegionSet set = WGBukkit
                .getRegionManager(player.getWorld())
                .getApplicableRegions(player.getLocation());
        
        if (canPlaceBreak(event.getBlock().getType())
                && set.testState(localPlayer, DefaultFlag.BUILD) == true)
        {
            // Cancels event, plays glass break sound if light; plank sound otherwise,
            // sets block to air to cancel drop.
            event.setCancelled(true);
            theBlock.getWorld().playEffect(theBlock.getLocation(), 
                    Effect.STEP_SOUND, 
                    isLight(theBlock.getType()) ? Material.GLASS.getId() : Material.WOOD.getId());
            theBlock.setType(Material.AIR);
        }
        else if (player.getGameMode() == GameMode.CREATIVE)
            event.setCancelled(false);
        else
        {
            event.setCancelled(true);
        }
    }
    
    /**
     * Iterates through the block_buffer file and sets all placed block locations
     * to air.
     * @return Blocks removed
     */
    public int removePlacedBlocks()
    {     
        try
        {
            final BufferedReader reader = new BufferedReader(new FileReader(blockBuffer));
            final String buffer = reader.readLine();
            reader.close();
            
            if (buffer == null)
                return 0;
            
            final String[] locations = buffer.split("#");
            int removeCount = 0;
            
            for (String loc : locations) {
                LocationSerialize.deserializeAndSet(loc);
                ++removeCount;
            }
            
            final FileWriter blockWriter = new FileWriter(blockBuffer);
            blockWriter.write("");
            blockWriter.close();
            return removeCount;
        }
        catch (IOException ex)
        {
            Logger.getLogger(BlockPlaceListener.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }
    
    /**
     * Iterates through the light_buffer file and teleports the command sender
     * to each chunk that contains placed lights, waits 20 ticks, and sets the
     * torch block to air.
     * 
     * @param sender Command sender
     * @return Lights removed
     */
    public int removePlacedLights(final Player sender)
    {
        try
        {
            final HashMap<Chunk, LinkedList<String>> hash = new HashMap<>();
            final String[] serializedBlocks;
            final Location originalLocation = sender.getLocation();
            final BufferedReader reader = new BufferedReader(new FileReader(lightBuffer));
            final String buffer = reader.readLine();
            reader.close();
            
            if (buffer == null)
                return 0;
            
            serializedBlocks = buffer.split("#");
            int blockCount = 0;
            tickDelay = 0;
            Chunk tempChunk;
            
            for (String block : serializedBlocks) {
                tempChunk = LocationSerialize.deserializeGetChunk(block);
                if (hash.get(tempChunk) == null)
                    hash.put(tempChunk, new LinkedList<String>());
                
                hash.get(tempChunk).add(block);
                ++blockCount;
            }
            
            for (final Map.Entry<Chunk, LinkedList<String>> entry: hash.entrySet())
            {
                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable()
                {
                    @Override
                    public void run()
                    {
                        // Make sure sender is online to actually teleport and remove
                        if (!sender.isOnline())
                            return;
                        
                        sender.teleport(entry.getKey().getBlock(8, 30, 8).getLocation());
                        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                while (!entry.getValue().isEmpty())
                                    LocationSerialize.deserializeAndSet(entry.getValue().removeFirst());
                            }
                            
                        }, 20);
                    }
                }, tickDelay);
                tickDelay+=40;
            }
            
            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable()
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
            {
                final FileWriter blockWriter = new FileWriter(lightBuffer);
                blockWriter.write("");
                blockWriter.close();
            }
            return blockCount;
        }
        catch (Exception ex)
        {
            Logger.getLogger(BlockPlaceListener.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }
    
    /**
     * Returns whether the block can be placed or broke.
     * 
     * @param type Block material type
     * @return True if it's placable/breakable.
     */
    public static boolean canPlaceBreak(Material type)
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
    
    public static boolean isLight(Material type)
    {
        return type.equals(Material.TORCH);
    }
}
