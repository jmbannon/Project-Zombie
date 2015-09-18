/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.custom_interactions.listeners;

import net.projectzombie.custom_interactions.custom_items.CustomItem;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import net.projectzombie.custom_interactions.custom_items.CustomItemType;
import net.projectzombie.custom_interactions.custom_items.InteractableItem;
import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

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
        final CustomItem customItem = CustomItemType.getCustomItem(inHand);
        
        if (customItem == null
                || inHand == null
                || clickedBlock == null
                || !player.getGameMode().equals(GameMode.SURVIVAL))
            return;
        
        final LocalPlayer localPlayer = WGBukkit.getPlugin().wrapPlayer(player);
        final ApplicableRegionSet set = WGBukkit
                .getRegionManager(clickedBlock.getWorld())
                .getApplicableRegions(clickedBlock.getLocation());
        
        if (set.allows(DefaultFlag.BUILD, localPlayer) == true
                && event.getAction().equals(Action.LEFT_CLICK_BLOCK)
                && customItem instanceof InteractableItem)
        {
            final InteractableItem interactable = (InteractableItem)customItem;
            interactable.blockInteract(player, inHand, clickedBlock);
        }
    }
    
    
}
