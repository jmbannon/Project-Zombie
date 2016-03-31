/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.weps;

import java.util.ArrayList;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.GunModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.projectile.IgniteModifier;

/**
 *
 * @author jb
 */
public class GunIgnite extends Chance<IgniteModifier>
{
    
    private final double igniteDurationInSeconds;
    private final double igniteFireDmgMultiplier;
    private final double igniteDPS;
    
    public GunIgnite(final GunModifier[] mods,
                     final double totalFireDamage)
    {
        super(getIgniteModifiers(mods),
              getIgniteChance(mods));
        
        this.igniteDurationInSeconds = getIgniteDurationInSeconds(mods);
        this.igniteFireDmgMultiplier = getIgniteFireDamageMultiplierSum(mods);
        this.igniteDPS = this.igniteFireDmgMultiplier * totalFireDamage;
    }
    
    static
    private double getIgniteChance(final GunModifier[] modSet)
    {
        double baseDamageValueSum = 0;

        for (IgniteModifier mod : getIgniteModifiers(modSet))
        {
            baseDamageValueSum += mod.getIgniteChance();
        }
        return baseDamageValueSum;
    }
    
    static
    private double getIgniteDurationInSeconds(final GunModifier[] modSet)
    {
        double baseDamageValueSum = 0;

        for (IgniteModifier mod : getIgniteModifiers(modSet))
        {
            baseDamageValueSum += mod.getIgniteDuration();
        }
        return baseDamageValueSum;
    }
    
    static 
    private double getIgniteFireDamageMultiplierSum(final GunModifier[] modSet)
    {
        double fireDamageMultiplierSum = 0.0;

        for (IgniteModifier mod : getIgniteModifiers(modSet))
        {
            fireDamageMultiplierSum += mod.getIgniteDamageMultiplierFromFireDamage();
        }
        return fireDamageMultiplierSum;
    }
    
    /**
     * @param modSet
     * @return Returns all DamageModifiers on the gun.
     */
    static 
    private ArrayList<IgniteModifier> getIgniteModifiers(final GunModifier[] modSet)
    {
        final ArrayList<IgniteModifier> mods = new ArrayList<>();
        for (GunModifier mod : modSet)
        {
            if (mod instanceof IgniteModifier)
                mods.add((IgniteModifier)mod);
        }
        return mods;
    }
}
