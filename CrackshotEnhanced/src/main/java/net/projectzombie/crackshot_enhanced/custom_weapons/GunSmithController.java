/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons;

import net.projectzombie.crackshot_enhanced.custom_weapons.types.Gun;
import net.projectzombie.crackshot_enhanced.custom_weapons.types.Mod.ModType;
import net.projectzombie.crackshot_enhanced.custom_weapons.utilities.CrackshotLore;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

/**
 *
 * @author jbannon
 */
public class GunSmithController
{
    static
    public void addModification(final Player player,
                                final ModType mod)
    {
        final Gun newGun, currentGun = Gun.getGun(player.getItemInHand());
        if (currentGun == null)
        {
            player.sendMessage("Cannot modify an item that is not a gun!");
            return;
        }
        
        newGun = currentGun.getModification(mod);
        if (newGun == null)
        {
            player.sendMessage(mod.getName() + " is not available for this weapon.");
            return;
        }
        
        if (CrackshotLore.getModifiedGunItem(player.getItemInHand(), newGun) == null)
            player.sendMessage("CSUtility says its null bro");
        
        player.setItemInHand(CrackshotLore.getModifiedGunItem(player.getItemInHand(), newGun));
        player.updateInventory();
    }
    
    static
    public void repairWeapon(final Player player)
    {
        final int repairPrice = repairPrice(player);
        
        if (repairPrice < 0)
            player.sendMessage("Cannot repair an item that is not a gun!");
        else if (repairPrice == 0)
            player.sendMessage("Gun is fully repaired already!");
        else
        {
            if (purchase(player, repairPrice) && CrackshotLore.repairWeapon(player.getItemInHand()))
                player.sendMessage("Gun has been repaired for $" + repairPrice + ".");
            else
                player.sendMessage("You need $" + (repairPrice - getTotalMoney(player)) + " to repair this weapon!");
        }
    }
    
    static
    public int repairPrice(final Player player)
    {
        final ItemStack weaponInHand = player.getInventory().getItemInHand();
        final Gun gun = Gun.getGun(CrackshotLore.getWeaponID(weaponInHand));
        
        return gun == null || !CrackshotLore.isPostShotWeapon(player.getItemInHand()) ? -1 : gun.getRepairPrice(weaponInHand);
    }
    
    static
    public void upgradeWeapon(final Player player)
    { 
        final int upgradePrice = upgradePrice(player);
        
        if (upgradePrice < 0)
            player.sendMessage("Cannot upgrade an item that is not a gun!");
        else if (upgradePrice == 0)
            player.sendMessage("Gun is fully upgraded already!");
        else
        {
            if (purchase(player, upgradePrice) && CrackshotLore.upgradeWeapon(player.getItemInHand()))
                player.sendMessage("Gun has been upgraded for $" + upgradePrice + ".");
            else
                player.sendMessage("You need $" + (upgradePrice - getTotalMoney(player)) + " to upgrade this weapon!");
        }
    }
    
    static
    public int upgradePrice(final Player player)
    {
        final ItemStack weaponInHand = player.getInventory().getItemInHand();
        final Gun gun = Gun.getGun(CrackshotLore.getWeaponID(weaponInHand));
        
        return gun == null ? -1 : gun.getUpgradePrice(weaponInHand);
    }
    
    static
    private int getTotalMoney(final Player player)
    {
        final PlayerInventory inv = player.getInventory();
        int totalMoney = 0;
        
        for (ItemStack item : inv.getContents())
        {
            totalMoney += item != null && item.getType().equals(Material.EMERALD) ? item.getAmount() : 0;
        }
        return totalMoney;
    }
    
    static
    private boolean canAfford(final Player player,
                              final int price)
    {
        return getTotalMoney(player) >= price;
    }
    
    static
    private boolean purchase(final Player player,
                             final int price)
    {
        if (!canAfford(player, price))
            return false;
        
        final PlayerInventory inv = player.getInventory();
        int itemAmount, paid = 0;
        
        for (ItemStack item : inv.getContents())
        {  
            if (item != null && item.getType().equals(Material.EMERALD))
            {
                itemAmount = item.getAmount();
                if (itemAmount + paid <= price)
                {
                    inv.remove(item);
                    paid += itemAmount;
                }
                else if (paid < price && itemAmount + paid > price)
                {
                    item.setAmount(itemAmount - (price - paid));
                    paid += (price - paid);
                }
                else { break; }
            }
        }
        return true;
    }
}
