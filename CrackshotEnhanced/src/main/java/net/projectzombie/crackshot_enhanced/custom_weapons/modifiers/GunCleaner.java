/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.modifiers;

import net.projectzombie.crackshot_enhanced.custom_weapons.types.Type;
import org.bukkit.Material;

/**
 *
 * @author jesse
 */
public enum GunCleaner implements Type, CraftableModifier
{

    SOLVENT(),
    LUBR_OIL(),
    CLEANING_KIT();
    
    private GunCleaner()
    {
        
    }

    @Override
    public int getEnumValue() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String title() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int price() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double getBulletSpreadBoost(int baseBulletSpread) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getDamageBoost(int baseDamage) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Calculates an int based on Crackshot gun's maximum durability to be added
     * to a weapon's durability.
     * @param maximumDurability Maximum durability from a Crackshot gun skeleton.
     * @return Int to be added to a Crackshot gun's durability.
     */
    public int getDurabilityBoost(final int maximumDurability) 
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Material getMaterial() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public byte getMaterialData() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
