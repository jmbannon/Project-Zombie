/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.modifiers;

import static net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Attatchment.*;
import static net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Barrel.*;
import static net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Bolt.*;
import static net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.FireMode.*;
import static net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Magazine.*;
import static net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Scope.*;
import static net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Stock.*;

/**
 *
 * @author jesse
 */
public enum ModifierSet
{
    PISTOL_1(new Attatchment[] { INC },
             new Barrel[]      { THREADED, PRECISION },
             new Bolt[]        {},
             new FireMode[]    { SEMI },
             new Magazine[]    { MAG_QUICK, MAG_FAST },
             new Scope[]       { IRON, ACOG },
             new Stock[]       {}),
    
    PISTOL_2(new Attatchment[] { SUP, INC },
             new Barrel[]      { THREADED, PRECISION },
             new Bolt[]        {},
             new FireMode[]    { SEMI, BURST },
             new Magazine[]    { MAG_QUICK, MAG_FAST, MAG_ALTERED, MAG_EXTENDED },
             new Scope[]       { IRON, ACOG },
             new Stock[]       { ST_FOLDING }),
    
   REVOLVER1(new Attatchment[] { INC },
             new Barrel[]      { THREADED, PRECISION, EXTENDED, SHORT },
             new Bolt[]        {},
             new FireMode[]    { SEMI },
             new Magazine[]    { MAG_QUICK, MAG_FAST },
             new Scope[]       { IRON, ACOG },
             new Stock[]       {}),
   
   REVOLVER2(new Attatchment[] { INC },
             new Barrel[]      { THREADED, PRECISION, EXTENDED, SHORT },
             new Bolt[]        {},
             new FireMode[]    { SEMI },
             new Magazine[]    { MAG_QUICK, MAG_FAST },
             new Scope[]       { IRON, ACOG, TACT },
             new Stock[]       {}),
   
BOLT_SNIPER1(new Attatchment[] { INC },
             new Barrel[]      { BARTLEIN, KRIEGER, BRUX },
             new Bolt[]        { BO_LUBED, BO_POLISHED },
             new FireMode[]    { BOLT },
             new Magazine[]    { MAG_QUICK, MAG_FAST, MAG_ALTERED, MAG_EXTENDED },
             new Scope[]       { IRON, ACOG, TACT, LONG },
             new Stock[]       { ST_RECOIL_ABS, ST_TACTICAL, ST_FIBERGLASS, ST_SYNTHETIC }),
   
SEMI_SNIPER1(new Attatchment[] { INC, SUP },
             new Barrel[]      { BARTLEIN, KRIEGER, BRUX },
             new Bolt[]        {},
             new FireMode[]    { SEMI },
             new Magazine[]    { MAG_QUICK, MAG_FAST, MAG_ALTERED, MAG_EXTENDED },
             new Scope[]       { IRON, ACOG, TACT, LONG },
             new Stock[]       { ST_RECOIL_ABS, ST_TACTICAL, ST_FIBERGLASS, ST_SYNTHETIC });
    
    private final Attatchment[] attatchments;
    private final Barrel[] barrels;
    private final Bolt[] bolts;
    private final FireMode[] fireModes;
    private final Magazine[] magazines;
    private final Scope[] scopes;
    private final Stock[] stocks;
    
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
    }
    
    public Attatchment[] getAttatchments() { return attatchments; }
    public Barrel[]      getBarrels()      { return barrels;      }
    public Bolt[]        getBolts()        { return bolts;        } 
    public FireMode[]    getFireModes()    { return fireModes;    }
    public Magazine[]    getMagazines()    { return magazines;    }
    public Scope[]       getScopes()       { return scopes;       }
    public Stock[]       getStocks()       { return stocks;       }
}
