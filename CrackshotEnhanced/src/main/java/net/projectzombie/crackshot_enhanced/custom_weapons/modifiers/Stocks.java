/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.modifiers;

import net.projectzombie.crackshot_enhanced.custom_weapons.csv.CSVReader;
import net.projectzombie.crackshot_enhanced.custom_weapons.csv.CSVInput;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Stocks.Stock;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.BulletSpreadModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.GunModifier;

/**
 *
 * @author jesse
 */
public class Stocks extends CSVInput<Stock>
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
        "Display Name (STR)",
        "Material (INT)",
        "Material Data (INT)",
        "Price (INT)",
        "Color (STR)",
        "Bullet Spread Multiplier (DBL)",
        "Running Bullet Spread Multiplier (DBL)",
        "Running Speed Modifier (DBL)"
    };
    
    private Stocks()
    {
        super(STOCK_CSV_NAME, buildStocks(), STOCK_VALUES);
    }
    
    @Override
    public Stock getNullValue()
    {
        return new Stock();
    }
    
    /**
     * Builds all stocks, if any. Allowed to not have any in CSV.
     * @return Array of all stocks (including null stock).
     */
    static private Stock[] buildStocks()
    {
        final CSVReader csv = new CSVReader(STOCK_CSV_NAME, STOCK_VALUES);
        final int rowCount = csv.getRowCount();
        
        if (rowCount <= 0)
        {
            return new Stock[] { new Stock() };
        }
        
        int j = 0;
        final Stock[] toReturn             = new Stock[rowCount + 1];
        final String[] displayNames         = csv.getColumnString(j++);
        final String[] materialNames        = csv.getColumnString(j++);
        final int[]    materialBytes        = csv.getColumnInt(j++);
        final int[]    price                = csv.getColumnInt(j++);
        final String[] colors               = csv.getColumnString(j++);
        final double[] bulletSpreadMultiplier = csv.getColumnDouble(j++);
        final double[] runningBulletSpreadMultiplier = csv.getColumnDouble(j++);
        final double[] runningSpeedMultiplier = csv.getColumnDouble(j++);
        
        toReturn[rowCount] = new Stock();
        for (int i = 0; i < rowCount; i++)
        {
            toReturn[i] = new Stock(
                    displayNames[i],
                    materialNames[i],
                    materialBytes[i],
                    price[i],
                    colors[i],
                    bulletSpreadMultiplier[i],
                    runningBulletSpreadMultiplier[i],
                    runningSpeedMultiplier[i]);
        }
        return toReturn;
    }
    
    static public class Stock extends GunModifier implements BulletSpreadModifier
    {
        private final double bulletSpreadMultiplier;
        private final double runningBulletSpreadMultiplier;
        private final double runningSpeedMultiplier;

        private Stock(final String displayName,
                       final String material,
                       final int materialData,
                       final int price,
                       final String color,
                       final double bulletSpreadMultiplier,
                       final double runningBulletSpreadMultiplier,
                       final double runningSpeedMultiplier)
        {
            super(displayName, material, materialData, price, color);
            this.bulletSpreadMultiplier = bulletSpreadMultiplier;
            this.runningBulletSpreadMultiplier = runningBulletSpreadMultiplier;
            this.runningSpeedMultiplier = runningSpeedMultiplier;
        }

        private Stock() { this(null, null, 0, 0, null, 0, 0, 0); }
        
        public double getRunningBulletSpreadMultiplier() { return runningBulletSpreadMultiplier; }
        public double getRunningSpeedMultiplier()        { return runningSpeedMultiplier; }
        @Override public Stock getNullModifier()         { return singleton.getNullValue(); }
        @Override public double getBulletSpreadMultiplier() { return bulletSpreadMultiplier; }
    }
}
