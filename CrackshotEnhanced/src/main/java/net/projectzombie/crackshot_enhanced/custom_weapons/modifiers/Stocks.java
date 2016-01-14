/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.modifiers;

import net.projectzombie.crackshot_enhanced.custom_weapons.csv.CSVReader;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.BulletSpreadModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.csv.CSVInput;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.GunModifier2;

/**
 *
 * @author jesse
 */
public class Stocks extends CSVInput
{
    static private Stocks singleton = null;
    static public Stocks getInstance()
    {
        if (singleton == null)
            singleton = new Stocks();
        return singleton;
    }
    
    static private final String STOCK_CSV_NAME = "Stocks.csv";
    static private final String[] STOCK_VALUES = {
        "display name",
        "material",
        "materialData",
        "price",
        "color",
        "bullet spread multiplier"
    };
    
    private Stocks()
    {
        super(STOCK_CSV_NAME, buildStocks(), STOCK_VALUES);
    }
    
    @Override
    public Stock2 getNullValue()
    {
        return new Stock2();
    }
    
    /**
     * Builds all stocks, if any. Allowed to not have any in CSV.
     * @return Array of all stocks (including null stock).
     */
    static private Stock2[] buildStocks()
    {
        final CSVReader csv = new CSVReader(STOCK_CSV_NAME, STOCK_VALUES);
        final int rowCount = csv.getRowCount();
        
        if (rowCount <= 0)
        {
            return new Stock2[] { new Stock2() };
        }
        
        int j = 0;
        final Stock2[] toReturn             = new Stock2[rowCount + 1];
        final String[] displayNames         = csv.getColumnString(j++);
        final String[] materialNames        = csv.getColumnString(j++);
        final int[]    materialBytes        = csv.getColumnInt(j++);
        final int[]    price                = csv.getColumnInt(j++);
        final String[] colors               = csv.getColumnString(j++);
        final double[] bulletSpreadModifier = csv.getColumnDouble(j++);
        
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
        System.out.println("Initialized " + rowCount + " stocks.");
        return toReturn;
    }
    
    static public class Stock2 extends GunModifier2 implements BulletSpreadModifier
    {
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

        private Stock2() { this(null, null, 0, 0, null, 0); }
        
        @Override public double getBulletSpreadMultiplier() { return bulletSpreadMultiplier; }
        @Override public Stock2 getNullModifier() { return singleton.getNullValue(); }
    }
}
