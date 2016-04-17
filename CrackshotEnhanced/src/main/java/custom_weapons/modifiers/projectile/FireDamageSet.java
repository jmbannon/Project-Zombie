/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package custom_weapons.modifiers.projectile;

import java.util.ArrayList;
import custom_weapons.modifiers.GunModifier;
import static custom_weapons.modifiers.ModifierLoreBuilder.STAT_SEPERATOR;
import static custom_weapons.modifiers.ModifierLoreBuilder.getMultiplierStat;
import static custom_weapons.modifiers.ModifierLoreBuilder.getValueStat;
import custom_weapons.modifiers.projectile.FireDamageAttributes;

/**
 *
 * @author jb
 */
public class FireDamageSet extends DamageOnHit<FireDamageAttributes>
{
    
    public FireDamageSet(GunModifier[] modifiers)
    {
        super("Fire Damage",
              fireDamageValueSum(modifiers),
              fireDamageMultiplierSum(modifiers));
    }
    
    @Override
    public ArrayList<String> getStats()
    {
        final ArrayList<String> stats = new ArrayList<>();
        stats.add(getValueStat(super.getTotal(), "total fire damage"));
        stats.add(STAT_SEPERATOR);
        stats.add(getValueStat(super.getValue(), "fire damage"));
        stats.add(getMultiplierStat(super.getMultiplier(), "fire damage"));
        return stats;
    }
    
    static private double fireDamageValueSum(final GunModifier[] modSet)
    {
        double baseDamageValueSum = 0;

        for (FireDamageAttributes mod : getIncendiaryModifiers(modSet))
        {
            baseDamageValueSum += mod.getFireDamageValue();
        }
        return baseDamageValueSum;
    }
    
    static private double fireDamageMultiplierSum(final GunModifier[] modSet)
    {
        double baseDamageMultiplierSum = 1.0;

        for (FireDamageAttributes mod : getIncendiaryModifiers(modSet))
        {
            baseDamageMultiplierSum += mod.getFireDamageMultiplier();
        }
        return baseDamageMultiplierSum;
    }
    
    /**
     * @param modSet
     * @return Returns all DamageModifiers on the gun.
     */
    static private ArrayList<FireDamageAttributes> getIncendiaryModifiers(final GunModifier[] modSet)
    {
        final ArrayList<FireDamageAttributes> mods = new ArrayList<>();
        for (GunModifier mod : modSet)
        {
            if (mod instanceof FireDamageAttributes)
                mods.add((FireDamageAttributes)mod);
        }
        return mods;
    }
}
