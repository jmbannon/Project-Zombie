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

package net.projectzombie.custom_interactions.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.LocalPlayer;
import net.projectzombie.block_buffer.buffer.Buffer;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;


/**
 *
 * @author jbannon
 */
public class BlockListener implements Listener
{
    

    public BlockListener() { /* Do nothing */ }
    
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
        
        LocalPlayer localPlayer = WGBukkit.getPlugin().wrapPlayer(player);
        ApplicableRegionSet set = WGBukkit.getPlugin()
                .getRegionManager(player.getWorld())
                .getApplicableRegions(blockPlaced.getLocation());

        if (player.getGameMode() == GameMode.SURVIVAL
                && Utilities.canPlaceBreak(blockPlaced)
                && set.testState(localPlayer, DefaultFlag.BUILD))
        {
            final Material replaced = event.getBlockReplacedState().getType();
            if (!replaced.isSolid() && !replaced.equals(Material.AIR))
            {
                player.sendMessage(ChatColor.RED + "Sorry, you cannot place a block against that!");
                event.setCancelled(true);
            }
            else
            {
                boolean wroteSuccessfully = Buffer.writeToPlacedBuffer(event.getBlock());
                
                if (!wroteSuccessfully)
                {
                    event.setCancelled(true);
                    player.sendMessage("An error has occured. Please consult an admin.");
                }
            }
        }
        else if (player.getGameMode() == GameMode.CREATIVE)
            event.setCancelled(false);
        else
            event.setCancelled(true);
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
                .getRegionManager(theBlock.getWorld())
                .getApplicableRegions(theBlock.getLocation());
        
        if (Utilities.canPlaceBreak(theBlock)
                && set.testState(localPlayer, DefaultFlag.BUILD) == true
                && player.getGameMode().equals(GameMode.SURVIVAL))
        {
            // Cancels event, plays glass break sound if light; plank sound otherwise,
            // sets block to air to cancel drop.
            event.setCancelled(true);
            theBlock.getWorld().playEffect(theBlock.getLocation(), 
                    Effect.STEP_SOUND, 
                    Utilities.isLight(theBlock) ? Material.GLASS.getId() : Material.WOOD.getId());
            theBlock.setType(Material.AIR);
        }
        else if (player.getGameMode().equals(GameMode.CREATIVE))
            event.setCancelled(false);
        else
            event.setCancelled(true);
    }    
}
