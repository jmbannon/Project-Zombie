/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.crafting;


import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Attatchment;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.ModifierSet;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Scope;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.GunModifier;
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
        
    }
    
    @EventHandler(priority = EventPriority.NORMAL)
    public void onGunCraft(PrepareItemCraftEvent event)
    {
        System.out.println("is recipe?");
        
        CraftingInventory inv    = event.getInventory();
        GunSkeleton skele        = getSkeleton(inv.getResult());
        CrackshotGun currentGun  = null;
        CrackshotGun newGun      = null;
        ItemStack currentGunItem = null;
        ItemStack newGunItem     = null;
        GunModifier mod          = null;
        
        if (skele == null)
            return;
        
        for (ItemStack item : inv.getContents())
        {
            if (CrackshotLore.isPostShotWeapon(item) || CrackshotLore.isPreShotWeapon(item))
            {
                currentGunItem = item;
            }
            if (ModifierSet.getCraftingMap().containsKey(item.getData()))
            {
                mod = ModifierSet.getCraftingMap().get(item.getData());
            }
        }
        
        if (currentGunItem == null || mod == null)
            return;
        
        currentGun = Guns.get(currentGunItem);
        if (currentGun == null)
            return;
        
        newGun = currentGun.getModifiedGun(mod);
        if (newGun == null)
            return;
        
        newGunItem = CrackshotLore.getModifiedGunItem(currentGunItem, newGun);
        event.getInventory().setResult(newGunItem);
    }
    
    /**
     * Checks to see if the result of the recipe is a skeleton bare ItemStack.
     * @param result ItemStack in result of crafting inventory.
     * @return Skeleton if ItemStack is skeleton's bare ItemStack.
     */
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
    

    
    
    
    static public void initializeCraftingRecipes()
    {
        for (GunSkeleton skele : GunSkeleton.values())
        {
            //intializeScopes(skele);
            intializeAttatchments(skele);
        }
    }
    
    static private void intializeScopes(final GunSkeleton skele)
    {
        final ItemStack bareSkeleItemStack = skele.getBareItemStack();
        for (Scope scope : skele.getModifierSet().getScopes())
        {
            if (scope.getMaterialData() != null)
            {
                ShapedRecipe scopeRecipe = new ShapedRecipe(bareSkeleItemStack);
                scopeRecipe.shape(" S ", " G ", "   ")
                        .setIngredient('S', scope.getMaterialData())
                        .setIngredient('G', skele.getMaterialData());
                Bukkit.getServer().addRecipe(scopeRecipe);
            }
        }  
    }
    
    static private void intializeAttatchments(final GunSkeleton skele)
    {
        final ItemStack bareSkeleItemStack = skele.getBareItemStack();
        for (Attatchment att : skele.getModifierSet().getAttatchments())
        {
            if (att.getMaterialData() != null)
            {
                ShapedRecipe attRecipe = new ShapedRecipe(bareSkeleItemStack);
                attRecipe.shape("   ", " G ", "A  ")
                        .setIngredient('A', att.getMaterialData())
                        .setIngredient('G', skele.getMaterialData());
                Bukkit.getServer().addRecipe(attRecipe);
            }
        }  
    }
    
    
    
}
