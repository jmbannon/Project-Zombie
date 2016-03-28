/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.weps;

import net.projectzombie.crackshot_enhanced.custom_weapons.qualities.Condition;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Random;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Attachments.Attachment;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.ProjectileAttachments.ProjectileAttachment;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Barrels.Barrel;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Bolts.Bolt;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.FireModes.FireMode;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Magazines.Magazine;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Sights.Scope;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Stocks.Stock;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.projectile.BleedoutModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.skeleton.BoltModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.projectile.BulletSpreadModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.projectile.CritModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.projectile.DamageModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.GunModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.projectile.IgniteModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.projectile.IncendiaryModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.skeleton.MagazineModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.skeleton.ProjectileModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.projectile.ShrapnelModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.projectile.StunModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.skeleton.SkeletonTypes.SkeletonType;
import net.projectzombie.crackshot_enhanced.custom_weapons.utilities.CrackshotLore;
import net.projectzombie.crackshot_enhanced.custom_weapons.skeleton.GunSkeletons.GunSkeleton;
import static net.projectzombie.crackshot_enhanced.custom_weapons.utilities.Constants.TPS;
import org.bukkit.inventory.ItemStack;


/**
 *
 * @author jbannon
 */
public class Guns
{
    private static HashMap<String, CrackshotGun> guns = new HashMap<>();
    
    private Guns() { /* Do nothing. */ }
    
    static
    public int initialize()
    {
        return guns.size();
    }
    
    static
    public CrackshotGun get(final GunID gunID)
    {
        if (gunID.isValid())
        {
            if (guns.containsKey(gunID.getUniqueID()))
            {
                return guns.get(gunID.getUniqueID());
            }
            else
            {
                final CrackshotGun gun = new CrackshotGun(gunID);
                guns.put(gunID.getUniqueID(), gun);
                System.out.println("Total guns in hash map: " + guns.size());
                return gun;
            }
        }
        else
        {
            return null;
        }
    }
    
    static
    public CrackshotGun get(final String gunID)
    {
        return get(new GunID(gunID));
    }
    
    static
    public CrackshotGun get(final ItemStack item)
    {
        return get(new GunID(CrackshotLore.getWeaponID(item)));
    }
    
    static
    public Collection<CrackshotGun> getGuns()
    {
        return guns.values();
    }

    static public class CrackshotGun extends GunSkeleton
    {   
        private static final Random rand = new Random();
        
        private final String uniqueID;
        private final String csUniqueID;
        
        private final GunModifier[] modifiers;
        private final GunBaseDamage baseDamage;
        private final GunBleedout bleedout;
        private final GunShrapnelDamage shrapnel;
        

        public CrackshotGun(final GunSkeleton skeleton,
                             final Attachment attachmentOne,
                             final Attachment attachmentTwo,
                             final Attachment attachmentThree,
                             final Barrel barrel,
                             final Bolt bolt,
                             final FireMode firemodeType,
                             final Magazine magazine,
                             final Scope scopeType,
                             final Stock stock)
        {
            super(skeleton);
            final GunID id = new GunID(skeleton, attachmentOne, attachmentTwo, attachmentThree, barrel, bolt, firemodeType, magazine, scopeType, stock);
            
            this.modifiers = new GunModifier[]
            { 
                attachmentOne,
                attachmentTwo,
                attachmentThree,
                barrel,
                bolt,
                firemodeType,
                magazine,
                scopeType,
                stock
            };
            
            this.uniqueID = id.getUniqueID();
            this.csUniqueID = skeleton.getFileName() + "_" + id.getCSUniqueID();
            
            this.baseDamage = new GunBaseDamage(this.modifiers, skeleton.getSkeletonBaseDamage());
            this.shrapnel = new GunShrapnelDamage(this.modifiers);
            this.bleedout = new GunBleedout(this.modifiers, shrapnel.getTotal());
        }
        
        private CrackshotGun(final GunID id)
        {
            super(id.getSkeleton());
            this.modifiers = new GunModifier[]
            { 
                id.getAttatchmentOne(),
                id.getAttatchmentTwo(),
                id.getAttatchmentThree(),
                id.getBolt(),
                id.getBarrel(),
                id.getFireMode(),
                id.getMagazine(),
                id.getScope(),
                id.getStock()
            };
            
            this.uniqueID = id.getUniqueID();
            this.csUniqueID = super.getFileName() + "_" + id.getCSUniqueID();
            this.baseDamage = new GunBaseDamage(modifiers, super.getSkeletonBaseDamage());
        }

        public Attachment   getAttachmentOne()    { return (Attachment) modifiers[0]; }
        public Attachment   getAttachmentTwo()    { return (Attachment) modifiers[1]; }
        public Attachment   getAttachmentThree()  { return (Attachment) modifiers[2]; }
        public Bolt         getBolt()             { return (Bolt) modifiers[3];       }
        public Barrel       getBarrel()           { return (Barrel) modifiers[4];     }
        public FireMode     getFireMode()         { return (FireMode) modifiers[5];   }
        public Magazine     getMagazine()         { return (Magazine) modifiers[6];   }
        public Scope        getScope()            { return (Scope) modifiers[7];      }
        public Stock        getStock()            { return (Stock) modifiers[8];      }
        public String       getCSWeaponName()     { return csUniqueID;                }
        public String       getUniqueID()         { return uniqueID;                  }
        public double       getInitBulletSpread() { return skeleton.getBulletSpread();  }
        
        public GunBaseDamage getBaseDamage()      { return baseDamage;                  }
        @Override public String toString()        { return uniqueID;  }
        
        public double getDamageOnEntityHit(final boolean headshot)
        {
            final double dice = rand.nextDouble();
            
            double totalDamage = getBaseDamage()
                    + getShrapnelDamage()
                    + getFireDamage();
            
            if (dice < getCritChance())
            {
                totalDamage += getCritStrike();
            }
            if (headshot)
            {
                totalDamage += getHeadShotDamage();
            }
            return totalDamage;
        }
        
        public double getDPS()
        {
            double totalDamage = getBaseDamage()
                    + getShrapnelDamage()
                    + getFireDamage()
                    + (Math.min(getCritChance(), 1.0) * getCritStrike())
                    + (getBleedoutDamagePerSecond() * getBleedoutDurationInSeconds());
            
            return totalDamage;
        }

        
        /**
         * Initializes value with 0.
         * Initializes multiplier with 1.0.
         * Sums all headshot damage mod values and adds it to value.
         * Sums all headshot damage mod multiplier and adds it to multiplier.
         * @return baseDamageValues * baseDamageMultiplier.
         */
        public double getHeadShotDamage()
        {
            double headshotDamageValue = 0;
            double headshotDamageMultiplier = 1.0;
            
            for (DamageModifier mod : getDamageModifiers())
            {
                headshotDamageValue += mod.getHeadshotDamageModifier();
                headshotDamageMultiplier += mod.getHeadshotDamageMultiplier();
            }
            return nonNegative(headshotDamageValue * headshotDamageMultiplier);
        }

        public double getFireDamage()
        {
            double fireDamageValue = 0;
            double fireDamageMultiplier = 1.0;
            
            for (IncendiaryModifier mod : getIncendiaryModifiers())
            {
                fireDamageValue += mod.getFireDamageValue();
                fireDamageMultiplier += mod.getFireDamageMultiplier();
            }
            return nonNegative(fireDamageValue * fireDamageMultiplier);
        }

        
        public double getCritChance()
        {
            double critChance = 0;
            for (CritModifier mod : getCritModifiers())
            {
                critChance += mod.getCritChance();
            }
            return nonNegative(critChance);
        }
        
        /**
         * Calculates critical strike based off base damage.
         * @return 
         */
        public double getCritStrike()
        {
            final double baseDamage = getBaseDamage();
            double critStrike = 0;
            
            for (CritModifier mod : getCritModifiers())
            {
                critStrike += mod.getCritStrike();
            }
            return nonNegative(critStrike * baseDamage);
        }
        
        public double getStunChance()
        {
            double stunChance = 0;
            for (StunModifier mod : getStunModifiers())
            {
                stunChance += mod.getStunChance();
            }
            return nonNegative(stunChance);
        }
        
        public double getStunDurationInTicks()
        {
            double stunDuration = 0;
            for (StunModifier mod : getStunModifiers())
            {
                stunDuration += mod.getStunDuration();
            }
            return nonNegative(stunDuration * TPS);
        }
        
        public double getStandingBulletSpread()
        {
            final double skeletonBulletSpread = skeleton.getBulletSpread();
            double bulletSpreadMultiplier = 1.0;
            
            for (BulletSpreadModifier mod : getBulletSpreadModifiers())
            {
                bulletSpreadMultiplier += mod.getBulletSpreadMultiplier();
            }
            
            return nonNegative(skeletonBulletSpread * bulletSpreadMultiplier);
        }
        
        public double getZoomedBulletSpread()
        {
            return nonNegative(getStandingBulletSpread()
                    * (1.0 + getScope().getZoomBulletSpreadMultiplier()));
        }
        
        public double getRunningBulletSpread()
        {
            return nonNegative(getStandingBulletSpread()
                    * (1.0 + getStock().getRunningBulletSpreadMultiplier()));
        }

        private double nonNegative(final double number)
        {
            if (number < 0)
                return 0;
            else
                return number;
        }

        public int getInitialDurability()
        {
            final int maxDurability = this.getMaxDurability();
            return maxDurability - rand.nextInt(maxDurability/2);
        }

        /**
         * Returns the integer of the gun condition.
         * @param durability Current durability of the gun.
         * @return Gun tier integer.
         */
        public int getConditionInt(final int durability)
        {
            if (durability == 0)
                return Condition.BROKEN.getEnumValue();

            final double ratio = (double)durability / (double)this.getMaxDurability();
            for (int i = 0; i <= Condition.TIERS; i++)
                if (Double.compare(ratio, (double)i/(double)Condition.TIERS) <= 0) return i;

            return Condition.TIERS;
        }

        public CrackshotGun getModifiedGun(final GunModifier modifier,
                                           final GunModifier.GunModifierType type)
        {
            final GunID modifierGunID = new GunID(this, modifier, type);
            return Guns.get(modifierGunID);
        }


        /**a
         * @return Returns all BoltModifiers on the gun.
         */
        public ArrayList<BoltModifier> getBoltModifiers()
        {
            final ArrayList<BoltModifier> mods = new ArrayList<>();
            for (GunModifier mod : getCraftableModifiers())
            {
                if (mod instanceof BoltModifier)
                    mods.add((BoltModifier)mod);
            }
            return mods;
        }

        /**
         * @return Returns all BulletSpreadModifiers on the gun.
         */
        public ArrayList<BulletSpreadModifier> getBulletSpreadModifiers()
        {
            final ArrayList<BulletSpreadModifier> mods = new ArrayList<>();
            for (GunModifier mod : getCraftableModifiers())
            {
                if (mod instanceof BulletSpreadModifier)
                    mods.add((BulletSpreadModifier)mod);
            }
            return mods;
        }

        /**
         * @return Returns all CritModifiers on the gun.
         */
        public ArrayList<CritModifier> getCritModifiers()
        {
            final ArrayList<CritModifier> mods = new ArrayList<>();
            for (GunModifier mod : getCraftableModifiers())
            {
                if (mod instanceof CritModifier)
                    mods.add((CritModifier)mod);
            }
            return mods;
        }

        /**
         * @return Returns all IncendiaryModifiers on the gun.
         */
        public ArrayList<IncendiaryModifier> getIncendiaryModifiers()
        {
            final ArrayList<IncendiaryModifier> mods = new ArrayList<>();
            for (GunModifier mod : getCraftableModifiers())
            {
                if (mod instanceof IncendiaryModifier)
                    mods.add((IncendiaryModifier)mod);
            }
            return mods;
        }
        
        /**
         * @return Returns all IgniteModifiers on the gun.
         */
        public ArrayList<IgniteModifier> getIgniteModifiers()
        {
            final ArrayList<IgniteModifier> mods = new ArrayList<>();
            for (GunModifier mod : getCraftableModifiers())
            {
                if (mod instanceof IgniteModifier)
                    mods.add((IgniteModifier)mod);
            }
            return mods;
        }

        /**
         * @return Returns all MagazineModifiers on the gun.
         */
        public ArrayList<MagazineModifier> getMagazineModifiers()
        {
            final ArrayList<MagazineModifier> mods = new ArrayList<>();
            for (GunModifier mod : getCraftableModifiers())
            {
                if (mod instanceof MagazineModifier)
                    mods.add((MagazineModifier)mod);
            }
            return mods;
        }

        /**
         * @return Returns all ProjectileAmountModifiers on the gun.
         */
        public ArrayList<ProjectileModifier> getProjectileAmountModifiers()
        {
            final ArrayList<ProjectileModifier> mods = new ArrayList<>();
            for (GunModifier mod : getCraftableModifiers())
            {
                if (mod instanceof ProjectileModifier)
                    mods.add((ProjectileModifier)mod);
            }
            return mods;
        }
        
        /**
         * @return Returns all StunModifiers on the gun.
         */
        public ArrayList<StunModifier> getStunModifiers()
        {
            final ArrayList<StunModifier> mods = new ArrayList<>();
            for (GunModifier mod : getCraftableModifiers())
            {
                if (mod instanceof StunModifier)
                    mods.add((StunModifier)mod);
            }
            return mods;
        }
    }
}
