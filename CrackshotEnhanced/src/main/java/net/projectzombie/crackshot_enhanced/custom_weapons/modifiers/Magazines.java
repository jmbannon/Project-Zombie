/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.modifiers;

import net.projectzombie.crackshot_enhanced.custom_weapons.csv.CSVReader;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Magazines.Magazine2;
import net.projectzombie.crackshot_enhanced.custom_weapons.csv.CSVInput;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.GunModifier2;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.MagazineModifier;

/**
 *
 * @author jesse
 */
public class Magazines extends CSVInput<Magazine2>
{
    static private Magazines singleton = null;
    static public Magazines getInstance()
    {
        if (singleton == null)
            singleton = new Magazines();
        return singleton;
    }
    
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
    
    private Magazine2[] magazines;
    
    private Magazines()
    {
        super(MAGAZINES_CSV_NAME,  buildMagazines(), MAGAZINE_VALUES);
    }
    
    @Override
    public Magazine2 getNullValue()
    {
        return new Magazine2();
    }
    
    /**
     * Builds all magazines, if any. Allowed to not have any in CSV.
     * @return Array of all magazines (including null magazine).
     */
    static private Magazine2[] buildMagazines()
    {
        final CSVReader csv = new CSVReader(MAGAZINES_CSV_NAME, MAGAZINE_VALUES);
        final int rowCount = csv.getRowCount();
        
        if (rowCount <= 0)
        {
            return new Magazine2[] { new Magazine2() };
        }
        
        int j = 0;
        final Magazine2[] toReturn          = new Magazine2[rowCount + 1];
        final String[] displayNames         = csv.getColumnString(j++);
        final String[] materialNames        = csv.getColumnString(j++);
        final int[]    materialBytes        = csv.getColumnInt(j++);
        final int[]    price                = csv.getColumnInt(j++);
        final String[] colors               = csv.getColumnString(j++);
        final int[] magazineBoost           = csv.getColumnInt(j++);
        final double[] reloadSpeedMultiplier= csv.getColumnDouble(j++);
        
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
        return toReturn;
    }
    
    static public class Magazine2 extends GunModifier2 implements MagazineModifier
    {
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

        private Magazine2()
        {
            this(null, null, 0, 0, null, 0, 0);
        }

        @Override public int getMagazineBoost()            { return magazineBoost; }
        @Override public double getReloadSpeedMultiplier() { return reloadSpeedMultiplier; }
        @Override public GunModifier2 getNullModifier() { return singleton.getNullValue(); }
    }
}
