/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.modifiers;

import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.BoltModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.GunModifier;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import static org.bukkit.Material.MONSTER_EGG;
import org.bukkit.material.MaterialData;

/**
 *
 * @author jesse
 */
public enum Bolt implements GunModifier, BoltModifier
{
    NULL_BOLT(null, 0, null),
    BO_LUBED(MONSTER_EGG, 90, "Lubed"),
    BO_POLISHED(MONSTER_EGG, 91, "Polished");
    
    private final String displayName;
    private final MaterialData materialData;
    
    private Bolt(final Material material,
                 final int materialByte,
                 final String displayName)
    {
        this.displayName = displayName;
        if (material == null)
            this.materialData = null;
        else
            this.materialData = new MaterialData(material, (byte)materialByte);
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

    @Override
    public MaterialData getMaterialData() { return materialData;    }

}
