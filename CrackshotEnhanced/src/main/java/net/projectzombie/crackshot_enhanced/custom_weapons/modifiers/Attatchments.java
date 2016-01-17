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
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.GunModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.IncendiaryModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.Shrapnel;
/**
 *
 * @author jbannon
 */
public class Attatchments extends CSVInput<Attatchment>
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
        "Is Silencer (T/F)"
    };
    
    private Attatchments()
    {
        super(ATTATCHMENT_CSV_NAME, buildAttatchments(), ATTATCHMENT_VALUES);
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
    static private Attatchment[] buildAttatchments()
    {
        final CSVReader csv = new CSVReader(ATTATCHMENT_CSV_NAME, ATTATCHMENT_VALUES);
        final int rowCount = csv.getRowCount();
 
        if (rowCount <= 0)
        {
            return new Attatchment[] { new Attatchment() };
        }
        int j = 0;
        final Attatchment[] toReturn      = new Attatchment[rowCount + 1];
        final String[] displayNames        = csv.getColumnString(j++);
        final String[] materialNames       = csv.getColumnString(j++);
        final int[]    materialBytes       = csv.getColumnInt(j++);
        final int[]    price               = csv.getColumnInt(j++);
        final String[] colors              = csv.getColumnString(j++);
        final double[] bsMultiplier        = csv.getColumnDouble(j++);
        final double[] damageModifier      = csv.getColumnDouble(j++);
        final double[] damageMultiplier    = csv.getColumnDouble(j++);
        final double[] headshotModifier    = csv.getColumnDouble(j++);
        final double[] headshotMultiplier  = csv.getColumnDouble(j++);
        final double[] critChanceBoost     = csv.getColumnDouble(j++);
        final double[] critMultiplierBoost = csv.getColumnDouble(j++);
        final double[] bleedoutDuration    = csv.getColumnDouble(j++);
        final double[] bleedoutDurationMultiplier = csv.getColumnDouble(j++);
        final double[] bleedoutDamage      = csv.getColumnDouble(j++);
        final double[] bleedoutDamageFromBase = csv.getColumnDouble(j++);
        final double[] bleedoutDamageFromShrap = csv.getColumnDouble(j++);
        final boolean[] isSilencer         = csv.getColumnBoolean(j++);
 
        toReturn[rowCount] = new Attatchment();
        for (int i = 0; i < rowCount; i++)
        {
            toReturn[i] = new Attatchment(
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
                isSilencer[i]);
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
        private final double 
            bulletSpreadMultiplier,
            damageModifier,
            damageMultiplier,
            headshotDamageModifier,
            headshotDamageMultiplier,
            critChanceBoost,
            critStrikeMultiplier,
            bleedoutDurationSeconds,
            bleedoutDurationMultiplier,
            bleedoutDamageBoost,
            bleedoutDamageMultiplierFromBase,
            bleedoutDamageMultiplierFromShrap;
        
        private final boolean isSilencer;

        private Attatchment(final String displayname,
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
                            final boolean isSilencer)
        {        
            super(displayname, materialName, materialByte, price, color);
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
            this.isSilencer = isSilencer;
        }

        /**
         * Constructs the null Attatchment.
         */
        private Attatchment()
        {
            this(null, null, 0, 0, null, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, false);
        }

        public boolean isSilencer()                         { return isSilencer; }
        @Override public double getDamageValue()            { return damageModifier;   }
        @Override public double getDamageMultiplier()       { return damageMultiplier; }
        @Override public double getBulletSpreadMultiplier() { return bulletSpreadMultiplier; }
        @Override public double getCritChance()             { return critChanceBoost; }
        @Override public double getCritStrike()             { return critStrikeMultiplier; }
        @Override public double getBleedoutDurationValue()  { return bleedoutDurationSeconds; }
        @Override public double getBleedoutDamageValuePerSecond() { return bleedoutDamageBoost;  }
        @Override public double getHeadshotDamageValue()          { return headshotDamageModifier; }
        @Override public double getHeadshotDamageMultiplier()     { return headshotDamageMultiplier; }
        @Override public double getBleedoutDurationMultiplier()          { return bleedoutDurationMultiplier; }
        @Override public double getBleedoutDamageMultiplierFromDamage()  { return bleedoutDamageMultiplierFromBase; }
        @Override public double getBleedoutDamageMultiplerFromShrapnel() { return bleedoutDamageMultiplierFromShrap; }
        @Override public Attatchment getNullModifier()                   { return singleton.getNullValue(); }

        @Override
        public double getFireDamageValue()
        {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public double getFireDamageMultiplier()
        {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public double getIgniteChance()
        {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public double getIgniteDuration()
        {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public double getShrapnelDamageValue()
        {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public double getShrapnelDamageMultiplier()
        {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public double getStunChance()
        {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public double getStunDuration()
        {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
}
