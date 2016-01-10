/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types;

import java.util.ArrayList;
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
        this.materialData = materialData;
        this.price = price;
        this.material = matchMaterial(material);
        this.color = matchChatColor(color);
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
        return this.equals(this.getNullModifier());
    }
    
    public boolean isModifier(final String name)
    {
        if (name == null)
            return false;
        
        for (GunModifier2 mod : getAll())
        {
            if (mod == null || mod.name == null)
                continue;
            
            if (mod.name.equalsIgnoreCase(name))
                return true;
        }
        return false;
    }
    
    public abstract GunModifier2[] valueOf(final String names[],
                                           final boolean includeNull);
    
    /**
     * @param names Names to retrieve.
     * @return ArrayList of the indexes at which they appear in the child's set of modifiers.
     */
    public ArrayList<Integer> getIndexes(final String names[])
    {
        final ArrayList<Integer> toReturn = new ArrayList<>();
        int validModifiers = 0;
        GunModifier2 temp;
        
        for (int i = 0; i < names.length; i++)
        {
            if (isModifier(names[i]))
                toReturn.add(i);
            else
            {
                System.out.println("Could not find modifier '" + names[i] + "'");
            }
        }
        return toReturn;
    }
    
    static private ChatColor matchChatColor(final String string)
    {
        if (string == null)
            return null;
        
        ChatColor colors[] = ChatColor.values();
        for (ChatColor color : colors)
        {
            if (color.toString().equalsIgnoreCase(string))
            {
                return color;
            }
        }
        return null;
    }
    
    static private Material matchMaterial(final String string)
    {
        if (string == null)
            return null;
        
        return Material.matchMaterial(string);
    }
}
