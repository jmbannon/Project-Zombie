/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons;

import com.shampaggon.crackshot.events.WeaponPreShootEvent;
import java.util.List;
import net.projectzombie.crackshot_enhanced.custom_weapons.utilities.CrackshotLore;
import static net.projectzombie.crackshot_enhanced.custom_weapons.utilities.CrackshotLore.LORE_SIZE;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author Jesse Bannon (jmbannon@uw.edu)
 * 
 * Gun decay happens within the lore of the item of the weapon. There are two
 * lines that the listener reads from to determine gun durability and accuracy.
 * 
 * These lines appear to be invisible from the player's perspective, but
 * are actually converted to HEX and have the chat symbol placed in front
 * of them to act as colors with no text. These lines are encoded and decoded
 * through the HiddenStringUtils class.
 * 
 * Durability lore string:
 * PZ==durability==maxDurability==ConditionType==BuildType
 * 
 * Stat lore string:
 * PZ==gunType==CSBulletSpread==ScopeType==FireModeType
 * 
 */
public class ShootListener implements Listener
{   
    public ShootListener() { /* Do nothing */ }
    
    /**
     * Right before the weapon fires, method will check to see if custom 
     * durability has been initialized within the lore. Initializes it if it
     * has not, then decrements the durability by one.
     * 
     * @param event Event before the weapon fires. 
     */
    @EventHandler(priority = EventPriority.HIGH)
    public void preDecayEvent(WeaponPreShootEvent event)
    {
        final Player shooter = event.getPlayer();
        final double bulletSpread = shoot(event.getBulletSpread(), shooter.getItemInHand());
        
        if (bulletSpread == -2)
        {
            shooter.sendMessage("A parsing error has occured. Please consult an admin.");
            event.setCancelled(true);
        }
        else if (bulletSpread < 0)
        {
            shooter.sendMessage("Weapon's configuration is not encrypted. Please consult an admin.");
        }
        else if (bulletSpread == 0)
        {
            shooter.sendMessage("Your " + event.getWeaponTitle() + " is broken. Repair it at the gunsmith");
            event.setCancelled(true);
        }
        else
            event.setBulletSpread(bulletSpread);
    }
    
    /**
     * 
     * @param eventBulletSpread
     * @param item
     * @return -2 - An error occurred in the parsing.
     *         -1 - The weapon's lore is not encrypted
     *          0 - The weapon is broken
     *         >0 - The bullet spread to set for the event
     */
    public static double shoot(final double eventBulletSpread,
                               final ItemStack item)
    {
        final int ERROR = -2, UNENCRYPTED = -1;
        final ItemMeta gunMeta = item.getItemMeta();
        
        double bulletSpread = ERROR;
        
        if (!item.hasItemMeta() || !item.getItemMeta().hasLore())
            return bulletSpread;
        
        List<String> lore = item.getItemMeta().getLore();
        
        if (CrackshotLore.isUnencrypted(lore))
            return UNENCRYPTED;
        if (CrackshotLore.isPreShotWeapon(lore))
            bulletSpread = CrackshotLore.initializeGun(eventBulletSpread, lore);
        else if (lore.size() == LORE_SIZE)
            bulletSpread = CrackshotLore.decrementDurability(eventBulletSpread, lore);
        
        gunMeta.setLore(lore);
        item.setItemMeta(gunMeta);
        return bulletSpread;
    }
}
