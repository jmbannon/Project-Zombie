/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.modifiers;

import net.projectzombie.crackshot_enhanced.custom_weapons.csv.CSVReader;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Sights.Scope2;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.BulletSpreadModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.csv.CSVInput;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.GunModifier2;

/**
 *
 * @author jbannon
 */
public class Sights extends CSVInput<Scope2>
{
    static private Sights singleton = null;
    static public Sights getInstance()
    {
        if (singleton == null)
            singleton = new Sights();
        return singleton;
    }
    
    static private final String SCOPE_CSV_NAME = "Scopes.csv";
    static private final String[] SCOPE_VALUES = {
        "display name",
        "material",
        "materialData",
        "price",
        "color",
        "crackshot zoom amount",
        "bullet spread multiplier"
    };

    private Sights()
    {
        super(SCOPE_CSV_NAME, buildScopes(), SCOPE_VALUES);
    }
    
    @Override
    public Scope2 getNullValue()
    {
        return new Scope2();
    }
    
    /**
     * Builds all Scopes, if any. Allowed to not have any in CSV.
     * @return Array of all Scopes. Null otherwise.
     */
    static private Scope2[] buildScopes()
    {
        final CSVReader csv = new CSVReader(SCOPE_CSV_NAME, SCOPE_VALUES);
        final int rowCount = csv.getRowCount();
        
        if (rowCount <= 0)
        {
            return null;
        }
        
        int j = 0;
        final Scope2[] toReturn             = new Scope2[rowCount + 1];
        final String[] displayNames         = csv.getColumnString(j++);
        final String[] materialNames        = csv.getColumnString(j++);
        final int[]    materialBytes        = csv.getColumnInt(j++);
        final int[]    price                = csv.getColumnInt(j++);
        final String[] colors               = csv.getColumnString(j++);
        final int[] crackshotZoomAmount     = csv.getColumnInt(j++);
        final double[] bulletSpreadModifier = csv.getColumnDouble(j++);
        toReturn[rowCount] = new Scope2();
        
        for (int i = 0; i < rowCount; i++)
        {
            toReturn[i] = new Scope2(displayNames[i],
                                      materialNames[i],
                                      materialBytes[i],
                                      price[i],
                                      colors[i],
                                      crackshotZoomAmount[i],
                                      bulletSpreadModifier[i]);
        }
        return toReturn;
    }
    
    static public class Scope2 extends GunModifier2 implements BulletSpreadModifier
    {
        private final int zoomAmount;
        private final double bulletSpreadModifier;

        private Scope2(final String displayName,
                       final String material,
                       final int materialByte,
                       final int price,
                       final String color,
                       final int crackshotZoomAmount,
                       final double bulletSpreadModifier)
        {
            super(displayName, material, materialByte, price, color);
            this.zoomAmount = crackshotZoomAmount;
            this.bulletSpreadModifier = bulletSpreadModifier;
        }

        private Scope2()
        {
            this(null, null, 0, 0, null, 0, 0);
        }

        public int getZoomAmount()                          { return zoomAmount;   }
        @Override public double getBulletSpreadMultiplier() { return bulletSpreadModifier; }
        @Override public Scope2 getNullModifier()           { return singleton.getNullValue(); }
    }
}
