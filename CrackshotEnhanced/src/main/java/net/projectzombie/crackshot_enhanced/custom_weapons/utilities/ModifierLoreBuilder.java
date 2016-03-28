/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.utilities;

import java.util.ArrayList;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.projectile.BleedoutModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.skeleton.BoltModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.projectile.BulletSpreadModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.projectile.CritModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.projectile.DamageModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.GunModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.projectile.IgniteModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.projectile.IncendiaryModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.skeleton.MagazineModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.skeleton.ProjectileModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.skeleton.FirearmActions.FirearmAction;
import static net.projectzombie.crackshot_enhanced.custom_weapons.utilities.Constants.FORMATTER;
import static net.projectzombie.crackshot_enhanced.custom_weapons.utilities.Constants.TPS;
import net.projectzombie.crackshot_enhanced.custom_weapons.weps.Guns.CrackshotGun;
import org.bukkit.ChatColor;

/**
 *
 * @author jesse
 */
public class ModifierLoreBuilder
{

    
    private static final String STAT_COLOR = ChatColor.GRAY.toString() + ChatColor.ITALIC.toString();
    private static final String TITLE_COLOR = ChatColor.RED.toString();
    private static final String STAT_TYPE_SEPERATOR = ChatColor.GRAY + "--------------------------------";
    private static final String STAT_SEPERATOR = ChatColor.DARK_GRAY + "---------------";
    
    private final CrackshotGun gun;
    
    public ModifierLoreBuilder(final CrackshotGun gun)
    {
        this.gun = gun;
    }
    
    
    public ArrayList<String> getAllStatInfo()
    {
        final ArrayList<String> stats = new ArrayList<>();
        
        stats.add(toTitle(gun.getName() + " Stats"));
        stats.add(STAT_TYPE_SEPERATOR);
        stats.add(toTitle("Bullet Spread"));
        stats.addAll(this.getBulletSpreadModifierStats());
        stats.add(STAT_TYPE_SEPERATOR);
        stats.add(toTitle("Base Damage"));
        stats.addAll(this.getDamageModifierStats());
        stats.add(STAT_TYPE_SEPERATOR);
        stats.add(toTitle("Bleedout Damage"));
        stats.addAll(getBleedoutStats());
        stats.add(STAT_TYPE_SEPERATOR);
        stats.add(toTitle("Fire Damage"));
        stats.addAll(this.getIncendiaryStats());
        stats.add(STAT_TYPE_SEPERATOR);
        stats.add(toTitle("Critical Strike"));
        stats.addAll(this.getCritModifierStats());
        stats.add(STAT_TYPE_SEPERATOR);
        stats.add(toTitle("Bolt Action"));
        stats.addAll(this.getBoltModifierStats());
        
        return stats;
    }
    
    private String toTitle(final String title)
    {
        return TITLE_COLOR + title;
    }
    
    private ArrayList<String> getBleedoutStats()
    {
        final ArrayList<String> stats = new ArrayList<>();
        double bleedoutDamageValuePerSecond = 0;
        double bleedoutDamageMultFromShrap = 0;
        double bleedoutDurationValue = 0;
        double bleedoutDurationMult = 0;
        
        for (BleedoutModifier mod : gun.getBleedoutModifiers())
        {
            bleedoutDamageValuePerSecond += mod.getBleedoutDamageValuePerSecond();
            bleedoutDamageMultFromShrap += mod.getBleedoutDamageMultiplerFromShrapnel();
            bleedoutDurationValue += mod.getBleedoutDurationValue();
            bleedoutDurationMult += mod.getBleedoutDurationMultiplier();
        }
        
        stats.add(getValueStat(gun.getBleedoutDamagePerSecond(), "total bleed damage p/sec"));
        stats.add(getValueStat(gun.getBleedoutDurationInSeconds(), "total bleed duration"));
        stats.add(STAT_SEPERATOR);
        stats.add(getValueStat(bleedoutDamageValuePerSecond, "bleed damage p/sec"));
        stats.add(getMultiplierStat(bleedoutDamageValuePerSecond, "bleed damage dealt from base damage p/sec"));
        stats.add(getMultiplierStat(bleedoutDamageValuePerSecond, "bleed damage dealt from shrapnel damage p/sec"));
        stats.add(getValueStat(bleedoutDurationValue, "bleed duration"));
        stats.add(getMultiplierStat(bleedoutDurationMult, "bleed duration"));
        
        return stats;
    }
    
    private ArrayList<String> getBoltModifierStats()
    {
        final ArrayList<String> stats = new ArrayList<>();
        final double baseBoltSpeed = gun.getWeaponType().getAction().getBoltActionDurationInTicks();
        final double newBoltSpeed;
        double boltDurationMultiplier = 0; 
        for (BoltModifier mod : gun.getBoltModifiers())
        {
            boltDurationMultiplier += mod.getBoltDurationMultiplier();
        }
        newBoltSpeed = baseBoltSpeed * (1.0 + boltDurationMultiplier);
        
        stats.add(getValueStat(newBoltSpeed / TPS, "bolt action duration in seconds"));
        stats.add(STAT_SEPERATOR);
        stats.add(getValueStat(baseBoltSpeed / TPS, "stock bolt action duration"));
        stats.add(getMultiplierStat(boltDurationMultiplier, "bolt action duration"));
        
        return stats;
    }
    
    private ArrayList<String> getBulletSpreadModifierStats()
    {
        final ArrayList<String> stats = new ArrayList<>();
        final double originalBS = gun.getBulletSpread();
        double bsMultiplier = 0;
        final double newBS;
        
        for (BulletSpreadModifier mod : gun.getBulletSpreadModifiers())
        {
            bsMultiplier += mod.getBulletSpreadMultiplier();
        }
        newBS = originalBS * (1.0 + bsMultiplier);
        
        stats.add(getValueStat(newBS, "total bullet spread"));
        stats.add(STAT_SEPERATOR);
        stats.add(getValueStat(originalBS, "stock bullet spread"));
        stats.add(getMultiplierStat(bsMultiplier, "bullet spread"));
        
        return stats;
    }
    
    private ArrayList<String> getCritModifierStats()
    {
        final ArrayList<String> stats = new ArrayList<>();
        double critStrike = 0;
        for (CritModifier mod : gun.getCritModifiers())
        {
            critStrike += mod.getCritStrike();
        }
        stats.add(getMultiplierStat(gun.getCritStrike(), "total damage on critical hit"));
        stats.add(STAT_SEPERATOR);
        stats.add(getMultiplierStat(critStrike, "base damage dealt on critical hit"));
        stats.add(getMultiplierStat(gun.getCritChance(), "critical hit chance"));
        
        return stats;
    }
    
    
    private ArrayList<String> getDamageModifierStats()
    {
        final ArrayList<String> stats = new ArrayList<>();
        
        final double totalHeadshotDamage;
        final double stockDamage = gun.getSkeletonBaseDamage();
        double headShotValue = 0;
        double headShotMultiplier = 0;
        for (DamageModifier mod : gun.getBaseDamage().getModifiers())
        {
            headShotValue += mod.getHeadshotDamageModifier();
            headShotMultiplier += mod.getHeadshotDamageMultiplier();
        }
        
        totalHeadshotDamage = headShotValue + (1.0 * headShotMultiplier);
        stats.add(getValueStat(gun.getBaseDamage().getTotal(), "total base damage"));
        stats.add(getValueStat(totalHeadshotDamage, "extra damage on headshot"));
        stats.add(STAT_SEPERATOR);
        stats.add(getValueStat(stockDamage, "stock base damage"));
        stats.add(getValueStat(gun.getBaseDamage().getValue(), "base damage"));
        stats.add(getMultiplierStat(gun.getBaseDamage().getMultiplier(), "base damage"));
        stats.add(getValueStat(headShotValue, "head shot damage"));
        stats.add(getMultiplierStat(headShotMultiplier, "head shot damage"));

        return stats;
    }
    
    private ArrayList<String> getIncendiaryStats()
    {
        final ArrayList<String> stats = new ArrayList<>();
        final double totalFireDamage = gun.getFireDamage();

        double fireDamageValue = 0;
        double fireDamageMultiplier = 0;
        
        for (IncendiaryModifier mod : gun.getIncendiaryModifiers())
        {
            fireDamageValue += mod.getFireDamageValue();
            fireDamageMultiplier += mod.getFireDamageMultiplier();
        }
        
        stats.add(getValueStat(totalFireDamage, "total fire damage"));
        stats.add(STAT_SEPERATOR);
        stats.add(getValueStat(fireDamageValue, "fire damage"));
        stats.add(getMultiplierStat(fireDamageMultiplier, "fire damage"));
        return stats;
    }
    
    private ArrayList<String> getIgniteStats()
    {
        final ArrayList<String> stats = new ArrayList<>();
        final double totalIgniteDamage;

        double igniteChance = 0;
        double igniteDuration = 0;
        double igniteDamageFromFireMult = 0;
        
        for (IgniteModifier mod : gun.getIgniteModifiers())
        {
            igniteChance += mod.getIgniteChance();
            igniteDuration += mod.getIgniteDuration();
            igniteDamageFromFireMult += mod.getIgniteDamageMultiplierFromFireDamage();
        }
        totalIgniteDamage = (igniteDamageFromFireMult * gun.getFireDamage());
        
        stats.add(getValueStat(totalIgniteDamage, "total ignite damage p/sec"));
        stats.add(getMultiplierStat(igniteChance, "ignite chance"));
        stats.add(getValueStat(igniteDuration, "ignite duration in seconds"));
        stats.add(STAT_SEPERATOR);
        stats.add(getMultiplierStat(igniteDamageFromFireMult, "ignite damage dealt from fire damage p/sec"));
        return stats;
    }
    
    static
    private String getMultiplierStat(final double multiplier,
                                     final String description)
    {
        return STAT_COLOR + "  " + numPercentage(multiplier) + " " + description;

    }
    
    static
    private String getValueStat(final double value,
                                final String description)
    {
        return STAT_COLOR + "  " + numValue(value) + " " + description;
    }
    
    static
    private String numPercentage(final double num)
    {
        return getSign(num) + FORMATTER.format(num) + "%";
    }
    
    static
    private String numValue(final double num)
    {
        return getSign(num) + FORMATTER.format(num);
    }
    
    static
    private String getSign(final double num)
    {
        if (num > 0.0)
            return "+";
        else if (num == 0)
            return "";
        else
            return "-";
    }
}
