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
public interface GunModifier
{
    /**
     * @return Returns the price of the gun modification at the gunsmith.
     */
    public int price();
    
    /**
     * @return Name of gun modification.
     */
    public String getDisplayName();
    
    
}
