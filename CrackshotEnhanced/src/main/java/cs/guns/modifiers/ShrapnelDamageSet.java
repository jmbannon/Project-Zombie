/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs.guns.modifiers;

import java.util.ArrayList;
import cs.guns.components.GunModifier;
import static cs.guns.components.ModifierLoreBuilder.STAT_SEPERATOR;
import static cs.guns.components.ModifierLoreBuilder.getMultiplierStat;
import static cs.guns.components.ModifierLoreBuilder.getValueStat;

/**
 *
 * @author jb
 */
public class ShrapnelDamageSet extends DamageOnHit
{
    
    public ShrapnelDamageSet(GunModifier[] modifiers)
    {
        super("Shrapnel Damage",
              getShrapnelDamageValue(modifiers),
              getShrapnelDamageMultiplier(modifiers));
    }
    
    public ShrapnelDamageSet(GunModifier mod)
    {
        this(new GunModifier[] { mod });
    }
    
    @Override
    public ArrayList<String> getStats()
    {
        final ArrayList<String> stats = new ArrayList<>();
        stats.add(getValueStat(super.getTotal(), "total shrapnel damage"));
        stats.add(STAT_SEPERATOR);
        stats.addAll(getStat());
        return stats;
    }
    
    @Override
    public ArrayList<String> getStat()
    {
        final ArrayList<String> stats = new ArrayList<>();
        stats.add(getValueStat(super.getValue(), "shrapnel damage"));
        stats.add(getMultiplierStat(super.getMultiplier(), "shrapnel damage"));
        return stats;
    }
    
    static
    private double getShrapnelDamageValue(final GunModifier[] modifiers)
    {
        double shrapnelDamageValue = 0;

        for (ShrapnelDamageAttributes mod : getShrapnelModifiers(modifiers))
        {
            shrapnelDamageValue += mod.getShrapnelDamageValue();
        }
        return Math.max(0, shrapnelDamageValue);
    }
    
    static
    private double getShrapnelDamageMultiplier(final GunModifier[] modifiers)
    {
        double shrapnelDamageMultiplier = 1.0;

        for (ShrapnelDamageAttributes mod : getShrapnelModifiers(modifiers))
        {
            shrapnelDamageMultiplier += mod.getShrapnelDamageMultiplier();
        }
        return Math.max(0, shrapnelDamageMultiplier);
    }
    
    /**
     * @param modifiers
    * @return Returns all ShrapnelModifiers on the gun.
    */
    static
    private ArrayList<ShrapnelDamageAttributes> getShrapnelModifiers(final GunModifier[] modifiers)
    {
        final ArrayList<ShrapnelDamageAttributes> mods = new ArrayList<>();
        for (GunModifier mod : modifiers)
        {
            if (mod instanceof ShrapnelDamageAttributes)
                mods.add((ShrapnelDamageAttributes)mod);
        }
        return mods;
    }
    
}
