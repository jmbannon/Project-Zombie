/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.modifiers;

import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.BleedoutModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.BulletSpreadModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.CritModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.DamageModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.GunModifier;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import static org.bukkit.Material.MONSTER_EGG;
import org.bukkit.material.MaterialData;

/**
 *
 * @author jbannon
 */
public enum Attatchment implements GunModifier, 
                                   BulletSpreadModifier, 
                                   DamageModifier,
                                   CritModifier,
                                   BleedoutModifier
{
    NULL_ATTATCHMENT        (null,        0,  0,  null,                          1.0,  0, 0,  0,  0, 0),
    SUPPRESOR               (MONSTER_EGG, 50, 10, "Suppressor",                  1.2, -2, 0,  0,  0, 0),
    INCENDIARY              (MONSTER_EGG, 51, 10, "Incendiary Rounds",           1.0,  0, 0,  0,  0, 0),
    CRIT_CHANCE             (MONSTER_EGG, 52, 10, "Crit Chance Increase",        1.0,  0, .3, 0,  0, 0),
    CRIT_STRIKE             (MONSTER_EGG, 54, 20, "Crit Strike Multiplier",      1.0,  0, 0,  .3, 0, 0),
    BLEEDOUT_INCREASE       (MONSTER_EGG, 55, 20, "Bleedout Time Increase",      0,    0, 0,  0,  2, 0),
    BLEEDOUT_DAMAGE_INCREASE(MONSTER_EGG, 56, 20, "Bleedout Damage Increase",    0,    0, 0,  0,  0, 2);
    
    private final int price;
    private final String displayName;
    private final MaterialData materialData;
    
    Attatchment(final Material material,
                final int materialByte,
                final int price,
                final String displayname,
                final double bulletSpreadPercentModifier,
                final double damageModifier,
                final double critChanceBoost,
                final double critMultiplierBoost,
                final double bleedoutDurationSeconds,
                final double bleedoutDamageBoost)
    {
        this.price = price;
        this.displayName = displayname;
        if (material == null)
            materialData = null;
        else
            materialData = new MaterialData(material, (byte)materialByte);
    }
    
    
    @Override public String toString()                  { return displayName; }
    @Override public int    price()                     { return price;       }
    @Override public String getDisplayName()            { return displayName; }
    @Override public double getDamageValue()            { return 0;           }
    @Override public double getDamageMultiplier()       { return 1.0;         }
    @Override public double getBulletSpreadMultiplier() { return 1.0; }
    @Override public double getCritChance()             { return 1.2; }
    @Override public double getCritStrike()             { return 1.1; }
    @Override public double getBleedoutDurationValue()  { return 1.0; }
    @Override public double getBleedoutDamageValue()    { return 1;  }
    @Override public boolean isNull()                   { return this.equals(NULL_ATTATCHMENT); }
    @Override public ChatColor getColor()               { return ChatColor.GREEN; }
    @Override public MaterialData getMaterialData()     { return materialData; }
    
}
