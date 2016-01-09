/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.modifiers;

import java.util.ArrayList;
import net.projectzombie.crackshot_enhanced.custom_weapons.csv.CSVReader;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.GunModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.GunModifier2;

/**
 *
 * @author jesse
 */
public class ModifierSet2
{
    static private final String MODIFIER_SET_CSV_NAME = "ModifierSet.csv";
    static private final int LINES_PER_SET = 9;
    
    private final String name;
    private final ArrayList<Attatchment> attatchments;
    private final ArrayList<Barrel> barrels;
    private final ArrayList<Bolt> bolts;
    private final ArrayList<FireMode> fireModes;
    private final ArrayList<Magazine> magazines;
    private final ArrayList<Scope> scopes;
    private final ArrayList<Stock> stocks;
    private final GunModifier[] modifiers;
    
    private ModifierSet2(final String name,
                        final ArrayList<Attatchment> attatchments,
                        final ArrayList<Barrel> barrels,
                        final ArrayList<Bolt> bolts,
                        final ArrayList<FireMode> fireModes,
                        final ArrayList<Magazine> magazines,
                        final ArrayList<Scope> scopes,
                        final ArrayList<Stock> stocks)
    {
        this.name = name;
        this.attatchments = attatchments;
        this.barrels = barrels;
        this.bolts = bolts;
        this.fireModes = fireModes;
        this.magazines = magazines;
        this.scopes = scopes;
        this.stocks = stocks;
        this.modifiers = constructGunModifierArray();
    }
    
    public ArrayList<Attatchment> getAttatchments() { return attatchments; }
    public ArrayList<Barrel>          getBarrels()  { return barrels;      }
    public ArrayList<Bolt>             getBolts()   { return bolts;        } 
    public ArrayList<FireMode>     getFireModes()   { return fireModes;    }
    public ArrayList<Magazine>     getMagazines()   { return magazines;    }
    public ArrayList<Scope>          getScopes()    { return scopes;       }
    public ArrayList<Stock>           getStocks()   { return stocks;       }
    public GunModifier[] getModifiers()    { return modifiers;    }
    
    public int getCombinationCount()
    {
        return attatchments.size() * barrels.size() * bolts.size() * fireModes.size() 
                * magazines.size() * scopes.size() * stocks.size();
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
        mods.addAll(attatchments);
        mods.addAll(barrels);
        mods.addAll(bolts);
        mods.addAll(fireModes);
        mods.addAll(magazines);
        mods.addAll(scopes);
        mods.addAll(stocks);
        
        GunModifier toReturn[] = new GunModifier[mods.size()];
        return mods.toArray(toReturn);
    }
    
    
    static public ModifierSet2[] initializeModifierSet()
    {
        final CSVReader csv = new CSVReader(MODIFIER_SET_CSV_NAME, LINES_PER_SET);
        final int rowCount = csv.getRowCount();
        GunModifier2 temp;
        
        String[]       name;
        Attatchment2[] attatchments;
        Barrel2[]      barrels;
        Bolt2[]        bolts;
        FireMode[]     fireModes;
        Magazine[]     magazines;
        Scope[]        scopes;
        Stock[]        stocks;
        
        if (rowCount % LINES_PER_SET != 0)
            return null;
        
        for (int i = 0; i < rowCount; i += 9)
        {
            name         = csv.getRowData(i);
            attatchments = (Attatchment2[])new Attatchment2().valueOf(csv.getRowData(i+1));
            barrels      = (Barrel2[])new Barrel2().valueOf(csv.getRowData(i+2));
            bolts        = (Bolt2[])new Bolt2().valueOf(csv.getRowData(i+3));
            fireModes    = csv.getRowData(i+4);
            magazines    = csv.getRowData(i+5);
            scopes       = csv.getRowData(i+6);
            stocks       = csv.getRowData(i+7);
        }
    }
    
    static private ModifierSet2 createModifierSet(String[] name,
                                                  String[] attatchments,
                                                  String[] barrels,
                                                  String[] bolts,
                                                  String[] fireModes,
                                                  String[] magazines,
                                                  String[] scopes,
                                                  String[] stocks)
    {
        final String setName;
        if (name.length != 1)
            return null;
        
        setName = name[0];
    }
}
