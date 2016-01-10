/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.modifiers;

import java.util.ArrayList;
import net.projectzombie.crackshot_enhanced.custom_weapons.csv.CSVReader;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.GunModifier2;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.MagazineModifier;

/**
 *
 * @author jesse
 */
public class Magazine2 extends GunModifier2 implements MagazineModifier
{
    static private Magazine2[] magazines = null;
    static private final String MAGAZINES_CSV_NAME = "Magazines.csv";
    static private final String[] MAGAZINE_VALUES = {
        "display name",
        "material",
        "materialData",
        "price",
        "color",
        "magazine boost",
        "reload speed multiplier"
    };
    
    private final int magazineBoost;
    private final double reloadSpeedMultiplier;
    
    private Magazine2(final String displayName,
                      final String material,
                      final int materialByte,
                      final int price,
                      final String color,
                      final int magazineBoost,
                      final double reloadSpeedMultiplier)
    {
        super(displayName, material, materialByte, price, color);
        this.magazineBoost = magazineBoost;
        this.reloadSpeedMultiplier = reloadSpeedMultiplier;
    }
    
    public Magazine2()
    {
        this(null, null, 0, 0, null, 0, 0);
    }
    
    @Override public int getMagazineBoost()            { return magazineBoost; }
    @Override public double getReloadSpeedMultiplier() { return reloadSpeedMultiplier; }
    @Override public Magazine2[] getAll() { return magazines; }
    @Override public Magazine2 getNullModifier() { return new Magazine2(); }
    
    static public void initializeFireModes()
    {
        if (magazines == null)
            magazines = buildMagazines();
    }
    
    static private Magazine2[] buildMagazines()
    {
        final CSVReader csv = new CSVReader(MAGAZINES_CSV_NAME, MAGAZINE_VALUES);
        final int rowCount = csv.getRowCount();
        
        if (rowCount <= 0)
        {
            return new Magazine2[] { new Magazine2() };
        }
        
        final Magazine2[] toReturn          = new Magazine2[rowCount + 1];
        final String[] displayNames         = csv.getColumnString(0);
        final String[] materialNames        = csv.getColumnString(1);
        final int[]    materialBytes        = csv.getColumnInt(2);
        final int[]    price                = csv.getColumnInt(3);
        final String[] colors               = csv.getColumnString(4);
        final int[] magazineBoost           = csv.getColumnInt(5);
        final double[] reloadSpeedMultiplier= csv.getColumnDouble(6);
        
        toReturn[rowCount] = new Magazine2();
        for (int i = 0; i < rowCount; i++)
        {
            toReturn[i] = new Magazine2(displayNames[i],
                                      materialNames[i],
                                      materialBytes[i],
                                      price[i],
                                      colors[i],
                                      magazineBoost[i],
                                      reloadSpeedMultiplier[i]);
        }
        System.out.println("Initialized " + rowCount + " magazines.");
        return toReturn;
    }
    
    @Override
    public Magazine2[] valueOf(final String[] names,
                               final boolean includeNull)
    {
        final Magazine2[] values = this.getAll();
        final Magazine2[] toReturn;
        final ArrayList<Integer> indexes = this.getIndexes(names);
        final int size = indexes.size();
        int j = 0;
        
        if (size < 1)
        {
            if (includeNull)
                return new Magazine2[] { this.getNullModifier() };
            else
                return null;
        }
        else
        {
            if (includeNull)
            {
                toReturn = new Magazine2[size+1];
                toReturn[size] = this.getNullModifier();
            }
            else
            {
                toReturn = new Magazine2[size];
            }
            for (Integer i : indexes)
            {
                toReturn[j++] = values[i];
            }
            return toReturn;
        }
    }
}
