/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.modifiers;

import net.projectzombie.crackshot_enhanced.custom_weapons.csv.CSVReader;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Magazines.Magazine;
import net.projectzombie.crackshot_enhanced.custom_weapons.csv.CSVInput;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.GunModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.MagazineModifier;

/**
 *
 * @author jesse
 */
public class Magazines extends CSVInput<Magazine>
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
        "Display Name (STR)",
        "Material (INT)",
        "Material Data (INT)",
        "Price (INT)",
        "Color (STR)",
        "Magazine Modifier (INT)",
        "Magazine Multiplier (DBL)",
        "Reload Speed Multiplier (DBL)"
    };
    
    private Magazine[] magazines;
    
    private Magazines()
    {
        super(MAGAZINES_CSV_NAME,  buildMagazines(), MAGAZINE_VALUES);
    }
    
    @Override
    public Magazine getNullValue()
    {
        return new Magazine();
    }
    
    /**
     * Builds all magazines, if any. Allowed to not have any in CSV.
     * @return Array of all magazines (including null magazine).
     */
    static private Magazine[] buildMagazines()
    {
        final CSVReader csv = new CSVReader(MAGAZINES_CSV_NAME, MAGAZINE_VALUES);
        final int rowCount = csv.getRowCount();
        
        if (rowCount <= 0)
        {
            return new Magazine[] { new Magazine() };
        }
        
        int j = 0;
        final Magazine[] toReturn          = new Magazine[rowCount + 1];
        final String[] displayNames         = csv.getColumnString(j++);
        final String[] materialNames        = csv.getColumnString(j++);
        final int[]    materialBytes        = csv.getColumnInt(j++);
        final int[]    price                = csv.getColumnInt(j++);
        final String[] colors               = csv.getColumnString(j++);
        final int[] magazineBoost           = csv.getColumnInt(j++);
        final double[] magazineMultiplier   = csv.getColumnDouble(j++);
        final double[] reloadSpeedMultiplier= csv.getColumnDouble(j++);
        
        toReturn[rowCount] = new Magazine();
        for (int i = 0; i < rowCount; i++)
        {
            toReturn[i] = new Magazine(displayNames[i],
                                      materialNames[i],
                                      materialBytes[i],
                                      price[i],
                                      colors[i],
                                      magazineBoost[i],
                                      magazineMultiplier[i],
                                      reloadSpeedMultiplier[i]);
        }
        return toReturn;
    }
    
    static public class Magazine extends GunModifier implements MagazineModifier
    {
        private final int magazineBoost;
        private final double magazineMultiplier;
        private final double reloadSpeedMultiplier;

        private Magazine(final String displayName,
                          final String material,
                          final int materialByte,
                          final int price,
                          final String color,
                          final int magazineBoost,
                          final double magazineMultiplier,
                          final double reloadSpeedMultiplier)
        {
            super(displayName, material, materialByte, price, color);
            this.magazineBoost = magazineBoost;
            this.magazineMultiplier = magazineMultiplier;
            this.reloadSpeedMultiplier = reloadSpeedMultiplier;
        }

        private Magazine()
        {
            this(null, null, 0, 0, null, 0, 0, 0);
        }

        @Override public int getMagazineModifier()          { return magazineBoost; }
        @Override public double getReloadSpeedMultiplier()  { return reloadSpeedMultiplier; }
        @Override public GunModifier getNullModifier()      { return singleton.getNullValue(); }
        @Override public double getMagazineSizeMultiplier() { return magazineMultiplier; }

    }
}
