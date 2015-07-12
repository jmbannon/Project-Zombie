package net.projectzombie.crackshot_enhanced.custom_weapons;

import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import com.shampaggon.crackshot.events.WeaponScopeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.PlayerInventory;

/**
 * 
 * @author Jesse Bannon (jmbannon@uw.edu)
 * 
 * Listens for a scope event from CrackShot guns and places a pumpkin on
 * the player's head to replicate a scope. The player's helmet is saved to a
 * hash map of players and is restored when the player zooms out or when the
 * server is restarted.
 */
public class ScopeZoomListener implements Listener
{
	private final HashMap<Player, ItemStack> helmetList = new HashMap<>();

        /**
         * Places pumpkin on the player's head when zoomed in. Restores helmet
         * when the player zooms out.
         * 
         * @param event CrackShot weapon scope event.
         */
	@EventHandler(priority = EventPriority.NORMAL)
	public void scopeEvent(WeaponScopeEvent event)
        {
            final Player player = event.getPlayer();
            final ItemStack pumpkinItem = new ItemStack(Material.PUMPKIN);
            
            if (event.isZoomIn())
            {
                helmetList.put(player, player.getInventory().getHelmet());	
                player.getInventory().setHelmet(pumpkinItem);
            } 
            else
            {
                for (Entry<Player, ItemStack> entry : helmetList.entrySet())
                {
                    if (entry.getKey().equals(player)) {
                            player.getInventory().setHelmet(entry.getValue());
                            helmetList.remove(player);
                    }
                }
            }
	}
    
    /**
     * Removes the pumpkin if the player attempts to take it off their head
     * while scoped.
     * 
     * @param event Inventory click on the pumpkin. 
     */
    @EventHandler(priority = EventPriority.NORMAL)
    public void RemoveScopeOnClick(InventoryClickEvent event)
    {
        final int slot = event.getSlot();
        final PlayerInventory inv = event.getWhoClicked().getInventory();
        if (slot < 0 || slot >= inv.getSize())
            return;
        
        final ItemStack clickedItem = inv.getItem(slot);
        if (clickedItem != null && clickedItem.getType().equals(Material.PUMPKIN))
            inv.remove(clickedItem);
    }
	
    /**
     * To be ran on disable. Restores any helmets if someone is scoped while
     * the server is disabled.
     */
    public void disable()
    {
        for (Entry<Player, ItemStack> entry : helmetList.entrySet())
        {
            entry.getKey().getInventory().setHelmet(entry.getValue());
            helmetList.remove(entry.getKey());
        }
    }
	
}
