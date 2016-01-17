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
public interface BulletSpreadModifier
{
    /**
     * Multiplier - percentage to modifiy the bullet spread.
     * @return Percentage to modifiy the bullet spread (0, inf).
     */
    public double getBulletSpreadMultiplier();
}
