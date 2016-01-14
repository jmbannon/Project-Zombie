/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.modifiers;

import java.util.ArrayList;
import java.util.Arrays;
import net.projectzombie.crackshot_enhanced.custom_weapons.csv.CSVReader;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Attatchments.Attatchment2;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Barrels.Barrel2;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Bolts.Bolt2;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.FireModes.FireMode2;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Magazines.Magazine2;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.ModifierSets.ModifierSet2;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Sights.Scope2;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Stocks.Stock2;
import net.projectzombie.crackshot_enhanced.custom_weapons.csv.CSVInput;
import net.projectzombie.crackshot_enhanced.custom_weapons.csv.CSVValue;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.GunModifier2;

/**
 *
 * @author jesse
 */
public class ModifierSets extends CSVInput<ModifierSet2>
{
    static private ModifierSets singleton = null;
    static public ModifierSets getInstance()
    {
        if (singleton == null)
            singleton = new ModifierSets();
        return singleton;
    }
    
    static private final String MODIFIER_SET_CSV_TEMPLATE = "Set Name:,Example Set\nAttatchments:,someAttatchment\nBarrels:\nBolts:\nFireModes:\nMagazines:\nScopes:\nStocks\n---------";
    static private final String MODIFIER_SET_CSV_NAME = "ModifierSet.csv";
    static private final int LINES_PER_SET = 9;
    
    public ModifierSets()
    {
        super(MODIFIER_SET_CSV_NAME, initializeModifierSet(), MODIFIER_SET_CSV_TEMPLATE);
    }
    
    /**
     * Builds all ModifierSets, if any. Must have at least one in CSV.
     * @return Array of all ModifierSets. Null otherwise.
     */
    static private ModifierSet2[] initializeModifierSet()
    {
        final CSVReader csv = new CSVReader(MODIFIER_SET_CSV_NAME, LINES_PER_SET, MODIFIER_SET_CSV_TEMPLATE);
        final int rowCount = csv.getRowCount();
        final ArrayList<ModifierSet2> toReturn = new ArrayList<>();
        ModifierSet2 temp;
        
        String[]       name;
        ArrayList<Attatchment2> attatchments;
        ArrayList<Barrel2>      barrels;
        ArrayList<Bolt2>        bolts;
        ArrayList<FireMode2>    fireModes;
        ArrayList<Magazine2>    magazines;
        ArrayList<Scope2>       scopes;
        ArrayList<Stock2>       stocks;
        
        if (rowCount % LINES_PER_SET != 0)
            return null;
        
        for (int i = 0; i < rowCount; i += 9)
        {
            name         = csv.getRowString(i);
            attatchments = Attatchments.getInstance().get(csv.getRowString(i+1), true);
            barrels      = Barrels.getInstance().get(csv.getRowString(i+2), true);
            bolts        = Bolts.getInstance().get(csv.getRowString(i+3), true);
            fireModes    = FireModes.getInstance().get(csv.getRowString(i+4), false);
            magazines    = Magazines.getInstance().get(csv.getRowString(i+5), true);
            scopes       = Sights.getInstance().get(csv.getRowString(i+6), true);
            stocks       = Stocks.getInstance().get(csv.getRowString(i+7), true);
            
            temp = createModifierSet(name, attatchments, barrels, bolts, fireModes, magazines, scopes, stocks);
            if (temp != null)
                toReturn.add(temp);
        }
        
        System.out.println("Initialized " + toReturn.size() + " modifier sets");
        return toReturn.toArray(new ModifierSet2[toReturn.size()]);
    }
    
    static private ModifierSet2 createModifierSet(String[] nameArray,
                                           ArrayList<Attatchment2> attatchments,
                                           ArrayList<Barrel2> barrels,
                                           ArrayList<Bolt2> bolts,
                                           ArrayList<FireMode2> fireModes,
                                           ArrayList<Magazine2> magazines,
                                           ArrayList<Scope2> scopes,
                                           ArrayList<Stock2> stocks)
    {
        if (nameArray.length < 1 || fireModes.size() < 1)
        {
            return null;
        }
        else
        {
            return new ModifierSet2(nameArray[0], attatchments, barrels,  bolts,
                                    fireModes, magazines, scopes, stocks);
        }
    }
    
    static public class ModifierSet2 extends CSVValue
    {
        private final ArrayList<Attatchment2> attatchments;
        private final ArrayList<Barrel2> barrels;
        private final ArrayList<Bolt2> bolts;
        private final ArrayList<FireMode2> fireModes;
        private final ArrayList<Magazine2> magazines;
        private final ArrayList<Scope2> scopes;
        private final ArrayList<Stock2> stocks;
        private final GunModifier2[] modifiers;

        private ModifierSet2(final String name,
                            final ArrayList<Attatchment2> attatchments,
                            final ArrayList<Barrel2> barrels,
                            final ArrayList<Bolt2> bolts,
                            final ArrayList<FireMode2> fireModes,
                            final ArrayList<Magazine2> magazines,
                            final ArrayList<Scope2> scopes,
                            final ArrayList<Stock2> stocks)
        {
            super(name);
            this.attatchments = attatchments;
            this.barrels = barrels;
            this.bolts = bolts;
            this.fireModes = fireModes;
            this.magazines = magazines;
            this.scopes = scopes;
            this.stocks = stocks;
            this.modifiers = constructGunModifierArray();
        }

        public ArrayList<Attatchment2> getAttatchments() { return attatchments; }
        public ArrayList<Barrel2>          getBarrels()  { return barrels;      }
        public ArrayList<Bolt2>             getBolts()   { return bolts;        } 
        public ArrayList<FireMode2>     getFireModes()   { return fireModes;    }
        public ArrayList<Magazine2>     getMagazines()   { return magazines;    }
        public ArrayList<Scope2>          getScopes()    { return scopes;       }
        public ArrayList<Stock2>           getStocks()   { return stocks;       }
        public GunModifier2[] getModifiers()    { return modifiers;    }

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
        private GunModifier2[] constructGunModifierArray()
        {
            final ArrayList<GunModifier2> mods = new ArrayList<>();
            mods.addAll(attatchments);
            mods.addAll(barrels);
            mods.addAll(bolts);
            mods.addAll(fireModes);
            mods.addAll(magazines);
            mods.addAll(scopes);
            mods.addAll(stocks);

            return mods.toArray(new GunModifier2[mods.size()]);
        }
    }
}
