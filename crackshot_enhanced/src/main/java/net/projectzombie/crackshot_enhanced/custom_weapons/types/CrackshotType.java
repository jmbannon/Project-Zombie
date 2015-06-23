/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.types;

import java.util.ArrayList;
import java.util.Random;
import static net.projectzombie.crackshot_enhanced.custom_weapons.types.FireModeTypes.*;
import static net.projectzombie.crackshot_enhanced.custom_weapons.types.ScopeTypes.*;
import static net.projectzombie.crackshot_enhanced.custom_weapons.types.WeaponType.*;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;


/**
 *
 * @author jbannon
 */
public enum CrackshotType implements Type
{
    Deagle  (PISTOL, SemiAuto, na, 293, 0, "Deagle", 1.101),
    Colt45  (PISTOL, SemiAuto, na, 338, 0, "Colt45", 1.40),
    P228    (PISTOL, SemiAuto, na, 351, 8, "P228", 1.09),
    USP     (PISTOL, SemiAuto, na, 292, 0, "USP", 1.32),
    
    Mag44       (REVOLVER, SemiAuto, na,   294, 0, "44Mag",      0.78),
    DirtyFrank  (REVOLVER, SemiAuto, acog, 336, 0, "DirtyFrank", 0.50);
    
    private static final Random rand = new Random();
    
    private final WeaponType weaponType;
    private final Material material;
    private final byte materialData;
    private final String value;
    private final double initialBulletSpread;
    
    protected static final ChatColor TITLE_COLOR = ChatColor.DARK_RED;
    protected static final ChatColor VALUE_COLOR = ChatColor.GOLD;
    
    /*
    private static final int AMMO_IDX = 0;
    private static final int ACCURACY_IDX = 1;
    private static final int CONDITION_IDX = 2;
    private static final int BUILD_IDX = 3;
    private static final int FIRE_MODE_IDX = 4;
    private static final int SCOPE_IDX = 5;
    private static final int HIDDEN_DURABILITY_IDX = 6;
    */
    
    private static final int HIDDEN_PRE_STAT_IDX = 2;
    private static final int HIDDEN_POST_STAT_IDX = 7;
    
    private CrackshotType(final WeaponType weaponType,
                          final FireModeTypes firemodeType,
                          final ScopeTypes scopeType,
                          final int itemId,
                          final int itemData,
                          final String crackshotWeaponName,
                          final double initialBulletSpread)
    {
        this.weaponType = weaponType;
        this.material = Material.getMaterial(itemId);
        this.materialData = (byte)itemData;
        this.value = crackshotWeaponName;
        this.initialBulletSpread = initialBulletSpread;
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
    
    public ArrayList<String> generateLore(final ItemStack item,
                                          final double crackshotBulletSpread)
    {
        final ArrayList<CrackshotType> typeArray = CrackshotType.getTypes(item);
        final CrackshotType gun;
        if (typeArray.size() <= 0)
            return null;
        
        gun = typeArray.get(rand.nextInt(typeArray.size()));
        return null; //NOT FINISHED
    }
    
    private String generateAmmoLore()
    {
        return this.getLoreString(WeaponType.getTitle(), weaponType.getValue());
    }
    
    private String getLoreString(final String title,
                                 final String value)
    {
        return this.getLoreString(title, VALUE_COLOR, value);
    }
    
    private String getLoreString(final String title,
                                 final ChatColor valueColor,
                                 final String value)
    {
        final StringBuilder stb = new StringBuilder();
        stb.append(TITLE_COLOR);
        stb.append(title);
        stb.append(VALUE_COLOR);
        stb.append(value);
        return stb.toString();
    }
}
