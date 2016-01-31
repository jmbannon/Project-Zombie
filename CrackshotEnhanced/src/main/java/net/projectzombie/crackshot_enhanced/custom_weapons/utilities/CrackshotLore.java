/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.utilities;

import net.projectzombie.crackshot_enhanced.custom_weapons.weps.Guns;
import com.shampaggon.crackshot.CSUtility;
import java.util.ArrayList;
import java.util.List;
import net.projectzombie.crackshot_enhanced.custom_weapons.qualities.Build;
import net.projectzombie.crackshot_enhanced.custom_weapons.qualities.Condition;
import net.projectzombie.crackshot_enhanced.custom_weapons.weps.Guns.CrackshotGun;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.FireModes.FireMode;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.GunModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.weps.WeaponTypes.Weapon;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author jbannon
 */
public class CrackshotLore
{
    private static final int INFO_IDX = 0;
    private static final int DPS_IDX = 1;
    private static final int ACCURACY_IDX = 2;
    private static final int ZOOOM_IDX = 3;
    private static final int BUILD_IDX = 4;
    private static final int CONDITION_IDX = 5;
    private static final int LINE_IDX = 6;
    private static final int ATT1_IDX = 7;
    private static final int ATT2_IDX = 8;
    private static final int ATT3_IDX = 9;
    private static final int BARREL_IDX = 10;
    private static final int BOLT_IDX = 11;
    private static final int FIRE_MODE_IDX = 12;
    private static final int MAG_IDX = 13;
    private static final int SIGHT_IDX = 14;
    private static final int STOCK_IDX = 15;
    private static final int MORE_INFO_IDX = 16;
    public static final int  LORE_SIZE = 17;
    
    // PZ`gunID`durability`ConditionType`BuildType
    private static final int INFO_VERIFY_IDX = 0;
    private static final int INFO_ID_IDX = 1;
    private static final int INFO_DUR_IDX = 2;
    private static final int INFO_COND_IDX = 3;
    private static final int INFO_BUILD_IDX = 4;
    private static final int INFO_SIZE = 5;
    
    private static final int PRE_SHOT_VERIFICATION_IDX = 1;
    
    public static final ChatColor ITEM_COLOR  = ChatColor.YELLOW;
    public static final ChatColor TITLE_COLOR = ChatColor.DARK_GREEN;
    public static final ChatColor VALUE_COLOR = ChatColor.GRAY;
    
    
    public static final String preShotVerification = TITLE_COLOR + "Unviel using an identitying kit.";
    public static final String verification = "PZ";
    public static final String seperator = "`";
    public static final String line = TITLE_COLOR + "=-=-=-=-=-=-=-=-=-=-=";
    public static final String moreInfo = TITLE_COLOR + "More info using " + VALUE_COLOR + "/gunsmith info";

    public static CSUtility crackshot = new CSUtility();
    
    public static List<String> generateLore(final List<String> lore)
    {
        final CrackshotGun gun = Guns.get(getWeaponId(lore));
        if (gun == null)
            return null;
        
        final int initialDurability = gun.getInitialDurability();
        final int initialCondition = gun.getConditionInt(initialDurability);
        return(generateLore(gun, initialDurability, initialCondition));
    }
    
    /**
     * Adds lore to a lore list using gun info.
     * @param gun Gun to generate lore about.
     * @param initialDurability Initial Durability of the gun.
     * @param initialCondition Initial Condition of the gun.
     * @return 
     */
    static
    public List<String> generateLore(final CrackshotGun gun,
                                     final int initialDurability,
                                     final int initialCondition)
    {
        final ArrayList<String> lore = new ArrayList<>(LORE_SIZE);
        lore.clear();
        lore.add(INFO_IDX,       buildStatLore(gun, initialDurability));
        lore.add(DPS_IDX,        VALUE_COLOR + "DPS: 10");
        lore.add(ACCURACY_IDX,   buildAccuracyLore(gun, Build.STOCK.getEnumValue(), initialDurability));
        lore.add(ZOOOM_IDX,      buildAccuracyLore(gun, Build.STOCK.getEnumValue(), initialDurability));
        lore.add(BUILD_IDX,      buildBuildLore(Build.STOCK.getEnumValue()));
        lore.add(CONDITION_IDX,  buildConditionLore(initialCondition));
        lore.add(LINE_IDX, line);
        lore.add(ATT1_IDX,       VALUE_COLOR + "Attatchment One: " + getModifierDisplayName(gun.getAttatchmentOne()));
        lore.add(ATT2_IDX,       VALUE_COLOR + "Attatchment Two: " + getModifierDisplayName(gun.getAttatchmentTwo()));
        lore.add(ATT3_IDX,       VALUE_COLOR + "Attatchment Three: " + getModifierDisplayName(gun.getAttatchmentThree()));
        lore.add(BARREL_IDX,     VALUE_COLOR + "Barrel: " + getModifierDisplayName(gun.getBarrel()));
        lore.add(BOLT_IDX,       VALUE_COLOR + "Bolt: " + getModifierDisplayName(gun.getBolt()));
        lore.add(FIRE_MODE_IDX,  VALUE_COLOR + "Fire Mode: " + getModifierDisplayName(gun.getFireMode()));
        lore.add(MAG_IDX,        VALUE_COLOR + "Magazine: " + getModifierDisplayName(gun.getMagazine()));
        lore.add(SIGHT_IDX,      VALUE_COLOR + "Sight: " + getModifierDisplayName(gun.getScope()));
        lore.add(STOCK_IDX,      VALUE_COLOR + "Stock: " + getModifierDisplayName(gun.getStock()));
        lore.add(MORE_INFO_IDX,  moreInfo);
        return lore;
    }
    
   static
   public String getModifierDisplayName(final GunModifier mod)
   {
       if (mod != null)
           return mod.getDisplayName();
       else
           return ChatColor.DARK_RED + "n/a";
       
   }
    
    static
    public String getEncryptedPreInfoString(final ItemStack item)
    {
        final List<String> lore;
       
        if (!item.hasItemMeta() || !item.getItemMeta().hasLore())
            return null;
           
        lore = item.getItemMeta().getLore();
        return (lore.size() >= 1) ? 
                HiddenStringUtils.encodeString(lore.get(INFO_IDX).replace(line, "")).replace(ChatColor.COLOR_CHAR, '&')
                : null;
    }
    
    static
    public int getDurability(final ItemStack item)
    {
        final String[] split = getInfoSplit(item);
        return split == null ? -1 : Integer.valueOf(split[INFO_DUR_IDX]);
    }
    
    static
    public int getBuild(final ItemStack item)
    {
        final String[] split = getInfoSplit(item);
        return split == null ? -1 : Integer.valueOf(split[INFO_BUILD_IDX]);
    }
    
    static
    public boolean isPreShotWeapon(final ItemStack item)
    {
        return item.hasItemMeta() && item.getItemMeta().hasLore() && isPreShotWeapon(item.getItemMeta().getLore());
    }
    
    static
    public boolean isPreShotWeapon(final List<String> lore)
    {
        return lore.size() == 2 && lore.get(PRE_SHOT_VERIFICATION_IDX).equalsIgnoreCase(preShotVerification);
    }
    
    static
    public boolean isPostShotWeapon(final ItemStack item)
    {
        return item.hasItemMeta() && item.getItemMeta().hasLore() && isPostShotWeapon(item.getItemMeta().getLore());
    }
    
    static
    public boolean isPostShotWeapon(final List<String> lore)
    {
        String infoSplit[] = getInfoSplit(lore);
        return infoSplit.length == INFO_SIZE && infoSplit[INFO_VERIFY_IDX].equalsIgnoreCase(verification);
    }
    
    static
    public double decrementDurability(final double eventBulletSpread,
                                      List<String> lore)
    {
        if (!isPostShotWeapon(lore))
            return -1;
        
        final int durability, condition, newCondition, build;
        String infoSplit[] = getInfoSplit(lore);
        
        CrackshotGun gun = Guns.get(infoSplit[INFO_ID_IDX]);
        if (gun == null)
            return -1;

        durability = Integer.valueOf(infoSplit[INFO_DUR_IDX]) - 1;

        if (durability < 0) // Gun is already broken
            return 0;

        condition = Integer.valueOf(infoSplit[INFO_COND_IDX]);
        newCondition = gun.getConditionInt(durability);
        build = Integer.valueOf(infoSplit[INFO_BUILD_IDX]);
        
        infoSplit[INFO_DUR_IDX] = String.valueOf(durability);
        if (condition != newCondition)
        {
            infoSplit[INFO_BUILD_IDX] = String.valueOf(newCondition);
            lore.set(CONDITION_IDX, buildConditionLore(newCondition));
            lore.set(ACCURACY_IDX,  buildAccuracyLore(gun, build, durability));
        }

        lore.set(INFO_IDX, rebuildInfoLore(infoSplit));
        //Bukkit.broadcastMessage("" + getBulletSpread(gun, eventBulletSpread, newCondition, build));
        return getBulletSpread(gun, eventBulletSpread, newCondition, build);
    }
    
    static
    public String getWeaponID(final ItemStack item)
    {
        if (!item.hasItemMeta() || !item.getItemMeta().hasLore())
            return null;   
 
        return getWeaponId(item.getItemMeta().getLore());
    }
    
    static
    private String getWeaponId(final List<String> lore)
    {
        return hasWeaponLore(lore) ? getInfoSplit(lore)[INFO_ID_IDX] : null;
    }
    
    static
    public boolean repairWeapon(final ItemStack item)
    {
        if (!isPostShotWeapon(item))
            return false;
        
        final ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        String split[] = getInfoSplit(lore);
        
        final CrackshotGun gun = Guns.get(split[INFO_ID_IDX]);
        final int maxDur = gun.getMaxDurability()-1;
        final int build = Integer.valueOf(split[INFO_BUILD_IDX]);
        
        split[INFO_DUR_IDX] = String.valueOf(maxDur);
        lore.set(INFO_IDX, rebuildInfoLore(split));
        lore.set(CONDITION_IDX, buildConditionLore(maxDur));
        lore.set(ACCURACY_IDX, buildAccuracyLore(gun, build, maxDur));
        
        meta.setLore(lore);
        item.setItemMeta(meta);
        
        return true;
    }
    
    static
    public boolean upgradeWeapon(final ItemStack item)
    {
        if (!isPostShotWeapon(item))
            return false;
        
        final ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        String split[] = getInfoSplit(lore);
        
        final CrackshotGun gun = Guns.get(split[INFO_ID_IDX]);
        final int newBuild = Integer.valueOf(split[INFO_BUILD_IDX]) + 1;
        final int durability = Integer.valueOf(split[INFO_DUR_IDX]);
        
        split[INFO_BUILD_IDX] = String.valueOf(newBuild);
        lore.set(INFO_IDX, rebuildInfoLore(split));
        lore.set(BUILD_IDX, buildBuildLore(newBuild));
        lore.set(ACCURACY_IDX, buildAccuracyLore(gun, newBuild, durability));
        
        meta.setLore(lore);
        item.setItemMeta(meta);
        
        return true;
    }
    
    static
    public ItemStack getModifiedGunItem(final ItemStack originalGunItem,
                                        final CrackshotGun newGun)
    {
        final ItemStack newGunItem = crackshot.generateWeapon(newGun.getCSWeaponName());
        if (newGunItem == null)
            return null;
        
        final ItemMeta newMeta = newGunItem.getItemMeta();
        List<String> newLore = newMeta.getLore();
        final String adjustedAmmoDisplayName = getAdjustedAmmoCount(originalGunItem, newMeta);
        final int durability, condition;
        
        if (isPostShotWeapon(originalGunItem))
        {
            durability = CrackshotLore.getDurability(originalGunItem);
            condition = newGun.getConditionInt(durability);
            newLore = CrackshotLore.generateLore(newGun, durability, condition);
            newMeta.setLore(newLore);
            newMeta.setDisplayName(adjustedAmmoDisplayName);
            newGunItem.setItemMeta(newMeta);
            return newGunItem;
        }
        else if (isPreShotWeapon(originalGunItem))
        {
            newLore = CrackshotLore.setWeaponID(newGun.getUniqueID(), newLore);
            newMeta.setLore(newLore);
            return newGunItem;
        }
        else
            return null;
    }
    
    /* PRIVATE METHODS */
    
    static
    private String buildAmmoLore(final CrackshotGun gun)
    {
        return buildLoreString("Ammo: ", gun.getWeaponType().toString());
    }
    
    static
    private String buildAccuracyLore(final CrackshotGun gun,
                                     final int build,
                                     final int durability)
    {
        final Weapon wepType = gun.getWeaponType();
        final String accuracyString = (durability > 0) ?
                wepType.getAccuracyValue(gun.getInitBulletSpread() * Build.getBuildType(build).getScalar(),
                                                        gun.getConditionInt(durability)) : "n/a";
        
        return buildLoreString("Accuracy: ", accuracyString);
    }
    
    static
    private String buildConditionLore(final int condition)
    {
         return buildLoreString(Condition.getTitle(), Condition.getValue(condition));
    }
    
    static
    private String buildBuildLore(final int build)
    {
        return buildLoreString(Build.getTitle(), Build.getValue(build));
    }
    
    static
    private String buildFireModeLore(final CrackshotGun gun)
    {
        final FireMode gunFireMode = gun.getFireMode();
        return buildLoreString(FireMode.getTitle(), gunFireMode.toString());
    }
    
    static
    private String buildStatLore(final CrackshotGun gun,
                                 final int durability)
    {
        // PZ`gunID`durability`maxDurability`ConditionTypeBuildType
        StringBuilder stb = new StringBuilder();
        stb.append(seperator);
        stb.append(verification);
        stb.append(seperator);
        stb.append(gun.getUniqueID());
        stb.append(seperator);
        stb.append(durability);
        stb.append(seperator);
        stb.append(gun.getConditionInt(durability));
        stb.append(seperator);
        stb.append(0);
        return line + HiddenStringUtils.encodeString(stb.toString());
    }
    
    static
    private String rebuildInfoLore(String infoSplit[])
    {
        final StringBuilder stb = new StringBuilder();
        for (String info : infoSplit)
        {
            stb.append(seperator);
            stb.append(info);
        }
        return line + HiddenStringUtils.encodeString(stb.toString());
    }
    
    static
    private String buildLoreString(final String title,
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
    
    static
    private String buildLoreString(final String title,
                                   final String value)
    {
        return buildLoreString(title, VALUE_COLOR, value);
    }
    
    static
    private double getBulletSpread(final CrackshotGun gun,
                                   final double eventBulletSpread,
                                   final int condition,
                                   final int build)
    {
        if (condition == Condition.BROKEN.getEnumValue())
            return 0;
        
        return gun.getWeaponType().getBulletSpread(eventBulletSpread, condition)
                * Build.getBuildType(build).getScalar();
    }
    
    
    
    static private List<String> setWeaponID(final String weaponID,
                                            final List<String> lore)
    {
        
        
        if (hasWeaponLore(lore))
        {
            final String[] infoSplit = getInfoSplit(lore);
            infoSplit[INFO_ID_IDX] = weaponID;
            
            lore.set(INFO_IDX, rebuildInfoLore(infoSplit));
            return lore;
        }
        else
            return lore;
    }
    
    static
    private boolean hasWeaponLore(final List<String> lore)
    {
        return lore.size() > 1 && getInfoSplit(lore)[INFO_VERIFY_IDX].equalsIgnoreCase(verification);
    }
    
    static
    private String getDecryptedInfoString(final List<String> lore)
    {
        if (lore.size() < 1)
            return null;
                                                                    // Gets rid of first seperator
        return HiddenStringUtils.extractHiddenString(lore.get(INFO_IDX).replace(line, "")).replaceFirst(seperator, "");
    }
    
    static
    private String[] getInfoSplit(final List<String> lore)
    {
        if (lore.size() < 1)
            return null;
        
        return getDecryptedInfoString(lore).split(seperator);
    }
    
    static 
    private String[] getInfoSplit(final ItemStack item)
    {
        if (!item.hasItemMeta() || !item.getItemMeta().hasLore())
            return null;
        
        final List<String> lore = item.getItemMeta().getLore();
        
        if (!isPostShotWeapon(lore))
            return null;
        
        return getInfoSplit(lore);
    }
    
    static
    public boolean isBroken(final ItemStack item)
    {       
        return CrackshotLore.isPostShotWeapon(item) && CrackshotLore.getDurability(item) <= 0;
    }
    
    static
    private String getAdjustedAmmoCount(final ItemStack originalGun,
                                        final ItemMeta newGunMeta)
    {
        final String origName = originalGun.getItemMeta().getDisplayName();
        final String newName = newGunMeta.getDisplayName();
        
        String ammoSubString = origName.substring(origName.indexOf("«"), origName.indexOf("»"));
        String adjustedNewName = newName.substring(0, newName.indexOf("«")) + ammoSubString + newName.substring(newName.indexOf("»"), newName.length());
        
        return adjustedNewName;
        
    }
    
}
