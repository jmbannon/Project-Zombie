/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.weps;

import java.util.ArrayList;
import net.projectzombie.crackshot_enhanced.custom_weapons.csv.CSVReader;
import net.projectzombie.crackshot_enhanced.custom_weapons.csv.CSVInput;
import net.projectzombie.crackshot_enhanced.custom_weapons.csv.CSVValue;
import net.projectzombie.crackshot_enhanced.custom_weapons.weps.FirearmActions.FirearmAction;
import net.projectzombie.crackshot_enhanced.custom_weapons.weps.WeaponTypes.Weapon;

/**
 *
 * @author Jesse Bannon (jmbannon@uw.edu)
 * 
 * 
 */
public class WeaponTypes extends CSVInput<Weapon>
{  
    static private WeaponTypes singleton = null;
    static public WeaponTypes getInstance()
    {
        if (singleton == null)
            singleton = new WeaponTypes();
        return singleton;
    }
    
    static private final String WEAPON_TYPE_CSV_NAME = "WeaponTypes.csv";
    static private final String[] WEAPON_TYPE_VALUES = {
        "Display Name (STR)",
        "Firearm Action Name (STR)",
        "Ammo Name (STR)",
        "Ammo ID (INT)",
        "Ammo Data (INT)",
        "Projectile Amount (INT)",
        "Projectile Speed (INT)",
        "Removal/Drag Delay (STR)",
        "Inventory Control (STR)",
        "Repair Price Weight (DBL)",
        "Upgrade Price Weight (DBL)",
        "Bullet Spread Weight (DBL)"
    }; 
    
    private WeaponTypes()
    {
        super(WEAPON_TYPE_CSV_NAME, buildWeaponTypes(), WEAPON_TYPE_VALUES);
    }


    static private Weapon[] buildWeaponTypes()
    {
        final CSVReader csv = new CSVReader(WEAPON_TYPE_CSV_NAME, WEAPON_TYPE_VALUES);
        final int rowCount = csv.getRowCount();
        
        if (rowCount <= 0)
        {
            return null;
        }
        
        int j = 0;
        final Weapon[] toReturn          = new Weapon[rowCount];
        final String[] displayNames       = csv.getColumnString(j++);
        final ArrayList<FirearmAction> firearmActions = FirearmActions.getInstance().get(csv.getColumnString(j++), false);
        final String[] ammoNames          = csv.getColumnString(j++);
        final int[]    ammoIDs            = csv.getColumnInt(j++);
        final int[]    ammoDatas          = csv.getColumnInt(j++);
        final int[]    projectileAmounts  = csv.getColumnInt(j++);
        final int[]    projectileSpeeds   = csv.getColumnInt(j++);
        final String[] removalDragDelays  = csv.getColumnString(j++);
        final String[] inventoryControls  = csv.getColumnString(j++);
        final double[] repairPriceWeight  = csv.getColumnDouble(j++);
        final double[] upgradePriceWeight = csv.getColumnDouble(j++);
        final double[] bulletSpreadWeight = csv.getColumnDouble(j++);
        
        if (firearmActions == null || firearmActions.size() != ammoNames.length)
            return null;

        for (int i = 0; i < rowCount; i++)
        {
            toReturn[i] = new Weapon(
                    i,
                    displayNames[i],
                    firearmActions.get(i),
                    ammoNames[i],
                    ammoIDs[i],
                    ammoDatas[i],
                    projectileAmounts[i],
                    projectileSpeeds[i],
                    removalDragDelays[i],
                    inventoryControls[i],
                    repairPriceWeight[i],
                    upgradePriceWeight[i],
                    bulletSpreadWeight[i]);
        }
        return toReturn;
    }

    static public class Weapon extends CSVValue
    {
        private final FirearmAction firearmAction;
        private final String ammoName;
        private final int ammoID;
        private final int ammoData;
        private final int projectileAmount;
        private final int projectileSpeed;
        private final String removalDragDelay;
        private final String inventoryControl;
        private final double repairPriceWeight;
        private final double upgradePriceWeight;
        private final double bulletSpreadWeight;

        /**
         * Initializes the weapon type and numbers needed for bullet spread
         * and durability algorithms.
         * 
         * @param enumValue Enum integer value of the weapon.
         * @param repairPriceWeight Weight for calculating repair price.
         * @param upgradePriceWeight Weight for calculating upgrade price.
         * @param bulletSpreadWeight Weight for calculating current bullet spread based on tier.
         */
        private Weapon(final int index,
                       final String displayName,
                        final FirearmAction firearmAction,
                        final String ammoName,
                        final int ammoID,
                        final int ammoData,
                        final int projectileAmount,
                        final int projectileSpeed,
                        final String removalDragDelay,
                        final String inventoryControl,
                        final double repairPriceWeight,
                        final double upgradePriceWeight,
                        final double bulletSpreadWeight)
        {
            super(index, displayName);
            this.firearmAction = firearmAction;
            this.ammoName = ammoName;
            this.ammoID = ammoID;
            this.ammoData = ammoData;
            this.projectileAmount = projectileAmount;
            this.projectileSpeed = projectileSpeed;
            this.removalDragDelay = removalDragDelay;
            this.inventoryControl = inventoryControl;
            this.repairPriceWeight = repairPriceWeight;
            this.upgradePriceWeight = upgradePriceWeight;
            this.bulletSpreadWeight = bulletSpreadWeight;
        }

        public FirearmAction getAction()            { return firearmAction;      }
        public String        getAmmoName()           { return ammoName;           }
        public int           getAmmoID()             { return ammoID;             }
        public int           getAmmoData()           { return ammoData;           }
        public int           getProjectileAmount()   { return projectileAmount;   }
        public int           getProjectileSpeed()    { return projectileSpeed;    }
        public String        getRemovalDragDelay()   { return removalDragDelay;   }
        public String        getInventoryControl()   { return inventoryControl;   }
        public double        getRepairPriceWeight()  { return repairPriceWeight;  }
        public double        getUpgradePriceWeight() { return upgradePriceWeight; }
        @Override public String toString()           { return ammoName;           }

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
}