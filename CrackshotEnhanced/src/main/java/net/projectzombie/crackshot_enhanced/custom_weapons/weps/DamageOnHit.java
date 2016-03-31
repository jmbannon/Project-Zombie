/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.weps;

import java.util.ArrayList;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Modifier;

/**
 *
 * @author jb
 * @param <T>
 */
public abstract class DamageOnHit<T extends Modifier> extends GunModifierSet<T>
{
    private final double totalDamage;
    private final double damageValue;
    private final double damageMultiplier;
    
    public DamageOnHit(ArrayList<T> modifiers,
                       final double damageValue,
                       final double damageMultiplier)
    {
        super(modifiers);
        this.damageValue = damageValue;
        this.damageMultiplier = damageMultiplier;
        this.totalDamage = Math.max(0.0, damageValue * damageMultiplier);
    }
    
    public double getValue()      { return damageValue; }
    public double getMultiplier() { return damageMultiplier; }
    public double getTotal()      { return totalDamage; }
}
