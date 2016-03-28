/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.weps;

import java.util.ArrayList;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.GunModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.projectile.ShrapnelModifier;

/**
 *
 * @author jb
 */
public class GunShrapnelDamage extends DamageOnHit
{
    
    public GunShrapnelDamage(GunModifier[] modifiers)
    {
        super(modifiers,
              getShrapnelDamageValue(modifiers),
              getShrapnelDamageMultiplier(modifiers));
    }
    
    static
    public double getShrapnelDamageValue(final GunModifier[] modifiers)
    {
        double shrapnelDamageValue = 0;

        for (ShrapnelModifier mod : getShrapnelModifiers(modifiers))
        {
            shrapnelDamageValue += mod.getShrapnelDamageValue();
        }
        return Math.max(0, shrapnelDamageValue);
    }
    
    static
    public double getShrapnelDamageMultiplier(final GunModifier[] modifiers)
    {
        double shrapnelDamageMultiplier = 1.0;

        for (ShrapnelModifier mod : getShrapnelModifiers(modifiers))
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
    public ArrayList<ShrapnelModifier> getShrapnelModifiers(final GunModifier[] modifiers)
    {
        final ArrayList<ShrapnelModifier> mods = new ArrayList<>();
        for (GunModifier mod : modifiers)
        {
            if (mod instanceof ShrapnelModifier)
                mods.add((ShrapnelModifier)mod);
        }
        return mods;
    }
    
}
