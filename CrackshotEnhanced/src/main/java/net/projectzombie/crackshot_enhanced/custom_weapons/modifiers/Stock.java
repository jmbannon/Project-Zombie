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
    NULL_STOCK  (null),
    IVORY_GRIP  ("Ivory Grip"),
    ST_FOLDING  ("Folding Stock"),
    RECOIL_ABS  ("Recoil Absorber Stock"),
    TACTICAL    ("Tactical Stock"),
    FIBERGLASS  ("Fiberglass Stock"),
    SYNTHETIC   ("Synthetic Stock");

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
    public String getDisplayName()
    {
        return displayName;
    }

    @Override
    public double getBulletSpreadModifyPercentage()
    {
        return 1.0;
    }
    
}
