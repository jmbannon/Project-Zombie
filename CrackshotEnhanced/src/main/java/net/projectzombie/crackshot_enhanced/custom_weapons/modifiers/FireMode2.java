/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.modifiers;

import java.util.ArrayList;
import net.projectzombie.crackshot_enhanced.custom_weapons.csv.CSVReader;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.GunModifier2;
import net.projectzombie.crackshot_enhanced.custom_weapons.types.Type;

/**
 *
 * @author jbannon
 */
public class FireMode2 extends GunModifier2 implements Type
{
    private static final String TITLE = "Fire Mode: ";
    
    static private FireMode2[] firemodes = null;
    static private final String STOCK_CSV_NAME = "FireModes.csv";
    static private final String[] STOCK_VALUES = {
        "display name",
        "material",
        "materialData",
        "price",
        "color",
        "is burst fire",
        "shots per burst",
        "is automatic"
    }; 
    
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
    
    public FireMode2()
    {
        this(null, null, 0, 0, null, false, 0, false);
    }
    
    @Override public int price()                 { return 40;       }
    @Override public String title()              { return TITLE;    }
    @Override public FireMode2[] getAll()        { return firemodes; }
    @Override public FireMode2 getNullModifier() {  return new FireMode2(); }
    
    static public void initializeFireModes()
    {
        if (firemodes == null)
            firemodes = buildFireModes();
    }
    
    static private FireMode2[] buildFireModes()
    {
        final CSVReader csv = new CSVReader(STOCK_CSV_NAME, STOCK_VALUES);
        final int rowCount = csv.getRowCount();
        
        if (rowCount <= 0)
        {
            return new FireMode2[] { new FireMode2() };
        }
        
        final FireMode2[] toReturn           = new FireMode2[rowCount + 1];
        final String[] displayNames          = csv.getColumnString(0);
        final String[] materialNames         = csv.getColumnString(1);
        final int[]    materialBytes         = csv.getColumnInt(2);
        final int[]    price                 = csv.getColumnInt(3);
        final String[] colors                = csv.getColumnString(4);
        final boolean[] isBurstFire          = csv.getColumnBoolean(5);
        final int[]     shotsPerBurst        = csv.getColumnInt(6);
        final boolean[] isAutomatic          = csv.getColumnBoolean(7);
        
        toReturn[rowCount] = new FireMode2();
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
    
    @Override
    public FireMode2[] valueOf(final String[] names,
                               final boolean includeNull)
    {
        final FireMode2[] values = this.getAll();
        final FireMode2[] toReturn;
        final ArrayList<Integer> indexes = this.getIndexes(names);
        final int size = indexes.size();
        int j = 0;
        
        if (size < 1)
        {
            if (includeNull)
                return new FireMode2[] { this.getNullModifier() };
            else
                return null;
        }
        else
        {
            if (includeNull)
            {
                toReturn = new FireMode2[size+1];
                toReturn[size] = this.getNullModifier();
            }
            else
            {
                toReturn = new FireMode2[size];
            }
            for (Integer i : indexes)
            {
                toReturn[j++] = values[i];
            }
            return toReturn;
        }
    }
}
