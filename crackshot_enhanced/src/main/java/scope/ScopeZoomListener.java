package scope;

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

public class ScopeZoomListener implements Listener {

	private final HashMap<Player, ItemStack> helmetList = new HashMap<>();

	@EventHandler(priority = EventPriority.NORMAL)
	public void scopeEvent(WeaponScopeEvent event) {
		final Player playerTemp = event.getPlayer();
		final ItemStack pumpkinItem = new ItemStack(Material.PUMPKIN);
		
		if (event.isZoomIn()) {
			helmetList.put(playerTemp, playerTemp.getInventory().getHelmet());	
			playerTemp.getInventory().setHelmet(pumpkinItem);
		} 
		else {
			for (Entry<Player, ItemStack> entry : helmetList.entrySet()) {
				if (entry.getKey().equals(playerTemp)) {
					playerTemp.getInventory().setHelmet(entry.getValue());
					helmetList.remove(playerTemp);
				}
			}
		}
	}
    
    @EventHandler(priority = EventPriority.NORMAL)
    public void RemoveScopeOnClick(InventoryClickEvent event)
    {
        final int slot = event.getSlot();
        final PlayerInventory inv = event.getWhoClicked().getInventory();
        final ItemStack clickedItem = inv.getItem(slot);
        if (clickedItem.getType() == Material.PUMPKIN)
            inv.remove(clickedItem);
    }
	
	public void disable()
    {
		for (Entry<Player, ItemStack> entry : helmetList.entrySet()) {
				entry.getKey().getInventory().setHelmet(entry.getValue());
				helmetList.remove(entry.getKey());
		}
	}
	
}
