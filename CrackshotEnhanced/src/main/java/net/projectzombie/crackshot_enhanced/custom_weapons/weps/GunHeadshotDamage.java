/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.weps;

import java.util.ArrayList;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.GunModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.projectile.DamageModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.projectile.HeadshotModifier;

/**
 *
 * @author jb
 */
public class GunHeadshotDamage extends DamageOnHit<HeadshotModifier>
{
    
    public GunHeadshotDamage(final GunModifier[] modifiers)
    {
        super(getHeadshotModifiers(modifiers),
              headshotDamageValueSum(modifiers),
              headshotDamageMultiplierSum(modifiers));
    }
    
    static private double headshotDamageValueSum(final GunModifier[] modSet)
    {
        double baseDamageValueSum = 0;

        for (HeadshotModifier mod : getHeadshotModifiers(modSet))
        {
            baseDamageValueSum += mod.getHeadshotDamageModifier();
        }
        return baseDamageValueSum;
    }
    
    static private double headshotDamageMultiplierSum(final GunModifier[] modSet)
    {
        double baseDamageMultiplierSum = 1.0;

        for (HeadshotModifier mod : getHeadshotModifiers(modSet))
        {
            baseDamageMultiplierSum += mod.getHeadshotDamageMultiplier();
        }
        
        return baseDamageMultiplierSum;
    }
    
    /**
     * @param modSet
     * @return Returns all DamageModifiers on the gun.
     */
    static private ArrayList<HeadshotModifier> getHeadshotModifiers(final GunModifier[] modSet)
    {
        final ArrayList<HeadshotModifier> mods = new ArrayList<>();
        for (GunModifier mod : modSet)
        {
            if (mod instanceof DamageModifier)
                mods.add((HeadshotModifier)mod);
        }
        return mods;
    }
}
