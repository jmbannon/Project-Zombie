/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.modifiers;

import java.io.File;
import net.projectzombie.crackshot_enhanced.custom_weapons.csv.CSVReader;
import static net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Attatchment.NULL_ATTATCHMENT;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.BleedoutModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.BulletSpreadModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.CritModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.DamageModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.GunModifier;
import org.bukkit.ChatColor;

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
    static private final File ATTATCHMENT_CSV = null;
    static private final String[] ATTATCHMENT_VALUES = {
        "display name",
        "bulletspread percent modifier",
        "damage modifier",
        "crit chance boost",
        "crist multiplier boost",
        "bleedout duration",
        "bleedout damage"
    };
    
    private final String displayName;
    
    Attatchment2(final String displayname,
                final double bulletSpreadPercentModifier,
                final double damageModifier,
                final double critChanceBoost,
                final double critMultiplierBoost,
                final double bleedoutDurationSeconds,
                final double bleedoutDamageBoost)
    {
        this.displayName = displayname;
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
    @Override public boolean isNull() { return this.equals(NULL_ATTATCHMENT); }

    @Override
    public ChatColor getColor() {
        return ChatColor.GREEN;
    }
    
    static Attatchment2[] buildAttatchments()
    {
        
        final CSVReader csv = new CSVReader(ATTATCHMENT_CSV,
                                            ATTATCHMENT_VALUES);
        final int rowCount = csv.getRowCount();
        if (rowCount <= 0)
            return null;
        
        final Attatchment2[] toReturn      = new Attatchment2[rowCount];
        final String[] displayNames        = csv.getColumnString(0);
        final double[] bsPercentModifier   = csv.getColumnDouble(1);
        final double[] damageModifier      = csv.getColumnDouble(2);
        final double[] critChanceBoost     = csv.getColumnDouble(3);
        final double[] critMultiplierBoost = csv.getColumnDouble(4);
        final double[] bleedoutDuration    = csv.getColumnDouble(5);
        final double[] bleedoutDamage      = csv.getColumnDouble(6);
        
        for (int i = 0; i < rowCount; i++)
        {
            toReturn[i] = new Attatchment2(displayNames[i],
                                           bsPercentModifier[i],
                                           damageModifier[i],
                                           critChanceBoost[i],
                                           critMultiplierBoost[i],
                                           bleedoutDuration[i],
                                           bleedoutDamage[i]);
        }
        return toReturn;
    }
}
