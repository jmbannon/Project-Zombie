/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs.guns.physical.weps;

import cs.guns.components.Attachments;
import cs.guns.components.Barrels;
import cs.guns.components.Bolts;
import cs.guns.components.FireModes;
import cs.guns.components.Magazines;
import cs.guns.components.Sights;
import cs.guns.components.Stocks;
import cs.guns.qualities.Build;
import cs.guns.qualities.Condition;
import cs.guns.skeleton.SkeletonTypes;
import cs.guns.weps.GunID;
import cs.guns.weps.Guns.CrackshotGun;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;

/**
 *
 * @author jb
 */
public class CrackshotGunLore extends HiddenGunInfo
{
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
    
    private static final int INFO_IDX = 0;
    private static final int DPS_IDX = 1;
    private static final int ACCURACY_IDX = 2;
    private static final int BUILD_IDX = 3;
    private static final int CONDITION_IDX = 4;
    private static final int LINE_IDX = 5;
    
    private List<String> lore;
    
    public CrackshotGunLore(List<String> lore)
    {
        super(extractHiddenInfo(lore));
        this.lore = lore;
    }
    
    /**
     * Creates a Pre-shot gun lore
     * @param id 
     */
    public CrackshotGunLore(final GunID id)
    {
        super(id);
        this.lore = null;
    }
    
    public List<String> getLore()
    {
        return lore;
    }
    
    static
    private boolean hasLoreContents(List<String> lore)
    {
        return lore != null && lore.size() > INFO_IDX;
    }
    
    static
    private String buildStatLore(final String hiddenLore)
    {
        return statsLine + hiddenLore;
    }
    
    static
    private String extractHiddenInfo(final List<String> lore)
    {
        if (hasLoreContents(lore))
            return lore.get(INFO_IDX).replace(statsLine, "");
        else
            return null;
    }
    
    @Override
    public void setGunID(final String newID)
    {
        super.setGunID(newID);
        setHiddenInfo();
    }
    
    @Override
    public void setDurability(final int dur)
    {
        super.setDurability(dur);
        setHiddenInfo();
    }
    
    @Override
    public int decrementDurability()
    {
        final int decDur = super.decrementDurability();
        setHiddenInfo();
        return decDur;
    }
    
    @Override
    public void setBuild(final Build build)
    {
        super.setBuild(build);
        setHiddenInfo();
    }
    
    private void setHiddenInfo()
    {
        lore.set(INFO_IDX, buildStatLore(this.getHiddenLore()));
    }
    
    /**
     * @return PostShot lore with Build value STOCK and an initial durability.
     */
    public List<String> generatePostShotLore()
    {
        return generatePostShotLore(Build.STOCK, this.getGun().getInitialDurability());
    }
    
    /**
     * Creates an ItemStack lore (List<String>) for a Crackshot gun.
     * @param build
     * @param durability
     * @return 
     */
    public List<String> generatePostShotLore(final Build build,
                                             final int durability)
    {
        final CrackshotGun gun = super.getGun();
        
        final Attachments.Attachment att1 = gun.getAttachmentOneMod();
        final Attachments.Attachment att2 = gun.getAttachmentTwoMod();
        final Attachments.Attachment att3 = gun.getAttachmentThreeMod();
        final Barrels.Barrel barrel = gun.getBarrelMod();
        final Bolts.Bolt bolt = gun.getBoltMod();
        final FireModes.FireMode firemode = gun.getFireModeMod();
        final Magazines.Magazine mag = gun.getMagazineMod();
        final Sights.Scope scope = gun.getScopeMod();
        final Stocks.Stock stock = gun.getStockMod();
        
        lore = new ArrayList<>();
        lore.add(INFO_IDX,       buildStatLore(this.getHiddenLore()));
        lore.add(DPS_IDX,        buildLoreString("DPS: ", String.valueOf(gun.getDPS())));
        lore.add(ACCURACY_IDX,   buildAccuracyLore(gun, build, durability));
        lore.add(BUILD_IDX,      buildBuildLore(build));
        lore.add(CONDITION_IDX,  buildConditionLore(Condition.getCondition(durability, gun.getDurabilitySet().getMaxDurability())));
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
    
    public List<String> generatePreShotLore()
    {
        lore = new ArrayList<>();
        lore.add(buildStatLore(this.getPreShotHiddenLore()));
        lore.add(preShotVerification);
        return lore;
    }
    
    static
    private String buildAccuracyLore(final CrackshotGun gun,
                                     final Build build,
                                     final int durability)
    {
        final SkeletonTypes.SkeletonType wepType = gun.getWeaponType();
        
        return buildLoreString("Accuracy: ", "WIP");
    }
    
    static
    private String buildConditionLore(final Condition condition)
    {
         return buildLoreString(Condition.getTitle(), condition.name());
    }
    
    static
    private String buildBuildLore(final Build build)
    {
        return buildLoreString(Build.getTitle(), build.getDisplayName());
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
}
