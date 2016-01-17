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
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.DamageModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.GunModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.ProjectileModifier;

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
                                                              ProjectileModifier
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
        @Override public int getProjectileValue() { return additionalProjectiles; }
        @Override public double getBulletSpreadMultiplier()  { return bulletSpreadModifier; }
        @Override public Barrel getNullModifier()      { return singleton.getNullValue(); }
    }
}
