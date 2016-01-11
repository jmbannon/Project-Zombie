/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.modifiers;

import java.util.ArrayList;
import net.projectzombie.crackshot_enhanced.custom_weapons.csv.CSVReader;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.BulletSpreadModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.CSVInput;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.CSVValue;
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
    
    private Stock2[] stocks;
    
    private Stocks()
    {
        super(STOCK_CSV_NAME, STOCK_VALUES);
        this.stocks = buildStocks();
    }
    
    @Override
    public Stock2[] getAll()
    {
        return stocks;
    }

    @Override
    public Stock2[] get(final String[] names)
    {
        return get(names, false);
    }
    
    @Override
    public Stock2 get(String name)
    {
        final int index = super.getIndex(name);
        if (index == -1)
            return null;
        else
            return stocks[index];
    }

    @Override
    public Stock2[] get(final String[] names,
                        final boolean includeNull)
    {
        final Stock2 toReturn[];
        final ArrayList<Integer> indexes = super.getIndexes(names);
        int j = 0;
        
        if (indexes == null || indexes.isEmpty())
            return null;
        else
        {
            toReturn = new Stock2[indexes.size()];
            for (Integer i : indexes)
            {
                toReturn[j++] = stocks[i];
            }
            return toReturn;
        }
    }
    
    /**
     * Builds all stocks, if any. Allowed to not have any in CSV.
     * @return Array of all stocks (including null stock).
     */
    private Stock2[] buildStocks()
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
    
    public class Stock2 extends GunModifier2 implements BulletSpreadModifier
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

        public Stock2() { this(null, null, 0, 0, null, 0); }
        @Override public double getBulletSpreadMultiplier() { return bulletSpreadMultiplier; }
        @Override public Stock2 getNullModifier() { return new Stock2(); }
    }
}
