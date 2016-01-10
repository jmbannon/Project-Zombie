/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.modifiers;

import java.util.ArrayList;
import net.projectzombie.crackshot_enhanced.custom_weapons.csv.CSVReader;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.BoltModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.GunModifier2;

/**
 *
 * @author jesse
 */
public class Bolt2 extends GunModifier2 implements BoltModifier
{
    static private Bolt2[] bolts = null;
    static private final String BOLTS_CSV_NAME = "Bolts.csv";
    static private final String[] BOLT_VALUES = {
        "display name",
        "material",
        "materialData",
        "price",
        "color",
        "duration multiplier"
    };
    
    private final double durationMultiplier;
    
    private Bolt2(final String displayName,
                  final String material,
                  final int materialByte,
                  final int price,
                  final String color,
                  final double durationMultiplier)
    {
        super(displayName, material, materialByte, price, color);
        this.durationMultiplier = durationMultiplier;
    }
    
    public Bolt2()
    {
        this(null, null, 0, 0, null, 0);
    }

    @Override public double getDurationMultiplier() { return durationMultiplier; }
    @Override public Bolt2[] getAll()               { return bolts; }
    @Override public Bolt2 getNullModifier()        { return new Bolt2(); }
    
    static public void initializeBolts()
    {
        if (bolts == null)
            bolts = buildBolts();
    }
    
    static private Bolt2[] buildBolts()
    {
        final CSVReader csv = new CSVReader(BOLTS_CSV_NAME, BOLT_VALUES);
        final int rowCount = csv.getRowCount();
        
        if (rowCount <= 0)
        {
            return new Bolt2[] { new Bolt2() };
        }
        
        final Bolt2[] toReturn            = new Bolt2[rowCount + 1];
        final String[] displayNames         = csv.getColumnString(0);
        final String[] materialNames        = csv.getColumnString(1);
        final int[]    materialBytes        = csv.getColumnInt(2);
        final int[]    price                = csv.getColumnInt(3);
        final String[] colors               = csv.getColumnString(4);
        final double[] durationMultiplie    = csv.getColumnDouble(5);
        
        toReturn[rowCount] = new Bolt2();
        for (int i = 0; i < rowCount; i++)
        {
            toReturn[i] = new Bolt2(displayNames[i],
                                      materialNames[i],
                                      materialBytes[i],
                                      price[i],
                                      colors[i],
                                      durationMultiplie[i]);
        }
        System.out.println("Initialized " + rowCount + " bolts.");
        return toReturn;
    }

    @Override
    public Bolt2[] valueOf(final String[] names,
                           final boolean includeNull)
    {
        final Bolt2[] values = this.getAll();
        final Bolt2[] toReturn;
        final ArrayList<Integer> indexes = this.getIndexes(names);
        final int size = indexes.size();
        int j = 0;
        
        if (size < 1)
        {
            if (includeNull)
                return new Bolt2[] { this.getNullModifier() };
            else
                return null;
        }
        else
        {
            if (includeNull)
            {
                toReturn = new Bolt2[size+1];
                toReturn[size] = this.getNullModifier();
            }
            else
            {
                toReturn = new Bolt2[size];
            }
            for (Integer i : indexes)
            {
                toReturn[j++] = values[i];
            }
            return toReturn;
        }
    }
}
