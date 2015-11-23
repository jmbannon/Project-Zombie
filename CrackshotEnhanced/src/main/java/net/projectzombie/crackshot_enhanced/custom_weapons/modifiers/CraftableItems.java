/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.modifiers;

import java.util.HashMap;
import static net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Scope.*;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;

/**
 *
 * @author jb
 */
public enum CraftableItems
{
    
    ITEM_IRON(IRON, new MaterialData(Material.MONSTER_EGG, (byte)60)),
    ITEM_ACOG(ACOG, new MaterialData(Material.MONSTER_EGG, (byte)60)),
    ITEM_TACT(TACT, new MaterialData(Material.MONSTER_EGG, (byte)60)),
    ITEM_LONG(LONG, new MaterialData(Material.MONSTER_EGG, (byte)60));
    
    private static final HashMap<MaterialData, GunModifier> modHash = initializeMods();
    private static final HashMap<GunModifier, MaterialData> itemHash = initializeItems();
    
    private final GunModifier mod;
    private final MaterialData materialData;
    
    private CraftableItems(final GunModifier mod,
                          final MaterialData materialData)
    {
        this.mod = mod;
        this.materialData = materialData;
    }
    
    public GunModifier getGunModifier() { return mod; }
    public MaterialData getMaterialData() { return materialData; } 
    
    
    
    static
    public HashMap<MaterialData, GunModifier> initializeMods()
    {
        HashMap<MaterialData, GunModifier> craftableHash = new HashMap<>();
        for (CraftableItems item : CraftableItems.values())
        {
            craftableHash.put(item.getMaterialData(), item.getGunModifier());
        }
        return craftableHash;
    }
    
    static
    public HashMap<GunModifier, MaterialData> initializeItems()
    {
        HashMap<GunModifier, MaterialData> craftableHash = new HashMap<>();
        for (CraftableItems item : CraftableItems.values())
        {
            craftableHash.put(item.getGunModifier(), item.getMaterialData());
        }
        return craftableHash;
    }
    
    static
    public GunModifier getGunModifier(final MaterialData md)
    {
        return modHash.get(md);
    }
    
    static
    public MaterialData getMaterial(final GunModifier mod)
    {
        return itemHash.get(mod);
    }
    
    static
    public boolean contains(final MaterialData md)
    {
        return modHash.containsKey(md);
    }
    
    static
    public boolean contains(final GunModifier mod)
    {
        return modHash.containsValue(mod);
    }
}
