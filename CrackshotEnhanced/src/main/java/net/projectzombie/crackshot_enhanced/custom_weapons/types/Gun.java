/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.types;

import java.util.ArrayList;
import java.util.Random;
import static net.projectzombie.crackshot_enhanced.custom_weapons.types.FireMode.*;
import static net.projectzombie.crackshot_enhanced.custom_weapons.types.Scope.*;
import static net.projectzombie.crackshot_enhanced.custom_weapons.types.Weapon.*;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;


/**
 *
 * @author jbannon
 */
public enum Gun implements Type
{
//  Name    ID, Type  , FireMode, scope, itemID, itemData, CSname, CSaccuracy, maxDurability)  
    Deagle  (0, PISTOL, SEMI_AUTO, NA, 293, 0, "Deagle", 1.101, 120),
    Colt45  (1, PISTOL, SEMI_AUTO, NA, 338, 0, "Colt45", 1.40,  120),
    P228    (2, PISTOL, SEMI_AUTO, NA, 351, 8, "P228",   1.09,  120),
    USP     (3, PISTOL, SEMI_AUTO, NA, 292, 0, "USP",    1.32,  120),
    
    Mag44      (4, REVOLVER, SEMI_AUTO, NA,   294, 0, "44Mag",      0.78, 120),
    DirtyFrank (5, REVOLVER, SEMI_AUTO, ACOG, 336, 0, "DirtyFrank", 0.50, 120),
    
    Scout    (6, SNIPER, SEMI_AUTO, LONG_RANGE, 351, 9,  "Scout",    0.32, 120),
    Dragonuv (7, SNIPER, SEMI_AUTO, LONG_RANGE, 351, 14, "Dragonuv", 0.46, 120),
    AWP      (8, SNIPER, SEMI_AUTO, LONG_RANGE, 278, 0,  "AWP",      0.16, 120),
    
    Remington   (9,  HUNTING_RIFLE, BOLT_ACTION, LONG_RANGE, 351, 12, "Remington700",      0.26, 120),
    Springfield (10, HUNTING_RIFLE, BOLT_ACTION, NA,        351, 13, "M1903 Springfield", 0.52, 120),
    Model70     (11, HUNTING_RIFLE, BOLT_ACTION, LONG_RANGE, 351, 9,  "Model70",           0.41, 120),
    
    Mac10 (12, SMG, AUTOMATIC, NA, 351, 6,  "Mac10",  1.25, 120),
    Ump45 (13, SMG, AUTOMATIC, NA, 351, 10, "Ump45",  0.99, 120),
    P90   (14, SMG, AUTOMATIC, NA, 351, 7,  "P90",    1.16, 120),
    MP5   (15, SMG, SEMI_AUTO,  NA, 285, 0,  "MP5",    0.93, 120),
    TMP   (16, SMG, AUTOMATIC, NA, 284, 0,  "TMP",    1.05, 120),
    
    SG552 (17, AUTO_SNIPER, AUTOMATIC, ACOG,      351, 11, "SG552", 0.61, 120),
    AUG   (18, AUTO_SNIPER, AUTOMATIC, ACOG,      348, 0,  "AUG",   0.55, 120),
    G3SG1 (19, AUTO_SNIPER, SEMI_AUTO,  LONG_RANGE, 277, 0,  "G3SG1", 0.48, 120),
    
    AK47  (20, ASSAULT_RIFLE, AUTOMATIC,  NA, 281, 0,  "AK-47", 0.765, 120),
    M16   (21, ASSAULT_RIFLE, BURST,      NA, 362, 0,  "M16",   0.598, 120),
    GALIL (22, ASSAULT_RIFLE, AUTOMATIC,  NA, 351, 2,  "GALIL", 0.85,  120),
    FAMAS (23, ASSAULT_RIFLE, AUTOMATIC,  NA, 351, 4,  "FAMAS", 0.697, 120),
    M4A1  (24, ASSAULT_RIFLE, AUTOMATIC,  NA, 271, 0,  "M4A1",  0.704, 120),
    SAW   (25, ASSAULT_RIFLE, AUTOMATIC,  NA, 274, 0,  "SAW",   1.215, 120),
    
    Olympia (26, SHOTGUN, SINGLE_SHOT, NA, 291, 0,  "Olympia", 2.43, 120),
    AA12    (27, SHOTGUN, SEMI_AUTO,   NA, 286, 0,  "AA12",    2.0,  120),
    M3      (28, SHOTGUN, PUMP_ACTION, NA, 351, 5,  "M3",      1.88, 120),
    XM1014  (29, SHOTGUN, SEMI_AUTO,   NA, 290, 0,  "XM1014",  2.2,  120);
    
    private static final Random rand = new Random();
    
    private final int uniqueId;
    private final Weapon weaponType;
    private final FireMode firemodeType;
    private final Scope scopeType;
    private final Material material;
    private final byte materialData;
    private final String csWeaponName;
    private final double initialBulletSpread;
    private final int maxDurability;
    
    private Gun(final int uniqueId,
                final Weapon weaponType,
                final FireMode firemodeType,
                final Scope scopeType,
                final int itemId,
                final int itemData,
                final String crackshotWeaponName,
                final double initialBulletSpread,
                final int maxDurability)
    {
        this.uniqueId = uniqueId;
        this.weaponType = weaponType;
        this.firemodeType = firemodeType;
        this.scopeType = scopeType;
        this.material = Material.getMaterial(itemId);
        this.materialData = (byte)itemData;
        this.csWeaponName = crackshotWeaponName;
        this.initialBulletSpread = initialBulletSpread;
        this.maxDurability = maxDurability;
    }
    
    public int           getUniqueId()         { return uniqueId; }
    public Weapon        getWeaponType()       { return weaponType; }
    public FireMode      getFireMode()         { return firemodeType; }
    public Scope         getScopeType()        { return scopeType; }
    public Material      getMaterial()         { return material;  }
    public Byte          getMaterialData()     { return materialData; }
    public double        getInitBulletSpread() { return initialBulletSpread; }
    public int           getMaxDurability()    { return maxDurability; }
    
    @Override public String toString()         { return csWeaponName;  }
    @Override public int getEnumValue()        { return uniqueId; }
    
    public int getInitialDurability()
    {
        return this.maxDurability - rand.nextInt(maxDurability/2);
    }
    
    /**
     * Returns the integer of the gun condition.
     * @param durability Current durability of the gun.
     * @return Gun tier integer.
     */
    public int getConditionInt(final int durability)
    {
        final double ratio = (double)durability / (double)this.maxDurability;
        for (int i = 0; i <= Condition.TIERS; i++)
            if (Double.compare(ratio, (double)i/(double)Condition.TIERS) <= 0) return i;
        
        return Condition.TIERS;
    }
    
    public static ArrayList<Gun> getTypes(final ItemStack item)
    {
        final ArrayList<Gun> types = new ArrayList();
        
        for (Gun type : Gun.values())
            if (type.material.equals(item.getType())
                    && type.materialData == item.getData().getData())
                types.add(type);
        
        return types;
    }
    
    public static Gun getGunType(final int ID)
    {
        for (Gun type : Gun.values())
            if (type.uniqueId == ID)
                return type;
        
        return null;
    }

    
}
