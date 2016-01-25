/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.modifiers;

import java.util.ArrayList;
import java.util.Collections;
import net.projectzombie.crackshot_enhanced.custom_weapons.csv.CSVValue;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.BleedoutModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.BoltModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.BulletSpreadModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.CritModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.DamageModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.DurabilityModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.FireModeModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.IncendiaryModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.MagazineModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.ProjectileModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.RunningModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.Shrapnel;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.SilencerModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.ZoomModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.utilities.GunUtils;
import net.projectzombie.crackshot_enhanced.custom_weapons.utilities.HiddenStringUtils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;

/**
 *
 * @author jesse
 */
public abstract class GunModifier extends CSVValue
{
    private static final String statColor = ChatColor.GRAY.toString() 
                                               + ChatColor.ITALIC.toString();
    
    private final int uniqueID;
    private final Material material;
    private final int materialData;
    private final int price;
    private final ChatColor color;
    
    public GunModifier(final int uniqueID,
                       final String name,
                       final String material,
                       final int materialData,
                       final int price,
                       final String color)
    {
        super(name);
        this.uniqueID = uniqueID;
        this.materialData = materialData;
        this.price = price;
        this.material = GunUtils.matchMaterial(material);
        this.color = GunUtils.matchChatColor(color);
    }
    
    /**
     * @return Returns the null modifier.
     */
    abstract public GunModifier getNullModifier();
    
    /**
     * @return Returns the unique ID of the GunModifier (also the index within the array).
     */
    public int getUniqueID()
    {
        return uniqueID;
    }
    
    /**
     * @return Returns the price of the gun modification at the gunsmith.
     */
    public int price()
    {
        return price;
    }
    
    /**
     * @return Color of the GunModifier in lore based on rarity.
     */
    public ChatColor getColor()
    {
        return color;
    }
    
    /**
     * @return MaterialData of item if craftable. Null otherwise.
     */
    public MaterialData getMaterialData()
    {
        if (material == null)
            return null;
        else
            return new MaterialData(material, (byte)materialData);
    }
    
    /**
     * @return Whether the modifier is null.
     */
    public boolean isNull()
    {
        return this.equals(this.getNullModifier());
    }
    
    public ArrayList<String> getLore()
    {
        final ArrayList<String> stats = new ArrayList<>();
        if (this.isNull())
            return null;
        
        if (this instanceof BleedoutModifier)     stats.addAll(getBleedoutStats((BleedoutModifier)this));
        if (this instanceof BoltModifier)         stats.addAll(getBoltModifierStats((BoltModifier)this));
        if (this instanceof BulletSpreadModifier) stats.addAll(getBulletSpreadModifierStats((BulletSpreadModifier)this));
        if (this instanceof CritModifier)         stats.addAll(getCritModifierStats((CritModifier)this));
        if (this instanceof DamageModifier)       stats.addAll(getDamageModifierStats((DamageModifier)this));
        if (this instanceof DurabilityModifier)   stats.addAll(getDurabilityModifierStats((DurabilityModifier)this));
        if (this instanceof FireModeModifier)     stats.addAll(getFireModeModifierStats((FireModeModifier)this));
        if (this instanceof IncendiaryModifier)   stats.addAll(getIncendiaryModifierStats((IncendiaryModifier)this));
        if (this instanceof MagazineModifier)     stats.addAll(getMagazineModifierStats((MagazineModifier)this));
        if (this instanceof ProjectileModifier)   stats.addAll(getProjectileModifierStats((ProjectileModifier)this));
        if (this instanceof RunningModifier)      stats.addAll(getRunningModifierStats((RunningModifier)this));
        if (this instanceof Shrapnel)             stats.addAll(getShrapnelModifierStats((Shrapnel)this));
        if (this instanceof SilencerModifier)     stats.addAll(getSilencerModifierStats((SilencerModifier)this));
        if (this instanceof ZoomModifier)         stats.addAll(getZoomModifierStats((ZoomModifier)this));

        if (!stats.isEmpty())
        {
            Collections.shuffle(stats);
            stats.add(0, HiddenStringUtils.encodeString(String.valueOf(this.uniqueID)));
            stats.add(1, this.color + super.getName());
            return stats;
        }
        else
        {
            return null;
        }
    }
    
    static
    private ArrayList<String> getBleedoutStats(final BleedoutModifier bleedoutMod)
    {
        final ArrayList<String> stats = new ArrayList<>();
        final String bleedDamage = getValueStat(bleedoutMod.getBleedoutDamageValuePerSecond(), "bleed damage p/sec");
        final String bleedDuration = getValueStat(bleedoutMod.getBleedoutDurationValue(), "bleed duration");
        final String bleedoutDurationMult = getMultiplierStat(bleedoutMod.getBleedoutDurationMultiplier(), "bleed duration");
        final String bleedDamageFromBase = getMultiplierStat(bleedoutMod.getBleedoutDamageMultiplierFromDamage(), "bleed damage from base damage p/sec");
        final String bleedDamageFromShrap = getMultiplierStat(bleedoutMod.getBleedoutDamageMultiplerFromShrapnel(), "bleed damage from shrapnel damage p/sec");
        
        if (bleedDamage != null)          stats.add(bleedDamage);
        if (bleedDuration != null)        stats.add(bleedDuration);
        if (bleedoutDurationMult != null) stats.add(bleedoutDurationMult);
        if (bleedDamageFromBase != null)  stats.add(bleedDamageFromBase);
        if (bleedDamageFromShrap != null) stats.add(bleedDamageFromShrap);
        
        return stats;
    }
    
    static
    private ArrayList<String> getBoltModifierStats(final BoltModifier boltMod)
    {
        final ArrayList<String> stats = new ArrayList<>();
        final String durationIncrease = getMultiplierStat(boltMod.getBoltDurationMultiplier(), "bolt action speed");
        
        if (durationIncrease != null) stats.add(durationIncrease);
        
        return stats;
    }
    
    static
    ArrayList<String> getBulletSpreadModifierStats(final BulletSpreadModifier bsMod)
    {
        final ArrayList<String> stats = new ArrayList<>();
        final String bsMultiplier = getMultiplierStat(bsMod.getBulletSpreadMultiplier(), "bullet spread");
        
        if (bsMultiplier != null) stats.add(bsMultiplier);
        
        return stats;
    }
    
    static
    private ArrayList<String> getCritModifierStats(final CritModifier critMod)
    {
        final ArrayList<String> stats = new ArrayList<>();
        final String critStrike = getMultiplierStat(critMod.getCritStrike(), "critical strike");
        final String critChance = getMultiplierStat(critMod.getCritChance(), "critical chance");
        
        if (critStrike != null) stats.add(critStrike);
        if (critChance != null) stats.add(critChance);
        
        return stats;
    }
    
    static
    private ArrayList<String> getDamageModifierStats(final DamageModifier damageMod)
    {
        final ArrayList<String> stats = new ArrayList<>();
        final String damageValue = getValueStat(damageMod.getDamageValue(), "base damage");
        final String damageMultiplier = getMultiplierStat(damageMod.getDamageMultiplier(), "base damage");
        final String headshotValue = getValueStat(damageMod.getHeadshotDamageModifier(), "headshot damage");
        final String headshotMultiplier = getValueStat(damageMod.getHeadshotDamageMultiplier(), "headshot damage");
        
        if (damageValue != null)        stats.add(damageValue);
        if (damageMultiplier != null)   stats.add(damageMultiplier);
        if (headshotValue != null)      stats.add(headshotValue);
        if (headshotMultiplier != null) stats.add(headshotMultiplier);
        
        return stats;
    }
    
    static
    private ArrayList<String> getDurabilityModifierStats(final DurabilityModifier durMod)
    {
        final ArrayList<String> stats = new ArrayList<>();
        final String durabilityValue = getValueStat(durMod.getDurabilityModifier(), "durability");
        final String durabilityMultiplier = getMultiplierStat(durMod.getDurabilityMultiplier(), "durability");
        
        if (durabilityValue != null)      stats.add(durabilityValue);
        if (durabilityMultiplier != null) stats.add(durabilityMultiplier);
        
        return stats;
    }
    
    static
    private ArrayList<String> getFireModeModifierStats(final FireModeModifier durMod)
    {
        final ArrayList<String> stats = new ArrayList<>();
        final String fireModeType;
        
        if (durMod.isBurstFire())
            fireModeType = getValueStat(durMod.getShotsPerBurst(), "round burst");
        else if (durMod.isAutomatic())
            fireModeType = getBooleanStat(durMod.isAutomatic(), "Automatic");
        else
            fireModeType = null;
        
        if (fireModeType != null) stats.add(fireModeType);
        
        return stats;
    }
    
    static
    private ArrayList<String> getIncendiaryModifierStats(final IncendiaryModifier incMod)
    {
        final ArrayList<String> stats = new ArrayList<>();
        final String fireValue = getValueStat(incMod.getFireDamageValue(), "fire damage");
        final String fireMult = getMultiplierStat(incMod.getFireDamageMultiplier(), "fire damage");
        final String igniteChance = getMultiplierStat(incMod.getIgniteChance(), "ignite chance");
        final String igniteDuration = getValueStat(incMod.getIgniteDuration(), "ignite duration");
        
        if (fireValue != null)      stats.add(fireValue);
        if (fireMult != null)       stats.add(fireMult);
        if (igniteChance != null)   stats.add(igniteChance);
        if (igniteDuration != null) stats.add(igniteDuration);
        
        return stats;
    }
    
    static
    private ArrayList<String> getMagazineModifierStats(final MagazineModifier magMod)
    {
        final ArrayList<String> stats = new ArrayList<>();
        final String magBoost = getValueStat(magMod.getMagazineSizeModifier(), "mag size");
        final String magMult = getMultiplierStat(magMod.getMagazineSizeMultiplier(), "mag size");
        final String reloadSpeed = getMultiplierStat(magMod.getReloadSpeedMultiplier(), "reload speed");
        
        if (magBoost != null)    stats.add(magBoost);
        if (magMult != null)     stats.add(magMult);
        if (reloadSpeed != null) stats.add(reloadSpeed);
        
        return stats;
    }
    
    static
    private ArrayList<String> getProjectileModifierStats(final ProjectileModifier projMod)
    {
        final ArrayList<String> stats = new ArrayList<>();
        final String projBoost = getValueStat(projMod.getProjectileValue(), "projectiles");
        final String rangeValue = getValueStat(projMod.getProjectileRangeValue(), "projectile range");
        final String rangeMult = getMultiplierStat(projMod.getProjectileRangeMultiplier(), "projectile range");
        final String speedMult = getMultiplierStat(projMod.getProjectileSpeedMultiplier(), "projectile speed");
        
        if (projBoost != null)  stats.add(projBoost);
        if (rangeValue != null) stats.add(rangeValue);
        if (rangeMult != null)  stats.add(rangeMult);
        if (speedMult != null)  stats.add(speedMult);
        
        return stats;
    }
    
    static
    private ArrayList<String> getRunningModifierStats(final RunningModifier runMod)
    {
        final ArrayList<String> stats = new ArrayList<>();
        final String runningBS = getMultiplierStat(runMod.getRunningBulletSpreadMultiplier(), "bullet spread while running");
        final String runningSpeed = getMultiplierStat(runMod.getRunningSpeedMultiplier(), "running speed");
        
        if (runningBS != null)    stats.add(runningBS);
        if (runningSpeed != null) stats.add(runningSpeed);
        
        return stats;
    }
    
    static
    private ArrayList<String> getShrapnelModifierStats(final Shrapnel shrapMod)
    {
        final ArrayList<String> stats = new ArrayList<>();
        final String shrapVal = getValueStat(shrapMod.getShrapnelDamageValue(), "shrapnel damage");
        final String shrapMul = getValueStat(shrapMod.getShrapnelDamageMultiplier(), "shrapnel damage");
        final String stunChance = getMultiplierStat(shrapMod.getStunChance(), "stun chance");
        final String stunDur = getMultiplierStat(shrapMod.getStunDuration(), "stun duration");
        
        if (shrapVal != null)   stats.add(shrapVal);
        if (shrapMul != null)   stats.add(shrapMul);
        if (stunChance != null) stats.add(stunChance);
        if (stunDur != null)    stats.add(stunDur);
        
        return stats;
    }
    
    static
    private ArrayList<String> getSilencerModifierStats(final SilencerModifier silMod)
    {
        final ArrayList<String> stats = new ArrayList<>();
        final String isSil = getBooleanStat(silMod.isSilencer(), "Silenced");
        
        if (isSil != null) stats.add(isSil);
        
        return stats;
    }
    
    static
    private ArrayList<String> getZoomModifierStats(final ZoomModifier zoomMod)
    {
        final ArrayList<String> stats = new ArrayList<>();
        final String zoomVal = getValueStat(zoomMod.getZoomAmount(), "zoom amount");
        final String zoomBS = getMultiplierStat(zoomMod.getZoomBulletSpreadMultiplier(), "bullet spread while zoomed");
        
        if (zoomVal != null) stats.add(zoomVal);
        if (zoomBS != null)  stats.add(zoomBS);
        
        return stats;
    }
    
    static
    private String getMultiplierStat(final double multiplier,
                                     final String description)
    {
        if (modifiesMultiplier(multiplier))
        {
            return statColor + "  " + numPercentage(multiplier) + " " + description;
        }
        else
            return null;
    }
    
    static
    private String getValueStat(final double value,
                                final String description)
    {
        if (modifiesValue(value))
            return statColor + "  " + numValue(value) + " " + description;
        else
            return null;
    }
    
    static
    private String getBooleanStat(final boolean value,
                                  final String description)
    {
        if (value)
            return statColor + "  " + description;
        else
            return null;
    }
    
    static 
    private boolean modifiesValue(final double valueModifier)
    {
        return valueModifier != 0.;
    }
    
    static
    private boolean modifiesMultiplier(final double multiplier)
    {
        return multiplier != 0. && multiplier != 1.;
    }
    
    static
    private String numPercentage(final double num)
    {
        return getPercentageSign(num) + Math.round(Math.abs(1.-num) * 100) + "%";
    }
    
    static
    private String numValue(final double num)
    {
        return getValueSign(num) + Math.round(num);
    }
    
    static
    private String getPercentageSign(final double num)
    {
        if (num >= 1.0)
            return "+";
        else
            return "-";
    }
    
    static
    private String getValueSign(final double num)
    {
        if (num >= 0)
            return "+";
        else
            return "-";
    }
}
