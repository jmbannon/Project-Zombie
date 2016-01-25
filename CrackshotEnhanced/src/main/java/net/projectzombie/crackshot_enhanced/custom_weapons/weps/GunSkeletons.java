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
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.GunModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Attatchments.Attatchment;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Barrels.Barrel;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Bolts.Bolt;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.FireModes.FireMode;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Magazines.Magazine;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.ModifierSets;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.ModifierSets.ModifierSet;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Sights.Scope;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Stocks.Stock;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.GunModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.weps.WeaponTypes.Weapon;
import net.projectzombie.crackshot_enhanced.custom_weapons.weps.GunSkeletons.GunSkeleton;
import net.projectzombie.crackshot_enhanced.custom_weapons.weps.Guns.CrackshotGun;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

/**
 *
 * @author jbannon
 * Contains standard Crackshot guns with no modifiers (i.e. scopes, 
 * 
 */
public class GunSkeletons extends CSVInput<GunSkeleton>
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
        "Modifier Set (STR)",
        "Material ID (INT)",
        "Material Data (INT)",
        "Initial Bullet Spread (DBL)",
        "Delay Between Shots (INT)",
        "Max Durability (INT)",
        "Damage (DBL)",
        "Reload Amount (INT)",
        "Reload Duration (INT)",
        "Reload Bullets Individually (T/F)",
        "Recoil Amount (INT)",
        "Shoot Sound (STR)",
        "Silenced Sound (STR)",
        "Particle Shoot (STR)",
        "Reload Sound (STR)",
    };
    
    public GunSkeletons()
    {
        super(SKELETONS_CSV_NAME, buildSkeletons(), SKELETON_VALUES);
    }
    
    static private GunSkeleton[] buildSkeletons()
    {
        final CSVReader csv = new CSVReader(SKELETONS_CSV_NAME, SKELETON_VALUES);
        final int rowCount = csv.getRowCount();
        
        if (rowCount <= 0)
        {
            return null;
        }
        
        int j = 0;
        final GunSkeleton[] toReturn       = new GunSkeleton[rowCount];
        final String[] skeletonNames        = csv.getColumnString(j++);
        final ArrayList<Weapon> wepTypes = WeaponTypes.getInstance().get(csv.getColumnString(j++), false);
        final ArrayList<ModifierSet> modifierSets = ModifierSets.getInstance().get(csv.getColumnString(j++), false);
        final int[]    materialIDs          = csv.getColumnInt(j++);
        final int[]    materialData         = csv.getColumnInt(j++);
        final double[] initialBulletSpreads = csv.getColumnDouble(j++);
        final int[]    delayBetweenShots    = csv.getColumnInt(j++);
        final int[]    maxDurabilities      = csv.getColumnInt(j++);
        final double[] damages              = csv.getColumnDouble(j++);
        final int[]    reloadAmounts        = csv.getColumnInt(j++);
        final int[]    reloadDurations      = csv.getColumnInt(j++);
        final boolean[] reloadIndividually  = csv.getColumnBoolean(j++);
        final int[]    recoilAmounts        = csv.getColumnInt(j++);
        
        final String[] shootSounds          = csv.getColumnString(j++);
        final String[] silencedSounds       = csv.getColumnString(j++);
        final String[] reloadSounds         = csv.getColumnString(j++);
        final String[] particleShoots       = csv.getColumnString(j++);
        
        if (modifierSets.isEmpty() || wepTypes.isEmpty())
            return null;
        
        for (int i = 0; i < rowCount; i++)
        {
            toReturn[i] = new GunSkeleton(
                    skeletonNames[i],
                    wepTypes.get(i),
                    modifierSets.get(i),
                    materialIDs[i],
                    materialData[i],
                    initialBulletSpreads[i],
                    delayBetweenShots[i],
                    maxDurabilities[i],
                    damages[i],
                    reloadAmounts[i],
                    reloadDurations[i],
                    reloadIndividually[i],
                    recoilAmounts[i],
                    shootSounds[i],
                    silencedSounds[i],
                    particleShoots[i],
                    reloadSounds[i]);
        }
        return toReturn;
    }
    
    static public class GunSkeleton extends CSVValue
    {   
        private final Weapon weaponType;
        private final double bulletSpread;
        private final double damage;
        private final ModifierSet modSet;
        private final String particleShoot;
        private final boolean reloadBulletsIndividually;
        
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

        private GunSkeleton(final String skeletonName,
                            final Weapon weaponType,
                            final ModifierSet set,
                            final int materialID,
                            final int materialData,
                            final double initialBulletSpread,
                            final int delay_between_shots,
                            final int maxDurability,
                            final double damage,
                            final int reload_amount,
                            final int reload_duration,
                            final boolean reloadBulletsIndividually,
                            final int recoilAmount,
                            
                            final String sounds_shoot,
                            final String sounds_silenced,
                            final String particle_shoot,
                            final String sounds_reloading)
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
            this.reloadBulletsIndividually = reloadBulletsIndividually;
        }

        public String        getFileName()       { return super.getName().toLowerCase(); }
        public Weapon        getWeaponType()     { return weaponType;     }
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
        public ModifierSet   getModifierSet()    { return modSet; }
        public boolean       reloadsBulletsIndividually() { return reloadBulletsIndividually; }
        public GunModifier[] getModifiers()      { return modSet.getModifiers(); }

        public CrackshotGun[] getGuns(final int uniqueIDOffset)
        {
            final int combinationCount = modSet.getCombinationCount();
            int i = 0;

            if (combinationCount <= 0)
                return null;

            CrackshotGun guns[] = new CrackshotGun[combinationCount];

            for (Attatchment attatchmentOne : modSet.getSlot1Attatchments())
            {
                for (Attatchment attatchmentTwo : modSet.getSlot2Attatchments())
                {
                    for (Attatchment attatchmentThree : modSet.getSlot3Attatchments())
                    {
                        for (Barrel barrel : modSet.getBarrels())
                        {
                            for (Bolt bolt : modSet.getBolts())
                            {
                                for (FireMode fireMode : modSet.getFireModes())
                                {
                                    for (Magazine magazine : modSet.getMagazines())
                                    {
                                        for (Scope scope : modSet.getScopes())
                                        {
                                            for (Stock stock : modSet.getStocks())
                                            {
                                                 guns[i] = new CrackshotGun(uniqueIDOffset, uniqueIDOffset + i, this, attatchmentOne, attatchmentTwo, attatchmentThree, barrel, bolt, fireMode, magazine, scope, stock);
                                                 ++i;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return guns;
        }

        public CrackshotGun getModifiedGun(final CrackshotGun gun,
                                            final GunModifier modifier)
        {
            final int gunIDOffset = gun.getIDOffset();
            final int gunCombinationCount = gun.getSkeleton().getModifierSet().getCombinationCount();
            boolean containsModifier = false;

            GunModifier[] modifiedSet = gun.getCraftableModifiers();
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
                    return Guns.get(i);
            }

            return null;
        }


        private boolean containsMod(final GunModifier modifier)
        {
            boolean containsMod = false;
            for (GunModifier mod : getModifiers())
            {
                if (mod.equals(modifier))
                    containsMod = true;
            }

            return containsMod;
        }
    }
}
