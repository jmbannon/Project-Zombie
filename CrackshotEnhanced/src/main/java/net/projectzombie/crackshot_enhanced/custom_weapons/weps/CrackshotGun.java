/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.weps;

import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.GunModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Scope;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.FireMode;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Attatchment;
import net.projectzombie.crackshot_enhanced.custom_weapons.qualities.Condition;
import net.projectzombie.crackshot_enhanced.custom_weapons.qualities.Build;
import java.util.ArrayList;
import java.util.Random;
import net.projectzombie.crackshot_enhanced.custom_weapons.types.Weapon;
import static net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Scope.*;
import net.projectzombie.crackshot_enhanced.custom_weapons.utilities.CrackshotLore;
import net.projectzombie.crackshot_enhanced.custom_weapons.utilities.GunUtils;
import org.bukkit.inventory.ItemStack;


/**
 *
 * @author jbannon
 */
public class CrackshotGun
{   
    private static final Random rand = new Random();
    
    private final int uniqueID;
    private final GunSkeleton skeleton;
    private final FireMode firemodeType;
    private final Scope scopeType;
    private final Attatchment attatchment;
    private final String csWeaponName;
    private final double initialBulletSpread;
    
    public CrackshotGun(final int uniqueID,
                final GunSkeleton skeleton,
                final FireMode firemodeType,
                final Scope scopeType,
                final Attatchment attatchment)
    {
        this.uniqueID = uniqueID;
        this.skeleton = skeleton;
        this.firemodeType = firemodeType;
        this.scopeType = scopeType;
        this.attatchment = attatchment;
        this.csWeaponName = String.valueOf(uniqueID) + "_" + skeleton.getFileName();
        this.initialBulletSpread = skeleton.getBulletSpread();
    }
    
    public GunSkeleton getSkeleton()         { return skeleton;                }
    public int         getUniqueId()         { return uniqueID;                }
    public Weapon      getWeaponType()       { return skeleton.getWeaponType();}
    public FireMode    getFireMode()         { return firemodeType;            }
    public Scope       getScope()            { return scopeType;               }
    public Attatchment getAttatchment()      { return attatchment;             }
    public int         getItemID()           { return skeleton.getItemID();    }
    public int         getItemData()         { return skeleton.getItemData();  }
    public String      getCSWeaponName()     { return csWeaponName;            }
    public double      getInitBulletSpread() { return initialBulletSpread;     }
    public int         getMaxDurability()    { return skeleton.getMaxDurability(); }
    
    @Override public String toString()       { return csWeaponName;  }

    public double getCSBulletSpread()
    {
        switch(scopeType)
        {
        case IRON: return initialBulletSpread;
        case ACOG: return initialBulletSpread + 0.25;
        case TACT: return initialBulletSpread + 0.50;
        case LONG: return initialBulletSpread + 1.00;
        default:   return initialBulletSpread;
        }
    }
    
    public double getSneakCSBulletSpread()
    {
        return (GunUtils.hasScope(this)) ? 
                getCSBulletSpread() : initialBulletSpread * 0.85;
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
    
    public boolean containsMods(final FireMode firemode,
                                final Scope scope,
                                final Attatchment attatchment)
    {
        return this.firemodeType == firemode 
                && this.scopeType == scope 
                && this.attatchment == attatchment;
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
        return GunUtils.sortMods(mods);
    }
}
