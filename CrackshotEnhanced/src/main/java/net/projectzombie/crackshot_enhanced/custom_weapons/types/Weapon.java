/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.types;

import org.bukkit.ChatColor;

/**
 *
 * @author Jesse Bannon (jmbannon@uw.edu)
 * 
 * 
 */
public enum Weapon implements Type
{
    PISTOL    (0, 1.1, 90,  0.450, "Pistol Bullets"),
    REVOLVER  (1, 1.3, 150, 1.376, "Revolver Rounds"),
    HUNTING   (2, 1.5, 130, 2.105, "Hunting Rifle Bullets"),
    SHOTGUN   (3, 1.3, 120, 0.333, "Shotgun Shells"),
    SMG       (4, 1.4, 120, 0.521, "SMG Rounds"),
    ASSAULT   (5, 1.6, 180, 0.846, "Assault Rifle Bullets"),
    AUTO_S    (6, 2.4, 230, 1.124, "Auto-Sniper Rounds"),
    SNIPER    (7, 2.7, 250, 2.105, "Sniper Bullets");
    
    private static final String TITLE = "Ammo: ";
    
    protected static final ChatColor STAT_COLOR = ChatColor.DARK_RED;
    protected static final ChatColor VALUE_COLOR = ChatColor.GOLD;
    
    private final int enumValue;
    private final double repairPriceWeight, upgradePriceWeight, bulletSpreadWeight;
    private final String ammoValue;
    
    private static final Double ACCURACY_WEIGHT = 25.3487;
    
    /**
     * Initializes the weapon type and numbers needed for bullet spread
     * and durability algorithms.
     * 
     * @param enumValue Enum integer value of the weapon.
     * @param repairPriceWeight Weight for calculating repair price.
     * @param upgradePriceWeight Weight for calculating upgrade price.
     * @param bulletSpreadWeight Weight for calculating current bullet spread based on tier.
     */
    private Weapon(final int enumValue,
                   final double repairPriceWeight,
                   final double upgradePriceWeight,
                   final double bulletSpreadWeight,
                   final String ammoType)
    {
        this.enumValue = enumValue;
        this.repairPriceWeight = repairPriceWeight;
        this.upgradePriceWeight = upgradePriceWeight;
        this.bulletSpreadWeight = bulletSpreadWeight;
        this.ammoValue = ammoType;
    }
    
    public static String getTitle()       { return TITLE;     }
    public double getRepairPriceWeight()  { return repairPriceWeight;  }
    public double getUpgradePriceWeight() { return upgradePriceWeight; }
    @Override public String toString()    { return ammoValue; }
    @Override public int getEnumValue()   { return enumValue; }
    
    /**
     * Gets the bullet spread to be set for the event.
     * @param eventBulletSpread Bullet spread from the event.
     * @param condition Tier of the weapon.
     * @return Bullet spread to be set for the event.
     */
    public double getBulletSpread(final double eventBulletSpread,
                                  final int condition)
    {
        return eventBulletSpread + (eventBulletSpread * bulletSpreadWeight/(double)condition);
    }
    
    public String getAccuracyTitle()
    {
        return (this.equals(SHOTGUN)) ? "BB Spread: " : "Accuracy: ";
    }
    
    /**
     * Returns the accuracy to be shown in the lore for the weapon.
     * @param CSBulletSpread CrackShot bullet spread found within the lore.
     * @param condition Current tier of the weapon.
     * @return String representation of the accuracy.
     */
    public String getAccuracyValue(final double CSBulletSpread,
                                   final int condition)
    {
        return (this.equals(SHOTGUN)) ?
            String.format("%.1f", getAccuracyPercentage(CSBulletSpread, condition)/2)
                : String.format("%.2f%%", getAccuracyPercentage(CSBulletSpread, condition));
    }
   
    private double getAccuracyPercentage(final double CSBulletSpread,
                                         final int condition)
    {
        return ((double)100 - (ACCURACY_WEIGHT * getBulletSpread(CSBulletSpread, condition)));
    }
}
