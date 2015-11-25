/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.weps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.CraftableItems;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.GunModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.utilities.CrackshotLore;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

/**
 *
 * @author jbannon
 */
public class Guns
{
    private static final ArrayList<CrackshotGun> guns = initializeGuns();
    
    
    static
    private ArrayList<CrackshotGun> initializeGuns()
    {
        ArrayList<CrackshotGun> gunArray = new ArrayList<>();
        final GunSkeleton gunSkeletons[] = GunSkeleton.values();
        CrackshotGun skeleGuns[];
        int id = 0;
        
        for (GunSkeleton skeleton : gunSkeletons)
        {
            skeleGuns = skeleton.getGuns(id);
            if (skeleGuns != null)
            {
                gunArray.addAll(Arrays.asList(skeleGuns));
                id += skeleGuns.length;
            }
        }
        return gunArray;
    }
    

    static
    public CrackshotGun get(final int gunID)
    {
        return (gunID >= 0 && gunID < guns.size()) ? guns.get(gunID) : null;
    }
    
    static
    public CrackshotGun get(final ItemStack item)
    {
        return get(CrackshotLore.getWeaponID(item));
    }
    
    static
    public ArrayList<CrackshotGun> getGuns()
    {
        return guns;
    }
}
