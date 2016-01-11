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
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.CSVInput;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.CritModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.DamageModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.GunModifier2;
/**
 *
 * @author jbannon
 */
public class Attatchments extends CSVInput
{
    static private Attatchments singleton = null;
    
    static public Attatchments getInstance()
    {
        if (singleton == null)
            singleton = new Attatchments();
        return singleton;
    }
    
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
    
    private final Attatchment2[] attatchments;
    
    private Attatchments()
    {
        super(ATTATCHMENT_CSV_NAME, ATTATCHMENT_VALUES);
        this.attatchments = buildAttatchments();
    }
    
    @Override
    public Attatchment2[] getAll() { return attatchments; }

    @Override
    public Attatchment2 get(String name)
    {
        final int index = super.getIndex(name);
        if (index == -1)
            return null;
        else
            return attatchments[index];
    }

    @Override
    public Attatchment2[] get(String[] names)
    {
        return get(names, false);
    }

    @Override
    public Attatchment2[] get(String[] names, boolean includeNull)
    {
        final Attatchment2 toReturn[];
        final ArrayList<Integer> indexes = super.getIndexes(names);
        int j = 0;
        
        if (indexes == null || indexes.isEmpty())
            return null;
        else
        {
            if (includeNull)
            {
                toReturn = new Attatchment2[indexes.size() + 1];
                toReturn[indexes.size()] = new Attatchment2();
            }
            else
            {
                toReturn = new Attatchment2[indexes.size()];
            }
            for (Integer i : indexes)
            {
                toReturn[j++] = attatchments[i];
            }
            return toReturn;
        }
    }
    
    /**
     * Builds all attatchments, if any. Allowed to not have any in CSV.
     * @return Array of all attatchments (including null attatchment).
     */
    private Attatchment2[] buildAttatchments()
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

    public class Attatchment2 extends GunModifier2 implements BulletSpreadModifier, 
                                                              DamageModifier,
                                                              CritModifier,
                                                              BleedoutModifier
    {
        private final double bulletSpreadMultiplier;
        private final double damageMultiplier;
        private final double critChanceBoost;
        private final double critStrikeMultiplier;
        private final double bleedoutDurationSeconds;
        private final double bleedoutDamageBoost;

        private Attatchment2(final String displayname,
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
        private Attatchment2()
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
        @Override public Attatchment2   getNullModifier()   { return new Attatchment2(); }
    }
}
