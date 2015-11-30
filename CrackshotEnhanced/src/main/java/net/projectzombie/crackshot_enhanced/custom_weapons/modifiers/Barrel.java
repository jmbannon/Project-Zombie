/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.modifiers;

import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.BulletSpreadModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.DamageModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.GunModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.ProjectileAmountModifier;

/**
 *
 * @author jesse
 */
public enum Barrel implements GunModifier, BulletSpreadModifier, DamageModifier, ProjectileAmountModifier
{
    NULL_BARREL(null),
    
    // Pistol
    THREADED("Threaded Barrel"),
    PRECISION("Precision Barrel"),
    
    // Rifle
    EXTENDED("Extended Barrel"),
    SHORT("Short Barrel"),
    
    // Hunting rifle
    BARTLEIN("Bartlein Barrel"),
    KRIEGER("Krieger Barrel"),
    BRUX("Brux Barrel"),
    
    // Shotguns
    SMOOTH_BORE("Smooth Bore Barrel"),
    RIFLED("Rifled Barrel"),
    SAWED_OFF("Sawed Off Barrel");
    
    private final String displayName;
    
    private Barrel(final String displayName)
    {
        this.displayName = displayName;
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

    @Override
    public double getBulletSpreadBoost(double baseBulletSpread)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double getBulletSpreadModifyPercentage(double baseBulletSpread)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double getDamageBoost(double baseDamage)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double getDamageModifyPercentage(double baseDamage)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getAdditionalProjectileAmount()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
