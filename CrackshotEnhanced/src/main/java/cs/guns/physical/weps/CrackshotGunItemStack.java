/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs.guns.physical.weps;

import static cs.guns.utilities.Constants.CRACKSHOT;
import cs.guns.weps.Guns.CrackshotGun;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author Jesse Bannon
 * 
 * Represents a CrackshotGun in the form of an ItemStack. To be used
 * in an event when a player is holding an ItemStack.
 */
public class CrackshotGunItemStack extends CrackshotGunLore
{
    private final ItemStack gunItem;
    
    /**
     * Initializes the CrackshotGun ItemStack. If the ItemStack does not
     * contain lore the item is set to null. TODO: Throw exception.
     * 
     * @param item ItemStack to initialize as CrackshotGun.
     */
    public CrackshotGunItemStack(final ItemStack item)
    {
        super(hasLore(item) ? item.getItemMeta().getLore() : null);
        this.gunItem = item;
    }

    /**
     * Returns a string that contains the modified name with respect to the 
     * new GunMeta. Also sets the ammo amount to be the same.
     * 
     * @param newGunMeta ItemMeta of the newly modified version.
     * @return Modified name and ammo amount.
     */
    private String getModifiedName(final ItemMeta newGunMeta)
    {
        final String origName = gunItem.getItemMeta().getDisplayName();
        final String newName = newGunMeta.getDisplayName();
        
        String ammoSubString = origName.substring(origName.indexOf("«"), origName.indexOf("»"));
        String adjustedNewName = newName.substring(0, newName.indexOf("«")) + ammoSubString + newName.substring(newName.indexOf("»"), newName.length());
        
        return adjustedNewName;
    }
    
    /**
     * Returns the bullet spread to be set for the event.
     * Takes into account the durability and build of the gun.
     * 
     * @param eventBulletSpread Original bullet spread from the event.
     * @return Adjusted event bullet spread.
     */
    public double getEventBulletSpread(final double eventBulletSpread)
    {
        return super.getGun().getEventBulletSpread(eventBulletSpread, 
            super.getDurability(),
            super.getBuild());
    }
    
    /**
     * Returns the ItemStack of a modified gun.
     * 
     * @param newGun Modified version of current gun.
     * @return ItemStack of the new weapon.
     */
    public ItemStack getModifiedGunItem(final CrackshotGun newGun)
    {
        final ItemStack newGunItem = CRACKSHOT.generateWeapon(newGun.getCSWeaponName());
        if (newGunItem == null)
            return null;
        
        final ItemMeta newMeta = newGunItem.getItemMeta();
        final CrackshotGunLore newLore = new CrackshotGunLore(newMeta.getLore());
        
        if (super.isPostShot())
        {
            newLore.toPostShotLore(this.getBuild(), this.getDurability());
            newMeta.setLore(newLore.getLore());
            newMeta.setDisplayName(getModifiedName(newMeta));
            newGunItem.setItemMeta(newMeta);
            return newGunItem;
        }
        else if (super.isPreShot())
        {
            newLore.setGunID(newGun.getUniqueID());
            newLore.toPreShotLore();
            newMeta.setLore(newLore.getLore());
            return newGunItem;
        }
        else
            return null;
    }
    
    /**
     * Returns true if the ItemStack contains lore.
     * @param item ItemStack to check for lore.
     * @return True if item has lore. False otherwise.
     */
    static private boolean hasLore(final ItemStack item)
    {
        return item.hasItemMeta()
            && item.getItemMeta().hasLore();
    }
    
}
