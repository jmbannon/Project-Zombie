/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gun_decay;

import com.shampaggon.crackshot.events.WeaponShootEvent;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author jbannon
 */
public class ShootListener implements Listener {
    
    @EventHandler(priority = EventPriority.NORMAL)
	public void decayEvent(WeaponShootEvent event) {
        final ItemStack gun = event.getPlayer().getItemInHand();
        ItemMeta temp = gun.getItemMeta();
        
        if (!temp.hasLore())
            return;
        
        List<String> lore = temp.getLore();
        if (isNumeric(lore.get(lore.size()-1))) {
            int decay = Integer.valueOf(lore.get(lore.size()-1)) - 1;
            if (decay < 0) {
                event.getPlayer().getInventory().removeItem(gun);
                event.getPlayer().sendMessage("Your gun has broke!");
                return;
            }
            else
                lore.set(lore.size()-1, String.valueOf(decay));
        } else
            lore.add("45");
        temp.setLore(lore);
        gun.setItemMeta(temp);
            
    }
    
    public static boolean isNumeric(final String str) {
        for (char c : str.toCharArray())
            if (!Character.isDigit(c)) return false;
        return true;
    }
}
