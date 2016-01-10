/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.modifiers;

import java.util.ArrayList;
import net.projectzombie.crackshot_enhanced.custom_weapons.csv.CSVReader;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.BleedoutModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.BulletSpreadModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.CritModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.DamageModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.GunModifier2;
/**
 *
 * @author jbannon
 */
public class Attatchment2 extends GunModifier2 implements BulletSpreadModifier, 
                                                          DamageModifier,
                                                          CritModifier,
                                                          BleedoutModifier
{
    
    static private Attatchment2[] attatchments = null;
    static private final String ATTATCHMENT_CSV_NAME = "Attatchments.csv";
    static private final String[] ATTATCHMENT_VALUES = {
        "display name",
        "material",
        "materialData",
        "price",
        "color",
        "bulletspread percent modifier",
        "damage modifier",
        "crit chance boost",
        "crit multiplier boost",
        "bleedout duration",
        "bleedout damage"
    };
    
    private final double bulletSpreadMultiplier;
    private final double damageMultiplier;
    private final double critChanceBoost;
    private final double critStrikeMultiplier;
    private final double bleedoutDurationSeconds;
    private final double bleedoutDamageBoost;
    
    public Attatchment2(final String displayname,
                 final String materialName,
                 final int materialByte,
                 final int price,
                 final String color,
                 final double bulletSpreadMultiplier,
                 final double damageMultiplier,
                 final double critChanceBoost,
                 final double critStrikeMultiplier,
                 final double bleedoutDurationSeconds,
                 final double bleedoutDamageBoost)
    {        
        super(displayname, materialName, materialByte, price, color);
        this.bulletSpreadMultiplier = bulletSpreadMultiplier;
        this.damageMultiplier = damageMultiplier;
        this.critChanceBoost = critChanceBoost;
        this.critStrikeMultiplier = critStrikeMultiplier;
        this.bleedoutDurationSeconds = bleedoutDurationSeconds;
        this.bleedoutDamageBoost = bleedoutDamageBoost;
    }
    
    /**
     * Constructs the null Attatchment.
     */
    public Attatchment2()
    {
        this(null, null, 0, 0, null, 0, 0, 0, 0, 0, 0);
    }
    
    @Override public double getDamageValue()            { return 0;           }
    @Override public double getDamageMultiplier()       { return damageMultiplier; }
    @Override public double getBulletSpreadMultiplier() { return bulletSpreadMultiplier; }
    @Override public double getCritChance()             { return critChanceBoost; }
    @Override public double getCritStrike()             { return critStrikeMultiplier; }
    @Override public double getBleedoutDurationValue()  { return bleedoutDurationSeconds; }
    @Override public double getBleedoutDamageValue()    { return bleedoutDamageBoost;  }
    @Override public Attatchment2[] getAll()            { return attatchments; }
    @Override public Attatchment2 getNullModifier()     { return new Attatchment2(); }
    
    static public void initializeAttatchments()
    {
        if (attatchments == null)
            attatchments = buildAttatchments();
    }
    
    static private Attatchment2[] buildAttatchments()
    {
        final CSVReader csv = new CSVReader(ATTATCHMENT_CSV_NAME, ATTATCHMENT_VALUES);
        final int rowCount = csv.getRowCount();
        
        if (rowCount <= 0)
        {
            return new Attatchment2[] { new Attatchment2() };
        }
        
        final Attatchment2[] toReturn      = new Attatchment2[rowCount + 1];
        final String[] displayNames        = csv.getColumnString(0);
        final String[] materialNames       = csv.getColumnString(1);
        final int[]    materialBytes       = csv.getColumnInt(2);
        final int[]    price               = csv.getColumnInt(3);
        final String[] colors              = csv.getColumnString(4);
        final double[] bsPercentModifier   = csv.getColumnDouble(5);
        final double[] damageModifier      = csv.getColumnDouble(6);
        final double[] critChanceBoost     = csv.getColumnDouble(7);
        final double[] critMultiplierBoost = csv.getColumnDouble(8);
        final double[] bleedoutDuration    = csv.getColumnDouble(9);
        final double[] bleedoutDamage      = csv.getColumnDouble(10);
        
        toReturn[rowCount] = new Attatchment2();
        for (int i = 0; i < rowCount; i++)
        {
            toReturn[i] = new Attatchment2(displayNames[i],
                                           materialNames[i],
                                           materialBytes[i],
                                           price[i],
                                           colors[i],
                                           bsPercentModifier[i],
                                           damageModifier[i],
                                           critChanceBoost[i],
                                           critMultiplierBoost[i],
                                           bleedoutDuration[i],
                                           bleedoutDamage[i]);
        }
        System.out.println("Initialized " + rowCount + " attatchments.");
        return toReturn;
    }

    @Override
    public Attatchment2[] valueOf(final String[] names,
                                  final boolean includeNull)
    {
        final Attatchment2[] values = this.getAll();
        final Attatchment2[] toReturn;
        final ArrayList<Integer> indexes = this.getIndexes(names);
        final int size = indexes.size();
        int j = 0;
        
        if (size < 1)
        {
            if (includeNull)
                return new Attatchment2[] { this.getNullModifier() };
            else
                return null;
        }
        else
        {
            if (includeNull)
            {
                toReturn = new Attatchment2[size+1];
                toReturn[size] = this.getNullModifier();
            }
            else
            {
                toReturn = new Attatchment2[size];
            }
            for (Integer i : indexes)
            {
                toReturn[j++] = values[i];
            }
            return toReturn;
        }
    }
}
