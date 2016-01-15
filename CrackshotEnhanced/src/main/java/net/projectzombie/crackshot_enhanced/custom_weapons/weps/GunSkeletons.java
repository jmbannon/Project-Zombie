/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.weps;

import java.util.ArrayList;
import java.util.Arrays;
import net.projectzombie.crackshot_enhanced.custom_weapons.csv.CSVInput;
import net.projectzombie.crackshot_enhanced.custom_weapons.csv.CSVReader;
import net.projectzombie.crackshot_enhanced.custom_weapons.csv.CSVValue;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.GunModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Attatchments.Attatchment2;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Barrels.Barrel2;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Bolts.Bolt2;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.FireModes.FireMode2;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Magazines.Magazine2;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.ModifierSets;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.ModifierSets.ModifierSet2;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Sights.Scope2;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Stocks.Stock2;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.GunModifier2;
import net.projectzombie.crackshot_enhanced.custom_weapons.types.WeaponTypes;
import net.projectzombie.crackshot_enhanced.custom_weapons.types.WeaponTypes.WeaponType;
import net.projectzombie.crackshot_enhanced.custom_weapons.weps.GunSkeletons.GunSkeleton2;
import net.projectzombie.crackshot_enhanced.custom_weapons.weps.Guns2.CrackshotGun2;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

/**
 *
 * @author jbannon
 * Contains standard Crackshot guns with no modifiers (i.e. scopes, 
 * 
 */
public class GunSkeletons extends CSVInput<GunSkeleton2>
{
    static private GunSkeletons singleton = null;
    
    static public GunSkeletons getInstance()
    {
        if (singleton == null)
            singleton = new GunSkeletons();
        return singleton;
    }
    
    static private final String SKELETONS_CSV_NAME = "GunSkeletons.csv";
    static private final String[] SKELETON_VALUES = {
        "Skeleton Name (STR)",
        "Weapon Type (STR)",
        "Material ID (INT)",
        "Material Data (INT)",
        "Initial Bullet Spread (DBL)",
        "Delay Between Shots (INT)",
        "Max Durability (INT)",
        "Damage (DBL)",
        "Recoil Amount (INT)",
        "Shoot Sound (STR)",
        "Silenced Sound (STR)",
        "Particle Shoot (STR)",
        "Reload Amount (INT)",
        "Reload Duration (INT)",
        "Reload Sound (STR)",
        "Modifier Set (STR)"
    };
    
    public GunSkeletons()
    {
        super(SKELETONS_CSV_NAME, buildSkeletons(), SKELETON_VALUES);
    }
    
    static private GunSkeleton2[] buildSkeletons()
    {
        final CSVReader csv = new CSVReader(SKELETONS_CSV_NAME, SKELETON_VALUES);
        final int rowCount = csv.getRowCount();
        
        if (rowCount <= 0)
        {
            return null;
        }
        
        int j = 0;
        final GunSkeleton2[] toReturn       = new GunSkeleton2[rowCount];
        final String[] skeletonNames        = csv.getColumnString(j++);
        final ArrayList<WeaponType> wepTypes = WeaponTypes.getInstance().get(csv.getColumnString(j++));
        final int[]    materialIDs          = csv.getColumnInt(j++);
        final int[]    materialData         = csv.getColumnInt(j++);
        final double[] initialBulletSpreads = csv.getColumnDouble(j++);
        final int[]    delayBetweenShots    = csv.getColumnInt(j++);
        final int[]    maxDurabilities      = csv.getColumnInt(j++);
        final double[] damages              = csv.getColumnDouble(j++);
        final int[]    recoilAmounts        = csv.getColumnInt(j++);
        final String[] shootSounds          = csv.getColumnString(j++);
        final String[] silencedSounds       = csv.getColumnString(j++);
        final String[] particleShoots       = csv.getColumnString(j++);
        final int[]    reloadAmounts        = csv.getColumnInt(j++);
        final int[]    reloadDurations      = csv.getColumnInt(j++);
        final String[] reloadSounds         = csv.getColumnString(j++);
        final ArrayList<ModifierSet2> modifierSets = ModifierSets.getInstance().get(csv.getColumnString(j++));
        
        for (int i = 0; i < rowCount; i++)
        {
            toReturn[i] = new GunSkeleton2(
                    skeletonNames[i],
                    wepTypes.get(i),
                    materialIDs[i],
                    materialData[i],
                    initialBulletSpreads[i],
                    delayBetweenShots[i],
                    maxDurabilities[i],
                    damages[i], 
                    recoilAmounts[i],
                    shootSounds[i],
                    silencedSounds[i],
                    particleShoots[i],
                    reloadAmounts[i],
                    reloadDurations[i],
                    reloadSounds[i],
                    modifierSets.get(i));
        }
        System.out.println("Initialized " + rowCount + " gun skeletons.");
        return toReturn;
    }
    
    static public class GunSkeleton2 extends CSVValue
    {   
        private final WeaponType weaponType;
        private final double bulletSpread;
        private final double damage;
        private final ModifierSet2 modSet;
        private final String particleShoot;

        private final int
                itemID, 
                itemData, 
                shootDelay, 
                maxDurability,  
                recoilAmount, 
                reloadAmount, 
                reloadDuration;

        private final String
                soundShoot,
                soundSilenced,
                soundReload;

        private GunSkeleton2(final String skeletonName,
                            final WeaponType weaponType,
                            final int materialID,
                            final int materialData,
                            final double initialBulletSpread,
                            final int delay_between_shots,
                            final int maxDurability,
                            final double damage,
                            final int recoilAmount,

                            final String sounds_shoot,
                            final String sounds_silenced,
                            final String particle_shoot,

                            final int reload_amount,
                            final int reload_duration,
                            final String sounds_reloading,
                            final ModifierSet2 set)
        {
            super(skeletonName);
            this.weaponType = weaponType;
            this.itemID = materialID;
            this.itemData = materialData;
            this.shootDelay = delay_between_shots;
            this.maxDurability = maxDurability;
            this.bulletSpread = initialBulletSpread;
            this.damage = damage;
            this.recoilAmount = recoilAmount;    
            this.soundShoot = sounds_shoot;
            this.soundSilenced = sounds_silenced;
            this.particleShoot = particle_shoot;
            this.reloadAmount = reload_amount;
            this.reloadDuration = reload_duration;
            this.soundReload = sounds_reloading;
            this.modSet = set;
        }

        public String        getFileName()       { return super.getName().toLowerCase(); }
        public WeaponType    getWeaponType()     { return weaponType;     }
        public double        getBulletSpread()   { return bulletSpread;   }
        public int           getItemID()         { return itemID;         }
        public int           getItemData()       { return itemData;       }
        public Material      getMaterial()       { return Material.getMaterial(itemID); }
        public MaterialData  getMaterialData()   { return new MaterialData(itemID, (byte)itemData); }
        public int           getShootDelay()     { return shootDelay;     }
        public int           getMaxDurability()  { return maxDurability;  }
        public double        getDamage()         { return damage;         }
        public int           getRecoil()         { return recoilAmount;   }
        public String        getShootSound()     { return soundShoot;    }
        public String        getSilencedSound()  { return soundSilenced; }
        public String        getShootParticle()  { return particleShoot; }
        public int           getReloadAmount()   { return reloadAmount;   }
        public int           getReloadDuration() { return reloadDuration; }
        public String        getReloadSound()    { return soundReload;   }
        public ItemStack     getBareItemStack()  { return new ItemStack(itemID, 1, (short)itemData); }
        public ModifierSet2   getModifierSet()    { return modSet; }
        public GunModifier2[] getModifiers()      { return modSet.getModifiers(); }

        public CrackshotGun2[] getGuns(final int uniqueIDOffset)
        {
            final int combinationCount = modSet.getCombinationCount();
            int i = 0;

            if (combinationCount <= 0)
                return null;

            CrackshotGun2 guns[] = new CrackshotGun2[combinationCount];

            for (Attatchment2 attatchment : modSet.getAttatchments())
            {
                for (Barrel2 barrel : modSet.getBarrels())
                {
                    for (Bolt2 bolt : modSet.getBolts())
                    {
                        for (FireMode2 fireMode : modSet.getFireModes())
                        {
                            for (Magazine2 magazine : modSet.getMagazines())
                            {
                                for (Scope2 scope : modSet.getScopes())
                                {
                                    for (Stock2 stock : modSet.getStocks())
                                    {
                                         guns[i] = new CrackshotGun2(uniqueIDOffset, uniqueIDOffset + i, this, attatchment, barrel, bolt, fireMode, magazine, scope, stock);
                                         ++i;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return guns;
        }

        public CrackshotGun2 getModifiedGun(final CrackshotGun2 gun,
                                            final GunModifier2 modifier)
        {
            final int gunIDOffset = gun.getIDOffset();
            final int gunCombinationCount = gun.getSkeleton().getModifierSet().getCombinationCount();
            boolean containsModifier = false;

            GunModifier2[] modifiedSet = gun.getCraftableModifiers();
            for (int i = 0; i < modifiedSet.length; i++)
            {
                if (modifiedSet[i].getClass() == modifier.getClass())
                {
                    modifiedSet[i] = modifier;
                    containsModifier = true;
                    break;
                }
            }

            if (!containsModifier)
                return null;

            for (int i = gunIDOffset; i < gunIDOffset + gunCombinationCount; i++)
            {
                if (Arrays.equals(Guns.get(i).getCraftableModifiers(), modifiedSet))
                    return Guns2.get(i);
            }

            return null;
        }


        private boolean containsMod(final GunModifier modifier)
        {
            boolean containsMod = false;
            for (GunModifier2 mod : getModifiers())
            {
                if (mod.equals(modifier))
                    containsMod = true;
            }

            return containsMod;
        }
    }
}
