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
public interface CritModifier
{
    /**
     * Returns the crit chance percentage modifier.
     * @return Percent modifier of crit chance (0, inf).
     */
    public double getCritChance();
    
    /**
     * Returns the crit strike percentage modifier.
     * @return Percent modifier of crit strike (0, inf).
     */
    public double getCritStrike();
}
