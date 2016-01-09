/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.modifiers;

import net.projectzombie.crackshot_enhanced.custom_weapons.csv.CSVReader;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.BulletSpreadModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.DamageModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.GunModifier2;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.ProjectileModifier;

/**
 *
 * @author jesse
 */
public class Barrel2 extends GunModifier2 implements BulletSpreadModifier, DamageModifier, ProjectileModifier
{
    static private final Barrel2[] barrels = buildBarrels();
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
    
    private Barrel2(final String displayName,
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
    }
    
    public Barrel2()
    {
        this(null, null, 0, 0, null, 0, 0, 0, 0);
    }
    
    @Override public double getDamageValue()             { return 0.0; }
    @Override public double getDamageMultiplier()        { return 1.0; }
    @Override public int getAdditionalProjectileAmount() { return 0; }
    @Override public double getBulletSpreadMultiplier()  { return 1.0; }
    @Override public GunModifier2[] getAll()             { return barrels; }
    @Override public GunModifier2 getNullModifier()      { return new Barrel2(); }
    
    static private Barrel2[] buildBarrels()
    {
        final CSVReader csv = new CSVReader(BARREL_CSV_NAME, BARREL_VALUES);
        final int rowCount = csv.getRowCount();
        
        if (rowCount <= 0)
        {
            return new Barrel2[] { new Barrel2() };
        }
        
        final Barrel2[] toReturn            = new Barrel2[rowCount + 1];
        final String[] displayNames         = csv.getColumnString(0);
        final String[] materialNames        = csv.getColumnString(1);
        final int[]    materialBytes        = csv.getColumnInt(2);
        final int[]    price                = csv.getColumnInt(3);
        final String[] colors               = csv.getColumnString(4);
        final double[] damageValues         = csv.getColumnDouble(5);
        final double[] damageMultipliers    = csv.getColumnDouble(6);
        final int[] additionalProjectiles   = csv.getColumnInt(7);
        final double[] bulletSpreadModifier = csv.getColumnDouble(8);
        
        toReturn[rowCount] = new Barrel2();
        for (int i = 0; i < rowCount; i++)
        {
            toReturn[i] = new Barrel2(displayNames[i],
                                      materialNames[i],
                                      materialBytes[i],
                                      price[i],
                                      colors[i],
                                      damageValues[i],
                                      damageMultipliers[i],
                                      additionalProjectiles[i],
                                      bulletSpreadModifier[i]);
        }
        System.out.println("Initialized " + rowCount + " attatchments.");
        return toReturn;
    }

    
}
