/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.modifiers;

import net.projectzombie.crackshot_enhanced.custom_weapons.csv.CSVReader;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Bolts.Bolt;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.BoltModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.csv.CSVInput;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.GunModifier;

/**
 *
 * @author jesse
 */
public class Bolts extends CSVInput<Bolt>
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
    public Bolt getNullValue()
    {
        return new Bolt();
    }
    
    /**
    * Builds all bolts, if any. Allowed to not have any in CSV.
    * @return Array of all bolts (including null bolt).
    */
    static private Bolt[] buildBolts()
    {
        final CSVReader csv = new CSVReader(BOLTS_CSV_NAME, BOLT_VALUES);
        final int rowCount = csv.getRowCount();
 
        if (rowCount <= 0)
        {
            return new Bolt[] { new Bolt() };
        }
 
        int j = 0;
        final Bolt[] toReturn            = new Bolt[rowCount + 1];
        final String[] displayNames         = csv.getColumnString(j++);
        final String[] materialNames        = csv.getColumnString(j++);
        final int[]    materialBytes        = csv.getColumnInt(j++);
        final int[]    price                = csv.getColumnInt(j++);
        final String[] colors               = csv.getColumnString(j++);
        final double[] durationMultiplie    = csv.getColumnDouble(j++);
 
        toReturn[rowCount] = new Bolt();
        for (int i = 0; i < rowCount; i++)
        {
            toReturn[i] = new Bolt(displayNames[i],
                                      materialNames[i],
                                      materialBytes[i],
                                      price[i],
                                      colors[i],
                                      durationMultiplie[i]);
        }
        return toReturn;
    }
    
    static public class Bolt extends GunModifier implements BoltModifier
    {
        private final double durationMultiplier;

        private Bolt(final String displayName,
                      final String material,
                      final int materialByte,
                      final int price,
                      final String color,
                      final double durationMultiplier)
        {
            super(displayName, material, materialByte, price, color);
            this.durationMultiplier = durationMultiplier;
        }

        private Bolt()
        {
            this(null, null, 0, 0, null, 0);
        }

        @Override public double getBoltActionDurationMultiplier() { return durationMultiplier; }
        @Override public Bolt getNullModifier()        { return singleton.getNullValue(); }
    }
}
