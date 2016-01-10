/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.modifiers;

import java.util.ArrayList;
import java.util.Arrays;
import net.projectzombie.crackshot_enhanced.custom_weapons.csv.CSVReader;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.GunModifier2;

/**
 *
 * @author jesse
 */
public class ModifierSet2
{
    static private final String MODIFIER_SET_CSV_TEMPLATE = "Example Set\nAttatchments:,someAttatchment\nBarrels:\nBolts:\nFireModes:\nMagazines:\nScopes:\nStocks\n---------";
    static private final String MODIFIER_SET_CSV_NAME = "ModifierSet.csv";
    static private final int LINES_PER_SET = 9;
    private static ModifierSet2[] modifierSets = null;
    
    private final String name;
    private final Attatchment2[] attatchments;
    private final Barrel2[] barrels;
    private final Bolt2[] bolts;
    private final FireMode2[] fireModes;
    private final Magazine2[] magazines;
    private final Scope2[] scopes;
    private final Stock2[] stocks;
    private final GunModifier2[] modifiers;
    
    private ModifierSet2(final String name,
                        final Attatchment2[] attatchments,
                        final Barrel2[] barrels,
                        final Bolt2[] bolts,
                        final FireMode2[] fireModes,
                        final Magazine2[] magazines,
                        final Scope2[] scopes,
                        final Stock2[] stocks)
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
    
    public Attatchment2[] getAttatchments() { return attatchments; }
    public Barrel2[]          getBarrels()  { return barrels;      }
    public Bolt2[]             getBolts()   { return bolts;        } 
    public FireMode2[]     getFireModes()   { return fireModes;    }
    public Magazine2[]     getMagazines()   { return magazines;    }
    public Scope2[]          getScopes()    { return scopes;       }
    public Stock2[]           getStocks()   { return stocks;       }
    public GunModifier2[] getModifiers()    { return modifiers;    }
    
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
    private GunModifier2[] constructGunModifierArray()
    {
        final ArrayList<GunModifier2> mods = new ArrayList<>();
        mods.addAll(Arrays.asList(attatchments));
        mods.addAll(Arrays.asList(barrels));
        mods.addAll(Arrays.asList(bolts));
        mods.addAll(Arrays.asList(fireModes));
        mods.addAll(Arrays.asList(magazines));
        mods.addAll(Arrays.asList(scopes));
        mods.addAll(Arrays.asList(stocks));
        
        return mods.toArray(new GunModifier2[mods.size()]);
    }
    
    static public void initializeModifierSets()
    {
        if (modifierSets == null)
            modifierSets = initializeModifierSet();
    }
    
    static private ModifierSet2[] initializeModifierSet()
    {
        final CSVReader csv = new CSVReader(MODIFIER_SET_CSV_NAME, LINES_PER_SET, MODIFIER_SET_CSV_TEMPLATE);
        final int rowCount = csv.getRowCount();
        final ArrayList<ModifierSet2> toReturn = new ArrayList<>();
        ModifierSet2 temp;
        
        String[]       name;
        Attatchment2[] attatchments;
        Barrel2[]      barrels;
        Bolt2[]        bolts;
        FireMode2[]    fireModes;
        Magazine2[]    magazines;
        Scope2[]       scopes;
        Stock2[]       stocks;
        
        if (rowCount % LINES_PER_SET != 0)
            return null;
        
        for (int i = 0; i < rowCount; i += 9)
        {
            name         = csv.getRowData(i);
            attatchments = new Attatchment2().valueOf(csv.getRowData(i+1), true);
            barrels      = new Barrel2().valueOf(csv.getRowData(i+2), true);
            bolts        = new Bolt2().valueOf(csv.getRowData(i+3), true);
            fireModes    = new FireMode2().valueOf(csv.getRowData(i+4), false);
            magazines    = new Magazine2().valueOf(csv.getRowData(i+5), true);
            scopes       = new Scope2().valueOf(csv.getRowData(i+6), true);
            stocks       = new Stock2().valueOf(csv.getRowData(i+7), true);
            
            temp = createModifierSet(name, attatchments, barrels, bolts, fireModes, magazines, scopes, stocks);
            if (temp != null)
                toReturn.add(temp);
        }
        
        System.out.println("Initialized " + toReturn.size() + " modifier sets");
        return toReturn.toArray(new ModifierSet2[toReturn.size()]);
    }
    
    static private ModifierSet2 createModifierSet(String[] nameArray,
                                                  Attatchment2[] attatchments,
                                                  Barrel2[] barrels,
                                                  Bolt2[] bolts,
                                                  FireMode2[] fireModes,
                                                  Magazine2[] magazines,
                                                  Scope2[] scopes,
                                                  Stock2[] stocks)
    {
        if (nameArray.length != 1
                || attatchments.length < 1
                || barrels.length < 1
                || bolts.length < 1
                || fireModes.length < 1
                || magazines.length < 1
                || scopes.length < 1
                || stocks.length < 1)
        {
            return null;
        }
        else
        {
            return new ModifierSet2(nameArray[0],
                                    attatchments,
                                    barrels,
                                    bolts,
                                    fireModes,
                                    magazines,
                                    scopes,
                                    stocks);
        }
    }
}
