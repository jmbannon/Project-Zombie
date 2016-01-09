/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.modifiers;

import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.GunModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.MagazineModifier;
import org.bukkit.ChatColor;
import org.bukkit.material.MaterialData;

/**
 *
 * @author jesse
 */
public enum Magazine implements GunModifier, MagazineModifier
{
    NULL_MAG(null),
    MAG_QUICK("Quick"),
    MAG_FAST("Fast"),
    MAG_UNPLUGGED("Unplugged"),
    MAG_ALTERED("Altered"),
    MAG_EXTENDED("Extended"),
    MAG_DRUM("Drum"),
    MAG_BAND("Bandolier");

    private final String displayName;
    
    private Magazine(final String displayName)
    {
        this.displayName = displayName;
    }
    
    public int getMagBoost()
    {
        return 5;
    }
    
    public int getReloadBoost()
    {
        return 0;
    }
    
    @Override
    public int price()
    {
        return 0;
    }

    @Override
    public String getDisplayName()
    {
        return displayName;
    }

    @Override
    public int getMagazineBoost()
    {
        return 10;
    }

    @Override
    public double getReloadSpeedMultiplier()
    {
        return 1.0;
    }

    @Override
    public boolean isNull()
    {
        return this.equals(NULL_MAG);
    }
    
    @Override
    public ChatColor getColor() {
        return ChatColor.GREEN;
    }

    @Override
    public MaterialData getMaterialData() {
        return null;
    }
}
