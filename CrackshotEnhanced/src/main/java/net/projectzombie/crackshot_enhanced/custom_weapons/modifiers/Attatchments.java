/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.modifiers;

import net.projectzombie.crackshot_enhanced.custom_weapons.csv.CSVReader;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Attatchments.Attatchment;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.BleedoutModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.BulletSpreadModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.csv.CSVInput;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.CritModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.DamageModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.IncendiaryModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.Shrapnel;
/**
 *
 * @author jbannon
 */
public class Attatchments extends CSVInput<Attatchment>
{
    static private Attatchments slotOneSingleton = null;
    static private Attatchments slotTwoSingleton = null;
    static private Attatchments slotThreeSingleton = null;
            
    static public Attatchments getSlotOneInstance()
    {
        if (slotOneSingleton == null)
            slotOneSingleton = new Attatchments(ATTATCHMENT_ONE_CSV_NAME);
        return slotOneSingleton;
    }
    
    static public Attatchments getSlotTwoInstance()
    {
        if (slotTwoSingleton == null)
            slotTwoSingleton = new Attatchments(ATTATCHMENT_TWO_CSV_NAME);
        return slotTwoSingleton;
    }
    
    static public Attatchments getSlotThreeInstance()
    {
        if (slotThreeSingleton == null)
            slotThreeSingleton = new Attatchments(ATTATCHMENT_THREE_CSV_NAME);
        return slotThreeSingleton;
    }
    
    static private final String ATTATCHMENT_ONE_CSV_NAME = "Slot1_Attatchments.csv";
    static private final String ATTATCHMENT_TWO_CSV_NAME = "Slot2_Attatchments.csv";
    static private final String ATTATCHMENT_THREE_CSV_NAME = "Slot3_Attatchments.csv";
    
    static private final String[] ATTATCHMENT_VALUES = {
        "Display Name (STR)",
        "Material (INT)",
        "Material Data (INT)",
        "Price (INT)",
        "Color (STR)",
        "Bulletspread Multiplier (DBL)",
        "Damage Modifier (DBL)",
        "Damage Multiplier (DBL)",
        "Headshot Damage Modifier (DBL)",
        "Headshot Damage Multiplier (DBL)",
        "Crit Chance Modifier (DBL)",
        "Crit Strike Multiplier (DBL)",
        "Bleedout Duration Seconds (DBL)",
        "Bleedout Duration Multiplier (DBL)",
        "Bleedout Damage (DBL)",
        "Bleedout Damage Multiplier from Base Damage (DBL)",
        "Bleedout Damage Multiplier from Shrapnel (DBL)",
        "Fire Damage Modifier (DBL)",
        "Fire Damage Multiplier (DBL)",
        "Ignite Chance (DBL)",
        "Ignite Duration (DBL)",
        "Shrapnel Damage Modifier (DBL)",
        "Shrapnel Damage Multiplier (DBL)",
        "Stun Chance (DBL)",
        "Stun Duration (DBL)"
    };
    
    private Attatchments(final String csvName)
    {
        super(csvName, buildAttatchments(csvName), ATTATCHMENT_VALUES);
    }
    
    @Override
    public Attatchment getNullValue()
    {
        return new Attatchment();
    }
    
    /**
     * Builds all attatchments, if any. Allowed to not have any in CSV.
     * @return Array of all attatchments (including null attatchment).
     */
    static private Attatchment[] buildAttatchments(final String csvName)
    {
        final CSVReader csv = new CSVReader(csvName, ATTATCHMENT_VALUES);
        final int rowCount = csv.getRowCount();
 
        if (rowCount <= 0)
        {
            return new Attatchment[] { new Attatchment() };
        }
        int j = 0;
        final Attatchment[] toReturn              = new Attatchment[rowCount + 1];
        final String[] displayNames               = csv.getColumnString(j++);
        final String[] materialNames              = csv.getColumnString(j++);
        final int[]    materialBytes              = csv.getColumnInt(j++);
        final int[]    price                      = csv.getColumnInt(j++);
        final String[] colors                     = csv.getColumnString(j++);
        final double[] bsMultiplier               = csv.getColumnDouble(j++);
        final double[] damageModifier             = csv.getColumnDouble(j++);
        final double[] damageMultiplier           = csv.getColumnDouble(j++);
        final double[] headshotModifier           = csv.getColumnDouble(j++);
        final double[] headshotMultiplier         = csv.getColumnDouble(j++);
        final double[] critChanceBoost            = csv.getColumnDouble(j++);
        final double[] critMultiplierBoost        = csv.getColumnDouble(j++);
        final double[] bleedoutDuration           = csv.getColumnDouble(j++);
        final double[] bleedoutDurationMultiplier = csv.getColumnDouble(j++);
        final double[] bleedoutDamage             = csv.getColumnDouble(j++);
        final double[] bleedoutDamageFromBase     = csv.getColumnDouble(j++);
        final double[] bleedoutDamageFromShrap    = csv.getColumnDouble(j++);
        final double[] fireDamageModifier         = csv.getColumnDouble(j++);
        final double[] fireDamageMultiplier       = csv.getColumnDouble(j++);
        final double[] igniteChance               = csv.getColumnDouble(j++);
        final double[] igniteDuration             = csv.getColumnDouble(j++);
        final double[] shrapnelDamageModifier     = csv.getColumnDouble(j++);
        final double[] shrapnelDamageMultiplier   = csv.getColumnDouble(j++);
        final double[] stunChance                 = csv.getColumnDouble(j++);
        final double[] stunDuration               = csv.getColumnDouble(j++);
 
        toReturn[0] = new Attatchment();
        for (int i = 0; i < rowCount; i++)
        {
            toReturn[i+1] = new Attatchment(
                    i+1,
                    displayNames[i],
                    materialNames[i],
                    materialBytes[i],
                    price[i],
                    colors[i],
                    bsMultiplier[i],
                    damageModifier[i],
                    damageMultiplier[i],
                    headshotModifier[i],
                    headshotMultiplier[i],
                    critChanceBoost[i],
                    critMultiplierBoost[i],
                    bleedoutDuration[i],
                    bleedoutDurationMultiplier[i],
                    bleedoutDamage[i],
                    bleedoutDamageFromBase[i],
                    bleedoutDamageFromShrap[i],
                    fireDamageModifier[i],
                    fireDamageMultiplier[i],
                    igniteChance[i],
                    igniteDuration[i],
                    shrapnelDamageModifier[i],
                    shrapnelDamageMultiplier[i],
                    stunChance[i],
                    stunDuration[i]
            );
        }
        return toReturn;
    }

    static public class Attatchment extends GunModifier implements BulletSpreadModifier, 
                                                              DamageModifier,
                                                              CritModifier,
                                                              BleedoutModifier,
                                                              IncendiaryModifier,
                                                              Shrapnel
    {
        private final double bulletSpreadMultiplier;
        private final double damageModifier;
        private final double damageMultiplier;
        private final double headshotDamageModifier;
        private final double headshotDamageMultiplier;
        private final double critChanceBoost;
        private final double critStrikeMultiplier;
        private final double bleedoutDurationSeconds;
        private final double bleedoutDurationMultiplier;
        private final double bleedoutDamageBoost;
        private final double bleedoutDamageMultiplierFromBase;
        private final double bleedoutDamageMultiplierFromShrap;
        private final double fireDamageModifier;
        private final double fireDamageMultiplier;
        private final double igniteChance;
        private final double igniteDuration;
        private final double shrapnelDamageModifier;
        private final double shrapnelDamageMultiplier;
        private final double stunChance;
        private final double stunDuration;

        private Attatchment(final int uniqueID,
                            final String displayname,
                            final String materialName,
                            final int materialByte,
                            final int price,
                            final String color,
                            final double bulletSpreadMultiplier,
                            final double damageModifier,
                            final double damageMultiplier,
                            final double headshotDamageModifier,
                            final double headshotDamageMultiplier,
                            final double critChanceBoost,
                            final double critStrikeMultiplier,
                            final double bleedoutDurationSeconds,
                            final double bleedoutDurationMultiplier,
                            final double bleedoutDamageBoost,
                            final double bleedoutDamageMultiplierFromBase,
                            final double bleedoutDamageMultiplierFromShrap,
                            final double fireDamageModifier,
                            final double fireDamageMultiplier,
                            final double igniteChance,
                            final double igniteDuration,
                            final double shrapnelDamageModifier,
                            final double shrapnelDamageMultiplier,
                            final double stunChance,
                            final double stunDuration)
        {        
            super(uniqueID, displayname, materialName, materialByte, price, color);
            this.bulletSpreadMultiplier = bulletSpreadMultiplier;
            this.damageModifier = damageModifier;
            this.damageMultiplier = damageMultiplier;
            this.headshotDamageModifier = headshotDamageModifier;
            this.headshotDamageMultiplier = headshotDamageMultiplier;
            this.critChanceBoost = critChanceBoost;
            this.critStrikeMultiplier = critStrikeMultiplier;
            this.bleedoutDurationSeconds = bleedoutDurationSeconds;
            this.bleedoutDurationMultiplier = bleedoutDurationMultiplier;
            this.bleedoutDamageBoost = bleedoutDamageBoost;
            this.bleedoutDamageMultiplierFromBase = bleedoutDamageMultiplierFromBase;
            this.bleedoutDamageMultiplierFromShrap = bleedoutDamageMultiplierFromShrap;
            this.fireDamageModifier = fireDamageModifier;
            this.fireDamageMultiplier = fireDamageMultiplier;
            this.igniteChance = igniteChance;
            this.igniteDuration = igniteDuration;
            this.shrapnelDamageModifier = shrapnelDamageModifier;
            this.shrapnelDamageMultiplier = shrapnelDamageMultiplier;
            this.stunChance = stunChance;
            this.stunDuration = stunDuration;
        }

        /**
         * Constructs the null Attatchment.
         */
        private Attatchment()
        {
            this(0, null, null, 0, 0, null, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        }

        @Override public double getDamageValue()                         { return damageModifier; }
        @Override public double getDamageMultiplier()                    { return damageMultiplier; }
        @Override public double getBulletSpreadMultiplier()              { return bulletSpreadMultiplier; }
        @Override public double getCritChance()                          { return critChanceBoost; }
        @Override public double getCritStrike()                          { return critStrikeMultiplier; }
        @Override public double getBleedoutDurationValue()               { return bleedoutDurationSeconds; }
        @Override public double getBleedoutDamageValuePerSecond()        { return bleedoutDamageBoost;  }
        @Override public Attatchment getNullModifier()                   { return slotOneSingleton.getNullValue(); }
        @Override public double getHeadshotDamageModifier()              { return headshotDamageModifier; }
        @Override public double getHeadshotDamageMultiplier()            { return headshotDamageMultiplier; }
        @Override public double getBleedoutDurationMultiplier()          { return bleedoutDurationMultiplier; }
        @Override public double getBleedoutDamageMultiplierFromDamage()  { return bleedoutDamageMultiplierFromBase; }
        @Override public double getBleedoutDamageMultiplerFromShrapnel() { return bleedoutDamageMultiplierFromShrap; }
        @Override public double getFireDamageValue()                     { return fireDamageModifier; }
        @Override public double getFireDamageMultiplier()                { return fireDamageMultiplier; }
        @Override public double getIgniteChance()                        { return igniteChance; }
        @Override public double getIgniteDuration()                      { return igniteDuration; }
        @Override public double getShrapnelDamageValue()                 { return shrapnelDamageModifier; }
        @Override public double getShrapnelDamageMultiplier()            { return shrapnelDamageMultiplier; }
        @Override public double getStunChance()                          { return stunChance; }
        @Override public double getStunDuration()                        { return stunDuration; }
    }
}
