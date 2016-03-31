/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.weps;

import java.util.ArrayList;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.GunModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.projectile.CritModifier;
/**
 *
 * @author jb
 */
public class GunCrits extends Chance<CritModifier>
{
    private final double critStrikeMultiplier;
    private final double critStrikeDamage;
    
    public GunCrits(final GunModifier[] mods,
                    final double baseDamage)
    {
        super(getCritModifiers(mods),
              getCritChance(mods));
        
        this.critStrikeMultiplier = getCritMultiplier(mods);
        this.critStrikeDamage = critStrikeMultiplier * baseDamage;
    }
    
    static
    private double getCritMultiplier(final GunModifier gunMods[])
    {
        double critMultiplier = 0;
        for (CritModifier mod : getCritModifiers(gunMods))
        {
            critMultiplier += mod.getCritStrike();
        }
        return Math.max(0, critMultiplier);
    }

    static
    private double getCritChance(final GunModifier gunMods[])
    {
        double critChance = 0;
        for (CritModifier mod : getCritModifiers(gunMods))
        {
            critChance += mod.getCritChance();
        }
        return Math.max(0, critChance);
    }
    
    /**
     * @param gunMods
     * @return Returns all IncendiaryModifiers on the gun.
     */
    static
    private ArrayList<CritModifier> getCritModifiers(final GunModifier gunMods[])
    {
        final ArrayList<CritModifier> mods = new ArrayList<>();
        for (GunModifier mod : gunMods)
        {
            if (mod instanceof CritModifier)
                mods.add((CritModifier)mod);
        }
        return mods;
    }
}
