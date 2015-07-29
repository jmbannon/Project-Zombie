/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.types;


/**
 *
 * @author jbannon
 */
public class Mod implements Comparable<Mod>
{
    public enum ModType implements Type
    {
        NA_       (0,  "N/A",                    0),
        IRON_     (1,  "Iron-Sight",             10),
        ACOG_     (2,  "Acog-Scope",             35),
        TACT_     (3,  "Tactical-Scope",         55),
        LONG_     (4,  "Sniper-Scope",           75),
        SEMI_     (5,  "Semi-Auto Trigger",      60),
        BURST_    (6,  "Burst-Fire Trigger",     60),
        AUTO_     (7,  "Auto-Fire Trigger",      50),
        SUPP_     (8,  "Suppressor",             90),
        SO_       (9,  "Sawed-Off",              45),
        LUB_      (10, "Lubed-Bolt",             45),
        EXT_      (11, "Extended Mag",           45),
        REL_      (12, "Fast-Mags",              45),
        INC_      (13, "Incendiary Rounds",      120),
        GRA_      (14, "Grave-Spawn",            200);

        private final int enumValue;
        private final String name;
        private final int price;
        
        ModType(final int enumValue,
                final String name,
                final int price)
        {
            this.enumValue = enumValue;
            this.name = name;
            this.price = price;
        }
        public int getPrice() { return price; }
        
        @Override public int getEnumValue() { return enumValue; }
        @Override public String toString()  { return name;      }
        
        static
        private ModType getModType(final Attatchment attatchment)
        {
            switch(attatchment)
            {
            case SUP: return SUPP_;
            case SO:  return SO_;
            case LUB: return LUB_;
            case EXT: return EXT_;
            case REL: return REL_;
            case INC: return INC_;
            case GRA: return GRA_;
            default:  return NA_;
            }
        }
        
        static
        private ModType getModType(final FireMode fireMode)
        {
            switch(fireMode)
            {
            case SEMI:  return SEMI_;
            case BURST: return BURST_;
            case AUTO:  return AUTO_;
            default:    return null;
            }
        }
        
        static
        private ModType getModType(final Scope scopeType)
        {
            switch(scopeType)
            {
            case IRON: return IRON_;
            case ACOG: return ACOG_;
            case TACT: return TACT_;
            case LONG: return LONG_;
            default:   return null;
            }
        }
    }
    
    private final int ID;
    private final ModType mod;
    
    public Mod(final int ID,
               final ModType mod)
    {
        this.ID = ID;
        this.mod = mod;
    }
    
    public int getID()          { return ID;        }
    public int getPrice()       { return mod.price; }
    public ModType getModType() { return mod;       }
    
    @Override public String toString()    { return mod.name; }
    @Override public int compareTo(Mod t) { return this.getPrice() - t.getPrice(); }
    
    static
    public Mod getMod(final CrackshotGun gun,
                      final CrackshotGun gunMod)
    {
        int modDifference = 0;
        ModType mod = null;
        
        if (gun.getBase().equals(gunMod.getBase()))
        {
            if (!gun.getAttatchment().equals(gunMod.getAttatchment()))
            { 
                ++modDifference;
                mod = ModType.getModType(gunMod.getAttatchment());
            }
            if (!gun.getFireMode().equals(gunMod.getFireMode()))
            {
                ++modDifference;
                mod = ModType.getModType(gunMod.getFireMode());
            }
            if (!gun.getScopeType().equals(gunMod.getScopeType()))
            {
                ++modDifference;
                mod = ModType.getModType(gunMod.getScopeType());
            }
        }
        if (mod != null && modDifference == 1)
            return new Mod(gunMod.getEnumValue(), mod);
        else
            return null;
    }
}
