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
     * Calculates a double based on a Crackshot gun's bullet spread to be subtracted
     * from the modified bullet spread.
     * @param baseBulletSpread Initial bullet spread from a Crackshot gun skeleton.
     * @return Double to be subtracted from bullet spread.
     */
    public double getBulletSpreadBoost(final int baseBulletSpread);
}
