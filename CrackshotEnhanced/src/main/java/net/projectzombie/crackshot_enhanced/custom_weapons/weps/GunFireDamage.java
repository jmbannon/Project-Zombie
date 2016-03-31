/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.weps;

import java.util.ArrayList;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.GunModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.projectile.IncendiaryModifier;

/**
 *
 * @author jb
 */
public class GunFireDamage extends DamageOnHit<IncendiaryModifier>
{
    
    public GunFireDamage(GunModifier[] modifiers)
    {
        super(getIncendiaryModifiers(modifiers),
              fireDamageValueSum(modifiers),
              fireDamageMultiplierSum(modifiers));
    }
    
    static private double fireDamageValueSum(final GunModifier[] modSet)
    {
        double baseDamageValueSum = 0;

        for (IncendiaryModifier mod : getIncendiaryModifiers(modSet))
        {
            baseDamageValueSum += mod.getFireDamageValue();
        }
        return baseDamageValueSum;
    }
    
    static private double fireDamageMultiplierSum(final GunModifier[] modSet)
    {
        double baseDamageMultiplierSum = 1.0;

        for (IncendiaryModifier mod : getIncendiaryModifiers(modSet))
        {
            baseDamageMultiplierSum += mod.getFireDamageMultiplier();
        }
        return baseDamageMultiplierSum;
    }
    
    /**
     * @param modSet
     * @return Returns all DamageModifiers on the gun.
     */
    static private ArrayList<IncendiaryModifier> getIncendiaryModifiers(final GunModifier[] modSet)
    {
        final ArrayList<IncendiaryModifier> mods = new ArrayList<>();
        for (GunModifier mod : modSet)
        {
            if (mod instanceof IncendiaryModifier)
                mods.add((IncendiaryModifier)mod);
        }
        return mods;
    }
}
