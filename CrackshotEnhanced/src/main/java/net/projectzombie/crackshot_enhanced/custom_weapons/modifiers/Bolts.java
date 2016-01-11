/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.modifiers;

import java.util.ArrayList;
import net.projectzombie.crackshot_enhanced.custom_weapons.csv.CSVReader;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.BoltModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.CSVInput;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.GunModifier2;

/**
 *
 * @author jesse
 */
public class Bolts extends CSVInput
{
    static private Bolts singleton = null;
    static public Bolts getInstance()
    {
        if (singleton == null)
            singleton = new Bolts();
        return singleton;
    }
    
    static private final String BOLTS_CSV_NAME = "Bolts.csv";
    static private final String[] BOLT_VALUES = {
        "display name",
        "material",
        "materialData",
        "price",
        "color",
        "duration multiplier"
    };
    
    private final Bolt2[] bolts;
    
    private Bolts()
    {
        super(BOLTS_CSV_NAME, BOLT_VALUES);
        bolts = buildBolts();
    }
    
    @Override
    public Bolt2[] getAll()
    {
        return bolts;
    }

    @Override
    public Bolt2[] get(final String[] names)
    {
        return get(names, false);
    }
    
    @Override
    public Bolt2 get(String name)
    {
        final int index = super.getIndex(name);
        if (index == -1)
            return null;
        else
            return bolts[index];
    }

    @Override
    public Bolt2[] get(final String[] names,
                       final boolean includeNull)
    {
        final Bolt2 toReturn[];
        final ArrayList<Integer> indexes = super.getIndexes(names);
        int j = 0;
        
        if (indexes == null || indexes.isEmpty())
            return new Bolt2[] { new Bolt2() };
        else
        {
            if (includeNull)
            {
                toReturn = new Bolt2[indexes.size() + 1];
                toReturn[indexes.size()] = new Bolt2();
            }
            else
            {
                toReturn = new Bolt2[indexes.size()];
            }
            for (Integer i : indexes)
            {
                toReturn[j++] = bolts[i];
            }
            return toReturn;
        }
    }
    
    /**
    * Builds all bolts, if any. Allowed to not have any in CSV.
    * @return Array of all bolts (including null bolt).
    */
    private Bolt2[] buildBolts()
    {
        final CSVReader csv = new CSVReader(BOLTS_CSV_NAME, BOLT_VALUES);
        final int rowCount = csv.getRowCount();
 
        if (rowCount <= 0)
        {
            return new Bolt2[] { new Bolt2() };
        }
 
        int j = 0;
        final Bolt2[] toReturn            = new Bolt2[rowCount + 1];
        final String[] displayNames         = csv.getColumnString(j++);
        final String[] materialNames        = csv.getColumnString(j++);
        final int[]    materialBytes        = csv.getColumnInt(j++);
        final int[]    price                = csv.getColumnInt(j++);
        final String[] colors               = csv.getColumnString(j++);
        final double[] durationMultiplie    = csv.getColumnDouble(j++);
 
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
    
    public class Bolt2 extends GunModifier2 implements BoltModifier
    {
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

        private Bolt2()
        {
            this(null, null, 0, 0, null, 0);
        }

        @Override public double getDurationMultiplier() { return durationMultiplier; }
        @Override public Bolt2 getNullModifier()        { return new Bolt2(); }
    }
}
