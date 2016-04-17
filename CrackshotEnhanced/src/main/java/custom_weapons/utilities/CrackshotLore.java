/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package custom_weapons.utilities;

import custom_weapons.weps.Guns;
import java.util.ArrayList;
import java.util.List;
import custom_weapons.modifiers.Attachments.Attachment;
import custom_weapons.modifiers.Barrels.Barrel;
import custom_weapons.modifiers.Bolts.Bolt;
import custom_weapons.qualities.Build;
import custom_weapons.qualities.Condition;
import custom_weapons.weps.Guns.CrackshotGun;
import custom_weapons.modifiers.FireModes.FireMode;
import custom_weapons.modifiers.Magazines.Magazine;
import custom_weapons.modifiers.Sights.Scope;
import custom_weapons.modifiers.Stocks.Stock;
import custom_weapons.skeleton.SkeletonTypes.SkeletonType;
import static custom_weapons.utilities.Constants.CRACKSHOT;
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
    private static final int BUILD_IDX = 3;
    private static final int CONDITION_IDX = 4;
    private static final int LINE_IDX = 5;
    
    // PZ`gunID`durability`ConditionType`BuildType
    private static final int INFO_VERIFY_IDX = 0;
    private static final int INFO_ID_IDX = 1;
    private static final int INFO_DUR_IDX = 2;
    private static final int INFO_COND_IDX = 3;
    private static final int INFO_BUILD_IDX = 4;
    private static final int INFO_SIZE = 5;
    
    private static final int PRE_SHOT_VERIFICATION_IDX = 1;
    
    
    public static final ChatColor TITLE_COLOR = ChatColor.DARK_GREEN;
    public static final ChatColor VALUE_COLOR = ChatColor.GRAY;
    
    public static final ChatColor LINE_COLOR = ChatColor.GRAY;
    private static final ChatColor LINE_INFO_COLOR = ChatColor.RED;
    public static final ChatColor GUN_MOD_COLOR = ChatColor.GOLD;    
    
    public static final String preShotVerification = TITLE_COLOR + "Unviel using an identitying kit.";
    public static final String verification = "PZ";
    public static final String seperator = "`";
    
    
    
    public static final String statsLine = LINE_COLOR + "----- " + LINE_INFO_COLOR + "Stats" + LINE_COLOR + " ------------------";
    public static final String modLine =   LINE_COLOR + "----- " + LINE_INFO_COLOR + "Mods" + LINE_COLOR + " ------------------";
    public static final String moreInfo = LINE_INFO_COLOR + "Get more info using " + VALUE_COLOR + "/gunsmith info";

    
    
    /**
     * Generates the lore for a pre-shot weapon.
     * @param lore Lore of the ItemStack of the pre-shot weapon.
     * @return Lore to be added to the ItemStack of the pre-shot weapon.
     */
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
    private List<String> generateLore(final CrackshotGun gun,
                                     final int initialDurability,
                                     final int initialCondition)
    {
        final Attachment att1 = gun.getAttachmentOneMod();
        final Attachment att2 = gun.getAttachmentTwoMod();
        final Attachment att3 = gun.getAttachmentThreeMod();
        final Barrel barrel = gun.getBarrelMod();
        final Bolt bolt = gun.getBoltMod();
        final FireMode firemode = gun.getFireModeMod();
        final Magazine mag = gun.getMagazineMod();
        final Scope scope = gun.getScopeMod();
        final Stock stock = gun.getStockMod();
        
        final ArrayList<String> lore = new ArrayList<>();
        lore.clear();
        lore.add(INFO_IDX,       buildStatLore(gun, initialDurability));
        lore.add(DPS_IDX,        buildLoreString("DPS: ", String.valueOf(gun.getDPS())));
        lore.add(ACCURACY_IDX,   buildAccuracyLore(gun, Build.STOCK.getEnumValue(), initialDurability));
        lore.add(BUILD_IDX,      buildBuildLore(Build.STOCK.getEnumValue()));
        lore.add(CONDITION_IDX,  buildConditionLore(initialCondition));
        lore.add(LINE_IDX, modLine);
        
        if (att1 != null && !att1.isNull())         lore.add(GUN_MOD_COLOR + "Attachment One: " + att1.getDisplayName(true));
        if (att2 != null && !att2.isNull())         lore.add(GUN_MOD_COLOR + "Attachment Two: " + att2.getDisplayName(true));
        if (att3 != null && !att3.isNull())         lore.add(GUN_MOD_COLOR + "Attachment Three: " + att3.getDisplayName(true));
        if (barrel != null && !barrel.isNull())     lore.add(GUN_MOD_COLOR + "Barrel: " + barrel.getDisplayName(true));
        if (bolt != null && !bolt.isNull())         lore.add(GUN_MOD_COLOR + "Bolt: " + bolt.getDisplayName(true));
        if (firemode != null && !firemode.isNull()) lore.add(GUN_MOD_COLOR + "Fire Mode: " + firemode.getDisplayName(true));
        if (mag != null && !mag.isNull())           lore.add(GUN_MOD_COLOR + "Magazine: " + mag.getDisplayName(true));
        if (scope != null && !scope.isNull())       lore.add(GUN_MOD_COLOR + "Scope: " + scope.getDisplayName(true));
        if (stock != null && !stock.isNull())       lore.add(GUN_MOD_COLOR + "Stock: " + stock.getDisplayName(true));
        
        lore.add(moreInfo);
        return lore;
    }
    
    static
    public int getDurability(final ItemStack item)
    {
        final String[] split = getInfoSplit(item);
        return split == null ? -1 : Integer.valueOf(split[INFO_DUR_IDX]);
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
    public List<String> decrementDurability(final double eventBulletSpread,
                                            final List<String> lore)
    {
        if (!isPostShotWeapon(lore))
            return null;
        
        final int durability, condition, newCondition, build;
        String infoSplit[] = getInfoSplit(lore);
        
        CrackshotGun gun = Guns.get(infoSplit[INFO_ID_IDX]);
        if (gun == null)
            return null;

        durability = Integer.valueOf(infoSplit[INFO_DUR_IDX]) - 1;

        if (durability < 0) // Gun is already broken
            return null;

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
        return lore;
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
    public ItemStack getModifiedGunItem(final ItemStack originalGunItem,
                                        final CrackshotGun newGun)
    {
        final ItemStack newGunItem = CRACKSHOT.generateWeapon(newGun.getCSWeaponName());
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
        final SkeletonType wepType = gun.getWeaponType();
        
        return buildLoreString("Accuracy: ", "WIP");
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
        return statsLine + HiddenStringUtils.encodeString(stb.toString());
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
        return statsLine + HiddenStringUtils.encodeString(stb.toString());
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
    private String[] getInfoSplit(final List<String> lore)
    {
        final String infoString;
        if (lore.size() < 1)
            return null;
        
        // Gets rid of first seperator
        infoString = HiddenStringUtils.extractHiddenString(lore.get(INFO_IDX)
                .replace(statsLine, ""))
                .replaceFirst(seperator, "");
        
        return infoString.split(seperator);
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
