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
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Barrels.Barrel;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Bolts.Bolt;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.FireModes.FireMode;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Magazines.Magazine;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Sights.Scope;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Stocks.Stock;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.skeleton.BoltModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.projectile.BulletSpreadModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.GunModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.utilities.CrackshotLore;
import net.projectzombie.crackshot_enhanced.custom_weapons.skeleton.GunSkeletons.GunSkeleton;
import org.bukkit.inventory.ItemStack;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.skeleton.ProjectileModifier;


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
        private final BaseDamage baseDamage;
        private final GunHeadshotDamage headshot;
        private final Bleedout bleedout;
        private final GunShrapnelDamage shrapnel;
        private final GunFireDamage fireDamage;
        private final GunCrits crit;
        private final GunMagazine mag;
        

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
            
            this.baseDamage = new BaseDamage(this.modifiers, skeleton.getSkeletonBaseDamage());
            this.headshot = new GunHeadshotDamage(this.modifiers);
            this.shrapnel = new GunShrapnelDamage(this.modifiers);
            this.fireDamage = new GunFireDamage(this.modifiers);
            this.bleedout = new Bleedout(this.modifiers, this.shrapnel.getTotal());
            this.crit = new GunCrits(this.modifiers, this.baseDamage.getTotal());
            this.mag = new GunMagazine(this.modifiers, super.getReloadAmount(), super.getReloadDuration());
            
        }
        
        public CrackshotGun(final CrackshotGun gun)
        {
            this(gun,
                 gun.getAttachmentOne(),
                 gun.getAttachmentTwo(),
                 gun.getAttachmentThree(),
                 gun.getBarrel(),
                 gun.getBolt(),
                 gun.getFireMode(),
                 gun.getMagazine(),
                 gun.getScope(),
                 gun.getStock());
        }
        
        private CrackshotGun(final GunID id)
        {
            this(id.getSkeleton(),
                 id.getAttatchmentOne(),
                 id.getAttatchmentTwo(),
                 id.getAttatchmentThree(),
                 id.getBarrel(),
                 id.getBolt(),
                 id.getFireMode(),
                 id.getMagazine(),
                 id.getScope(),
                 id.getStock());
        }

        public Attachment   getAttachmentOne()    { return (Attachment) modifiers[0]; }
        public Attachment   getAttachmentTwo()    { return (Attachment) modifiers[1]; }
        public Attachment   getAttachmentThree()  { return (Attachment) modifiers[2]; }
        public Bolt         getBolt()             { return (Bolt)       modifiers[3]; }
        public Barrel       getBarrel()           { return (Barrel)     modifiers[4]; }
        public FireMode     getFireMode()         { return (FireMode)   modifiers[5]; }
        public Magazine     getMagazine()         { return (Magazine)   modifiers[6]; }
        public Scope        getScope()            { return (Scope)      modifiers[7]; }
        public Stock        getStock()            { return (Stock)      modifiers[8]; }
        public String       getCSWeaponName()     { return csUniqueID;                }
        public String       getUniqueID()         { return uniqueID;                  }
        
        public BaseDamage getBaseDamage()          { return baseDamage;    }
        public GunHeadshotDamage getHeadshotDamage()  { return headshot;      }
        public Bleedout getBleedout()              { return bleedout;      }
        public GunCrits getCritical()                 { return crit;          }
        public GunShrapnelDamage getShrapnel()        { return shrapnel;      }
        public GunFireDamage getFireDamage()          { return fireDamage;    }
        public GunMagazine getGunMagazine()           { return mag;           }
        
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
            return Guns.get(new GunID(this, modifier, type));
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
    }
}
