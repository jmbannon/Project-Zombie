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
    PISTOL          (0, 130, 185,  0.450, "Pistol Bullets"),
    REVOLVER        (1, 195, 260,  1.376, "Revolver Rounds"),
    HUNTING_RIFLE   (2, 370, 530,  2.105, "Hunting Rifle Bullets"),
    SHOTGUN         (3, 50,  110,  0.333, "Shotgun Shells"),
    SMG             (4, 255, 440,  0.521, "SMG Rounds"),
    ASSAULT_RIFLE   (5, 580, 850,  0.846, "Assault Rifle Bullets"),
    AUTO_SNIPER     (6, 480, 700,  1.124, "Auto-Sniper Rounds"),
    SNIPER          (7, 600, 1000, 2.105, "Sniper Bullets");
    
    private static final String TITLE = "Ammo: ";
    
    protected static final ChatColor STAT_COLOR = ChatColor.DARK_RED;
    protected static final ChatColor VALUE_COLOR = ChatColor.GOLD;
    
    private final int enumValue, lowerBound, upperBound;
    private final double weight;
    private final String ammoValue;
    
    private static final Double ACCURACY_WEIGHT = 25.3487;
    
    /**
     * Initializes the weapon type and numbers needed for bullet spread
     * and durability algorithms.
     * 
     * @param enumValue Enum integer value of the weapon.
     * @param lowerBound Lower bound for durability algorithm.
     * @param upperBound Upper bound for durability algorithm.
     * @param weight Weight for calculating current bullet spread based on tier.
     */
    private Weapon(final int enumValue,
                   final int lowerBound,
                   final int upperBound,
                   final double weight,
                   final String ammoType)
    {
        this.enumValue = enumValue;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.weight = weight;
        this.ammoValue = ammoType;
    }
    
    public static String getTitle()     { return TITLE;     }
    
    @Override public String toString()  { return ammoValue; }
    @Override public int getEnumValue() { return enumValue; }
    
    /**
     * Gets the bullet spread to be set for the event.
     * @param eventBulletSpread Bullet spread from the event.
     * @param condition Tier of the weapon.
     * @return Bullet spread to be set for the event.
     */
    public double getBulletSpread(final double eventBulletSpread,
                                  final int condition)
    {
        return eventBulletSpread + (eventBulletSpread * weight/(double)condition);
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
    
    /**
     * Returns the upgrade price based on the current tier of the weapon.
     * 
     * @param currentTier Current tier of the weapon.
     * @return Price of the upgrade. -1 if not applicable.
     */
    public int getUpgradePrice(final int currentTier)
    {
        switch(currentTier)
        {
        case 0: return upperBound/3;
        case 1: return upperBound/2;
        case 2: return upperBound;
        default: return -1;
        }
    }
    
    /**
     * Returns the repair price to fully repair a weapon based on its current
     * and max durability.
     * 
     * @param currentDurability Current durability of the weapon.
     * @param maxDurability Max durability of the weapon.
     * @return 
     */
    public int getRepairPrice(final int currentDurability,
                              final int maxDurability)
    {
        return (int)((double)currentDurability * (double)upperBound/(double)maxDurability);
    }
}
