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
    Deagle  (0, PISTOL, SemiAuto, na, 293, 0, "Deagle", 1.101, 120),
    Colt45  (1, PISTOL, SemiAuto, na, 338, 0, "Colt45", 1.40,  120),
    P228    (2, PISTOL, SemiAuto, na, 351, 8, "P228",   1.09,  120),
    USP     (3, PISTOL, SemiAuto, na, 292, 0, "USP",    1.32,  120),
    
    Mag44      (4, REVOLVER, SemiAuto, na,   294, 0, "44Mag",      0.78, 120),
    DirtyFrank (5, REVOLVER, SemiAuto, acog, 336, 0, "DirtyFrank", 0.50, 120),
    
    Scout    (6, SNIPER, SemiAuto, longrange, 351, 9,  "Scout",    0.32, 120),
    Dragonuv (7, SNIPER, SemiAuto, longrange, 351, 14, "Dragonuv", 0.46, 120),
    AWP      (8, SNIPER, SemiAuto, longrange, 278, 0,  "AWP",      0.16, 120),
    
    Remington   (9,  HUNTING_RIFLE, BoltAction, longrange, 351, 12, "Remington700",      0.26, 120),
    Springfield (10, HUNTING_RIFLE, BoltAction, na,        351, 13, "M1903 Springfield", 0.52, 120),
    Model70     (11, HUNTING_RIFLE, BoltAction, longrange, 351, 9,  "Model70",           0.41, 120),
    
    Mac10 (12, SMG, Automatic, na, 351, 6,  "Mac10",  1.25, 120),
    Ump45 (13, SMG, Automatic, na, 351, 10, "Ump45",  0.99, 120),
    P90   (14, SMG, Automatic, na, 351, 7,  "P90",    1.16, 120),
    MP5   (15, SMG, SemiAuto,  na, 285, 0,  "MP5",    0.93, 120),
    TMP   (16, SMG, Automatic, na, 284, 0,  "TMP",    1.05, 120),
    
    SG552 (17, AUTO_SNIPER, Automatic, acog,      351, 11, "SG552", 0.61, 120),
    AUG   (18, AUTO_SNIPER, Automatic, acog,      348, 0,  "AUG",   0.55, 120),
    G3SG1 (19, AUTO_SNIPER, SemiAuto,  longrange, 277, 0,  "G3SG1", 0.48, 120),
    
    AK47  (20, ASSAULT_RIFLE, Automatic,  na, 281, 0,  "AK-47", 0.765, 120),
    M16   (21, ASSAULT_RIFLE, Burst,      na, 362, 0,  "M16",   0.598, 120),
    GALIL (22, ASSAULT_RIFLE, Automatic,  na, 351, 2,  "GALIL", 0.85,  120),
    FAMAS (23, ASSAULT_RIFLE, Automatic,  na, 351, 4,  "FAMAS", 0.697, 120),
    M4A1  (24, ASSAULT_RIFLE, Automatic,  na, 271, 0,  "M4A1",  0.704, 120),
    SAW   (25, ASSAULT_RIFLE, Automatic,  na, 274, 0,  "SAW",   1.215, 120),
    
    Olympia (26, SHOTGUN, SingleShot, na, 291, 0,  "Olympia", 2.43, 120),
    AA12    (27, SHOTGUN, SemiAuto,   na, 286, 0,  "AA12",    2.0,  120),
    M3      (28, SHOTGUN, PumpAction, na, 351, 5,  "M3",      1.88, 120),
    XM1014  (29, SHOTGUN, SemiAuto,   na, 290, 0,  "XM1014",  2.2,  120);
    
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
    public Weapon    getWeaponType()       { return weaponType; }
    public FireMode getFireMode()         { return firemodeType; }
    public Scope    getScopeType()        { return scopeType; }
    public Material      getMaterial()         { return material;  }
    public Byte          getMaterialData()     { return materialData; }
    public double        getInitBulletSpread() { return initialBulletSpread; }
    public int           getMaxDurability()    { return maxDurability;       }
    @Override public String getValue() { return csWeaponName;  }
    
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
