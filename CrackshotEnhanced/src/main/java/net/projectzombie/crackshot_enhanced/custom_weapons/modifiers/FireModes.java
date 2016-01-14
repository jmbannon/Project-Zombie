/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.modifiers;

import net.projectzombie.crackshot_enhanced.custom_weapons.csv.CSVReader;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.FireModes.FireMode2;
import net.projectzombie.crackshot_enhanced.custom_weapons.csv.CSVInput;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.GunModifier2;
import net.projectzombie.crackshot_enhanced.custom_weapons.types.Type;

/**
 *
 * @author jbannon
 */
public class FireModes extends CSVInput<FireMode2>
{
    static private FireModes singleton = null;
    static public FireModes getInstance()
    {
        if (singleton == null)
            singleton = new FireModes();
        return singleton;
    }
    
    static private final String FIREMODE_CSV_NAME = "FireModes.csv";
    static private final String[] FIREMODE_VALUES = {
        "display name",
        "material",
        "materialData",
        "price",
        "color",
        "is burst fire",
        "shots per burst",
        "is automatic"
    };
    
    private FireModes()
    {
        super(FIREMODE_CSV_NAME, buildFireModes(), FIREMODE_VALUES);
    }
    
    @Override
    public FireMode2 getNullValue()
    {
        return null;
    }
    
    /**
     * Builds all FireModes, if any. Must have at least one in CSV.
     * @return Array of all FireModes. Null otherwise.
     */
    static private FireMode2[] buildFireModes()
    {
        final CSVReader csv = new CSVReader(FIREMODE_CSV_NAME, FIREMODE_VALUES);
        final int rowCount = csv.getRowCount();
        
        if (rowCount <= 0)
        {
            return null;
        }
        
        int j = 0;
        final FireMode2[] toReturn           = new FireMode2[rowCount];
        final String[] displayNames          = csv.getColumnString(j++);
        final String[] materialNames         = csv.getColumnString(j++);
        final int[]    materialBytes         = csv.getColumnInt(j++);
        final int[]    price                 = csv.getColumnInt(j++);
        final String[] colors                = csv.getColumnString(j++);
        final boolean[] isBurstFire          = csv.getColumnBoolean(j++);
        final int[]     shotsPerBurst        = csv.getColumnInt(j++);
        final boolean[] isAutomatic          = csv.getColumnBoolean(j++);
        
        for (int i = 0; i < rowCount; i++)
        {
            toReturn[i] = new FireMode2(displayNames[i],
                                      materialNames[i],
                                      materialBytes[i],
                                      price[i],
                                      colors[i],
                                      isBurstFire[i],
                                      shotsPerBurst[i],
                                      isAutomatic[i]);
        }
        System.out.println("Initialized " + rowCount + " fire modes.");
        return toReturn;
    }
    
    
    static public class FireMode2 extends GunModifier2 implements Type
    {
        private static final String TITLE = "Fire Mode: ";

        private final boolean isBurstFire;
        private final boolean isAutomatic;
        private final int shotsPerBurst;

        private FireMode2(final String displayName,
                          final String material,
                          final int materialByte,
                          final int price,
                          final String color,
                          final boolean isBurstFire,
                          final int shotsPerBurst,
                          final boolean isAutomatic) 
        {
            super(displayName, material, materialByte, price, color);
            this.isBurstFire = isBurstFire;
            this.shotsPerBurst = shotsPerBurst;
            this.isAutomatic = isAutomatic;
        }

        @Override public int price()                 { return 40;       }
        @Override public String title()              { return TITLE;    }
        @Override  public FireMode2 getNullModifier() { return singleton.getNullValue(); }
    }
}
