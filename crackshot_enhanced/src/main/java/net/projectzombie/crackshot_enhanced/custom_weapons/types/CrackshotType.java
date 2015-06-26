/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.types;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import static net.projectzombie.crackshot_enhanced.custom_weapons.types.FireModeTypes.*;
import static net.projectzombie.crackshot_enhanced.custom_weapons.types.ScopeTypes.*;
import static net.projectzombie.crackshot_enhanced.custom_weapons.types.WeaponType.*;
import net.projectzombie.crackshot_enhanced.utilities.HiddenStringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


/**
 *
 * @author jbannon
 */
public enum CrackshotType implements Type
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
    private static final String preShotVerification = ChatColor.DARK_RED + "Shoot to unviel stats.";
    private static final String verification = "PZ";
    private static final String seperator = "`";
    
    private final int uniqueId;
    private final WeaponType weaponType;
    private final FireModeTypes firemodeType;
    private final ScopeTypes scopeType;
    private final Material material;
    private final byte materialData;
    private final String value;
    private final double initialBulletSpread;
    private final int maxDurability;
    
    protected static final ChatColor TITLE_COLOR = ChatColor.DARK_RED;
    protected static final ChatColor VALUE_COLOR = ChatColor.GOLD;
    
    private static final int AMMO_IDX = 0;
    private static final int ACCURACY_IDX = 1;
    private static final int CONDITION_IDX = 2;
    private static final int BUILD_IDX = 3;
    private static final int FIRE_MODE_IDX = 4;
    private static final int SCOPE_IDX = 5;
    private static final int INFO_IDX = 6;
    private static final int LORE_SIZE = 7;
    
    // PZ`gunID`durability`ConditionType`BuildType
    private static final int INFO_VERIFY_IDX = 0;
    private static final int INFO_ID_IDX = 1;
    private static final int INFO_DUR_IDX = 2;
    private static final int INFO_COND_IDX = 3;
    private static final int INFO_BUILD_IDX = 4;
    private static final int INFO_SIZE = 5;
    
    private CrackshotType(final int uniqueId,
                          final WeaponType weaponType,
                          final FireModeTypes firemodeType,
                          final ScopeTypes scopeType,
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
        this.value = crackshotWeaponName;
        this.initialBulletSpread = initialBulletSpread;
        this.maxDurability = maxDurability;
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
    
    public double generateLore(final double eventBulletSpread,
                               List<String> lore)
    {
        lore.clear();
        final int initialDurability = this.getInitialDurability();
        final int initialCondition = this.getConditionInt(initialDurability);

        lore.add(AMMO_IDX,       this.getAmmoLore());
        lore.add(ACCURACY_IDX,   this.getAccuracyLore(initialDurability));
        lore.add(CONDITION_IDX,  this.getConditionLore(initialCondition));
        lore.add(BUILD_IDX,      this.getBuildLore(0));  // Initialize build to Stock
        lore.add(FIRE_MODE_IDX,  this.getFireModeLore());
        lore.add(SCOPE_IDX,      this.getScopeLore());
        lore.add(INFO_IDX,       this.getStatLore(initialDurability));
        
        return this.getBulletSpread(eventBulletSpread, initialCondition, 0);
    }
    
    public static double shootHandler(final double eventBulletSpread,
                                      final ItemStack item)
    {
        final ItemMeta gunMeta = item.getItemMeta();
        double bulletSpread = -1;
        
        if (!item.hasItemMeta() || !item.getItemMeta().hasLore())
            return bulletSpread;
        
        List<String> lore = item.getItemMeta().getLore();
        
        if (lore.size() == 2 && lore.get(0).equalsIgnoreCase(preShotVerification))
            bulletSpread = initializeGun(eventBulletSpread, lore);
        else if (lore.size() == LORE_SIZE)
            bulletSpread = decrementDurability(eventBulletSpread, lore);
        
        gunMeta.setLore(lore);
        item.setItemMeta(gunMeta);
        return bulletSpread;
    }
    
    public static CrackshotType getCrackshotType(final int ID)
    {
        for (CrackshotType type : CrackshotType.values())
            if (type.uniqueId == ID)
                return type;
        
        return null;
    }
    
    private static double initializeGun(final double eventBulletSpread,
                                     List<String> lore)
    {
        final CrackshotType gun = getCrackshotType(Integer.valueOf(lore.get(1)));
        if (gun == null)
            return -1;
        
        return gun.generateLore(eventBulletSpread, lore);  
    }
    
    private String getAmmoLore()
    {
        return this.getLoreString(WeaponType.getTitle(), weaponType.getValue());
    }
    
    private String getAccuracyLore(final int durability)
    {
        return this.getLoreString(this.weaponType.getAccuracyTitle(),
                this.weaponType.getAccuracyValue(this.initialBulletSpread, this.getConditionInt(durability)));
    }
    
    private String getConditionLore(final int condition)
    {
         return this.getLoreString(ConditionTypes.getTitle(), ConditionTypes.getValue(condition));
    }
    
    private String getBuildLore(final int build)
    {
        return this.getLoreString(BuildTypes.getTitle(), BuildTypes.getValue(build));
    }
    
    private String getFireModeLore()
    {
        return this.getLoreString(FireModeTypes.getTitle(), this.firemodeType.getValue());
    }
    
    private String getScopeLore()
    {
        return this.getLoreString(ScopeTypes.getTitle(), this.scopeType.getValue());
    }
    
    private String getStatLore(final int durability)
    {
        // PZ`gunID`durability`maxDurability`ConditionTypeBuildType
        StringBuilder stb = new StringBuilder();
        stb.append(verification);
        stb.append(seperator);
        stb.append(this.uniqueId);
        stb.append(seperator);
        stb.append(durability);
        stb.append(seperator);
        stb.append(this.getConditionInt(durability));
        stb.append(seperator);
        stb.append(0);
        return HiddenStringUtils.encodeString(stb.toString());
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
        stb.append(valueColor);
        stb.append(value);
        return stb.toString();
    }
    
    private int getInitialDurability()
    {
        return this.maxDurability - rand.nextInt(maxDurability/2);
    }
    
    /**
     * Returns the integer of the gun condition.
     * @param durability Current durability of the gun.
     * @return Gun tier integer.
     */
    private int getConditionInt(final int durability)
    {
        final double ratio = (double)durability / (double)this.maxDurability;
        for (int i = 0; i <= ConditionTypes.TIERS; i++)
            if (Double.compare(ratio, (double)i/(double)ConditionTypes.TIERS) <= 0) return i;
        
        return ConditionTypes.TIERS;
    }
    
    private static double decrementDurability(final double eventBulletSpread,
                                           List<String> lore)
    {
        final int durability, condition, newCondition;
        String infoSplit[] = HiddenStringUtils.extractHiddenString(lore.get(INFO_IDX)).split(seperator);
        
        if (infoSplit.length == INFO_SIZE && infoSplit[INFO_VERIFY_IDX].equalsIgnoreCase(verification))
        {
            CrackshotType gun = getCrackshotType(Integer.valueOf(INFO_ID_IDX));
            if (gun == null)
                return -1;
            
            durability = Integer.valueOf(infoSplit[INFO_DUR_IDX]) - 1;
            if (durability < 0) // Gun is already broken
                return 0;
            
            condition = Integer.valueOf(infoSplit[CONDITION_IDX]);
            newCondition = gun.getConditionInt(durability);
            
            infoSplit[INFO_DUR_IDX] = String.valueOf(durability);
            if (condition != newCondition)
                lore.set(CONDITION_IDX, gun.getConditionLore(newCondition));
            
            lore.set(INFO_IDX, gun.rebuildInfoLore(infoSplit));
            return gun.getBulletSpread(eventBulletSpread, condition, Integer.valueOf(infoSplit[INFO_BUILD_IDX]));
        }
        else
            return -1;
    }
    
    private String rebuildInfoLore(String infoSplit[])
    {
        final StringBuilder stb = new StringBuilder();
        for (int i = 0; i < infoSplit.length - 1; i++)
        {
            stb.append(infoSplit[i]);
            stb.append(seperator);
        }
        stb.append(infoSplit[infoSplit.length - 1]);
        return HiddenStringUtils.encodeString(stb.toString());
    }
    
    private double getBulletSpread(final double eventBulletSpread,
                                   final int condition,
                                   final int build)
    {
        return this.weaponType.getBulletSpread(eventBulletSpread, condition)
                * BuildTypes.getBuildType(build).getScalar();
    }
}
