/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.modifiers;

import java.util.ArrayList;
import net.projectzombie.crackshot_enhanced.custom_weapons.csv.CSVReader;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.BulletSpreadModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.GunModifier2;

/**
 *
 * @author jesse
 */
public class Stock2 extends GunModifier2 implements BulletSpreadModifier
{
    static private Stock2[] stocks = null;
    static private final String STOCK_CSV_NAME = "Stocks.csv";
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

    public Stock2() { this(null, null, 0, 0, null, 0); }
    @Override public double getBulletSpreadMultiplier() { return bulletSpreadMultiplier; }
    @Override public Stock2[] getAll()        { return stocks; }
    @Override public Stock2 getNullModifier() { return new Stock2(); }
    
    static public void initializeStocks()
    {
        if (stocks == null)
            stocks = buildStocks();
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
        System.out.println("Initialized " + rowCount + " stocks.");
        return toReturn;
    }
    
    @Override
    public Stock2[] valueOf(final String[] names,
                               final boolean includeNull)
    {
        final Stock2[] values = this.getAll();
        final Stock2[] toReturn;
        final ArrayList<Integer> indexes = this.getIndexes(names);
        final int size = indexes.size();
        int j = 0;
        
        if (size < 1)
        {
            if (includeNull)
                return new Stock2[] { this.getNullModifier() };
            else
                return null;
        }
        else
        {
            if (includeNull)
            {
                toReturn = new Stock2[size+1];
                toReturn[size] = this.getNullModifier();
            }
            else
            {
                toReturn = new Stock2[size];
            }
            for (Integer i : indexes)
            {
                toReturn[j++] = values[i];
            }
            return toReturn;
        }
    }
}
