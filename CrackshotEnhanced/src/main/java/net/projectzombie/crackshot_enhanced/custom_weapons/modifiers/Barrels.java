/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.modifiers;

import net.projectzombie.crackshot_enhanced.custom_weapons.csv.CSVReader;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Barrels.Barrel;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.BulletSpreadModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.csv.CSVInput;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.BleedoutModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.CritModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.DamageModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.DurabilityModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.GunModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.IncendiaryModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.ProjectileModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.Shrapnel;

/**
 *
 * @author jesse
 */
public class Barrels extends CSVInput<Barrel>
{
    static private Barrels singleton = null;
    static public Barrels getInstance()
    {
        if (singleton == null)
            singleton = new Barrels();
        return singleton;
    }
    
    static private final String BARREL_CSV_NAME = "Barrels.csv";
    static private final String[] BARREL_VALUES = {
        "Display Name (STR)",
        "Material (INT)",
        "Material Data (INT)",
        "Price (INT)",
        "Color (STR)",
        "Is Silencer (T/F)",
        "BulletSpreadModifier (DBL)",
        "Damage Value (DBL)",
        "Damage Multiplier (DBL)",
        "Headshot Modifier (DBL)",
        "Headshot Multiplier (DBL)",
        "AdditionalProjectiles (INT)",
        "Projectile Speed Multiplier (DBL)",
        "Projectile Range Modifier (INT)",
        "Projectile Range Multiplier (DBL)",
        "Interval Between Shot Multiplier (DBL)"
    };

    private Barrels()
    {
        super(BARREL_CSV_NAME, buildBarrels(), BARREL_VALUES);
    }
    
    @Override
    public Barrel getNullValue()
    {
        return new Barrel();
    }
    
    /**
    * Builds all barrels, if any. Allowed to not have any in CSV.
    * @return Array of all barrels (including null barrel).
    */
    static private Barrel[] buildBarrels()
    {
        final CSVReader csv = new CSVReader(BARREL_CSV_NAME, BARREL_VALUES);
        final int rowCount = csv.getRowCount();
 
        if (rowCount <= 0)
        {
            return new Barrel[] { new Barrel() };
        }
 
        int j = 0;
        final Barrel[] toReturn                      = new Barrel[rowCount + 1];
        final String[] displayName                   = csv.getColumnString(j++);
        final String[] material                      = csv.getColumnString(j++);
        final int[]    materialByte                  = csv.getColumnInt(j++);
        final int[]    price                         = csv.getColumnInt(j++);
        final String[] color                         = csv.getColumnString(j++);
        final boolean[] silencer                     = csv.getColumnBoolean(j++);
        final double[] bulletSpreadModifier          = csv.getColumnDouble(j++);
        final double[] damageValue                   = csv.getColumnDouble(j++);
        final double[] damageMultiplier              = csv.getColumnDouble(j++);
        final double[] headshotValue                 = csv.getColumnDouble(j++);
        final double[] headshotMultiplier            = csv.getColumnDouble(j++);
        final int[]    additionalProjectiles         = csv.getColumnInt(j++);
        final double[] projectileSpeedMultiplier     = csv.getColumnDouble(j++);
        final int[]    projectileRangeValue          = csv.getColumnInt(j++);
        final double[] projectileRangeMultiplier     = csv.getColumnDouble(j++);
        final double[] intervalBetweenShotMultiplier = csv.getColumnDouble(j++);
        
 
        toReturn[rowCount] = new Barrel();
        for (int i = 0; i < rowCount; i++)
        {
            toReturn[i] = new Barrel(
                displayName[i],              
                material[i],                     
                materialByte[i],                 
                price[i],                        
                color[i],
                silencer[i],
                bulletSpreadModifier[i],         
                damageValue[i],                  
                damageMultiplier[i],             
                headshotValue[i],                
                headshotMultiplier[i],           
                additionalProjectiles[i],        
                projectileSpeedMultiplier[i],    
                projectileRangeValue[i],         
                projectileRangeMultiplier[i],    
                intervalBetweenShotMultiplier[i]
            );
        }
        return toReturn;
    }
    

    static public class Barrel extends GunModifier implements BulletSpreadModifier,
                                                              DamageModifier,
                                                              ProjectileModifier
    {
        private final double bulletSpreadModifier;
        private final double damageValue;
        private final double damageMultiplier;
        private final double headshotValue;
        private final double headshotMultiplier;
        private final int    additionalProjectiles;
        private final double projectileSpeedMultiplier;
        private final int    projectileRangeValue;
        private final double projectileRangeMultiplier;
        private final double intervalBetweenShotMultiplier;
        private final boolean isSilencer;
        

        private Barrel(final String  displayName,
                        final String material,
                        final int    materialByte,
                        final int    price,
                        final String color,
                        final boolean isSilencer,
                        final double bulletSpreadModifier,
                        final double damageValue,
                        final double damageMultiplier,
                        final double headshotValue,
                        final double headshotMultiplier,
                        final int    additionalProjectiles,
                        final double projectileSpeedMultiplier,
                        final int    projectileRangeValue,
                        final double projectileRangeMultiplier,
                        final double intervalBetweenShotMultiplier)
        {
            super(displayName, material, materialByte, price, color);
            this.isSilencer = isSilencer;
            this.bulletSpreadModifier = bulletSpreadModifier;
            this.damageValue = damageValue;
            this.damageMultiplier = damageMultiplier;
            this.headshotValue = headshotValue;
            this.headshotMultiplier = headshotMultiplier;
            this.additionalProjectiles = additionalProjectiles;
            this.projectileSpeedMultiplier = projectileSpeedMultiplier;
            this.projectileRangeValue = projectileRangeValue;
            this.projectileRangeMultiplier = projectileRangeMultiplier;
            this.intervalBetweenShotMultiplier = intervalBetweenShotMultiplier;
        }

        public Barrel()
        {
            this(null, null, 0, 0, null, false, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        }

        public boolean isSilencer()                          { return isSilencer; }
        @Override public double getDamageValue()             { return damageValue; }
        @Override public double getDamageMultiplier()        { return damageMultiplier; }
        @Override public int getProjectileValue()            { return additionalProjectiles; }
        @Override public double getBulletSpreadMultiplier()  { return bulletSpreadModifier; }
        @Override public Barrel getNullModifier()            { return singleton.getNullValue(); }
        @Override public double getHeadshotDamageModifier()  { return headshotValue; }
        @Override public double getHeadshotDamageMultiplier() { return headshotMultiplier; }
        @Override public double getProjectileSpeedMultiplier() { return projectileSpeedMultiplier; }
        @Override public int getProjectileRangeValue()         { return projectileRangeValue; }
        @Override public double getProjectileRangeMultiplier() { return projectileRangeMultiplier; }
        @Override public double getIntervalBetweenShotModifier() { return intervalBetweenShotMultiplier; }
    }
}
