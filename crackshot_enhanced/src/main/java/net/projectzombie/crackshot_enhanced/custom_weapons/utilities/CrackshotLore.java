/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.utilities;

import java.util.List;
import net.projectzombie.crackshot_enhanced.custom_weapons.types.Build;
import net.projectzombie.crackshot_enhanced.custom_weapons.types.Condition;
import net.projectzombie.crackshot_enhanced.custom_weapons.types.Gun;
import static net.projectzombie.crackshot_enhanced.custom_weapons.types.Gun.getGunType;
import net.projectzombie.crackshot_enhanced.custom_weapons.types.FireMode;
import net.projectzombie.crackshot_enhanced.custom_weapons.types.Scope;
import net.projectzombie.crackshot_enhanced.custom_weapons.types.Weapon;
import net.projectzombie.crackshot_enhanced.utilities.HiddenStringUtils;
import org.bukkit.Bukkit;
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
    public static final int LORE_SIZE = 7;
    
    // PZ`gunID`durability`ConditionType`BuildType
    public static final int INFO_VERIFY_IDX = 0;
    public static final int INFO_ID_IDX = 1;
    public static final int INFO_DUR_IDX = 2;
    public static final int INFO_COND_IDX = 3;
    public static final int INFO_BUILD_IDX = 4;
    public static final int INFO_SIZE = 5;
    
    public static final int PRE_SHOT_VERIFICATION_IDX = 1;
    
    public static final ChatColor TITLE_COLOR = ChatColor.DARK_RED;
    public static final ChatColor VALUE_COLOR = ChatColor.GOLD;
    public static final ChatColor LINE_COLOR = ChatColor.DARK_GREEN;
    
    public static final String preShotVerification = TITLE_COLOR + "Shoot to unviel stats.";
    public static final String verification = "PZ";
    public static final String seperator = "`";
    public static final String line = LINE_COLOR + "=-=-=-=-=-=-=-=-=-=-=";

    public static double generateLore(final Gun gun,
                               final double eventBulletSpread,
                               List<String> lore)
    {
        lore.clear();
        final int initialDurability = gun.getInitialDurability();
        final int initialCondition = gun.getConditionInt(initialDurability);

        lore.add(INFO_IDX,       getStatLore(gun, initialDurability));
        lore.add(AMMO_IDX,       getAmmoLore(gun));
        lore.add(ACCURACY_IDX,   getAccuracyLore(gun, initialDurability));
        lore.add(CONDITION_IDX,  getConditionLore(initialCondition));
        lore.add(BUILD_IDX,      getBuildLore(0));  // Initialize build to Stock
        lore.add(FIRE_MODE_IDX,  getFireModeLore(gun));
        lore.add(SCOPE_IDX,      getScopeLore(gun));
        
        return getBulletSpread(gun, eventBulletSpread, initialCondition, 0);
    }
    
    private static String getAmmoLore(final Gun gun)
    {
        return buildLoreString(Weapon.getTitle(), gun.getWeaponType().getValue());
    }
    
    private static String getAccuracyLore(final Gun gun,
                                   final int durability)
    {
        final Weapon wepType = gun.getWeaponType();
        return buildLoreString(wepType.getAccuracyTitle(),
                                  wepType.getAccuracyValue(gun.getInitBulletSpread(),
                                                           gun.getConditionInt(durability)));
    }
    
    private static String getConditionLore(final int condition)
    {
         return buildLoreString(Condition.getTitle(), Condition.getValue(condition));
    }
    
    private static String getBuildLore(final int build)
    {
        return buildLoreString(Build.getTitle(), Build.getValue(build));
    }
    
    private static String getFireModeLore(final Gun gun)
    {
        return buildLoreString(FireMode.getTitle(), gun.getFireMode().getValue());
    }
    
    private static String getScopeLore(final Gun gun)
    {
        return buildLoreString(Scope.getTitle(), gun.getScopeType().getValue());
    }
    
    private static String getStatLore(final Gun gun,
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
    
    private static String rebuildInfoLore(String infoSplit[])
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
    
    private static String buildLoreString(final String title,
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
    
    private static String buildLoreString(final String title,
                                          final String value)
    {
        return buildLoreString(title, VALUE_COLOR, value);
    }
    
    private static double getBulletSpread(final Gun gun,
                                          final double eventBulletSpread,
                                          final int condition,
                                          final int build)
    {
        return gun.getWeaponType().getBulletSpread(eventBulletSpread, condition)
                * Build.getBuildType(build).getScalar();
    }
    
    public static double initializeGun(final double eventBulletSpread,
                                        List<String> lore)
    {
        
        final Gun gun = Gun.getGunType(getWeaponId(lore));
        if (gun == null)
            return -1;
        
        return CrackshotLore.generateLore(gun, eventBulletSpread, lore);  
    }
    
    public static boolean isPreShotWeapon(final List<String> lore)
    {
        return (lore.size() == 2 && lore.get(PRE_SHOT_VERIFICATION_IDX).equalsIgnoreCase(preShotVerification));
    }
    
    public static double decrementDurability(final double eventBulletSpread,
                                              List<String> lore)
    {
        final int durability, condition, newCondition;
        String infoSplit[] = getInfoSplit(lore);
        for (String tmep : infoSplit)
                Bukkit.broadcastMessage(tmep);
        
        if (infoSplit.length == INFO_SIZE && infoSplit[INFO_VERIFY_IDX].equalsIgnoreCase(verification))
        {
            Gun gun = getGunType(Integer.valueOf(INFO_ID_IDX));
            if (gun == null)
                return -1;
            
            durability = Integer.valueOf(infoSplit[INFO_DUR_IDX]) - 1;
            if (durability < 0) // Gun is already broken
                return 0;
            
            condition = Integer.valueOf(infoSplit[CONDITION_IDX]);
            newCondition = gun.getConditionInt(durability);
            
            infoSplit[INFO_DUR_IDX] = String.valueOf(durability);
            if (condition != newCondition)
                lore.set(CONDITION_IDX, getConditionLore(newCondition));
            
            lore.set(INFO_IDX, rebuildInfoLore(infoSplit));
            return getBulletSpread(gun, eventBulletSpread, condition, Integer.valueOf(infoSplit[INFO_BUILD_IDX]));
        }
        else
            return -1;
    }
    
    public static int getWeaponID(final ItemStack item)
    {
        final ItemMeta meta = item.getItemMeta();
        
        if (!meta.hasLore())
            return -1;
        
        return getWeaponId(meta.getLore());
    }
    
    private static int getWeaponId(final List<String> lore)
    {
       if (lore.size() > 1)
        {
            final String[] info = getInfoSplit(lore);
            if (info.length >= 2 && info[INFO_VERIFY_IDX].equalsIgnoreCase(verification))
                return Integer.valueOf(info[INFO_ID_IDX]);
        }
        Bukkit.broadcastMessage("nope!");
        return -1;
    }
    
    private static boolean hasWeaponLore(final List<String> lore)
    {
        return (lore.size() > 1 && getInfoSplit(lore)[INFO_VERIFY_IDX].equalsIgnoreCase(verification));
    }
    
    private static String getDecryptedInfoString(final List<String> lore)
    {
        if (lore.size() < 1)
            return null;
        
                                                                    // Gets rid of first seperator
        return HiddenStringUtils.extractHiddenString(lore.get(INFO_IDX).replace(line, "")).replaceFirst(seperator, "");
    }
    
    private static String[] getInfoSplit(final List<String> lore)
    {
        if (lore.size() < 1)
            return null;
        
        return getDecryptedInfoString(lore).split(seperator);
    }
    
    public static String getEncryptedPreInfoString(final ItemStack item)
    {
        final List<String> lore;
       
        if (!item.hasItemMeta())
            return null;
           
        lore = item.getItemMeta().getLore();
        return (lore.size() >= 1) ? 
                HiddenStringUtils.encodeString(lore.get(INFO_IDX).replace(line, "")).replace(ChatColor.COLOR_CHAR, '&')
                : null;
    }
}
