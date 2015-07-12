/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.types;

import java.util.Random;
import static net.projectzombie.crackshot_enhanced.custom_weapons.types.FireMode.*;
import net.projectzombie.crackshot_enhanced.custom_weapons.types.Mod.ModType;
import static net.projectzombie.crackshot_enhanced.custom_weapons.types.Mod.ModType.*;
import static net.projectzombie.crackshot_enhanced.custom_weapons.types.Scope.*;
import static net.projectzombie.crackshot_enhanced.custom_weapons.types.Weapon.*;
import net.projectzombie.crackshot_enhanced.custom_weapons.utilities.CrackshotLore;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;


/**
 *
 * @author jbannon
 */
public enum Gun implements Type
{
//  Name        GunID,  Type,           FireMode,   Sight,         Id,  Data,  CSname,        CSaccuracy, maxDurability, Modifications)
    
    Deagle      (0,     PISTOL,         SEMI_AUTO,  NA,             293,    0,  "Deagle",           1.101,  120,    new Mod[] {new Mod(30, SCOPE)}),
    SDeagle     (30,    PISTOL,         SEMI_AUTO,  ACOG,           293,    0,  "ZDeagle",          1.101,  120,    new Mod[]{}),
    
    Colt45      (1,     PISTOL,         SEMI_AUTO,  NA,             338,    0,  "Colt45",           1.40,   120,    new Mod[]{}),
    P228        (2,     PISTOL,         SEMI_AUTO,  NA,             351,    8,  "P228",             1.09,   120,    new Mod[]{}),
    USP         (3,     PISTOL,         SEMI_AUTO,  NA,             292,    0,  "USP",              1.32,   120,    new Mod[]{}),
    
    Mag44       (4,     REVOLVER,       SEMI_AUTO,  NA,             294,    0,  "44Mag",            0.78,   120,    new Mod[]{}),
    DirtyFrank  (5,     REVOLVER,       SEMI_AUTO,  ACOG,           336,    0,  "DirtyFrank",       0.50,   120,    new Mod[]{}),
    
    Scout       (6,     SNIPER,         SEMI_AUTO,  LONG_RANGE,     351,    9,  "Scout",            0.32,   120,    new Mod[]{}),
    Dragonuv    (7,     SNIPER,         SEMI_AUTO,  LONG_RANGE,     351,    14, "Dragonuv",         0.46,   120,    new Mod[]{}),
    AWP         (8,     SNIPER,         SEMI_AUTO,  LONG_RANGE,     278,    0,  "AWP",              0.16,   120,    new Mod[]{}),
    
    Remington   (9,     HUNTING_RIFLE,  BOLT_ACTION,LONG_RANGE,     351,    12, "Remington700",     0.26,   120,    new Mod[]{}),
    Springfield (10,    HUNTING_RIFLE,  BOLT_ACTION,NA,             351,    13, "M1903 Springfield",0.52,   120,    new Mod[]{}),
    Model70     (11,    HUNTING_RIFLE,  BOLT_ACTION,LONG_RANGE,     351,    9,  "Model70",          0.41,   120,    new Mod[]{}),
    
    Mac10       (12,    SMG,            AUTOMATIC,  NA,             351,    6,  "Mac10",            1.25,   120,    new Mod[]{}),
    Ump45       (13,    SMG,            AUTOMATIC,  NA,             351,    10, "Ump45",            0.99,   120,    new Mod[]{}),
    P90         (14,    SMG,            AUTOMATIC,  NA,             351,    7,  "P90",              1.16,   120,    new Mod[]{}),
    MP5         (15,    SMG,            SEMI_AUTO,  NA,             285,    0,  "MP5",              0.93,   120,    new Mod[]{}),
    TMP         (16,    SMG,            AUTOMATIC,  NA,             284,    0,  "TMP",              1.05,   120,    new Mod[]{}),
    
    SG552       (17,    AUTO_SNIPER,    AUTOMATIC,  ACOG,           351,    11, "SG552",            0.61,   120,    new Mod[]{}),
    AUG         (18,    AUTO_SNIPER,    AUTOMATIC,  ACOG,           348,    0,  "AUG",              0.55,   120,    new Mod[]{}),
    G3SG1       (19,    AUTO_SNIPER,    SEMI_AUTO,  LONG_RANGE,     277,    0,  "G3SG1",            0.48,   120,    new Mod[]{}),
    
    AK47        (20,    ASSAULT_RIFLE,  AUTOMATIC,  NA,             281,    0,  "AK-47",            0.765,  120,    new Mod[]{}),
    M16         (21,    ASSAULT_RIFLE,  BURST,      NA,             362,    0,  "M16",              0.598,  120,    new Mod[]{}),
    GALIL       (22,    ASSAULT_RIFLE,  AUTOMATIC,  NA,             351,    2,  "GALIL",            0.85,   120,    new Mod[]{}),
    FAMAS       (23,    ASSAULT_RIFLE,  AUTOMATIC,  NA,             351,    4,  "FAMAS",            0.697,  120,    new Mod[]{}),
    M4A1        (24,    ASSAULT_RIFLE,  AUTOMATIC,  NA,             271,    0,  "M4A1",             0.704,  120,    new Mod[]{}),
    SAW         (25,    ASSAULT_RIFLE,  AUTOMATIC,  NA,             274,    0,  "SAW",              1.215,  120,    new Mod[]{}),
    
    Olympia     (26,    SHOTGUN,        SINGLE_SHOT,NA,             291,    0,  "Olympia",          2.43,   120,    new Mod[]{}),
    AA12        (27,    SHOTGUN,        SEMI_AUTO,  NA,             286,    0,  "AA12",             2.0,    120,    new Mod[]{}),
    M3          (28,    SHOTGUN,        PUMP_ACTION,NA,             351,    5,  "M3",               1.88,   120,    new Mod[]{}),
    XM1014      (29,    SHOTGUN,        SEMI_AUTO,  NA,             290,    0,  "XM1014",           2.2,    120,    new Mod[]{});
    
    private static final Random rand = new Random();
    
    private final int uniqueID;
    private final Weapon weaponType;
    private final FireMode firemodeType;
    private final Scope scopeType;
    private final Material material;
    private final byte materialData;
    private final String csWeaponName;
    private final double initialBulletSpread;
    private final int maxDurability;
    private final Mod[] modifiedIDs;
    
    private Gun(final int uniqueID,
                final Weapon weaponType,
                final FireMode firemodeType,
                final Scope scopeType,
                final int itemId,
                final int itemData,
                final String crackshotWeaponName,
                final double initialBulletSpread,
                final int maxDurability,
                final Mod[] modifiedIDs)
    {
        this.uniqueID = uniqueID;
        this.weaponType = weaponType;
        this.firemodeType = firemodeType;
        this.scopeType = scopeType;
        this.material = Material.getMaterial(itemId);
        this.materialData = (byte)itemData;
        this.csWeaponName = crackshotWeaponName;
        this.initialBulletSpread = initialBulletSpread;
        this.maxDurability = maxDurability;
        this.modifiedIDs = modifiedIDs;
    }
    
    public int           getUniqueId()         { return uniqueID; }
    public Weapon        getWeaponType()       { return weaponType; }
    public FireMode      getFireMode()         { return firemodeType; }
    public Scope         getScopeType()        { return scopeType; }
    public Material      getMaterial()         { return material;  }
    public Byte          getMaterialData()     { return materialData; }
    public String        getCSWeaponName()     { return csWeaponName; }
    public double        getInitBulletSpread() { return initialBulletSpread; }
    public int           getMaxDurability()    { return maxDurability; }
    public Mod[]         getModifiedIDs()      { return modifiedIDs;   }
    
    @Override public String toString()         { return csWeaponName;  }
    @Override public int getEnumValue()        { return uniqueID; }
    
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

    public int getRepairPrice(final ItemStack item)
    {
        final int durability = CrackshotLore.getDurability(item);
        final double buildWeight = (double)CrackshotLore.getBuild(item)/10.0;
        
        if (durability < 0 || buildWeight < 0)
            return -1;
        
        return (int)((double)(this.maxDurability - durability) * this.weaponType.getRepairPriceWeight() / (1.0 - buildWeight));
    }
    
    public int getUpgradePrice(final ItemStack item)
    {
        final int build = CrackshotLore.getBuild(item);
        
        if (build < 0)
            return -1;
        else if (build == Build.ENHANCED.getEnumValue())
            return 0;
        else
            return (int)((build + 1) * this.weaponType.getUpgradePriceWeight());
    }
    
    public Gun getModification(final ModType modType)
    {
        for (Mod mod : this.modifiedIDs)
            if (mod.getModType().equals(modType))
                return getGun(mod.getID());
        return null;
    }
    
    static
    public Gun getGun(final int ID)
    {
        for (Gun type : Gun.values())
            if (type.uniqueID == ID)
                return type;
        
        return null;
    }
    
    static
    public Gun getGun(final ItemStack item)
    {
        return getGun(CrackshotLore.getWeaponID(item));
    }
}
