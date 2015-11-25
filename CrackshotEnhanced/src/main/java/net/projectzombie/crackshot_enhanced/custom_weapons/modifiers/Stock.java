/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.modifiers;

import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.BulletSpreadModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.GunModifier;

/**
 *
 * @author jesse
 */
public enum Stock implements GunModifier, BulletSpreadModifier
{

    ST_FOLDING   ("Folding"),
    ST_RECOIL_ABS("Recoil Absorber"),
    ST_TACTICAL  ("Tactical"),
    ST_FIBERGLASS("Fiberglass"),
    ST_SYNTHETIC ("Synthetic");

    private final String displayName;
    
    private Stock(final String displayName)
    {
        this.displayName = displayName;
    }
    
    @Override
    public int price()
    {
        return 10;
    }

    @Override
    public double getBulletSpreadBoost(int baseBulletSpread)
    {
        return 3;
    }

    @Override
    public String getDisplayName()
    {
        return displayName;
    }

    
}
