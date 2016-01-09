/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;

/**
 *
 * @author jesse
 */
public abstract class GunModifier2
{
    private final String name;
    private final Material material;
    private final int materialData;
    private final int price;
    private final ChatColor color;
    
    public GunModifier2(final String name,
                        final String material,
                        final int materialData,
                        final int price,
                        final String color)
    {
        this.name = name;
        this.material = Material.valueOf(material);
        this.materialData = materialData;
        this.price = price;
        this.color = ChatColor.valueOf(color);
    }
    
    /**
     * @return Returns all values of the associated gun modifier. 
     */
    abstract public GunModifier2[] getAll();
    
    /**
     * @return Returns the null modifier.
     */
    abstract public GunModifier2 getNullModifier();
    
    /**
     * @return Returns the price of the gun modification at the gunsmith.
     */
    public int price()
    {
        return price;
    }
    
    /**
     * @return Name of gun modification.
     */
    public String getDisplayName()
    {
        return name;
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
        return new MaterialData(material, (byte)materialData);
    }
    
    /**
     * @return Whether the modifier is null.
     */
    public boolean isNull()
    {
        return name == null;
    }
    
    public boolean isModifier(final String name)
    {
        for (GunModifier2 mod : getAll())
        {
            if (mod.name.equalsIgnoreCase(name))
                return true;
        }
        return false;
    }
    
    public GunModifier2 valueOf(final String name)
    {
        for (GunModifier2 mod : getAll())
        {
            if (mod.name.equalsIgnoreCase(name))
                return mod;
        }
        return null;
    }
    
    public GunModifier2[] valueOf(final String names[])
    {
        final GunModifier2[] toReturn;
        int validModifiers = 0;
        GunModifier2 temp;
        
        for (String modName : names)
        {
            if (isModifier(modName))
                ++validModifiers;
        }
        
        if (validModifiers > 0)
        {
            toReturn = new GunModifier2[validModifiers];
            validModifiers = 0;
            for (String modName : names)
            {
                if ((temp = valueOf(modName)) != null)
                    toReturn[validModifiers++] = temp;
            }
            return toReturn;
        }
        else
        {
            return null;
        }
    }
}
