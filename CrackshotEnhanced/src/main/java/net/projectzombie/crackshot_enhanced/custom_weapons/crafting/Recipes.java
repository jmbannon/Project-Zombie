/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.crafting;

import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Scope;
import net.projectzombie.crackshot_enhanced.custom_weapons.utilities.CrackshotLore;
import net.projectzombie.crackshot_enhanced.custom_weapons.weps.CrackshotGun;
import net.projectzombie.crackshot_enhanced.custom_weapons.weps.GunSkeleton;
import net.projectzombie.crackshot_enhanced.custom_weapons.weps.Guns;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

/**
 *
 * @author jesse
 */
public class Recipes implements Listener
{
    static ItemStack test;
    
    public Recipes()
    {
        ItemStack deagle = new ItemStack(Material.DIAMOND_HOE);
        ItemMeta meta = deagle.getItemMeta();
        meta.setDisplayName("testdeag");
        deagle.setItemMeta(meta);
        test = deagle;
        ShapedRecipe scopedGun = new ShapedRecipe(deagle);
        scopedGun.shape(" S ", " G ", "   ");
        scopedGun.setIngredient('G', Material.DIAMOND_HOE);
        scopedGun.setIngredient('S', Material.SPECKLED_MELON);
        test = deagle;
        Bukkit.getServer().addRecipe(scopedGun);
    }
    
    @EventHandler(priority = EventPriority.NORMAL)
    public void onGunCraft(PrepareItemCraftEvent event)
    {
        CrackshotGun currentGun, newGun;
        ItemStack currentGunItem, newGunItem;
        
        if (event.getRecipe().getResult().equals(test))
        {
            Bukkit.broadcastMessage("test Should pop up if crafting deagle w/scope");
            currentGunItem = event.getInventory().getItem(5);
            currentGun = Guns.get(currentGunItem);
            if (currentGun == null)
                Bukkit.broadcastMessage("current gun is null");
            newGun = currentGun.getModifiedGun(Scope.ACOG);
            newGunItem = CrackshotLore.getModifiedGunItem(currentGunItem, newGun);
            event.getInventory().setResult(newGunItem);
        }
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void disableMobEggs(final PlayerInteractEvent event)
    {
        if (event.getMaterial() == Material.MONSTER_EGG
                && event.getAction() == Action.RIGHT_CLICK_BLOCK)
        {
            event.setCancelled(true);
        }
    }
}
