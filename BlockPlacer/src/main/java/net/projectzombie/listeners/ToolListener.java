/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.listeners;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import static net.projectzombie.listeners.CustomItem.*;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Openable;

/**
 *
 * @author jbannon
 */
public class ToolListener implements Listener
{
    public ToolListener() { /* Do nothing */ }
    
    /**
     * If a player
     * 
     * @param event Block Break Event
     */
    @EventHandler(priority = EventPriority.MONITOR)
    public void toolInteract(PlayerInteractEvent event)
    {
        final Player player = event.getPlayer();
        final ItemStack inHand = player.getItemInHand();
        final Block clickedBlock = event.getClickedBlock();
        
        if (inHand == null || clickedBlock == null)
            return;
        
        final LocalPlayer localPlayer = WGBukkit.getPlugin().wrapPlayer(player);
        final ApplicableRegionSet set = WGBukkit
                .getRegionManager(clickedBlock.getWorld())
                .getApplicableRegions(clickedBlock.getLocation());
        
        if (set.testState(localPlayer, DefaultFlag.BUILD) == true
                && player.getGameMode().equals(GameMode.SURVIVAL)
                && event.getAction().equals(Action.LEFT_CLICK_BLOCK))
        {
            if (WireCutter.isTool(inHand))
                this.wireCutterInteract(player, inHand, clickedBlock);
            else if (Chainsaw.isTool(inHand))
                this.chainsawInteract(player, inHand, clickedBlock);
            else if (SledgeHammer.isTool(inHand))
                this.sledgeHammerInteract(player, inHand, clickedBlock);
        }
    }
    
    public static boolean engraveItem(final Player player,
                                      final String name)
    {
        ItemStack inHand = player.getItemInHand();
        if (player.getInventory().contains(Material.NAME_TAG)
                && inHand != null
                && inHand.hasItemMeta())
        {
            ItemMeta meta = inHand.getItemMeta();
            if (meta.getDisplayName().startsWith(""+ChatColor.YELLOW))
                meta.setDisplayName(ChatColor.YELLOW + name.replace(""+ChatColor.RESET, ""));
            else
                meta.setDisplayName(name.replace(""+ChatColor.RESET, ""));
            
            inHand.setItemMeta(meta);
            player.getInventory().remove(Material.NAME_TAG);
            return true;
        } else
            return false;
    }
    
    private boolean sledgeHammerInteract(final Player player,
                                         final ItemStack inHand,
                                         final Block clickedBlock)
    {
        if (SledgeHammer.canUse(clickedBlock) > 0)
        {
            breakWithTool(SledgeHammer, player, inHand, clickedBlock);
            return true;
        }
        else
            return false;
    }
    
    private boolean chainsawInteract(final Player player,
                                     final ItemStack inHand,
                                     final Block clickedBlock)
    {
        if (Chainsaw.canUse(clickedBlock) == 1)
        {
            breakWithTool(Chainsaw, player, inHand, clickedBlock);
            return true;
        }
        else
            return false;
    }
    
    private boolean wireCutterInteract(final Player player,
                                       final ItemStack inHand,
                                       final Block clickedBlock)
    {
        if (WireCutter.canUse(clickedBlock) == 1)
        {
            breakWithTool(WireCutter, player, inHand, clickedBlock);
        }
        else if (WireCutter.canUse(clickedBlock) == 2)
        {
            this.tempOpenDoor(clickedBlock);
            WireCutter.decreaseDurability(inHand);
        }
        else
            return false;
        
        // If it interacted with the clicked block.
        return true;
    }
    
    private void breakWithTool(final CustomItem tool,
                               final Player player,
                               final ItemStack inHand,
                               final Block clickedBlock)
    {
        if (Utilities.writeToBuffer(clickedBlock))
        {
            if (tool.breakBlock(inHand, clickedBlock))
                player.getInventory().remove(inHand);
        }
        else
            player.sendMessage("An error has occured. Please consult an admin.");
    }
    
    private void tempOpenDoor(final Block clickedBlock)
    {
        final Block door = clickedBlock.getRelative(BlockFace.DOWN).getType().equals(Material.IRON_DOOR_BLOCK) ?
                    clickedBlock.getRelative(BlockFace.DOWN) : clickedBlock;
        final BlockState doorState = door.getState();
        final MaterialData doorData = doorState.getData();
        final Openable opn = (Openable) doorData;

        if (opn.isOpen())
            return;

        opn.setOpen(true);
        doorState.setData(doorData);
        doorState.update();

        Utilities.plugin.getServer().getScheduler().scheduleSyncDelayedTask(Utilities.plugin, new Runnable()
        {
            @Override
            public void run()
            {
                opn.setOpen(false);
                doorState.setData(doorData);
                doorState.update();
            }
        }, 120);
    }
}
