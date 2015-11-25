/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.modifiers;

import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.GunModifier;

/**
 *
 * @author jesse
 */
public enum Magazine implements GunModifier
{
    MAG_QUICK("Quick"),
    MAG_FAST("Fast"),
    MAG_ALTERED("Altered"),
    MAG_EXTENDED("Extended"),
    MAG_DRUM("Drum"),
    MAG_BAND("Bandolier");

    private final String displayName;
    
    private Magazine(final String displayName)
    {
        this.displayName = displayName;
    }
    
    public int getMagBoost()
    {
        return 5;
    }
    
    public int getReloadBoost()
    {
        return 0;
    }
    
    @Override
    public int price()
    {
        return 0;
    }

    @Override
    public String getDisplayName()
    {
        return displayName;
    }
    
}
