/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.types;

import java.util.ArrayList;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;


/**
 *
 * @author jbannon
 */
public enum CrackshotType implements Type
{
    Deagle  (WeaponType.PISTOL, 293, 0, "Deagle"),
    Colt45  (WeaponType.PISTOL, 338, 0, "Colt45"),
    P228    (WeaponType.PISTOL, 351, 8, "P228"),
    USP     (WeaponType.PISTOL, 292, 0, "USP"),
    
    Mag44       (WeaponType.REVOLVER, 294, 0, "44Mag"),
    DirtyFrank  (WeaponType.REVOLVER, 336, 0, "DirtyFrank");
    
    
    private final WeaponType weaponType;
    private final Material material;
    private final byte materialData;
    private final String value;
    
    private CrackshotType(final WeaponType weaponType,
                          final int itemId,
                          final int itemData,
                          final String crackshotWeaponName)
    {
        this.weaponType = weaponType;
        this.material = Material.getMaterial(itemId);
        this.materialData = (byte)itemData;
        this.value = crackshotWeaponName;
    }
    
    @Override
    public String getValue()
    {
        return value;
    }
    
    public static ArrayList<CrackshotType> getTypes(final ItemStack item)
    {
        final ArrayList<CrackshotType> types = new ArrayList();
        
        for (CrackshotType type : CrackshotType.values())
            if (type.material.equals(item.getType())
                    && type.materialData == item.getData().getData())
                types.add(type);
        
        return types;
    }
    
    public static ArrayList<String> generateLore()
    {
        
    }
}
