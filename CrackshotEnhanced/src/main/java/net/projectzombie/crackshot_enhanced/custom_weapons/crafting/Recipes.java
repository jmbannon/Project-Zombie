/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.crafting;


import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.GunModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.GunModifier.GunModifierType;
import static net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.GunModifier.GunModifierType.*;
import net.projectzombie.crackshot_enhanced.custom_weapons.utilities.CrackshotLore;
import net.projectzombie.crackshot_enhanced.custom_weapons.skeleton.GunSkeletons;
import net.projectzombie.crackshot_enhanced.custom_weapons.weps.Guns.CrackshotGun;
import net.projectzombie.crackshot_enhanced.custom_weapons.skeleton.GunSkeletons.GunSkeleton;
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
 * @author Jesse Bannon
 */
public class Recipes implements Listener
{
    public Recipes()
    {
        
    }
    
    @EventHandler(priority = EventPriority.NORMAL)
    public void onGunCraft(PrepareItemCraftEvent event)
    {   
        final CraftingInventory inv = event.getInventory();
        final ItemStack[] mat = inv.getMatrix();
        
        if (mat.length <= 4)
            return;
        
        final ItemStack currentGunItem = mat[4];
        final ItemStack newGunItem;
        final GunSkeleton skele = getSkeleton(inv.getResult());
        final CrackshotGun currentGun;
        CrackshotGun newGun;
        ItemStack tempMod;
        
        if (isWeapon(currentGunItem) && skele != null && (currentGun = Guns.get(currentGunItem)) != null)
        {
            newGun = currentGun;
            for (GunModifierType type : GunModifierType.values())
            {
                tempMod = mat[type.getMatrixIndex()];
                if (tempMod != null && GunModifier.isGunModifier(tempMod))
                {
                    newGun = newGun.getModifiedGun(GunModifier.toModifier(tempMod, type.getMatrixIndex()), type);
                }
            }
            
            newGunItem = CrackshotLore.getModifiedGunItem(currentGunItem, newGun);
            inv.setResult(newGunItem);
        }
    }
    
    private boolean isWeapon(final ItemStack item)
    {
        return CrackshotLore.isPostShotWeapon(item) 
                || CrackshotLore.isPreShotWeapon(item);
    }
    
    /**
     * Checks to see if the result of the recipe is a skeleton bare ItemStack.
     * @param result ItemStack in result of crafting inventory.
     * @return Skeleton if ItemStack is skeleton's bare ItemStack.
     */
    private GunSkeleton getSkeleton(final ItemStack result)
    {
        for (GunSkeleton skele : GunSkeletons.getInstance().getAll())
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
        for (GunSkeleton skele : GunSkeletons.getInstance().getAll())
        {
            //intializeScopes(skele);
            intializeAttatchments(skele);
        }
    }
    
    static private void intializeAttatchments(final GunSkeleton skele)
    {
        final ItemStack bareSkeleItemStack = skele.getBareItemStack();
        
        final String[] idx0 = { " ", "A" }; 
        final String[] idx1 = { " ", "B" };
        final String[] idx2 = { " ", "C", "J" };
        final String[] idx3 = { " ", "D" };
        final String[] idx4 = { "E" };
        final String[] idx5 = { " ", "F" };
        final String[] idx6 = { " ", "G" };
        final String[] idx7 = { " ", "H" };
        final String[] idx8 = { " ", "I" };
        String all, top, mid, bot;
        char c;
        int len;
        ShapedRecipe modRecipe;
        
        // Powerset of all crafting combinations
        for (String i0 : idx0)
        {
        for (String i1 : idx1)
        {
        for (String i2 : idx2)
        {
        for (String i3 : idx3)
        {
        for (String i4 : idx4)
        {
        for (String i5 : idx5)
        {
        for (String i6 : idx6)
        {
        for (String i7 : idx7)
        {
        for (String i8 : idx8)
        {
            top = i0 + i1 + i2;
            mid = i3 + i4 + i5;
            bot = i6 + i7 + i8;
            all = (top + mid + bot).replace(" ", "");
            
            modRecipe = new ShapedRecipe(bareSkeleItemStack);
            modRecipe = modRecipe.shape(top, mid, bot);
            len = all.length();
            
            if (len == 1)
                continue;
            
            for (int i = 0; i < len; i++)
            {
                c = all.charAt(i);
                if      (c == 'A') modRecipe = modRecipe.setIngredient('A', SLOT_ONE_ATTATCHMENT.getMaterialData());
                else if (c == 'B') modRecipe = modRecipe.setIngredient('B', SIGHT.getMaterialData());
                else if (c == 'C') modRecipe = modRecipe.setIngredient('C', BOLT.getMaterialData());
                else if (c == 'J') modRecipe = modRecipe.setIngredient('J', FIREMODE.getMaterialData());
                else if (c == 'D') modRecipe = modRecipe.setIngredient('D', BARREL.getMaterialData());
                else if (c == 'E') modRecipe = modRecipe.setIngredient('E', skele.getMaterialData());
                else if (c == 'F') modRecipe = modRecipe.setIngredient('F', STOCK.getMaterialData());
                else if (c == 'G') modRecipe = modRecipe.setIngredient('G', SLOT_TWO_ATTATCHMENT.getMaterialData());
                else if (c == 'H') modRecipe = modRecipe.setIngredient('H', MAGAZINE.getMaterialData());
                else if (c == 'I') modRecipe = modRecipe.setIngredient('I', SLOT_THREE_ATTATCHMENT.getMaterialData());;  
            }
                    
            Bukkit.getServer().addRecipe(modRecipe);        
        }
        }
        }
        }
        }
        }
        }
        }
        }
    }
}
