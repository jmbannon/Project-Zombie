/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.utilities;

import java.util.ArrayList;
import java.util.Collections;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.BleedoutModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.BoltModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.BulletSpreadModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.CritModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.DamageModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.GunModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.MagazineModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.ProjectileModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.weps.Guns.CrackshotGun;
import org.bukkit.ChatColor;

/**
 *
 * @author jesse
 */
public class ModifierLoreBuilder
{

    private static final String statColor = ChatColor.GRAY.toString() + ChatColor.ITALIC.toString();
    
    private ModifierLoreBuilder() { /* Do nothing */ }
    
    static
    public ArrayList<String> buildModifierLore(final CrackshotGun gun)
    {
        final ArrayList<ArrayList<String>> attatchmentStats = new ArrayList<>();
        final ArrayList<String> loreToReturn = new ArrayList<>();
        ArrayList<String> attatchmentStat;
        for (GunModifier mod : gun.getCraftableModifiers())
        {
            if (!mod.isNull())
            {
                attatchmentStat = buildGunModifierLore(mod);
                if (attatchmentStat != null && !attatchmentStat.isEmpty())
                    attatchmentStats.add(attatchmentStat);
            }
        }
        
        Collections.shuffle(attatchmentStats);
        for (ArrayList<String> temp : attatchmentStats)
        {
            loreToReturn.addAll(temp);
        }
        
        return loreToReturn;
    }
    
    static
    private ArrayList<String> buildGunModifierLore(final GunModifier mod)
    {
        final ArrayList<String> stats = new ArrayList<>();
        
        if (mod instanceof BleedoutModifier)
        {
            stats.addAll(getBleedoutStats((BleedoutModifier)mod));
        }
        if (mod instanceof BoltModifier)
        {
            stats.addAll(getBoltModifierStats((BoltModifier)mod));
        }
        if (mod instanceof BulletSpreadModifier)
        {
            stats.addAll(getBulletSpreadModifierStats((BulletSpreadModifier)mod));
        }
        if (mod instanceof CritModifier)
        {
            stats.addAll(getCritModifierStats((CritModifier)mod));
        }
        if (mod instanceof DamageModifier)
        {
            stats.addAll(getDamageModifierStats((DamageModifier)mod));
        }
        if (mod instanceof MagazineModifier)
        {
            stats.addAll(getMagazineModifierStats((MagazineModifier)mod));
        }
        if (mod instanceof ProjectileModifier)
        {
            stats.addAll(getProjectileModifierStats((ProjectileModifier)mod));
        }
        
        if (!stats.isEmpty())
        {
            Collections.shuffle(stats);
            stats.add(0, mod.getColor() + mod.getName());
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
        
        if (bleedDamage != null)
            stats.add(bleedDamage);
        
        if (bleedDuration != null)
            stats.add(bleedDuration);
        
        return stats;
    }
    
    static
    ArrayList<String> getBoltModifierStats(final BoltModifier boltMod)
    {
        final ArrayList<String> stats = new ArrayList<>();
        final String durationIncrease = getMultiplierStat(boltMod.getBoltActionDurationMultiplier(), "bolt action speed");
        
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
        final String magBoost = getValueStat(magMod.getMagazineValue(), "mag size");
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
