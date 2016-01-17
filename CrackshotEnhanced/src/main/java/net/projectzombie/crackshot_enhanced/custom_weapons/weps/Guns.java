/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.weps;

import net.projectzombie.crackshot_enhanced.custom_weapons.qualities.Condition;
import net.projectzombie.crackshot_enhanced.custom_weapons.qualities.Build;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Attatchments.Attatchment;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Barrels.Barrel;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Bolts.Bolt;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.FireModes.FireMode;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Magazines.Magazine;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Sights.Scope;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Stocks.Stock;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.BleedoutModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.BoltModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.BulletSpreadModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.CritModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.DamageModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.GunModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.MagazineModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.ProjectileModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.weps.WeaponTypes.Weapon;
import net.projectzombie.crackshot_enhanced.custom_weapons.utilities.CrackshotLore;
import net.projectzombie.crackshot_enhanced.custom_weapons.weps.GunSkeletons.GunSkeleton;
import org.bukkit.inventory.ItemStack;


/**
 *
 * @author jbannon
 */
public class Guns
{
    private static ArrayList<CrackshotGun> guns = null;
    
    private Guns() { /* Do nothing. */ }
    
    static
    public int initialize()
    {
        if (guns == null)
            guns = initializeGuns();
        return guns.size();
    }
    
    static
    private ArrayList<CrackshotGun> initializeGuns()
    {
        ArrayList<CrackshotGun> gunArray = new ArrayList<>();
        final GunSkeleton gunSkeletons[] = GunSkeletons.getInstance().getAll();
        CrackshotGun skeleGuns[];
        int id = 0;
        
        for (GunSkeleton skeleton : gunSkeletons)
        {
            skeleGuns = skeleton.getGuns(id);
            if (skeleGuns != null)
            {
                gunArray.addAll(Arrays.asList(skeleGuns));
                id += skeleGuns.length;
            }
        }
        return gunArray;
    }
    

    static
    public CrackshotGun get(final int gunID)
    {
        return (gunID >= 0 && gunID < guns.size()) ? guns.get(gunID) : null;
    }
    
    static
    public CrackshotGun get(final ItemStack item)
    {
        return get(CrackshotLore.getWeaponID(item));
    }
    
    static
    public ArrayList<CrackshotGun> getGuns()
    {
        return guns;
    }

    static public class CrackshotGun
    {   
        private static final Random rand = new Random();
        private static final double CROUCH_BULLET_SPREAD_MODIFIER = -0.05;
        private static final double BULLET_SPREAD_MODIFIER_CAP = 0.10;

        private final int gunIDOffset;
        private final int uniqueID;
        private final GunSkeleton skeleton;

        /* Craftable gun modifiers */
        private final Attatchment attatchment;
        private final Barrel barrel;
        private final Bolt bolt;
        private final FireMode firemodeType;
        private final Magazine magazine;
        private final Scope scopeType;
        private final Stock stock;

        private final String csWeaponName;

        public CrackshotGun(final int gunIDOffset,
                             final int uniqueID,
                             final GunSkeleton skeleton,
                             final Attatchment attatchment,
                             final Barrel barrel,
                             final Bolt bolt,
                             final FireMode firemodeType,
                             final Magazine magazine,
                             final Scope scopeType,
                             final Stock stock)
        {
            this.gunIDOffset = gunIDOffset;
            this.uniqueID = uniqueID;
            this.skeleton = skeleton;
            this.firemodeType = firemodeType;
            this.scopeType = scopeType;
            this.attatchment = attatchment;
            this.bolt = bolt;
            this.barrel = barrel;
            this.stock = stock;
            this.magazine = magazine;
            this.csWeaponName = String.valueOf(uniqueID) + "_" + skeleton.getFileName();
        }

        public GunSkeleton getSkeleton()         { return skeleton;                }
        public int          getIDOffset()         { return gunIDOffset;             }
        public int          getUniqueId()         { return uniqueID;                }
        public Weapon   getWeaponType()       { return skeleton.getWeaponType();}
        public FireMode    getFireMode()         { return firemodeType;            }
        public Scope       getScope()            { return scopeType;               }
        public Attatchment getAttatchment()      { return attatchment;             }
        public Barrel      getBarrel()           { return barrel;                  }
        public int          getItemID()           { return skeleton.getItemID();    }
        public int          getItemData()         { return skeleton.getItemData();  }
        public String       getCSWeaponName()     { return csWeaponName;            }
        public double       getInitBulletSpread() { return skeleton.getBulletSpread();  }
        public int          getMaxDurability()    { return skeleton.getMaxDurability(); }

        public GunModifier[] getCraftableModifiers()
        {
            return new GunModifier[]
            { 
                attatchment,
                barrel,
                bolt,
                firemodeType,
                magazine,
                scopeType,
                stock
            };
        }

        @Override public String toString()       { return csWeaponName;  }


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

        public int getRepairPrice(final ItemStack item)
        {
            final int durability = CrackshotLore.getDurability(item);
            final double buildWeight = (double)CrackshotLore.getBuild(item)/10.0;

            if (durability < 0 || buildWeight < 0)
                return -1;

            return (int)((double)(this.getMaxDurability() - durability) 
                    * this.skeleton.getWeaponType().getRepairPriceWeight()
                    / (1.0 - buildWeight));
        }

        public int getUpgradePrice(final ItemStack item)
        {
            final int build = CrackshotLore.getBuild(item);

            if (build < 0)
                return -1;
            else if (build == Build.ENHANCED.getEnumValue())
                return 0;
            else
                return (int)((build + 1) * this.getWeaponType().getUpgradePriceWeight());
        }

        public CrackshotGun getModifiedGun(final GunModifier modType)
        {
            return skeleton.getModifiedGun(this, modType);
        }

        /**
         * Sorts list of possible modifications by price, descending.
         * @return List of modifications sorted by price.
         */
        public String[] getModifiedList()
        {
            ArrayList<GunModifier> mods = this.getModifiedIDs();
            String toReturn[] = new String[mods.size()];

            for (int i = 0; i < mods.size(); i++)
                toReturn[i] = "$" + mods.get(i).price() + " - " + mods.get(i).toString();

            return toReturn;
        }

        private ArrayList<GunModifier> getModifiedIDs()
        {
            final ArrayList<GunModifier> mods = new ArrayList<>();
            for (GunModifier modifier : skeleton.getModifiers())
            {
                if (this.attatchment != modifier
                        && this.firemodeType != modifier
                        && this.scopeType != modifier)
                {
                    mods.add(modifier);
                }
            }
            return mods;
        }



        /**
         * @return Returns all BleedoutModifiers on the gun.
         */
        public ArrayList<BleedoutModifier> getBleedoutModifiers()
        {
            final ArrayList<BleedoutModifier> mods = new ArrayList<>();
            for (GunModifier mod : getCraftableModifiers())
            {
                if (mod instanceof BleedoutModifier)
                    mods.add((BleedoutModifier)mod);
            }
            return mods;
        }

        /**
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
         * @return Returns all DamageModifiers on the gun.
         */
        public ArrayList<DamageModifier> getDamageModifiers()
        {
            final ArrayList<DamageModifier> mods = new ArrayList<>();
            for (GunModifier mod : getCraftableModifiers())
            {
                if (mod instanceof DamageModifier)
                    mods.add((DamageModifier)mod);
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
        
    }
}
