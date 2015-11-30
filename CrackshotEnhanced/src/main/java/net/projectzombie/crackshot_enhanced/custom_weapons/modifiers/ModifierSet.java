/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.modifiers;

import java.util.ArrayList;
import java.util.Arrays;
import static net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Attatchment.*;
import static net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Barrel.*;
import static net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Bolt.*;
import static net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.FireMode.*;
import static net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Magazine.*;
import static net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Scope.*;
import static net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Stock.*;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.GunModifier;

/**
 *
 * @author jesse
 */
public enum ModifierSet
{
    /** 
     * Deagle, Colt 45
     */
    PISTOL_1(new Attatchment[] { NULL_ATTATCHMENT, INCENDIARY },
                   new Barrel[]          { NULL_BARREL, THREADED, PRECISION },
                   new Bolt[]             { NULL_BOLT },
                   new FireMode[]     { SEMI },
                   new Magazine[]     { NULL_MAG, MAG_QUICK, MAG_FAST },
                   new Scope[]          { IRON, RED_IRON, ACOG },
                   new Stock[]           { NULL_STOCK, IVORY_GRIP }),
    
    /**
     * P228, USP
     */
    PISTOL_2(new Attatchment[] { NULL_ATTATCHMENT, SUPPRESOR, INCENDIARY },
                   new Barrel[]          { NULL_BARREL, THREADED, PRECISION },
                   new Bolt[]             { NULL_BOLT },
                   new FireMode[]     { SEMI, BURST },
                   new Magazine[]     { NULL_MAG, MAG_QUICK, MAG_FAST, MAG_ALTERED, MAG_EXTENDED },
                   new Scope[]          { IRON, RED_IRON, ACOG },
                   new Stock[]           { NULL_STOCK, IVORY_GRIP, ST_FOLDING }),
    
    /**
     * 44 Mag
     */
   REVOLVER_1(new Attatchment[] { NULL_ATTATCHMENT, INCENDIARY },
                      new Barrel[]          { NULL_BARREL, THREADED, PRECISION, EXTENDED, SHORT },
                      new Bolt[]             { NULL_BOLT },
                      new FireMode[]     { SEMI },
                      new Magazine[]     { NULL_MAG, MAG_QUICK, MAG_FAST },
                      new Scope[]          { IRON, RED_IRON, ACOG },
                      new Stock[]           { NULL_STOCK, IVORY_GRIP }),
   
   /**
    * Dirty Frank
    */
   REVOLVER_2(new Attatchment[] { NULL_ATTATCHMENT, INCENDIARY },
                      new Barrel[]          { NULL_BARREL, THREADED, PRECISION, EXTENDED, SHORT },
                      new Bolt[]             { NULL_BOLT },
                      new FireMode[]     { SEMI },
                      new Magazine[]     { NULL_MAG, MAG_QUICK, MAG_FAST },
                      new Scope[]          { BROKEN, ACOG, TACT },
                      new Stock[]           { NULL_STOCK, IVORY_GRIP }),
   
   /**
    * Scout, AWP, Remington, Springfield, Winchester
    */
BOLT_SNIPER_1(new Attatchment[] { NULL_ATTATCHMENT, INCENDIARY },
                       new Barrel[]           { NULL_BARREL, BARTLEIN, KRIEGER, BRUX },
                       new Bolt[]              { NULL_BOLT, BO_LUBED, BO_POLISHED },
                       new FireMode[]      { BOLT },
                       new Magazine[]      { NULL_MAG, MAG_QUICK, MAG_FAST, MAG_ALTERED, MAG_EXTENDED },
                       new Scope[]           { BROKEN, ACOG, TACT, LONG },
                       new Stock[]            { NULL_STOCK, RECOIL_ABS, TACTICAL, FIBERGLASS, SYNTHETIC }),
   
/**
 * G3, Dragonuv
 */
SEMI_SNIPER_1(new Attatchment[] { NULL_ATTATCHMENT, INCENDIARY, SUPPRESOR },
                       new Barrel[]          { NULL_BARREL, BARTLEIN, KRIEGER, BRUX },
                       new Bolt[]             { NULL_BOLT },
                       new FireMode[]     { SEMI },
                       new Magazine[]     { NULL_MAG, MAG_QUICK, MAG_FAST, MAG_ALTERED, MAG_EXTENDED },
                       new Scope[]          { BROKEN, ACOG, TACT, LONG },
                       new Stock[]           { NULL_STOCK, RECOIL_ABS, TACTICAL, FIBERGLASS, SYNTHETIC }),

           /**
            * Mac10, Ump45, TMP
            */
           SMG_1(new Attatchment[] { NULL_ATTATCHMENT, INCENDIARY, SUPPRESOR },
                       new Barrel[]           { NULL_BARREL, THREADED, PRECISION, EXTENDED, SHORT },
                       new Bolt[]              { NULL_BOLT },
                       new FireMode[]      { AUTO, BURST, SEMI },
                       new Magazine[]      { NULL_MAG, MAG_QUICK, MAG_FAST, MAG_ALTERED, MAG_EXTENDED },
                       new Scope[]           { IRON, RED_IRON, ACOG },
                       new Stock[]            { NULL_STOCK, RECOIL_ABS, TACTICAL, FIBERGLASS, SYNTHETIC, ST_FOLDING }),
           
           /**
            * MP5, 
            */
           SMG_2(new Attatchment[] { NULL_ATTATCHMENT, INCENDIARY, SUPPRESOR },
                       new Barrel[]           { NULL_BARREL, THREADED, PRECISION, EXTENDED, SHORT },
                       new Bolt[]              { NULL_BOLT },
                       new FireMode[]      { AUTO, BURST, SEMI },
                       new Magazine[]      { NULL_MAG, MAG_QUICK, MAG_FAST, MAG_ALTERED, MAG_EXTENDED },
                       new Scope[]           { IRON, RED_IRON, ACOG, TACT },
                       new Stock[]            { NULL_STOCK, RECOIL_ABS, TACTICAL, FIBERGLASS, SYNTHETIC, ST_FOLDING }),
           
           /**
            * P90
            */
           SMG_3(new Attatchment[]  { NULL_ATTATCHMENT, INCENDIARY },
                       new Barrel[]           { NULL_BARREL, THREADED, PRECISION, EXTENDED, SHORT },
                       new Bolt[]              { NULL_BOLT },
                       new FireMode[]      { AUTO, BURST, SEMI },
                       new Magazine[]      { NULL_MAG, MAG_QUICK, MAG_FAST, MAG_ALTERED, MAG_EXTENDED, MAG_DRUM, MAG_BAND },
                       new Scope[]           { IRON, RED_IRON, ACOG },
                       new Stock[]            { NULL_STOCK, RECOIL_ABS, TACTICAL, FIBERGLASS, SYNTHETIC, ST_FOLDING }),
           
           /**
            * US Assualt Rifles
            * AUG, M16, FAMAS, M4A1
            */
      ASSUALT_1(new Attatchment[]  { NULL_ATTATCHMENT, INCENDIARY, SUPPRESOR },
                       new Barrel[]             { NULL_BARREL, THREADED, PRECISION, EXTENDED, SHORT },
                       new Bolt[]                { NULL_BOLT },
                       new FireMode[]        { AUTO, BURST, SEMI },
                       new Magazine[]        { NULL_MAG, MAG_QUICK, MAG_FAST, MAG_ALTERED, MAG_EXTENDED },
                       new Scope[]             { IRON, RED_IRON, ACOG, TACT, LONG },
                       new Stock[]              { NULL_STOCK, RECOIL_ABS, TACTICAL, FIBERGLASS, SYNTHETIC }),
      
           /**
            * Foreign
            * SG, AK, Galil
            */
      ASSUALT_2(new Attatchment[]  { NULL_ATTATCHMENT, INCENDIARY },
                       new Barrel[]             { NULL_BARREL, THREADED, PRECISION, EXTENDED, SHORT },
                       new Bolt[]                { NULL_BOLT },
                       new FireMode[]        { AUTO, BURST, SEMI },
                       new Magazine[]        { NULL_MAG, MAG_QUICK, MAG_FAST, MAG_ALTERED, MAG_EXTENDED, MAG_DRUM },
                       new Scope[]             { IRON, RED_IRON, ACOG, TACT },
                       new Stock[]              { NULL_STOCK, RECOIL_ABS, TACTICAL, FIBERGLASS, SYNTHETIC }),
      
          /**
            * SAW
            */
      ASSUALT_3(new Attatchment[]  { NULL_ATTATCHMENT, INCENDIARY },
                       new Barrel[]             {NULL_BARREL, THREADED, PRECISION, EXTENDED, SHORT },
                       new Bolt[]                { NULL_BOLT },
                       new FireMode[]        { AUTO, BURST, SEMI },
                       new Magazine[]        { NULL_MAG, MAG_QUICK, MAG_FAST, MAG_ALTERED, MAG_EXTENDED, MAG_DRUM, MAG_BAND },
                       new Scope[]             { IRON, RED_IRON, ACOG, TACT },
                       new Stock[]              { NULL_STOCK, RECOIL_ABS, TACTICAL, FIBERGLASS, SYNTHETIC }),
      
      /**
       * Olympia
       */
    SHOTGUN_1(new Attatchment[]  { NULL_ATTATCHMENT, INCENDIARY },
                       new Barrel[]             { NULL_BARREL, SMOOTH_BORE, RIFLED, SAWED_OFF },
                       new Bolt[]                { NULL_BOLT },
                       new FireMode[]        { SING },
                       new Magazine[]        { NULL_MAG, MAG_QUICK, MAG_FAST },
                       new Scope[]             { IRON, RED_IRON },
                       new Stock[]              { NULL_STOCK, RECOIL_ABS, TACTICAL, FIBERGLASS, SYNTHETIC }),
    
    /**
     * AA12
     */
    SHOTGUN_2(new Attatchment[]  { NULL_ATTATCHMENT, INCENDIARY },
                       new Barrel[]             { NULL_BARREL, SMOOTH_BORE, RIFLED, SAWED_OFF },
                       new Bolt[]                { NULL_BOLT },
                       new FireMode[]        { SEMI },
                       new Magazine[]        { NULL_MAG, MAG_QUICK, MAG_FAST, MAG_ALTERED, MAG_EXTENDED, MAG_DRUM },
                       new Scope[]             { IRON, RED_IRON, ACOG },
                       new Stock[]              { NULL_STOCK, RECOIL_ABS, TACTICAL, FIBERGLASS, SYNTHETIC }),
          
    /**
     * M3
     */
    SHOTGUN_3(new Attatchment[]  { NULL_ATTATCHMENT, INCENDIARY },
                       new Barrel[]             { NULL_BARREL, SMOOTH_BORE, RIFLED, SAWED_OFF },
                       new Bolt[]                { NULL_BOLT },
                       new FireMode[]        { PUMP },
                       new Magazine[]        { NULL_MAG, MAG_QUICK, MAG_FAST, MAG_UNPLUGGED },
                       new Scope[]             { IRON, RED_IRON, ACOG },
                       new Stock[]              { NULL_STOCK, RECOIL_ABS, TACTICAL, FIBERGLASS, SYNTHETIC }),
    
    /**
     * XM
     */
    SHOTGUN_4(new Attatchment[]  { NULL_ATTATCHMENT, INCENDIARY },
                       new Barrel[]             { NULL_BARREL, SMOOTH_BORE, RIFLED, SAWED_OFF },
                       new Bolt[]                { NULL_BOLT },
                       new FireMode[]        { SEMI },
                       new Magazine[]        { NULL_MAG, MAG_QUICK, MAG_FAST, MAG_UNPLUGGED },
                       new Scope[]             { IRON, RED_IRON, ACOG },
                       new Stock[]              { NULL_STOCK, RECOIL_ABS, TACTICAL, FIBERGLASS, SYNTHETIC });


    
    private final Attatchment[] attatchments;
    private final Barrel[] barrels;
    private final Bolt[] bolts;
    private final FireMode[] fireModes;
    private final Magazine[] magazines;
    private final Scope[] scopes;
    private final Stock[] stocks;
    private final GunModifier[] modifiers;
    
    private ModifierSet(final Attatchment[] attatchments,
                                 final Barrel[] barrels,
                                 final Bolt[] bolts,
                                 final FireMode[] fireModes,
                                 final Magazine[] magazines,
                                 final Scope[] scopes,
                                 final Stock[] stocks)
    {
        this.attatchments = attatchments;
        this.barrels = barrels;
        this.bolts = bolts;
        this.fireModes = fireModes;
        this.magazines = magazines;
        this.scopes = scopes;
        this.stocks = stocks;
        this.modifiers = constructGunModifierArray();
    }
    
    public Attatchment[] getAttatchments() { return attatchments; }
    public Barrel[]          getBarrels()          { return barrels;          }
    public Bolt[]             getBolts()             { return bolts;             } 
    public FireMode[]     getFireModes()     { return fireModes;      }
    public Magazine[]     getMagazines()     { return magazines;      }
    public Scope[]          getScopes()          { return scopes;           }
    public Stock[]           getStocks()           { return stocks;            }
    public GunModifier[] getModifiers()      { return modifiers;       }
    
    public int getCombinationCount()
    {
        return attatchments.length * barrels.length * bolts.length * fireModes.length 
                * magazines.length * scopes.length * stocks.length;
    }
    
    /**
     * Necessities every ModifierSet must have at least one of, otherwise can't create the Crackshot gun.
     * @return ModifierSet is valid to create a gun in Crackshot .yml
     */
    public boolean isValid()
    {
        return getCombinationCount() != 0;
    }
    
    /**
     * @return An array of all the modifiers within the set.
     */   
    private GunModifier[] constructGunModifierArray()
    {
        final ArrayList<GunModifier> mods = new ArrayList<>();
        mods.addAll(Arrays.asList(attatchments));
        mods.addAll(Arrays.asList(barrels));
        mods.addAll(Arrays.asList(bolts));
        mods.addAll(Arrays.asList(fireModes));
        mods.addAll(Arrays.asList(magazines));
        mods.addAll(Arrays.asList(scopes));
        mods.addAll(Arrays.asList(stocks));
        
        GunModifier toReturn[] = new GunModifier[mods.size()];
        return mods.toArray(toReturn);
    }
}
