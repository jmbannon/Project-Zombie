/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.utilities;

import java.util.ArrayList;
import net.projectzombie.crackshot_enhanced.custom_weapons.weps.CrackshotGun;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.FireMode;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.CraftableModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Scope;
import net.projectzombie.crackshot_enhanced.custom_weapons.types.Weapon;

/**
 *
 * @author jbannon
 */
public class GunUtils
{
    static
    public Boolean isShotgun(final CrackshotGun gun)
    {
        final Weapon weaponType = gun.getSkeleton().getWeaponType();
        return (weaponType.equals(Weapon.SH_BREAK)
                || weaponType.equals(Weapon.SH_PUMP)
                || weaponType.equals(Weapon.SH_SLIDE));
    }
    
    static
    public Boolean isSniper(final CrackshotGun gun)
    {
        return gun.getSkeleton().getWeaponType().equals(Weapon.SNIPER);
    }
    
    static
    public Boolean isHuntingRifle(final CrackshotGun gun)
    {
        return gun.getSkeleton().getWeaponType().equals(Weapon.HUNTING);
    }
    
    static
    public Boolean hasScope(final CrackshotGun gun)
    {
        return !gun.getScope().equals(Scope.IRON);
    }
    
    static
    public Boolean isBurstFire(final CrackshotGun gun)
    {
        return gun.getFireMode().equals(FireMode.BURST);
    }
    
    static
    public Boolean isAutomatic(final CrackshotGun gun)
    {
        return gun.getFireMode().equals(FireMode.AUTO);
    }
    
    /**
     * Sorts GunModifiers by price ascending using bubble sort.
     * @param mods ArrayList of GunModifier
     * @return Sorted ArraylList of GunModifier
     */
    static
    public ArrayList<CraftableModifier> sortMods(final ArrayList<CraftableModifier> mods)
    {
        CraftableModifier temp;
        int i = 0;
        while (i < mods.size() - 1)
        {
            if (mods.get(i).price() > mods.get(i + 1).price())
            {
                temp = mods.get(i);
                mods.set(i, mods.get(i + 1));
                mods.set(i + 1, temp);
                i = 0;
            }
            else
            {
                ++i;
            }
        }
        return mods;
    }
}
