/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.modifiers;

import net.projectzombie.crackshot_enhanced.custom_weapons.csv.CSVReader;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.BulletSpreadModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.GunModifier2;

/**
 *
 * @author jesse
 */
public class Stock2 extends GunModifier2 implements BulletSpreadModifier
{
    static private final Stock2[] stocks = buildStocks();
    static private final String STOCK_CSV_NAME = "Scopes.csv";
    static private final String[] STOCK_VALUES = {
        "display name",
        "material",
        "materialData",
        "price",
        "color",
        "bullet spread multiplier"
    }; 
    
    private final double bulletSpreadMultiplier;
    
    private Stock2(final String displayName,
                   final String material,
                   final int materialData,
                   final int price,
                   final String color,
                   final double bulletSpreadMultiplier)
    {
        super(displayName, material, materialData, price, color);
        this.bulletSpreadMultiplier = bulletSpreadMultiplier;
    }

    public Stock2()
    {
        this(null, null, 0, 0, null, 0);
    }

    @Override
    public double getBulletSpreadMultiplier()
    {
        return bulletSpreadMultiplier;
    }

    @Override
    public GunModifier2[] getAll() {
        return stocks;
    }

    @Override
    public GunModifier2 getNullModifier() {
        return new Stock2();
    }
    
    static private Stock2[] buildStocks()
    {
        final CSVReader csv = new CSVReader(STOCK_CSV_NAME, STOCK_VALUES);
        final int rowCount = csv.getRowCount();
        
        if (rowCount <= 0)
        {
            return new Stock2[] { new Stock2() };
        }
        
        final Stock2[] toReturn          = new Stock2[rowCount + 1];
        final String[] displayNames         = csv.getColumnString(0);
        final String[] materialNames        = csv.getColumnString(1);
        final int[]    materialBytes        = csv.getColumnInt(2);
        final int[]    price                = csv.getColumnInt(3);
        final String[] colors               = csv.getColumnString(4);
        final double[] bulletSpreadModifier = csv.getColumnDouble(5);
        
        toReturn[rowCount] = new Stock2();
        for (int i = 0; i < rowCount; i++)
        {
            toReturn[i] = new Stock2(displayNames[i],
                                      materialNames[i],
                                      materialBytes[i],
                                      price[i],
                                      colors[i],
                                      bulletSpreadModifier[i]);
        }
        System.out.println("Initialized " + rowCount + " scopes.");
        return toReturn;
    }
}
