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

/**
 *
 * @author jb
 */
public class BaseDamageSet extends DamageOnHit<BaseDamageAttributes>
{
    private final double skeleBaseDamage;
    
    public BaseDamageSet(GunModifier[] modifiers,
                      final double skeletonBaseDamage)
    {
        super("Base Damage",
              baseDamageValueSum(modifiers, skeletonBaseDamage),
              baseDamageMultiplierSum(modifiers));
        this.skeleBaseDamage = skeletonBaseDamage;
    }
    
    @Override
    public ArrayList<String> getStats()
    {
        final ArrayList<String> stats = new ArrayList<>();
        stats.add(getValueStat(super.getTotal(), "total base damage"));
        stats.add(STAT_SEPERATOR);
        stats.add(getValueStat(skeleBaseDamage, "stock base damage"));
        stats.add(getValueStat(super.getValue(), "extra base damage"));
        stats.add(getMultiplierStat(super.getMultiplier(), "base damage"));
        return stats;
    }
    
    static private double baseDamageValueSum(final GunModifier[] modSet,
                                            final double skeletonBaseDamage)
    {
        double baseDamageValueSum = skeletonBaseDamage;

        for (BaseDamageAttributes mod : getDamageModifiers(modSet))
        {
            baseDamageValueSum += mod.getDamageValue();
        }
        return baseDamageValueSum;
    }
    
    static private double baseDamageMultiplierSum(final GunModifier[] modSet)
    {
        double baseDamageMultiplierSum = 1.0;

        for (BaseDamageAttributes mod : getDamageModifiers(modSet))
        {
            baseDamageMultiplierSum += mod.getDamageValue();
        }
        return baseDamageMultiplierSum;
    }
    
    /**
     * @param modSet
     * @return Returns all DamageModifiers on the gun.
     */
    static private ArrayList<BaseDamageAttributes> getDamageModifiers(final GunModifier[] modSet)
    {
        final ArrayList<BaseDamageAttributes> mods = new ArrayList<>();
        for (GunModifier mod : modSet)
        {
            if (mod instanceof BaseDamageAttributes)
                mods.add((BaseDamageAttributes)mod);
        }
        return mods;
    }
    
}
