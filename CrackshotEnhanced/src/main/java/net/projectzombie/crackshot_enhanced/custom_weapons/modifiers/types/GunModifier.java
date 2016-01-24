/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types;

import java.util.ArrayList;
import java.util.Collections;
import net.projectzombie.crackshot_enhanced.custom_weapons.csv.CSVValue;
import net.projectzombie.crackshot_enhanced.custom_weapons.utilities.GunUtils;

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
    
    private final Material material;
    private final int materialData;
    private final int price;
    private final ChatColor color;
    
    public GunModifier(final String name,
                       final String material,
                       final int materialData,
                       final int price,
                       final String color)
    {
        super(name);
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
        
        if (this instanceof BleedoutModifier)
        {
            stats.addAll(getBleedoutStats((BleedoutModifier)this));
        }
        if (this instanceof BoltModifier)
        {
            stats.addAll(getBoltModifierStats((BoltModifier)this));
        }
        if (this instanceof BulletSpreadModifier)
        {
            stats.addAll(getBulletSpreadModifierStats((BulletSpreadModifier)this));
        }
        if (this instanceof CritModifier)
        {
            stats.addAll(getCritModifierStats((CritModifier)this));
        }
        if (this instanceof DamageModifier)
        {
            stats.addAll(getDamageModifierStats((DamageModifier)this));
        }
        if (this instanceof DurabilityModifier)
        {
            
        }
        if (this instanceof IncendiaryModifier)
        {
            
        }
        if (this instanceof MagazineModifier)
        {
            stats.addAll(getMagazineModifierStats((MagazineModifier)this));
        }
        if (this instanceof ProjectileModifier)
        {
            stats.addAll(getProjectileModifierStats((ProjectileModifier)this));
        }
        if (this instanceof Shrapnel)
        {
            
        }
        
        if (!stats.isEmpty())
        {
            Collections.shuffle(stats);
            stats.add(0, this.color + super.getName());
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
        
        if (bleedDamage != null)
            stats.add(bleedDamage);
        if (bleedDuration != null)
            stats.add(bleedDuration);
        if (bleedoutDurationMult != null)
            stats.add(bleedoutDurationMult);
        if (bleedDamageFromBase != null)
            stats.add(bleedDamageFromBase);
        if (bleedDamageFromShrap != null)
            stats.add(bleedDamageFromShrap);
        
        return stats;
    }
    
    static
    private ArrayList<String> getBoltModifierStats(final BoltModifier boltMod)
    {
        final ArrayList<String> stats = new ArrayList<>();
        final String durationIncrease = getMultiplierStat(boltMod.getBoltDurationMultiplier(), "bolt action speed");
        
        if (durationIncrease != null)
            stats.add(durationIncrease);
        
        return stats;
    }
    
    static
    ArrayList<String> getBulletSpreadModifierStats(final BulletSpreadModifier bsMod)
    {
        final ArrayList<String> stats = new ArrayList<>();
        final String bsMultiplier = getMultiplierStat(bsMod.getBulletSpreadMultiplier(), "bullet spread");
        
        if (bsMultiplier != null)
            stats.add(bsMultiplier);
        
        return stats;
    }
    
    static
    private ArrayList<String> getCritModifierStats(final CritModifier critMod)
    {
        final ArrayList<String> stats = new ArrayList<>();
        final String critStrike = getMultiplierStat(critMod.getCritStrike(), "critical strike");
        final String critChance = getMultiplierStat(critMod.getCritChance(), "critical chance");
        
        if (critStrike != null)
            stats.add(critStrike);
        
        if (critChance != null)
            stats.add(critChance);
        
        return stats;
    }
    
    static
    private ArrayList<String> getDamageModifierStats(final DamageModifier damageMod)
    {
        final ArrayList<String> stats = new ArrayList<>();
        final String damageMultiplier = getMultiplierStat(damageMod.getDamageMultiplier(), "damage multiplier");
        final String damageValue = getValueStat(damageMod.getDamageValue(), "base damage");
        
        if (damageMultiplier != null)
            stats.add(damageMultiplier);
        
        if (damageValue != null)
            stats.add(damageValue);
        
        return stats;
    }
    
    static
    private ArrayList<String> getMagazineModifierStats(final MagazineModifier magMod)
    {
        final ArrayList<String> stats = new ArrayList<>();
        final String magBoost = getValueStat(magMod.getMagazineModifier(), "mag size");
        final String reloadSpeed = getMultiplierStat(magMod.getReloadSpeedMultiplier(), "reload speed");
        
        if (magBoost != null)
            stats.add(magBoost);
        
        if (reloadSpeed != null)
            stats.add(reloadSpeed);
        
        return stats;
    }
    
    static
    private ArrayList<String> getProjectileModifierStats(final ProjectileModifier projMod)
    {
        final ArrayList<String> stats = new ArrayList<>();
        final String projBoost = getValueStat(projMod.getProjectileValue(), "projectiles");
        
        if (projBoost != null)
            stats.add(projBoost);
        
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
