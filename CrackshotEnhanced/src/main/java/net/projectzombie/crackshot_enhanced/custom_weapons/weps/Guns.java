/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.weps;

import java.util.ArrayList;
import java.util.Arrays;
import net.projectzombie.crackshot_enhanced.custom_weapons.utilities.CrackshotLore;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author jbannon
 */
public class Guns
{
    private static ArrayList<CrackshotGun> guns = null;
    
    static
    public void initialize()
    {
        if (guns != null)
            return;
        
        guns = new ArrayList<>();
        final GunSkeleton gunSkeletons[] = GunSkeleton.values();
        CrackshotGun skeleGuns[];
        int id = 0;
        
        for (GunSkeleton skeleton : gunSkeletons)
        {
            skeleGuns = skeleton.getGuns(id);
            if (skeleGuns != null)
            {
                guns.addAll(Arrays.asList(skeleGuns));
                id += skeleGuns.length;
            }
        }
    }
    
    static
    public CrackshotGun get(final int gunID)
    {
        if (guns == null)
            initialize();
        
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
        if (guns == null)
            initialize();
        return guns;
    }
}
