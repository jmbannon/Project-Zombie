/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import static net.projectzombie.crackshot_enhanced.custom_weapons.types.FireMode.*;
import net.projectzombie.crackshot_enhanced.custom_weapons.types.Mod.ModType;
import static net.projectzombie.crackshot_enhanced.custom_weapons.types.Mod.ModType.*;
import static net.projectzombie.crackshot_enhanced.custom_weapons.types.Scope.*;
import static net.projectzombie.crackshot_enhanced.custom_weapons.types.Suppressor.*;
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
// Z-Scoped, B-Burst, S-Suppressed
    
    
//  Name        GunID, Type, Fire, Sight, Supp, Id,Data, CSname,    BSpread, MaxDur,  Modifications:     Scope,             Fire,               Suppressor
    
    /* PISTOLS ************************************************************************************************************************************/
    Deagle      (0,   PISTOL, SEMI,  IRON, NA,  93,  0,  "Deagle",      1.101, 120, new Mod[] {new Mod(30, ACOG_)}),
    Z_Deagle    (30,  PISTOL, SEMI,  ACOG, NA,  293, 0,  "Z_Deagle",    1.101, 120, new Mod[] {new Mod(0,  IRON_)}),
    
    Colt45      (1,   PISTOL, SEMI,  IRON, NA,  338, 0,  "Colt45",      1.40,  120, new Mod[] {new Mod(31, ACOG_)}),
    Z_Colt45    (31,  PISTOL, SEMI,  ACOG, NA,  338, 0,  "Z_Colt45",    1.40,  120, new Mod[] {new Mod(1,  IRON_)}),
    
    P228        (2,   PISTOL, SEMI,  IRON, OFF, 351, 8,  "P228",        1.09,  120, new Mod[] {new Mod(32, ACOG_), new Mod(33, BURST_), new Mod(35, SUPP_)}),
    Z_P228      (32,  PISTOL, SEMI,  ACOG, OFF, 351, 8,  "Z_P228",      1.09,  120, new Mod[] {new Mod(2,  IRON_), new Mod(34, BURST_), new Mod(36, SUPP_)}),
    B_P228      (33,  PISTOL, BURST, IRON, OFF, 351, 8,  "B_P228",      1.09,  120, new Mod[] {new Mod(34, ACOG_), new Mod(2,  SEMI_),  new Mod(37, SUPP_)}),
    ZB_P228     (34,  PISTOL, BURST, ACOG, OFF, 351, 8,  "ZB_P228",     1.09,  120, new Mod[] {new Mod(33, IRON_), new Mod(32, SEMI_),  new Mod(38, SUPP_)}),
    
    S_P228      (35,  PISTOL, SEMI,  IRON, ON,  351, 8,  "S_P228",      1.19,  120, new Mod[] {new Mod(32, ACOG_), new Mod(33, BURST_), new Mod(2,  UNSUPP_)}),
    SZ_P228     (36,  PISTOL, SEMI,  ACOG, ON,  351, 8,  "SZ_P228",     1.19,  120, new Mod[] {new Mod(2,  IRON_), new Mod(34, BURST_), new Mod(32, UNSUPP_)}),
    SB_P228     (37,  PISTOL, BURST, IRON, ON,  351, 8,  "SB_P228",     1.19,  120, new Mod[] {new Mod(34, ACOG_), new Mod(2,  SEMI_),  new Mod(33, UNSUPP_)}),
    SZB_P228    (38,  PISTOL, BURST, ACOG, ON,  351, 8,  "SZB_P228",    1.19,  120, new Mod[] {new Mod(33, IRON_), new Mod(32, SEMI_),  new Mod(34, UNSUPP_)}),
    
    USP         (3,   PISTOL, SEMI,  IRON,         ON,    292,    0,  "USP",              1.32,   120,    new Mod[]{}),
    
    
    /* REVOLVERS **********************************************************************************************************************************/
    Mag44       (4,   REVOLVER, SEMI,  IRON,      NA,       294,    0,  "44Mag",            0.78,   120,    new Mod[]{}),
    DirtyFrank  (5,   REVOLVER, SEMI,  ACOG,      NA,     336,    0,  "DirtyFrank",       0.50,   120,    new Mod[]{}),
    
    Scout       (6,   SNIPER,   SEMI,  LONG,  OFF,   351,    9,  "Scout",            0.32,   120,    new Mod[]{}),
    Dragonuv    (7,   SNIPER,   SEMI,  LONG,   NA,  351,    14, "Dragonuv",         0.46,   120,    new Mod[]{}),
    AWP         (8,   SNIPER,   SEMI,  LONG,   NA,  278,    0,  "AWP",              0.16,   120,    new Mod[]{}),
    
    Remington   (9,   HUNTING,  BOLT,  LONG,   NA,  351,    12, "Remington700",     0.26,   120,    new Mod[]{}),
    Springfield (10,  HUNTING,  BOLT,  IRON,   NA,          351,    13, "M1903",0.52,   120,    new Mod[]{}),
    Model70     (11,  HUNTING,  BOLT,  LONG,  NA,   351,    9,  "Model70",          0.41,   120,    new Mod[]{}),
    
    Mac10       (12,  SMG,      AUTO,  IRON,      OFF,       351,    6,  "Mac10",            1.25,   120,    new Mod[]{}),
    Ump45       (13,  SMG,      AUTO,  IRON,      OFF,       351,    10, "Ump45",            0.99,   120,    new Mod[]{}),
    P90         (14,  SMG,      AUTO,  IRON,       NA,      351,    7,  "P90",              1.16,   120,    new Mod[]{}),
    MP5         (15,  SMG,      SEMI,  IRON,       OFF,      285,    0,  "MP5",              0.93,   120,    new Mod[]{}),
    TMP         (16,  SMG,      AUTO,  IRON,      ON,       284,    0,  "TMP",              1.05,   120,    new Mod[]{}),
    
    SG552       (17,    AUTO_S,    AUTO,  ACOG,     NA,      351,    11, "SG552",            0.61,   120,    new Mod[]{}),
    AUG         (18,    AUTO_S,    AUTO,  ACOG,      NA,     348,    0,  "AUG",              0.55,   120,    new Mod[]{}),
    G3SG1       (19,    AUTO_S,    SEMI,  LONG,   NA,  277,    0,  "G3SG1",            0.48,   120,    new Mod[]{}),
    
    AK47        (20,    ASSAULT,  AUTO,  IRON,      NA,       281,    0,  "AK-47",            0.765,  120,    new Mod[]{}),
    M16         (21,    ASSAULT,  BURST,      IRON,   OFF,          362,    0,  "M16",              0.598,  120,    new Mod[]{}),
    GALIL       (22,    ASSAULT,  AUTO,  IRON,         NA,    351,    2,  "GALIL",            0.85,   120,    new Mod[]{}),
    FAMAS       (23,    ASSAULT,  AUTO,  IRON,        OFF,     351,    4,  "FAMAS",            0.697,  120,    new Mod[]{}),
    M4A1        (24,    ASSAULT,  AUTO,  IRON,         ON,    271,    0,  "M4A1",             0.704,  120,    new Mod[]{}),
    SAW         (25,    ASSAULT,  AUTO,  IRON,        NA,     274,    0,  "SAW",              1.215,  120,    new Mod[]{}),
    
    Olympia     (26,    SHOTGUN,        SING,IRON,       NA,      291,    0,  "Olympia",          2.43,   120,    new Mod[]{}),
    AA12        (27,    SHOTGUN,        SEMI,  IRON,     NA,        286,    0,  "AA12",             2.0,    120,    new Mod[]{}),
    M3          (28,    SHOTGUN,        PUMP,IRON,       NA,      351,    5,  "M3",               1.88,   120,    new Mod[]{}),
    XM1014      (29,    SHOTGUN,        SEMI,  IRON,      NA,       290,    0,  "XM1014",           2.2,    120,    new Mod[]{});
    
    private static final Random rand = new Random();
    
    private final int uniqueID;
    private final Weapon weaponType;
    private final FireMode firemodeType;
    private final Scope scopeType;
    private final Suppressor suppressorType;
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
                final Suppressor suppressorType,
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
        this.suppressorType = suppressorType;
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
    public Suppressor    getSuppressorType()   { return suppressorType; }
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
    
    public Gun getModifiedGun(final ModType modType)
    {
        for (Mod mod : this.modifiedIDs)
            if (mod.getModType().equals(modType))
                return getGun(mod.getID());
        return null;
    }
    
    /**
     * Sorts list of possible modifications by price, descending.
     * @return List of modifications sorted by price.
     */
    public String[] getModifiedList()
    {
        String toReturn[] = new String[this.modifiedIDs.length];
        ArrayList<Mod> list = new ArrayList<>();
        list.addAll(Arrays.asList(this.modifiedIDs));
        Collections.sort(list);
        
        for (int i = 0; i < list.size(); i++)
            toReturn[i] = "$" + list.get(i).getPrice() + " - " + list.get(i).toString();
        
        return toReturn;
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
