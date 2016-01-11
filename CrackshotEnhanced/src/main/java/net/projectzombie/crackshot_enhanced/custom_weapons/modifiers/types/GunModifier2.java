/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types;

import net.projectzombie.crackshot_enhanced.custom_weapons.utilities.GunUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;

/**
 *
 * @author jesse
 */
public abstract class GunModifier2 extends CSVValue
{
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
        super(name);
        this.materialData = materialData;
        this.price = price;
        this.material = GunUtils.matchMaterial(material);
        this.color = GunUtils.matchChatColor(color);
    }
    
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
}
