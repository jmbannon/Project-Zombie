/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons;

import net.projectzombie.crackshot_enhanced.custom_weapons.types.BuildTypes;
import net.projectzombie.crackshot_enhanced.custom_weapons.types.WeaponType;
import com.shampaggon.crackshot.events.WeaponPreShootEvent;
import java.util.List;
import net.projectzombie.crackshot_enhanced.custom_weapons.types.CrackshotType;
import org.bukkit.ChatColor;
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
        final double bulletSpread = CrackshotType.shootHandler(event.getBulletSpread(), shooter.getItemInHand());
        
        if (bulletSpread < 0)
        {
            shooter.sendMessage("An error has occured. Please consult an admin");
            event.setCancelled(true);
        }
        else if (bulletSpread == 0)
        {
            shooter.sendMessage("Your " + event.getWeaponTitle() + " is broken. Repair it at the gunsmith");
            event.setCancelled(true);
        }
        else
            event.setBulletSpread(bulletSpread);
    }
}
