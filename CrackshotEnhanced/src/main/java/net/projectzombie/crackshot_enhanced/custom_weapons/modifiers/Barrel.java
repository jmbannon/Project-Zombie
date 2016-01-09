/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.modifiers;

import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.BulletSpreadModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.DamageModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.GunModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.ProjectileModifier;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import static org.bukkit.Material.MONSTER_EGG;
import org.bukkit.material.MaterialData;

/**
 *
 * @author jesse
 */
public enum Barrel implements GunModifier, BulletSpreadModifier, DamageModifier, ProjectileModifier
{
    NULL_BARREL(null, 0, 0, null),
    
    // Pistol
    THREADED(MONSTER_EGG, 57, 10, "Threaded Barrel"),
    PRECISION(MONSTER_EGG, 58, 10, "Precision Barrel"),
    
    // Rifle
    EXTENDED(MONSTER_EGG, 59, 0, "Extended Barrel"),
    SHORT(MONSTER_EGG, 60, 0, "Short Barrel"),
    
    // Hunting rifle
    BARTLEIN(MONSTER_EGG, 61, 0, "Bartlein Barrel"),
    KRIEGER(MONSTER_EGG, 62, 0, "Krieger Barrel"),
    BRUX(MONSTER_EGG, 65, 0, "Brux Barrel"),
    
    // Shotguns
    SMOOTH_BORE(MONSTER_EGG, 66, 0, "Smooth Bore Barrel"),
    RIFLED(MONSTER_EGG, 67, 0, "Rifled Barrel"),
    SAWED_OFF(MONSTER_EGG, 68, 0, "Sawed Off Barrel");
    
    private final String displayName;
    private final MaterialData materialData;
    
    private Barrel(final Material material,
                   final int materialByte,
                   final int price,
                   final String displayName)
    {
        this.displayName = displayName;
        if (material == null)
            materialData = null;
        else
            materialData = new MaterialData(material, (byte)materialByte);
    }
    
    @Override public int price()                         { return 0; }
    @Override public String getDisplayName()             { return displayName; }
    @Override public double getDamageValue()             { return 0.0; }
    @Override public double getDamageMultiplier()        { return 1.0; }
    @Override public int getAdditionalProjectileAmount() { return 0; }
    @Override public double getBulletSpreadMultiplier()  { return 1.0; }
    @Override public boolean isNull()                    { return this.equals(NULL_BARREL); }
    @Override public ChatColor getColor()                { return ChatColor.GREEN; }
    @Override public MaterialData getMaterialData()      { return null; }
}
