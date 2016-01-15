/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.modifiers;

import net.projectzombie.crackshot_enhanced.custom_weapons.csv.CSVReader;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Bolts.Bolt2;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.BoltModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.csv.CSVInput;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.GunModifier2;

/**
 *
 * @author jesse
 */
public class Bolts extends CSVInput<Bolt2>
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
    
    private Bolts()
    {
        super(BOLTS_CSV_NAME, buildBolts(), BOLT_VALUES);
    }
    
    @Override
    public Bolt2 getNullValue()
    {
        return new Bolt2();
    }
    
    /**
    * Builds all bolts, if any. Allowed to not have any in CSV.
    * @return Array of all bolts (including null bolt).
    */
    static private Bolt2[] buildBolts()
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
        return toReturn;
    }
    
    static public class Bolt2 extends GunModifier2 implements BoltModifier
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
        @Override public Bolt2 getNullModifier()        { return singleton.getNullValue(); }
    }
}
