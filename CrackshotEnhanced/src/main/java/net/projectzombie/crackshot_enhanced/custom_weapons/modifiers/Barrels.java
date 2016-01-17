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
        "display name",
        "material",
        "materialData",
        "price",
        "color",
        "damageValue",
        "damageMultiplier",
        "additional projectiles",
        "bulletSpread modifier",
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
        final Barrel[] toReturn            = new Barrel[rowCount + 1];
        final String[] displayNames         = csv.getColumnString(j++);
        final String[] materialNames        = csv.getColumnString(j++);
        final int[]    materialBytes        = csv.getColumnInt(j++);
        final int[]    price                = csv.getColumnInt(j++);
        final String[] colors               = csv.getColumnString(j++);
        final double[] damageValues         = csv.getColumnDouble(j++);
        final double[] damageMultipliers    = csv.getColumnDouble(j++);
        final int[] additionalProjectiles   = csv.getColumnInt(j++);
        final double[] bulletSpreadModifier = csv.getColumnDouble(j++);
 
        toReturn[rowCount] = new Barrel();
        for (int i = 0; i < rowCount; i++)
        {
            toReturn[i] = new Barrel(displayNames[i],
                                      materialNames[i],
                                      materialBytes[i],
                                      price[i],
                                      colors[i],
                                      damageValues[i],
                                      damageMultipliers[i],
                                      additionalProjectiles[i],
                                      bulletSpreadModifier[i]);
        }
        return toReturn;
    }
    

    static public class Barrel extends GunModifier implements BulletSpreadModifier,
                                                              DamageModifier,
                                                              ProjectileModifier,
                                                              IncendiaryModifier,
                                                              BleedoutModifier,
                                                              CritModifier,
                                                              DurabilityModifier,
                                                              Shrapnel
    {
        private final double damageValue;
        private final double damageMultiplier;
        private final int    additionalProjectiles;
        private final double bulletSpreadModifier;

        private Barrel(final String displayName,
                        final String material,
                        final int materialByte,
                        final int price,
                        final String color,
                        final double damageValue,
                        final double damageMultiplier,
                        final int additionalProjectiles,
                        final double bulletSpreadModifier)
        {
            super(displayName, material, materialByte, price, color);
            this.damageValue = damageValue;
            this.damageMultiplier = damageMultiplier;
            this.additionalProjectiles = additionalProjectiles;
            this.bulletSpreadModifier = bulletSpreadModifier;
        }

        public Barrel()
        {
            this(null, null, 0, 0, null, 0, 0, 0, 0);
        }

        @Override public double getDamageValue()             { return damageValue; }
        @Override public double getDamageMultiplier()        { return damageMultiplier; }
        @Override public int    getProjectileValue()         { return additionalProjectiles; }
        @Override public double getBulletSpreadMultiplier()  { return bulletSpreadModifier; }
        @Override public Barrel getNullModifier()            { return singleton.getNullValue(); }
        @Override public double getHeadshotDamageValue()     { return 0; }
        @Override public double getHeadshotDamageMultiplier()  { return 0; }
        @Override public double getProjectileSpeedMultiplier() { return 0; }
        @Override public int    getProjectileRangeValue()      { return 0; }
        @Override public double getProjectileRangeModifier()     { return 0; }
        @Override public double getIntervalBetweenShotModifier() { return 0; }
        @Override public double getFireDamageValue()             { return 0; }
        @Override public double getFireDamageMultiplier()        { return 0; }
        @Override public double getIgniteChance()                { return 0; }
        @Override public double getIgniteDuration()              { return 0; }
        @Override public double getBleedoutDurationValue()       { return 0; }
        @Override public double getBleedoutDurationMultiplier()          { return 0; }
        @Override public double getBleedoutDamageValuePerSecond()        { return 0; }
        @Override public double getBleedoutDamageMultiplierFromDamage()  { return 0; }
        @Override public double getBleedoutDamageMultiplerFromShrapnel() { return 0; }
        @Override public double getCritChance()                          { return 0; }
        @Override public double getCritStrike()                          { return 0; }
        @Override public int    getDurabilityValue()                     { return 0; }
        @Override public double getDurabilityIncrease()                  { return 0; }
        @Override public double getShrapnelDamageValue()                 { return 0; }
        @Override public double getShrapnelDamageMultiplier()  { return 0; }
        @Override public double getStunChance()                { return 0; }
        @Override public double getStunDuration()              { return 0; }
    }
}
