/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types;

/**
 *
 * @author jesse
 */
public interface DamageModifier
{
    /**
     * Value - increase or decrease to base damage.
     * @return Double to be added to damage.
     */
    public double getDamageValue();
    
    /**
     * Multiplier - percentage to modify base damage.
     * @return Percentage to be multiplied to base damage (0, inf).
     */
    public double getDamageMultiplier();
    
    public double getHeadshotDamageValue();
    
    public double getHeadshotDamageMultiplier();
}
