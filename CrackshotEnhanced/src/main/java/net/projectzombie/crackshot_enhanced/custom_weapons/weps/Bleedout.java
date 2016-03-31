/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.weps;

import java.util.ArrayList;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.GunModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.projectile.BleedoutModifier;

/**
 *
 * @author jb
 */
public class Bleedout extends DamageOverTime<BleedoutModifier>
{    
    public Bleedout(final GunModifier[] modSet,
                       final double totalShrapnelDamage)
    {
        super(getBleedoutModifiers(modSet),
              calculateBleedoutDPS(modSet, totalShrapnelDamage),
              calculateBleedoutDuration(modSet));
    }
    
    /**
    * @return Returns all BleedoutModifiers on the gun.
    */
   private static ArrayList<BleedoutModifier> getBleedoutModifiers(final GunModifier[] modifierSet)
   {
       final ArrayList<BleedoutModifier> mods = new ArrayList<>();
       for (GunModifier mod : modifierSet)
       {
           if (mod instanceof BleedoutModifier)
               mods.add((BleedoutModifier)mod);
       }
       return mods;
   }
   
    
    static private double calculateBleedoutDPS(final GunModifier[] modifierSet,
                                               final double totalShrapnelDamage)
    {            
        final ArrayList<BleedoutModifier> bleedoutMods = getBleedoutModifiers(modifierSet);
        double bleedoutDamageValue = 0;
        double bleedoutDamageShrapMultiplier = 0;

        for (BleedoutModifier mod : bleedoutMods)
        {
            bleedoutDamageValue += mod.getBleedoutDamageValuePerSecond();
            bleedoutDamageShrapMultiplier += mod.getBleedoutDamageMultiplerFromShrapnel();
        }

        return Math.max(0, bleedoutDamageValue 
                + (bleedoutDamageShrapMultiplier * totalShrapnelDamage));
    }
    
    /**
     * Calculates bleedout duration in seconds.
     * @param modifierSet
     * @param bleedoutMods
     * @return 
     */
    static private double calculateBleedoutDuration(final GunModifier[] modifierSet)
    {
        final ArrayList<BleedoutModifier> bleedoutMods = getBleedoutModifiers(modifierSet);
        double bleedoutDurationValue = 0;
        double bleedoutDurationMultiplier = 1.0;

        for (BleedoutModifier mod : bleedoutMods)
        {
            bleedoutDurationValue += mod.getBleedoutDurationValue();
            bleedoutDurationMultiplier += mod.getBleedoutDurationMultiplier();
        }

        return Math.max(0, bleedoutDurationValue * bleedoutDurationMultiplier);
    }
}
