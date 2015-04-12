package breakable_windows;

import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import com.shampaggon.crackshot.events.WeaponScopeEvent;

public class ScopeZoomListener implements Listener {

	private HashMap<Player, ItemStack> helmetList = new HashMap<>();

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
	
	public void disable() {
		for (Entry<Player, ItemStack> entry : helmetList.entrySet()) {
				entry.getKey().getInventory().setHelmet(entry.getValue());
				helmetList.remove(entry.getKey());
		}
	}
	
}
