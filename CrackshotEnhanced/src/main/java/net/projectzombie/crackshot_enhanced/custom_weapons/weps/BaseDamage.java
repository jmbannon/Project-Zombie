/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.weps;

import java.util.ArrayList;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.GunModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.projectile.DamageModifier;

/**
 *
 * @author jb
 */
public class BaseDamage extends DamageOnHit<DamageModifier>
{
    
    public BaseDamage(GunModifier[] modifiers,
                         final double skeletonBaseDamage)
    {
        super(getDamageModifiers(modifiers),
              baseDamageValueSum(modifiers, skeletonBaseDamage),
              baseDamageMultiplierSum(modifiers));
    }
    
    static private double baseDamageValueSum(final GunModifier[] modSet,
                                            final double skeletonBaseDamage)
    {
        double baseDamageValueSum = skeletonBaseDamage;

        for (DamageModifier mod : getDamageModifiers(modSet))
        {
            baseDamageValueSum += mod.getDamageValue();
        }
        return baseDamageValueSum;
    }
    
    static private double baseDamageMultiplierSum(final GunModifier[] modSet)
    {
        double baseDamageMultiplierSum = 1.0;

        for (DamageModifier mod : getDamageModifiers(modSet))
        {
            baseDamageMultiplierSum += mod.getDamageValue();
        }
        return baseDamageMultiplierSum;
    }
    
    /**
     * @param modSet
     * @return Returns all DamageModifiers on the gun.
     */
    static private ArrayList<DamageModifier> getDamageModifiers(final GunModifier[] modSet)
    {
        final ArrayList<DamageModifier> mods = new ArrayList<>();
        for (GunModifier mod : modSet)
        {
            if (mod instanceof DamageModifier)
                mods.add((DamageModifier)mod);
        }
        return mods;
    }
    
}
