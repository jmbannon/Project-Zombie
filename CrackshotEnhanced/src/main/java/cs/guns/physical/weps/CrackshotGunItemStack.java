/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs.guns.physical.weps;

import cs.guns.qualities.Build;
import static cs.guns.utilities.Constants.CRACKSHOT;
import cs.guns.weps.Guns.CrackshotGun;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author jb
 */
public class CrackshotGunItemStack extends CrackshotGunLore
{
    final ItemStack gunItem;
    
    public CrackshotGunItemStack(final ItemStack item)
    {
        super(hasLore(item) ? item.getItemMeta().getLore() : null);
        this.gunItem = item;
    }

    public String getAdjustedAmmoCount(final ItemMeta newGunMeta)
    {
        final String origName = gunItem.getItemMeta().getDisplayName();
        final String newName = newGunMeta.getDisplayName();
        
        String ammoSubString = origName.substring(origName.indexOf("«"), origName.indexOf("»"));
        String adjustedNewName = newName.substring(0, newName.indexOf("«")) + ammoSubString + newName.substring(newName.indexOf("»"), newName.length());
        
        return adjustedNewName;
    }
    
    // TODO
    public double getEventBulletSpread(final double eventBulletSpread)
    {
        return super.getGun().getEventBulletSpread(eventBulletSpread, 
            super.getDurability(),
            super.getBuild());
    }
    
    public ItemStack getModifiedGunItem(final CrackshotGun newGun)
    {
        final ItemStack newGunItem = CRACKSHOT.generateWeapon(newGun.getCSWeaponName());
        if (newGunItem == null)
            return null;
        
        final ItemMeta newMeta = newGunItem.getItemMeta();
        final CrackshotGunLore newLore = new CrackshotGunLore(newMeta.getLore());
        
        if (super.isPostShot())
        {
            newMeta.setLore(newLore.generatePostShotLore(this.getBuild(), this.getDurability()));
            newMeta.setDisplayName(getAdjustedAmmoCount(newMeta));
            newGunItem.setItemMeta(newMeta);
            return newGunItem;
        }
        else if (super.isPreShot())
        {
            newLore.setGunID(newGun.getUniqueID());
            newMeta.setLore(newLore.generatePreShotLore());
            return newGunItem;
        }
        else
            return null;
    }
    
    
    static private boolean hasLore(final ItemStack item)
    {
        return item.hasItemMeta()
            && item.getItemMeta().hasLore();
    }
    
}
