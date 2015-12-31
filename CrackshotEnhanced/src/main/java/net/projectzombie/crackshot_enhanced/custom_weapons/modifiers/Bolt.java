/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.modifiers;

import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.BoltModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.GunModifier;
import org.bukkit.ChatColor;

/**
 *
 * @author jesse
 */
public enum Bolt implements GunModifier, BoltModifier
{
    NULL_BOLT(null),
    BO_LUBED("Lubed"),
    BO_POLISHED("Polished");
    
    private final String displayName;
    
    private Bolt(final String displayName)
    {
        this.displayName = displayName;
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
    public double getDurationMultiplier()
    {
        return 0.5;
    }

    @Override
    public boolean isNull()
    {
        return this.equals(NULL_BOLT);
    }
    
    @Override
    public ChatColor getColor() {
        return ChatColor.GREEN;
    }

}
