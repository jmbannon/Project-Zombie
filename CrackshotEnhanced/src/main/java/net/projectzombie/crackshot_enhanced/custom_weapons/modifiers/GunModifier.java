/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.modifiers;

import java.util.ArrayList;
import java.util.Collections;
import net.projectzombie.crackshot_enhanced.custom_weapons.csv.CSVValue;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.projectile.BleedoutModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.skeleton.BoltModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.projectile.BulletSpreadModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.projectile.CritModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.projectile.DamageModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.projectile.HeadshotModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.skeleton.DurabilityModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.skeleton.FireModeModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.projectile.IgniteModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.projectile.IncendiaryModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.skeleton.MagazineModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.skeleton.RunningModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.projectile.ShrapnelModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.skeleton.SilencerModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.projectile.StunModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.skeleton.ZoomModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.utilities.CrackshotLore;
import net.projectzombie.crackshot_enhanced.custom_weapons.utilities.GunUtils;
import net.projectzombie.crackshot_enhanced.custom_weapons.utilities.HiddenStringUtils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.skeleton.ProjectileModifier;

/**
 *
 * @author jesse
 */
public abstract class GunModifier extends CSVValue
{
    /**
     * Assigns each GunModifier a matrix index within the crafting inventory.
     * 
     *     [,0] [,1] [,2]
     * [0,]  0    1    2
     * [1,]  3    4    5    =   9
     * [2,]  6    7    8
     * 
     */
    public enum GunModifierType
    {
        SLOT_ONE_ATTACHMENT(0, "PRISMARINE_SHARD", 0),
        SLOT_TWO_ATTATCHMENT(6, "PRISMARINE_CRYSTALS", 0),
        SLOT_THREE_ATTATCHMENT(8, "RABBIT_HIDE", 0),
        BARREL(3, "QUARTZ", 0),
        BOLT(2, "SUGAR", 0),
        FIREMODE(2, "EGG", 0),
        MAGAZINE(7, "LEATHER", 0),
        SIGHT(1, "COAL", 1),
        STOCK(5, "BLAZE_ROD", 0);
        
        private final int matrixIndex;
        private final Material material;
        private final int materialData;
        
        private GunModifierType(final int matrixIndex,
                                final String material,
                                final int materialData)
        {
            this.matrixIndex = matrixIndex;
            this.material = GunUtils.matchMaterial(material);
            this.materialData = materialData;
        }
        
        public int getMatrixIndex() { return matrixIndex; }
        
        public MaterialData getMaterialData()
        {
            if (material != null)
                return new MaterialData(material, (byte)materialData);
            else
                return null;
        }
        
        static public GunModifierType getGunModifierType(final int matrixIndex)
        {
            for (GunModifierType type : GunModifierType.values())
                if (type.matrixIndex == matrixIndex)
                    return type;
            return null;
        }
    }
    
    private static final String STAT_COLOR = ChatColor.GRAY.toString() + ChatColor.ITALIC.toString();
    private static final String DEFAULT_COLOR = ChatColor.GREEN.toString();
    
    private final Material material;
    private final int materialData;
    private final int price;
    private final ChatColor color;
    
    public GunModifier(final int uniqueID,
                       final String name,
                       final String material,
                       final int materialData,
                       final int price,
                       final String color)
    {
        super(uniqueID, name);
        this.materialData = materialData;
        this.price = price;
        this.material = GunUtils.matchMaterial(material);
        this.color = GunUtils.matchChatColor(color);
    }
    
    /**
     * @return Returns the null modifier.
     */
    abstract public GunModifier getNullModifier();
    
    /**
     * @return Returns the price of the gun modification at the gunsmith.
     */
    public int price()
    {
        return price;
    }
    
    /**
     * @return Color of the GunModifier in lore based on rarity.
     */
    public ChatColor getColor()
    {
        return color;
    }
    
    /**
     * @return Whether the modifier is null.
     */
    public boolean isNull()
    {
        return this.equals(this.getNullModifier()) || super.getName() == null;
    }
    
    public String getDisplayName(final boolean italics)
    {
        final String chatColor;
        if (color == null)
            chatColor = DEFAULT_COLOR;
        else
            chatColor = color.toString();
        
        if (super.getName() != null)
        {
            if (italics)
                return chatColor + ChatColor.ITALIC.toString() + super.getName();
            else
                return chatColor + super.getName();
        }
        else
        {
            if (italics)
                return ChatColor.DARK_RED + ChatColor.ITALIC.toString() + "n/a";
            else
                return ChatColor.DARK_RED + "n/a"; 
        }
    }
    
    public ItemStack toItem(final GunModifierType type)
    {
        if (type.material != null)
        {
            final ItemStack item = new ItemStack(type.material, 1, (short)type.materialData);

            ItemMeta meta = item.getItemMeta();
            meta.setLore(getLore());
            meta.setDisplayName(this.color + super.getName());
            item.setItemMeta(meta);

            return item;
        }
        return null;
    }
    
    static public int getIndex(final ItemStack item)
    {

        final String infoString;
        final String indexString;
        final int lineIndex;
        if (isGunModifier(item))
        {
            infoString = item.getItemMeta().getLore().get(0);
            lineIndex = infoString.indexOf(CrackshotLore.statsLine);
            
            if (lineIndex >= 0)
            {
                indexString = HiddenStringUtils.extractHiddenString(infoString.substring(0, lineIndex));
                return Integer.valueOf(indexString);
            }
        }
        return -1;
    }
    
    static public boolean isGunModifier(final ItemStack item)
    {
        return item != null 
                && item.hasItemMeta()
                && item.getItemMeta().hasLore() 
                && item.getItemMeta().getLore().size() >= 1
                && item.getItemMeta().getLore().get(0).contains(CrackshotLore.statsLine);
    }
    
    static public GunModifier toModifier(final ItemStack item,
                                         final int matrixIndex)
    {
        final int index = getIndex(item);
        final MaterialData matData;
        if (index == -1)
        {
            return null;
        }
        else
        {
            switch(matrixIndex)
            {
                case 0: return Attachments.getInstance().get(index);
                case 1: return Sights.getInstance().get(index);
                case 2: return Bolts.getInstance().get(index);
                case 3: return Barrels.getInstance().get(index);
                case 5: return Stocks.getInstance().get(index);
                case 6: return Attachments.getInstance().get(index);
                case 7: return Magazines.getInstance().get(index);
                case 8: return Attachments.getInstance().get(index);
                default: return null;
            }
        }
    }
    
    public ArrayList<String> getLore()
    {
        final ArrayList<String> stats = new ArrayList<>();
        if (this.isNull())
            return null;
        
        if (this instanceof BleedoutModifier)     stats.addAll(getBleedoutStats((BleedoutModifier)this));
        if (this instanceof BoltModifier)         stats.addAll(getBoltModifierStats((BoltModifier)this));
        if (this instanceof BulletSpreadModifier) stats.addAll(getBulletSpreadModifierStats((BulletSpreadModifier)this));
        if (this instanceof CritModifier)         stats.addAll(getCritModifierStats((CritModifier)this));
        if (this instanceof DamageModifier)       stats.addAll(getDamageModifierStats((DamageModifier)this));
        if (this instanceof HeadshotModifier)     stats.addAll(getHeadshotModifierStats((HeadshotModifier)this));
        if (this instanceof DurabilityModifier)   stats.addAll(getDurabilityModifierStats((DurabilityModifier)this));
        if (this instanceof FireModeModifier)     stats.addAll(getFireModeModifierStats((FireModeModifier)this));
        if (this instanceof IgniteModifier)       stats.addAll(getIgniteModifierStats((IgniteModifier)this));
        if (this instanceof IncendiaryModifier)   stats.addAll(getIncendiaryModifierStats((IncendiaryModifier)this));
        if (this instanceof MagazineModifier)     stats.addAll(getMagazineModifierStats((MagazineModifier)this));
        if (this instanceof ProjectileModifier)   stats.addAll(getProjectileModifierStats((ProjectileModifier)this));
        if (this instanceof RunningModifier)      stats.addAll(getRunningModifierStats((RunningModifier)this));
        if (this instanceof ShrapnelModifier)     stats.addAll(getShrapnelModifierStats((ShrapnelModifier)this));
        if (this instanceof StunModifier)         stats.addAll(getStunModifierStats((StunModifier)this));
        if (this instanceof SilencerModifier)     stats.addAll(getSilencerModifierStats((SilencerModifier)this));
        if (this instanceof ZoomModifier)         stats.addAll(getZoomModifierStats((ZoomModifier)this));

        if (!stats.isEmpty())
        {
            Collections.shuffle(stats);
            stats.add(0, HiddenStringUtils.encodeString(String.valueOf(super.getIndex())) + CrackshotLore.statsLine);
            return stats;
        }
        else
        {
            return null;
        }
    }
    
    static
    private ArrayList<String> getBleedoutStats(final BleedoutModifier bleedoutMod)
    {
        final ArrayList<String> stats = new ArrayList<>();
        final String bleedDamage = getValueStat(bleedoutMod.getBleedoutDamageValuePerSecond(), "bleed damage p/sec");
        final String bleedDuration = getValueStat(bleedoutMod.getBleedoutDurationValue(), "bleed duration");
        final String bleedoutDurationMult = getMultiplierStat(bleedoutMod.getBleedoutDurationMultiplier(), "bleed duration");
        final String bleedDamageFromShrap = getMultiplierStat(bleedoutMod.getBleedoutDamageMultiplerFromShrapnel(), "bleed damage from shrapnel damage p/sec");
        
        if (bleedDamage != null)          stats.add(bleedDamage);
        if (bleedDuration != null)        stats.add(bleedDuration);
        if (bleedoutDurationMult != null) stats.add(bleedoutDurationMult);
        if (bleedDamageFromShrap != null) stats.add(bleedDamageFromShrap);
        
        return stats;
    }
    
    static
    private ArrayList<String> getBoltModifierStats(final BoltModifier boltMod)
    {
        final ArrayList<String> stats = new ArrayList<>();
        final String durationIncrease = getMultiplierStat(boltMod.getBoltDurationMultiplier(), "bolt action speed");
        
        if (durationIncrease != null) stats.add(durationIncrease);
        
        return stats;
    }
    
    static
    ArrayList<String> getBulletSpreadModifierStats(final BulletSpreadModifier bsMod)
    {
        final ArrayList<String> stats = new ArrayList<>();
        final String bsMultiplier = getMultiplierStat(bsMod.getBulletSpreadMultiplier(), "bullet spread");
        
        if (bsMultiplier != null) stats.add(bsMultiplier);
        
        return stats;
    }
    
    static
    private ArrayList<String> getCritModifierStats(final CritModifier critMod)
    {
        final ArrayList<String> stats = new ArrayList<>();
        final String critStrike = getMultiplierStat(critMod.getCritStrike(), "critical strike");
        final String critChance = getMultiplierStat(critMod.getCritChance(), "critical chance");
        
        if (critStrike != null) stats.add(critStrike);
        if (critChance != null) stats.add(critChance);
        
        return stats;
    }
    
    static
    private ArrayList<String> getDamageModifierStats(final DamageModifier damageMod)
    {
        final ArrayList<String> stats = new ArrayList<>();
        final String damageValue = getValueStat(damageMod.getDamageValue(), "base damage");
        final String damageMultiplier = getMultiplierStat(damageMod.getDamageMultiplier(), "base damage");
        
        if (damageValue != null)        stats.add(damageValue);
        if (damageMultiplier != null)   stats.add(damageMultiplier);
        
        return stats;
    }
    
    static
    private ArrayList<String> getHeadshotModifierStats(final HeadshotModifier headMod)
    {
        final ArrayList<String> stats = new ArrayList<>();
        final String headshotValue = getValueStat(headMod.getHeadshotDamageModifier(), "headshot damage");
        final String headshotMultiplier = getValueStat(headMod.getHeadshotDamageMultiplier(), "headshot damage");
        
        if (headshotValue != null)      stats.add(headshotValue);
        if (headshotMultiplier != null) stats.add(headshotMultiplier);
        
        return stats;
    }
    
    static
    private ArrayList<String> getDurabilityModifierStats(final DurabilityModifier durMod)
    {
        final ArrayList<String> stats = new ArrayList<>();
        final String durabilityValue = getValueStat(durMod.getDurabilityModifier(), "durability");
        final String durabilityMultiplier = getMultiplierStat(durMod.getDurabilityMultiplier(), "durability");
        
        if (durabilityValue != null)      stats.add(durabilityValue);
        if (durabilityMultiplier != null) stats.add(durabilityMultiplier);
        
        return stats;
    }
    
    static
    private ArrayList<String> getFireModeModifierStats(final FireModeModifier durMod)
    {
        final ArrayList<String> stats = new ArrayList<>();
        final String fireModeType;
        
        if (durMod.isBurstFire())
            fireModeType = getValueStat(durMod.getShotsPerBurst(), "round burst");
        else if (durMod.isAutomatic())
            fireModeType = getBooleanStat(durMod.isAutomatic(), "Automatic");
        else
            fireModeType = null;
        
        if (fireModeType != null) stats.add(fireModeType);
        
        return stats;
    }
    
    static
    private ArrayList<String> getIncendiaryModifierStats(final IncendiaryModifier incMod)
    {
        final ArrayList<String> stats = new ArrayList<>();
        final String fireValue = getValueStat(incMod.getFireDamageValue(), "fire damage");
        final String fireMult = getMultiplierStat(incMod.getFireDamageMultiplier(), "fire damage");
        
        if (fireValue != null)      stats.add(fireValue);
        if (fireMult != null)       stats.add(fireMult);
        
        return stats;
    }
    
    static
    private ArrayList<String> getIgniteModifierStats(final IgniteModifier ignMod)
    {
        final ArrayList<String> stats = new ArrayList<>();
        final String igniteChance = getMultiplierStat(ignMod.getIgniteChance(), "ignite chance");
        final String igniteDuration = getValueStat(ignMod.getIgniteDuration(), "ignite duration");
        final String igniteMultFromFire = getMultiplierStat(ignMod.getIgniteDamageMultiplierFromFireDamage(), "ignite damage from fire damage p/sec");
        
        if (igniteChance != null)   stats.add(igniteChance);
        if (igniteDuration != null) stats.add(igniteDuration);
        
        return stats;
    }
    
    static
    private ArrayList<String> getMagazineModifierStats(final MagazineModifier magMod)
    {
        final ArrayList<String> stats = new ArrayList<>();
        final String magBoost = getValueStat(magMod.getMagazineSizeModifier(), "mag size");
        final String magMult = getMultiplierStat(magMod.getMagazineSizeMultiplier(), "mag size");
        final String reloadSpeed = getMultiplierStat(magMod.getReloadSpeedMultiplier(), "reload speed");
        
        if (magBoost != null)    stats.add(magBoost);
        if (magMult != null)     stats.add(magMult);
        if (reloadSpeed != null) stats.add(reloadSpeed);
        
        return stats;
    }
    
    static
    private ArrayList<String> getProjectileModifierStats(final ProjectileModifier projMod)
    {
        final ArrayList<String> stats = new ArrayList<>();
        final String projBoost = getValueStat(projMod.getProjectileValue(), "projectiles");
        final String rangeValue = getValueStat(projMod.getProjectileRangeValue(), "projectile range");
        final String rangeMult = getMultiplierStat(projMod.getProjectileRangeMultiplier(), "projectile range");
        final String speedMult = getMultiplierStat(projMod.getProjectileSpeedMultiplier(), "projectile speed");
        
        if (projBoost != null)  stats.add(projBoost);
        if (rangeValue != null) stats.add(rangeValue);
        if (rangeMult != null)  stats.add(rangeMult);
        if (speedMult != null)  stats.add(speedMult);
        
        return stats;
    }
    
    static
    private ArrayList<String> getRunningModifierStats(final RunningModifier runMod)
    {
        final ArrayList<String> stats = new ArrayList<>();
        final String runningBS = getMultiplierStat(runMod.getRunningBulletSpreadMultiplier(), "bullet spread while running");
        final String runningSpeed = getMultiplierStat(runMod.getRunningSpeedMultiplier(), "running speed");
        
        if (runningBS != null)    stats.add(runningBS);
        if (runningSpeed != null) stats.add(runningSpeed);
        
        return stats;
    }
    
    static
    private ArrayList<String> getShrapnelModifierStats(final ShrapnelModifier shrapMod)
    {
        final ArrayList<String> stats = new ArrayList<>();
        final String shrapVal = getValueStat(shrapMod.getShrapnelDamageValue(), "shrapnel damage");
        final String shrapMul = getValueStat(shrapMod.getShrapnelDamageMultiplier(), "shrapnel damage");
        
        if (shrapVal != null)   stats.add(shrapVal);
        if (shrapMul != null)   stats.add(shrapMul);
        return stats;
    }
    
    static
    private ArrayList<String> getStunModifierStats(final StunModifier stunMod)
    {
        final ArrayList<String> stats = new ArrayList<>();
        final String stunChance = getMultiplierStat(stunMod.getStunChance(), "stun chance");
        final String stunDur = getMultiplierStat(stunMod.getStunDuration(), "stun duration");

        if (stunChance != null) stats.add(stunChance);
        if (stunDur != null)    stats.add(stunDur);
        
        return stats;
    }
    
    static
    private ArrayList<String> getSilencerModifierStats(final SilencerModifier silMod)
    {
        final ArrayList<String> stats = new ArrayList<>();
        final String isSil = getBooleanStat(silMod.isSilencer(), "Silenced");
        
        if (isSil != null) stats.add(isSil);
        
        return stats;
    }
    
    static
    private ArrayList<String> getZoomModifierStats(final ZoomModifier zoomMod)
    {
        final ArrayList<String> stats = new ArrayList<>();
        final String zoomVal = getValueStat(zoomMod.getZoomAmount(), "zoom amount");
        final String zoomBS = getMultiplierStat(zoomMod.getZoomBulletSpreadMultiplier(), "bullet spread while zoomed");
        
        if (zoomVal != null) stats.add(zoomVal);
        if (zoomBS != null)  stats.add(zoomBS);
        
        return stats;
    }
    
    static
    private String getMultiplierStat(final double multiplier,
                                     final String description)
    {
        if (modifiesMultiplier(multiplier))
        {
            return STAT_COLOR + "  " + numPercentage(multiplier) + " " + description;
        }
        else
            return null;
    }
    
    static
    private String getValueStat(final double value,
                                final String description)
    {
        if (modifiesValue(value))
            return STAT_COLOR + "  " + numValue(value) + " " + description;
        else
            return null;
    }
    
    static
    private String getBooleanStat(final boolean value,
                                  final String description)
    {
        if (value)
            return STAT_COLOR + "  " + description;
        else
            return null;
    }
    
    static 
    private boolean modifiesValue(final double valueModifier)
    {
        return valueModifier != 0.;
    }
    
    static
    private boolean modifiesMultiplier(final double multiplier)
    {
        return multiplier != 0. && multiplier != 1.;
    }
    
    static
    private String numPercentage(final double num)
    {
        return getPercentageSign(num) + Math.round(Math.abs(1.-num) * 100) + "%";
    }
    
    static
    private String numValue(final double num)
    {
        return getValueSign(num) + Math.round(num);
    }
    
    static
    private String getPercentageSign(final double num)
    {
        if (num >= 1.0)
            return "+";
        else
            return "-";
    }
    
    static
    private String getValueSign(final double num)
    {
        if (num >= 0)
            return "+";
        else
            return "-";
    }
}
