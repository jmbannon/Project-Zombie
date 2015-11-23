/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.crafting;

import java.util.ArrayList;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.CraftableItems;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.GunModifier;
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
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

/**
 *
 * @author jesse
 */
public class Recipes implements Listener
{
    static ItemStack test;
    
    public Recipes()
    {
        Bukkit.resetRecipes();
    }
    
    @EventHandler(priority = EventPriority.NORMAL)
    public void onGunCraft(PrepareItemCraftEvent event)
    {
        CraftingInventory inv = event.getInventory();
        GunSkeleton skele = getSkeleton(inv.getResult());
        ItemStack currentGunItem = null;
        GunModifier mod = null;

        if (skele == null)
            return;
        
        for (ItemStack item : inv.getContents())
        {
            if (CrackshotLore.isPostShotWeapon(item)
                || CrackshotLore.isPreShotWeapon(item))
            {
                currentGunItem = item;
            }
            if (CraftableItems.contains(item.getData()))
            {
                mod = CraftableItems.getGunModifier(item.getData());
            }
        }
        
        if (currentGunItem == null || mod == null)
            return;
        
        CrackshotGun currentGun = Guns.get(currentGunItem);
        if (currentGun == null)
            return;
        
        CrackshotGun newGun = currentGun.getModifiedGun(mod);
        if (newGun == null)
            return;
        
        ItemStack newGunItem = CrackshotLore.getModifiedGunItem(currentGunItem, newGun);
        event.getInventory().setResult(newGunItem);
    }
    
    private GunSkeleton getSkeleton(final ItemStack result)
    {
        for (GunSkeleton skele : GunSkeleton.values())
        {
            if (skele.getBareItemStack().equals(result))
                return skele;
        }
        return null;
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
    
    public void scopedRecipes()
    {
        ArrayList<GunModifier> mods = new ArrayList<>();
        for (GunSkeleton skele : GunSkeleton.values())
        {
            mods.addAll(skele.getScopes());
            addSkeletonRecipe(skele, mods, " X ", " G ", "   ");
            mods.clear();
        }
    }
    
    
    
    private void addSkeletonRecipe(final GunSkeleton skele,
                                   final ArrayList<GunModifier> mods,
                                   final String shapeTop,
                                   final String shapeMid,
                                   final String shapeBot)
    {
        for (GunModifier mod : mods)
        {
            if (!CraftableItems.contains(mod))
                mods.remove(mod);
        }
        
        if (mods.size() >= 2)
        {
            for (GunModifier mod : mods)
            {
                ShapedRecipe rec = new ShapedRecipe(skele.getBareItemStack());
                rec.shape(shapeTop, shapeMid, shapeBot);
                rec.setIngredient('G', skele.getItemMaterial(), skele.getItemData());
                rec.setIngredient('X', CraftableItems.getMaterial(mod));
                Bukkit.getServer().addRecipe(rec);
            }
        }
    }
    
}
