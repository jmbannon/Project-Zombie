/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.modifiers;

import net.projectzombie.crackshot_enhanced.custom_weapons.csv.CSVReader;
import net.projectzombie.crackshot_enhanced.custom_weapons.csv.CSVInput;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.AOEAttachments.AOEAttachment;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Attachments.Attachment;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.aoe.AOEModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.aoe.CombustModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.aoe.ElectricityModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.aoe.ExplosiveAOEModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.aoe.FlameAOE;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.aoe.PoisonAOEModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.aoe.RadiationAOEModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.aoe.ShockModifier;
/**
 *
 * @author jbannon
 */
public class AOEAttachments extends CSVInput<AOEAttachment>
{
    static private AOEAttachments singleton = null;
            
    static public AOEAttachments getInstance()
    {
        if (singleton == null)
            singleton = new AOEAttachments(ATTATCHMENT_ONE_CSV_NAME);
        return singleton;
    }
    
    static private final String ATTATCHMENT_ONE_CSV_NAME = "AOEAttatchments.csv";
    
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
        "Ignite Damage Multiplier From Fire Damage (DBL)",
        "Ignite Damage Multiplier From Base Damage (DBL)",
        "Shrapnel Damage Modifier (DBL)",
        "Shrapnel Damage Multiplier (DBL)",
        "Stun Chance (DBL)",
        "Stun Duration (DBL)"
    };
    
    private AOEAttachments(final String csvName)
    {
        super(csvName, buildAttatchments(csvName), ATTATCHMENT_VALUES);
    }
    
    @Override
    public AOEAttachment getNullValue()
    {
        return new AOEAttachment();
    }
    
    /**
     * Builds all attatchments, if any. Allowed to not have any in CSV.
     * @return Array of all attatchments (including null attatchment).
     */
    static private AOEAttachment[] buildAttatchments(final String csvName)
    {
        final CSVReader csv = new CSVReader(csvName, ATTATCHMENT_VALUES);
        final int rowCount = csv.getRowCount();
 
        if (rowCount <= 0)
        {
            return new AOEAttachment[] { new AOEAttachment() };
        }
        int j = 0;
        final AOEAttachment[] toReturn    = new AOEAttachment[rowCount + 1];
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
        final double[] igniteDamageMultiplierFromFire = csv.getColumnDouble(j++);
        final double[] igniteDamageMultiplierFromBase = csv.getColumnDouble(j++);
        final double[] shrapnelDamageModifier     = csv.getColumnDouble(j++);
        final double[] shrapnelDamageMultiplier   = csv.getColumnDouble(j++);
        final double[] stunChance                 = csv.getColumnDouble(j++);
        final double[] stunDuration               = csv.getColumnDouble(j++);
 
        toReturn[0] = new AOEAttachment();
        for (int i = 0; i < rowCount; i++)
        {
            toReturn[i+1] = new AOEAttachment(
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
                    igniteDamageMultiplierFromFire[i],
                    igniteDamageMultiplierFromBase[i],
                    shrapnelDamageModifier[i],
                    shrapnelDamageMultiplier[i],
                    stunChance[i],
                    stunDuration[i]
            );
        }
        return toReturn;
    }

    static public class AOEAttachment extends Attachment implements AOEModifier,
            CombustModifier,
            ElectricityModifier,
            ExplosiveAOEModifier,
            FlameAOE,
            PoisonAOEModifier,
            RadiationAOEModifier,
            ShockModifier
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
        private final double igniteDamageMultiplierFromFire;
        private final double igniteDamageMultiplierFromBase;
        private final double shrapnelDamageModifier;
        private final double shrapnelDamageMultiplier;
        private final double stunChance;
        private final double stunDuration;

        private AOEAttachment(final int uniqueID,
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
                            final double igniteDamageMultiplierFromFire,
                            final double igniteDamageMultiplierFromBase,
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
            this.igniteDamageMultiplierFromFire = igniteDamageMultiplierFromFire;
            this.igniteDamageMultiplierFromBase = igniteDamageMultiplierFromBase;
            this.shrapnelDamageModifier = shrapnelDamageModifier;
            this.shrapnelDamageMultiplier = shrapnelDamageMultiplier;
            this.stunChance = stunChance;
            this.stunDuration = stunDuration;
        }

        /**
         * Constructs the null Attatchment.
         */
        private AOEAttachment()
        {
            this(0, null, null, 0, 0, null, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        }

        @Override public double getAOERadiusValue()                        { return damageModifier; }
        @Override public double getAOERadiusMultiplier()                   { return damageMultiplier; }
        @Override public double getAOEDurationValue()                      { return bulletSpreadMultiplier; }
        @Override public double getAOEDurationMultiplier()                 { return critChanceBoost; }
        @Override public double getCombustAOERadius()                      { return critStrikeMultiplier; }
        @Override public double getCombustAOEDamageValue()                 { return bleedoutDurationSeconds; }
        @Override public double getCombustAOEDamageMultiplier()            { return bleedoutDamageBoost;  }
        @Override public double getElectricityDamageValue()                { return headshotDamageMultiplier; }
        @Override public double getElectricityDamageMultiplier()           { return headshotDamageModifier; }
        @Override public double getConcurrentElectricityCount()            { return bleedoutDamageMultiplierFromBase; }
        @Override public double getExplosiveAOERadius()                    { return bleedoutDamageMultiplierFromShrap; }
        @Override public double getExplosiveAOEDamageValue()               { return fireDamageModifier; }
        @Override public double getExplosiveAOEDamageMultiplier()          { return fireDamageMultiplier; }
        @Override public double getFlameAOERadius()                        { return igniteChance; }
        @Override public double getFlameAOEDuration()                      { return igniteDuration; }
        @Override public double getFlameAOEDamagePerSecond()               { return igniteDamageMultiplierFromFire; }
        @Override public double getFlameAOEDamagePerSecondMultiplier()     { return igniteDamageMultiplierFromBase; }
        @Override public double getPoisonAOERadius()                       { return shrapnelDamageModifier; }
        @Override public double getPoisonAOEDuration()                     { return shrapnelDamageMultiplier; }
        @Override public double getPoisonAOEDamagePerSecond()              { return stunChance; }
        @Override public double getPoisonAOEDamagePerSecondMultiplier()    { return stunDuration; }
        @Override public double getRadiationAOERadius()                    { return 0; }
        @Override public double getRadiationAOEDuration()                  { return 0; }
        @Override public double getRadiationAOEDamagePerSecond()           { return 0; }
        @Override public double getRadiationAOEDamagePerSecondMultiplier() { return 0; }
        @Override public double getShockChance()                           { return 0; }
        @Override public double getShockDamageValue()                      { return 0; }
        @Override public double getShockDamageMultiplierFromElectricity()  { return 0; }
        @Override public boolean isAOE() { return true; }
        @Override
        public GunModifier getNullModifier() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        
    }
}
