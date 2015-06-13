/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons;

import java.util.Random;
import org.bukkit.ChatColor;

/**
 *
 * @author jbannon
 */
public enum WeaponType
{
    PISTOL          (0, 130, 185,  0.450),
    REVOLVER        (1, 195, 260,  1.376),
    HUNTING_RIFLE   (2, 370, 530,  2.105),
    SHOTGUN         (3, 50,  110,  0.333),
    SMG             (4, 255, 440,  0.521),
    ASSAULT_RIFLE   (5, 580, 850,  0.846),
    AUTO_SNIPER     (6, 480, 700,  1.124),
    SNIPER          (7, 600, 1000, 2.105);
    
    private final int value, lowerBound, upperBound;
    private final double weight;
    
    /* Random initial durability */
    private static final Random RAND = new Random();
    private static final ChatColor STAT_COLOR = ChatColor.DARK_RED;
    private static final ChatColor VALUE_COLOR = ChatColor.GOLD;
    
    private static final Double ACCURACY_WEIGHT = 25.3487;
    
    /**
     * Initializes the weapon type and numbers needed for bullet spread
     * and durability algorithms.
     * 
     * @param value Enum integer value of the weapon.
     * @param lowerBound Lower bound for durability algorithm.
     * @param upperBound Upper bound for durability algorithm.
     * @param weight Weight for calculating current bullet spread based on tier.
     */
    private WeaponType(final int value,
                       final int lowerBound,
                       final int upperBound,
                       final double weight)
    {
        this.value = value;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.weight = weight;
    }
    
    /**
     * Returns initial durability from the first shot of the weapon to write
     * to the lore.
     * 
     * @param CSBulletSpread CrackShot bullet spread found within the lore.
     * @return Initial durability to write to the gun lore.
     */
    public int getInitialDurability(final double CSBulletSpread)
    {
        return (int)(CSBulletSpread * (double)(lowerBound + RAND.nextInt(upperBound - lowerBound + 1)));
    }
    
    /**
     * Returns max durability from the first shot of the weapon to write to the
     * lore.
     * 
     * @param CSBulletSpread CrackShot bullet spread found within the lore..
     * @return Max durability to write to the gun lore.
     */
    public int getMaxDurability(final double CSBulletSpread)
    {
        return (int)(CSBulletSpread * (double)upperBound);
    }
    
    /**
     * Gets the bullet spread to be set for the event.
     * @param eventBulletSpread Bullet spread from the event.
     * @param tier Tier of the weapon.
     * @return Bullet spread to be set for the event.
     */
    public double getBulletSpread(final double eventBulletSpread,
                                  final int tier)
    {
        return eventBulletSpread + (eventBulletSpread * weight/(double)tier);
    }
    
    /**
     * Returns the accuracy to be shown in the lore for the weapon.
     * @param CSBulletSpread CrackShot bullet spread found within the lore.
     * @param tier Current tier of the weapon.
     * @return String representation of the accuracy.
     */
    public String getAccuracy(final double CSBulletSpread,
                              final int tier)
    {
        final StringBuilder stb = new StringBuilder();
        final String accuracyType;
        final String accuracyValue;
        
        if (this == SHOTGUN) {
            accuracyType = "BB Spread: ";
            accuracyValue = String.format("%.1f", getAccuracyPercentage(CSBulletSpread, tier)/2); 
        } else {
            accuracyType = "Accuracy: ";
            accuracyValue = String.format("%.2f%%", getAccuracyPercentage(CSBulletSpread, tier));
        }
        stb.append(STAT_COLOR);
        stb.append(accuracyType);
        stb.append(VALUE_COLOR);
        stb.append(accuracyValue);
        return stb.toString();
    }
   
    private double getAccuracyPercentage(final double CSBulletSpread,
                                         final int tier)
    {
        return ((double)100 - (ACCURACY_WEIGHT * getBulletSpread(CSBulletSpread, tier)));
    }
    
    /**
     * Returns the condition to be displayed in the lore.
     * @param tier Current tier of the weapon.
     * @return Condition to be displayed in the lore.
     */
    public String getCondition(final int tier)
    {
        return STAT_COLOR + "Condition: " + WeaponConditionTiers.getCondition(tier);
    }
    
    /**
     * Returns the integer of the gun tier as a string.
     * @param CSBulletSpread CrackShot defined bullet spread found in the lore.
     * @param durability Current durability of the gun.
     * @return Gun tier integer as a string.
     */
    public String getTierString(final double CSBulletSpread,
                                final int durability)
    {
        return String.valueOf(getTierInt(CSBulletSpread, durability));
    }
    
    /**
     * Returns the integer of the gun tier.
     * @param CSBulletSpread CrackShot defined bullet spread found in the lore.
     * @param durability Current durability of the gun.
     * @return Gun tier integer.
     */
    public int getTierInt(final double CSBulletSpread,
                          final int durability)
    {
        final double ratio = (double)durability / (double)getMaxDurability(CSBulletSpread);
        for (int i = 0; i <= WeaponConditionTiers.TIERS; i++)
            if (Double.compare(ratio, (double)i/(double)WeaponConditionTiers.TIERS) <= 0) return i;
        
        return WeaponConditionTiers.TIERS;
    }
    
    /**
     * Returns gun type value as an integer.
     * @return Integer value of the gun type.
     */
    public int getIntValue()
    {
        return value;
    }
    
    /**
     * Returns gun type value as an string.
     * @return String value of the gun type.
     */
    public String getStringValue()
    {
        return String.valueOf(value);
    }
    
    public static WeaponType getType(final String typeEnum)
    {
        return getType(Integer.valueOf(typeEnum));
    }
    
    public static WeaponType getType(final int typeEnum)
    {
        switch(typeEnum)
        {
        case 0: return PISTOL;
        case 1: return REVOLVER;
        case 2: return HUNTING_RIFLE;
        case 3: return SHOTGUN;
        case 4: return SMG;
        case 5: return ASSAULT_RIFLE;
        case 6: return AUTO_SNIPER;
        case 7: return SNIPER;
        default: return null;
        }
    }
}
