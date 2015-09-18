/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.utilities;

import com.shampaggon.crackshot.CSUtility;
import java.util.List;
import net.projectzombie.crackshot_enhanced.custom_weapons.types.Build;
import net.projectzombie.crackshot_enhanced.custom_weapons.types.Condition;
import net.projectzombie.crackshot_enhanced.custom_weapons.types.CrackshotGun;
import net.projectzombie.crackshot_enhanced.custom_weapons.types.FireMode;
import net.projectzombie.crackshot_enhanced.custom_weapons.types.Scope;
import net.projectzombie.crackshot_enhanced.custom_weapons.types.Attatchment;
import net.projectzombie.crackshot_enhanced.custom_weapons.types.Weapon;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author jbannon
 */
public class CrackshotLore
{
    public static final int INFO_IDX = 0;
    public static final int AMMO_IDX = 1;
    public static final int ACCURACY_IDX = 2;
    public static final int CONDITION_IDX = 3;
    public static final int BUILD_IDX = 4;
    public static final int FIRE_MODE_IDX = 5;
    public static final int SCOPE_IDX = 6;
    public static final int ATT_IDX = 7;
    public static final int LORE_SIZE = 8;
    
    // PZ`gunID`durability`ConditionType`BuildType
    public static final int INFO_VERIFY_IDX = 0;
    public static final int INFO_ID_IDX = 1;
    public static final int INFO_DUR_IDX = 2;
    public static final int INFO_COND_IDX = 3;
    public static final int INFO_BUILD_IDX = 4;
    public static final int INFO_SIZE = 5;
    
    public static final int PRE_SHOT_VERIFICATION_IDX = 1;
    
    public static final ChatColor ITEM_COLOR  = ChatColor.YELLOW;
    public static final ChatColor TITLE_COLOR = ChatColor.DARK_RED;
    public static final ChatColor VALUE_COLOR = ChatColor.GOLD;
    
    public static final String preShotVerification = TITLE_COLOR + "Shoot to unviel stats.";
    public static final String verification = "PZ";
    public static final String seperator = "`";
    public static final String line = TITLE_COLOR + "=-=-=-=-=-=-=-=-=-=-=";

    public static CSUtility crackshot = new CSUtility();
    
    public static double generateLore(final CrackshotGun gun,
                                      final double eventBulletSpread,
                                      List<String> lore)
    {
        final int initialDurability = gun.getInitialDurability();
        final int initialCondition = gun.getConditionInt(initialDurability);
        generateLore(gun, lore, initialDurability, initialCondition);
        return getBulletSpread(gun, eventBulletSpread, initialCondition, 0);
    }
    
    static
    public void generateLore(final CrackshotGun gun,
                             List<String> lore,
                             final int initialDurability,
                             final int initialCondition)
    {
        lore.clear();
        lore.add(INFO_IDX,       buildStatLore(gun, initialDurability));
        lore.add(AMMO_IDX,       buildAmmoLore(gun));
        lore.add(ACCURACY_IDX,   buildAccuracyLore(gun, Build.STOCK.getEnumValue(), initialDurability));
        lore.add(CONDITION_IDX,  buildConditionLore(initialCondition));
        lore.add(BUILD_IDX,      buildBuildLore(Build.STOCK.getEnumValue()));
        lore.add(FIRE_MODE_IDX,  buildFireModeLore(gun));
        lore.add(SCOPE_IDX,      buildScopeLore(gun));
        lore.add(ATT_IDX,        buildAttatchmentLore(gun));
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
    public boolean isPostShotWeapon(final ItemStack item)
    {
        return item.hasItemMeta() && item.getItemMeta().hasLore() && isPostShotWeapon(item.getItemMeta().getLore());
    }
    
    static
    public boolean isPreShotWeapon(final List<String> lore)
    {
        return lore.size() == 2 && lore.get(PRE_SHOT_VERIFICATION_IDX).equalsIgnoreCase(preShotVerification);
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
        
        CrackshotGun gun = GunAccess.get(Integer.valueOf(INFO_ID_IDX));
        if (gun == null)
            return -1;

        durability = Integer.valueOf(infoSplit[INFO_DUR_IDX]) - 1;

        if (durability < 0) // Gun is already broken
            return 0;

        condition = Integer.valueOf(infoSplit[CONDITION_IDX]);
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
    public int getWeaponID(final ItemStack item)
    {
        if (!item.hasItemMeta() || !item.getItemMeta().hasLore())
            return -1;
        
        return getWeaponId(item.getItemMeta().getLore());
    }
    
    static
    public boolean isUnencrypted(final List<String> lore)
    {
        final String firstLine = line + seperator + verification + seperator;
        return lore.size() > 0  && lore.get(0).startsWith(firstLine);
    }
    
    static
    public double initializeGun(final double eventBulletSpread,
                                List<String> lore)
    {
        
        final CrackshotGun gun = CrackshotGun.getGun(getWeaponId(lore));
        if (gun == null)
            return -1;
        
        return CrackshotLore.generateLore(gun, eventBulletSpread, lore);  
    }
    
    static
    public boolean repairWeapon(final ItemStack item)
    {
        if (!isPostShotWeapon(item))
            return false;
        
        final ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        String split[] = getInfoSplit(lore);
        
        final CrackshotGun gun = CrackshotGun.getGun(Integer.valueOf(split[INFO_ID_IDX]));
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
        
        final CrackshotGun gun = CrackshotGun.getGun(Integer.valueOf(split[INFO_ID_IDX]));
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
        final List<String> newLore = newMeta.getLore();
        final int durability, condition;
        
        if (isPostShotWeapon(originalGunItem))
            durability = CrackshotLore.getDurability(originalGunItem);
        else if (isPreShotWeapon(originalGunItem))
            durability = newGun.getInitialDurability();
        else
            return null;
        
        condition = newGun.getConditionInt(durability);
        CrackshotLore.generateLore(newGun, newLore, durability, condition);
        
        newMeta.setLore(newLore);
        newGunItem.setItemMeta(newMeta);
        return newGunItem;
    }
    
    /* PRIVATE METHODS */
    
    static
    private String buildAmmoLore(final CrackshotGun gun)
    {
        return buildLoreString(Weapon.getTitle(), gun.getWeaponType().toString());
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
        
        return buildLoreString(wepType.getAccuracyTitle(), accuracyString);
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
        return buildLoreString(FireMode.getTitle(), gun.getFireMode().toString());
    }
    
    static
    private String buildScopeLore(final CrackshotGun gun)
    {
        return buildLoreString(Scope.getTitle(), gun.getScopeType().toString());
    }
    
    static
    private String buildAttatchmentLore(final CrackshotGun gun)
    {
        return buildLoreString(Attatchment.getTitle(), gun.getAttatchment().toString());
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
        stb.append(gun.getUniqueId());
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
    
    static
    private int getWeaponId(final List<String> lore)
    {
        return hasWeaponLore(lore) ? Integer.valueOf(getInfoSplit(lore)[INFO_ID_IDX]) : -1;
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
    
    
}
