/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.utilities;

import java.util.ArrayList;
import net.projectzombie.crackshot_enhanced.custom_weapons.weps.CrackshotGun;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.FireMode;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.GunModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Scope;
import net.projectzombie.crackshot_enhanced.custom_weapons.types.Weapon;
import org.bukkit.ChatColor;
import org.bukkit.Material;

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
    public ArrayList<GunModifier> sortMods(final ArrayList<GunModifier> mods)
    {
        GunModifier temp;
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
    
    /**
     * Attempts to match a string with a ChatColor enum.
     * @param string Name of ChatColor.
     * @return ChatColor if matches. Null otherwise.
     */
    static
    public ChatColor matchChatColor(final String string)
    {
        if (string == null)
            return null;
        
        ChatColor colors[] = ChatColor.values();
        for (ChatColor color : colors)
        {
            if (color.toString().equalsIgnoreCase(string))
            {
                return color;
            }
        }
        return null;
    }
    
    /**
     * Attempts to match a string with a Material enum.
     * @param string Name of Material.
     * @return Material if matches. Null otherwise.
     */
    static
    public Material matchMaterial(final String string)
    {
        if (string == null)
            return null;
        
        return Material.matchMaterial(string);
    }
    
    /**
     * Attempts to match a string with a Sounds enum.
     * @param string Name of Sounds enum.
     * @return Sounds enum if matches. Null otherwise.
     */
    static
    public Sounds matchSound(final String string)
    {
        if (string == null)
            return null;
        
        Sounds sounds[] = Sounds.values();
        for (Sounds sound : sounds)
        {
            if (sound.toString().equalsIgnoreCase(string))
            {
                return sound;
            }
        }
        return null;
    }
}
