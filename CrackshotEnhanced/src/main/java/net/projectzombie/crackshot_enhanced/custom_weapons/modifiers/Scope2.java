/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.modifiers;

import net.projectzombie.crackshot_enhanced.custom_weapons.csv.CSVReader;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.BulletSpreadModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.GunModifier2;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.ScopeModifier;

/**
 *
 * @author jbannon
 */
public class Scope2 extends GunModifier2 implements BulletSpreadModifier, ScopeModifier
{
    static private final Scope2[] scopes = buildScopes();
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
    
    public Scope2()
    {
        this(null, null, 0, 0, null, 0, 0);
    }
    
    @Override public int getZoomAmount()                { return zoomAmount;  }
    @Override public double getBulletSpreadMultiplier() { return 1.0; }
    @Override public GunModifier2[] getAll() { return scopes;  }
    @Override  public GunModifier2 getNullModifier() { return new Scope2(); }
    
    static private Scope2[] buildScopes()
    {
        final CSVReader csv = new CSVReader(SCOPE_CSV_NAME, SCOPE_VALUES);
        final int rowCount = csv.getRowCount();
        
        if (rowCount <= 0)
        {
            return new Scope2[] { new Scope2() };
        }
        
        final Scope2[] toReturn          = new Scope2[rowCount + 1];
        final String[] displayNames         = csv.getColumnString(0);
        final String[] materialNames        = csv.getColumnString(1);
        final int[]    materialBytes        = csv.getColumnInt(2);
        final int[]    price                = csv.getColumnInt(3);
        final String[] colors               = csv.getColumnString(4);
        final int[] crackshotZoomAmount     = csv.getColumnInt(5);
        final double[] bulletSpreadModifier = csv.getColumnDouble(6);
        
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
        System.out.println("Initialized " + rowCount + " scopes.");
        return toReturn;
    }
}
