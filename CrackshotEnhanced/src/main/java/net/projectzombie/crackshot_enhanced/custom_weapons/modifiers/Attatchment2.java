/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.modifiers;

import net.projectzombie.crackshot_enhanced.custom_weapons.csv.CSVReader;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.BleedoutModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.BulletSpreadModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.CritModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.DamageModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.GunModifier;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;

/**
 *
 * @author jbannon
 */
public class Attatchment2 implements GunModifier, 
                                     BulletSpreadModifier, 
                                     DamageModifier,
                                     CritModifier,
                                     BleedoutModifier
{
    static private final String ATTATCHMENT_CSV_NAME = "Attatchments.csv";
    static private final String[] ATTATCHMENT_VALUES = {
        "display name",
        "material",
        "materialData",
        "price",
        "bulletspread percent modifier",
        "damage modifier",
        "crit chance boost",
        "crit multiplier boost",
        "bleedout duration",
        "bleedout damage"
    };
    
    private final String displayName;
    private final MaterialData materialData;
    
    Attatchment2(final String displayname,
                 final String materialName,
                 final int materialByte,
                 final int price,
                 final double bulletSpreadPercentModifier,
                 final double damageModifier,
                 final double critChanceBoost,
                 final double critMultiplierBoost,
                 final double bleedoutDurationSeconds,
                 final double bleedoutDamageBoost)
    {        
        this.displayName = displayname;
        if (materialName == null)
        {
            materialData = null;
        }
        else
        {
            final Material tempMaterial = Material.valueOf(materialName);
            if (tempMaterial == null)
                materialData = null;
            else
                materialData = new MaterialData(tempMaterial, (byte)materialByte);
        }
    }
    
    
    @Override public String toString()                  { return displayName; }
    @Override public int    price()                     { return 0;       }
    @Override public String getDisplayName()            { return displayName; }
    @Override public double getDamageValue()            { return 0;           }
    @Override public double getDamageMultiplier()       { return 1.0;         }
    @Override public double getBulletSpreadMultiplier() { return 1.0; }
    @Override public double getCritChance()             { return 1.2; }
    @Override public double getCritStrike()             { return 1.1; }
    @Override public double getBleedoutDurationValue()  { return 1.0; }
    @Override public double getBleedoutDamageValue()    { return 1;  }
    @Override public MaterialData getMaterialData()     { return materialData; }
    @Override public boolean isNull() { return displayName == null; }

    @Override
    public ChatColor getColor() {
        return ChatColor.GREEN;
    }
    
    static public Attatchment2[] buildAttatchments()
    {
        final CSVReader csv = new CSVReader(ATTATCHMENT_CSV_NAME, ATTATCHMENT_VALUES);
        final Attatchment2 nullAttatchment = new Attatchment2(null, null, 0, 0, 0, 0, 0, 0, 0, 0);
        final int rowCount = csv.getRowCount();
        
        if (rowCount <= 0)
        {
            return new Attatchment2[] { nullAttatchment };
        }
        
        final Attatchment2[] toReturn      = new Attatchment2[rowCount + 1];
        final String[] displayNames        = csv.getColumnString(0);
        final String[] materialNames       = csv.getColumnString(1);
        final int[]    materialBytes       = csv.getColumnInt(2);
        final int[]    price               = csv.getColumnInt(3);
        final double[] bsPercentModifier   = csv.getColumnDouble(4);
        final double[] damageModifier      = csv.getColumnDouble(5);
        final double[] critChanceBoost     = csv.getColumnDouble(6);
        final double[] critMultiplierBoost = csv.getColumnDouble(7);
        final double[] bleedoutDuration    = csv.getColumnDouble(8);
        final double[] bleedoutDamage      = csv.getColumnDouble(9);
        
        toReturn[rowCount] = nullAttatchment;
        for (int i = 0; i < rowCount; i++)
        {
            toReturn[i] = new Attatchment2(displayNames[i],
                                           materialNames[i],
                                           materialBytes[i],
                                           price[i],
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


}
