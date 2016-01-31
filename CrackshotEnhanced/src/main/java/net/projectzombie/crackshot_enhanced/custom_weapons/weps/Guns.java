/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.weps;

import net.projectzombie.crackshot_enhanced.custom_weapons.qualities.Condition;
import net.projectzombie.crackshot_enhanced.custom_weapons.qualities.Build;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
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
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.GunModifier;
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
    private static HashMap<String, CrackshotGun> guns = new HashMap<>();
    
    private Guns() { /* Do nothing. */ }
    
    static
    public int initialize()
    {
        return guns.size();
    }
    
//    static
//    private HashMap<String, CrackshotGun> initializeGuns()
//    {
//        HashMap<String, CrackshotGun> gunMap = new HashMap<>();
//        final GunSkeleton gunSkeletons[] = GunSkeletons.getInstance().getAll();
//        CrackshotGun skeleGuns[];
//        int id = 0;
//        
//        for (GunSkeleton skeleton : gunSkeletons)
//        {
//            for (CrackshotGun skeleGun : skeleton.getGunBaseSet())
//            {
//                gunMap.put(skeleGun.uniqueID, skeleGun);
//            }
//        }
//        return gunMap;
//    }
    

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

    static public class CrackshotGun
    {   
        private static final Random rand = new Random();
        private static final double CROUCH_BULLET_SPREAD_MODIFIER = -0.05;
        private static final double BULLET_SPREAD_MODIFIER_CAP = 0.10;
        
        private final String uniqueID;
        private final String csUniqueID;
        
        private final GunSkeleton skeleton;

        /* Craftable gun modifiers */
        private final Attatchment attatchmentOne;
        private final Attatchment attatchmentTwo;
        private final Attatchment attatchmentThree;
        private final Barrel barrel;
        private final Bolt bolt;
        private final FireMode firemodeType;
        private final Magazine magazine;
        private final Scope scopeType;
        private final Stock stock;
        

        public CrackshotGun(final GunSkeleton skeleton,
                             final Attatchment attatchmentOne,
                             final Attatchment attatchmentTwo,
                             final Attatchment attatchmentThree,
                             final Barrel barrel,
                             final Bolt bolt,
                             final FireMode firemodeType,
                             final Magazine magazine,
                             final Scope scopeType,
                             final Stock stock)
        {
            final GunID id = new GunID(skeleton, attatchmentOne, attatchmentTwo, attatchmentThree, barrel, bolt, firemodeType, magazine, scopeType, stock);
            this.skeleton = skeleton;
            this.firemodeType = firemodeType;
            this.scopeType = scopeType;
            this.attatchmentOne = attatchmentOne;
            this.attatchmentTwo = attatchmentTwo;
            this.attatchmentThree = attatchmentThree;
            this.bolt = bolt;
            this.barrel = barrel;
            this.stock = stock;
            this.magazine = magazine;
            
            this.uniqueID = id.getUniqueID();
            this.csUniqueID = skeleton.getFileName() + "_" + id.getCSUniqueID();
        }
        
        private CrackshotGun(final GunID id)
        {
            this.skeleton = id.getSkeleton();
            this.attatchmentOne = id.getAttatchmentOne();
            this.attatchmentTwo = id.getAttatchmentTwo();
            this.attatchmentThree = id.getAttatchmentThree();
            this.bolt = id.getBolt();
            this.barrel = id.getBarrel();
            this.firemodeType = id.getFireMode();
            this.magazine = id.getMagazine();
            this.scopeType = id.getScope();
            this.stock = id.getStock();
            
            this.uniqueID = id.getUniqueID();
            this.csUniqueID = skeleton.getFileName() + "_" + id.getCSUniqueID();
        }

        public GunSkeleton  getSkeleton()         { return skeleton;                }
        public Weapon       getWeaponType()       { return skeleton.getWeaponType();}
        public FireMode     getFireMode()         { return firemodeType;            }
        public Scope        getScope()            { return scopeType;               }
        public Attatchment  getAttatchmentOne()   { return attatchmentOne;          }
        public Attatchment  getAttatchmentTwo()   { return attatchmentTwo;          }
        public Attatchment  getAttatchmentThree() { return attatchmentThree;        }
        public Barrel       getBarrel()           { return barrel;                  }
        public Bolt         getBolt()             { return bolt;                    }
        public Magazine     getMagazine()         { return magazine;                }
        public Stock        getStock()            { return stock;                   }
        public int          getItemID()           { return skeleton.getItemID();    }
        public int          getItemData()         { return skeleton.getItemData();  }
        public String       getCSWeaponName()     { return csUniqueID;            }
        public String       getUniqueID()         { return uniqueID;               }
        public double       getInitBulletSpread() { return skeleton.getBulletSpread();  }
        public int          getMaxDurability()    { return skeleton.getMaxDurability(); }
        
        public GunModifier[] getCraftableModifiers()
        {
            return new GunModifier[]
            { 
                attatchmentOne,
                attatchmentTwo,
                attatchmentThree,
                barrel,
                bolt,
                firemodeType,
                magazine,
                scopeType,
                stock
            };
        }

        @Override public String toString()       { return csUniqueID;  }


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
        
        public ArrayList<String> buildModifierLore()
        {
            final ArrayList<ArrayList<String>> attatchmentStats = new ArrayList<>();
            final ArrayList<String> loreToReturn = new ArrayList<>();
            ArrayList<String> attatchmentStat;
            for (GunModifier mod : getCraftableModifiers())
            {
                if (!mod.isNull())
                {
                    attatchmentStat = mod.getLore();
                    if (attatchmentStat != null && !attatchmentStat.isEmpty())
                        attatchmentStats.add(attatchmentStat);
                }
            }

            Collections.shuffle(attatchmentStats);
            for (ArrayList<String> temp : attatchmentStats)
            {
                loreToReturn.addAll(temp);
            }

            return loreToReturn;
        }

        public CrackshotGun getModifiedGun(final GunModifier modifier,
                                           final GunModifier.GunModifierType type)
        {
            final GunID modifierGunID = new GunID(this, modifier, type);
            return Guns.get(modifierGunID);
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
                if (this.attatchmentOne != modifier
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
