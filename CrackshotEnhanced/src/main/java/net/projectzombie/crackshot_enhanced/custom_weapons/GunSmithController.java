/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons;

import net.projectzombie.crackshot_enhanced.custom_weapons.types.Gun;
import net.projectzombie.crackshot_enhanced.custom_weapons.utilities.CrackshotLore;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author jbannon
 */
public class GunSmithController
{

    public void repairWeapon(final Player player)
    {
        final ItemStack weaponInHand = player.getInventory().getItemInHand();
        final int weaponID = CrackshotLore.getWeaponID(weaponInHand);
        
        if (weaponID >= 0)
        {
            Gun gun = Gun.getGunType(weaponID);
        }
    }
    /*
    public boolean isWeapon(final ItemStack itemInHand)
    {
        if (itemInHand.hasItemMeta() && itemInHand.getItemMeta().hasLore())
        {
            final List<String> lore = itemInHand.getItemMeta().getLore();
            if (lore.size() == )
            
        }
    }
    
    public int isUpgradable(final ItemStack weapon)
    {
        return -1;
    }
    
    public int isRepairable(final ItemStack weapon)
    {
        return -1;
    }
    */
}
