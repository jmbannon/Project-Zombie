/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.types;

import static net.projectzombie.crackshot_enhanced.custom_weapons.types.FirearmAction.*;

/**
 *
 * @author Jesse Bannon (jmbannon@uw.edu)
 * 
 * 
 */
public enum Weapon
{
    PISTOL    (null,         2,  1.1, 90,  0.450, "Pistol Bullets",        372, 0),
    REVOLVER  (null,         2,  1.3, 150, 1.376, "Revolver Rounds",       351, 0),
    HUNTING   (HUNTING_BOLT, 3,  1.5, 130, 2.105, "Hunting Rifle Bullets", 318, 0),
    SH_BREAK  (BREAK,        10, 1.3, 120, 0.333, "Shotgun Shells",        371, 0),
    SH_SLIDE  (SLIDE,        10, 1.3, 120, 0.333, "Shotgun Shells",        371, 0),
    SH_PUMP   (PUMP,         10, 1.3, 120, 0.333, "Shotgun Shells",        371, 0),
    SMG       (SMG_SLIDE,    2, 1.4, 120, 0.521, "SMG Rounds",            371, 0),
    ASSAULT   (SLIDE,        2, 1.6, 180, 0.846, "Assault Rifle Bullets", 361, 0),
    AUTO_S    (SLIDE,        2, 2.4, 230, 1.124, "Auto-Sniper Rounds",    351, 1),
    SNIPER    (SNIPER_BOLT,  3, 2.7, 250, 2.105, "Sniper Bullets",        337, 0);

    private final FirearmAction action;
    private final int projectileAmount;
    private final int ammoID;
    private final int ammoData;
    private final double repairPriceWeight;
    private final double upgradePriceWeight;
    private final double bulletSpreadWeight;
    private final String ammoValue;
    
    
    /**
     * Initializes the weapon type and numbers needed for bullet spread
     * and durability algorithms.
     * 
     * @param enumValue Enum integer value of the weapon.
     * @param repairPriceWeight Weight for calculating repair price.
     * @param upgradePriceWeight Weight for calculating upgrade price.
     * @param bulletSpreadWeight Weight for calculating current bullet spread based on tier.
     */
    private Weapon(final FirearmAction action,
                   final int projectileAmount,
                   final double repairPriceWeight,
                   final double upgradePriceWeight,
                   final double bulletSpreadWeight,
                   final String ammoType,
                   final int ammoID,
                   final int ammoData)
    {
        this.action = action;
        this.projectileAmount = projectileAmount;
        this.repairPriceWeight = repairPriceWeight;
        this.upgradePriceWeight = upgradePriceWeight;
        this.bulletSpreadWeight = bulletSpreadWeight;
        this.ammoValue = ammoType;
        this.ammoID = ammoID;
        this.ammoData = ammoData;
    }
    
    public FirearmAction getAction()             { return action;             }
    public int           getProjectileAmount()   { return projectileAmount;   }
    public double        getRepairPriceWeight()  { return repairPriceWeight;  }
    public double        getUpgradePriceWeight() { return upgradePriceWeight; }
    public int           getAmmoID()             { return ammoID;             }
    public int           getAmmoData()           { return ammoData;           }
    
    /**
     * Gets the bullet spread to be set for the event.
     * 
     * @param eventBulletSpread Bullet spread from the event.
     * @param condition Tier of the weapon.
     * @return Bullet spread to be set for the event.
     */
    public double getBulletSpread(final double eventBulletSpread,
                                  final int condition)
    {
        return eventBulletSpread + (eventBulletSpread * bulletSpreadWeight/(double)condition);
    }
    
    /**
     * Returns the accuracy to be shown in the lore for the weapon.
     * 
     * @param CSBulletSpread CrackShot bullet spread found within the lore.
     * @param condition Current tier of the weapon.
     * @return String representation of the accuracy.
     */
    public String getAccuracyValue(final double CSBulletSpread,
                                   final int condition)
    {
        double eventBulletSpread = getBulletSpread(CSBulletSpread, condition);
        return String.valueOf(Math.round(10 * eventBulletSpread / Math.pow(eventBulletSpread, 2.0)));
    }
   
}
