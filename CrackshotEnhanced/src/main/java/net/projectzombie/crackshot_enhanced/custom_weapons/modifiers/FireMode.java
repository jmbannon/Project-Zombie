/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.modifiers;

import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.GunModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.types.Type;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import static org.bukkit.Material.MONSTER_EGG;
import org.bukkit.material.MaterialData;

/**
 *
 * @author jbannon
 */
public enum FireMode implements Type, GunModifier
{
    SEMI    (MONSTER_EGG, 92, "Semi-Auto"),
    BURST   (MONSTER_EGG, 93, "Burst"),
    AUTO    (MONSTER_EGG, 94, "Automatic"),
    SING    (null, 0, "Single Shot"),
    BOLT    (null, 0, "Bolt-Action"),
    PUMP    (null, 0, "Pump-Action"),
    OVER    (null, 0, "Over/Under");
    
    private static final String TITLE = "Fire Mode: ";

    private final String displayName;
    private final MaterialData materialData;
    
    private FireMode(final Material material,
                     final int materialByte,
                     final String displayName) 
    {
        this.displayName = displayName;
        if (material == null)
            this.materialData = null;
        else
            this.materialData = new MaterialData(material, (byte)materialByte);
    }
    
    
    @Override public String toString()      { return displayName;    }
    @Override public int price()            { return 40;       }
    @Override public String title()         { return TITLE;    }
    

    @Override
    public String getDisplayName()
    {
        return displayName;
    }
    
    /**
     * FireMode can never be null - all guns have one!
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
