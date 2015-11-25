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
     * Calculates an int based on a Crackshot gun's base damage to be added to
     * the modified bullet spread.
     * @param baseDamage Initial damage from a Crackshot gun.
     * @return Int to be added to damage.
     */
    public int getDamageBoost(final int baseDamage);
}