/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.modifiers;

import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.BulletSpreadModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.GunModifier;
import org.bukkit.ChatColor;
import org.bukkit.material.MaterialData;

/**
 *
 * @author jbannon
 */
public enum Scope implements GunModifier, BulletSpreadModifier
{
    
    BROKEN   ("Broken",             0),
    IRON     ("Iron Sight",         0),
    RED_IRON ("Red Dot Iron Sight", 0),
    ACOG     ("ACOG Scope",         4),
    TACT     ("Tactical Scope",     5),
    LONG     ("Sniper Scope",       10);

    private final int zoomAmount;
    private final String displayName;
    
    private Scope(final String value,
                  final int crackshotZoomAmount)
    {
        this.displayName = value;
        this.zoomAmount = crackshotZoomAmount;
    }
    
    public int getZoomAmount()              { return zoomAmount;  }

    @Override
    public int price()
    {
        return 20;
    }

    @Override
    public String getDisplayName()
    {
        return displayName;
    }

    @Override
    public double getBulletSpreadMultiplier()
    {
        return 1.0;
    }

    /**
     * A sight can never be null - every gun needs one!
     * @return False.
     */
    @Override
    public boolean isNull()
    {
        return false;
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
