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
public class Mod
{
    public enum ModType
    {
        IRON_SIGHT ("Iron Sight"),
        SCOPE      ("Scope"),
        SINGLE_FIRE("Single Fire Trigger"),
        BURST_FIRE ("Burst Fire Trigger"),
        AUTO_FIRE  ("Auto Fire Trigger"),
        SUPPRESSED ("Suppressor");

        private final String name;
        ModType(final String name)
        {
            this.name = name;
        } 
        public String getName() { return name; }
    }
    
    private final int ID;
    private final ModType mod;
    
    public Mod(final int ID,
               final ModType mod)
    {
        this.ID = ID;
        this.mod = mod;
    }
    
    public int getID()           { return ID;  }
    public ModType getModType()  { return mod; }
}
