/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.modifiers;

import org.bukkit.Material;


/**
 *
 * @author jesse
 */
public interface CraftableModifier
{
    /**
     * @return Returns the price of the gun modification at the gunsmith.
     */
    public int price();
    
    /**
     * @return Name of gun modification.
     */
    public String name();
    
    /**
     * Calculates a double based on a Crackshot gun's bullet spread to be subtracted
     * from the modified bullet spread.
     * @param baseBulletSpread Initial bullet spread from a Crackshot gun skeleton.
     * @return Double to be subtracted from bullet spread.
     */
    public double getBulletSpreadBoost(final int baseBulletSpread);
    
    /**
     * Calculates an int based on a Crackshot gun's base damage to be added to
     * the modified bullet spread.
     * @param baseDamage Initial damage from a Crackshot gun.
     * @return Int to be added to damage.
     */
    public int getDamageBoost(final int baseDamage);
    
    public Material getMaterial();
    public byte     getMaterialData();
    
}
