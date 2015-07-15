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
        NA_       (0,  "Unsuppressed",           0),
        IRON_     (1,  "Iron-Sight",             10),
        ACOG_     (2,  "Acog-Scope",             35),
        TACT_     (3,  "Tactical-Scope",         55),
        LONG_     (4,  "Sniper-Scope",           75),
        SEMI_     (5,  "Semi-Auto Trigger",      60),
        BURST_    (6,  "Burst-Fire Trigger",     60),
        AUTO_     (7,  "Auto-Fire Trigger",      50),
        SUPP_     (8,  "Suppressor",             90),
        SAW_      (9,  "Sawed-Off",              45),
        LUB_      (10, "Lubed-Bolt",             45),
        EXT_      (11, "Extended Mag",           45),
        REL_      (12, "Fast-Mags",              45);

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
}