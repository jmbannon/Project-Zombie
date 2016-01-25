/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.modifiers;

import java.util.ArrayList;
import net.projectzombie.crackshot_enhanced.custom_weapons.csv.CSVReader;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Attatchments.Attatchment;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Barrels.Barrel;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Bolts.Bolt;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.FireModes.FireMode;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Magazines.Magazine;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.ModifierSets.ModifierSet;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Sights.Scope;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Stocks.Stock;
import net.projectzombie.crackshot_enhanced.custom_weapons.csv.CSVInput;
import net.projectzombie.crackshot_enhanced.custom_weapons.csv.CSVValue;

/**
 *
 * @author jesse
 */
public class ModifierSets extends CSVInput<ModifierSet>
{
    static private ModifierSets singleton = null;
    static public ModifierSets getInstance()
    {
        if (singleton == null)
            singleton = new ModifierSets();
        return singleton;
    }
    
    static private final String MODIFIER_SET_CSV_TEMPLATE = "Set Name:,Example Set\nSlot1_Attatchments:\nSlot2_Attatchments:\nSlot3_Attatchments:\nBarrels:\nBolts:\nFireModes:\nMagazines:\nScopes:\nStocks\n---------";
    static private final String MODIFIER_SET_CSV_NAME = "ModifierSet.csv";
    static private final int LINES_PER_SET = 11;
    
    public ModifierSets()
    {
        super(MODIFIER_SET_CSV_NAME, initializeModifierSet(), MODIFIER_SET_CSV_TEMPLATE);
    }
    
    /**
     * Builds all ModifierSets, if any. Must have at least one in CSV.
     * @return Array of all ModifierSets. Null otherwise.
     */
    static private ModifierSet[] initializeModifierSet()
    {
        final CSVReader csv = new CSVReader(MODIFIER_SET_CSV_NAME, LINES_PER_SET, MODIFIER_SET_CSV_TEMPLATE);
        final int rowCount = csv.getRowCount();
        final ArrayList<ModifierSet> toReturn = new ArrayList<>();
        ModifierSet temp;
        
        String[]       name;
        ArrayList<Attatchment> slot1_attatchments;
        ArrayList<Attatchment> slot2_attatchments;
        ArrayList<Attatchment> slot3_attatchments;
        ArrayList<Barrel>      barrels;
        ArrayList<Bolt>        bolts;
        ArrayList<FireMode>    fireModes;
        ArrayList<Magazine>    magazines;
        ArrayList<Scope>       scopes;
        ArrayList<Stock>       stocks;
        
        if (rowCount <= 0 || rowCount % LINES_PER_SET != 0)
            return null;
        
        for (int i = 0; i < rowCount; i += LINES_PER_SET)
        {
            name         = csv.getRowString(i + 0);
            slot1_attatchments = Attatchments.getSlotOneInstance().get(csv.getRowString(i+1), true);
            slot2_attatchments = Attatchments.getSlotTwoInstance().get(csv.getRowString(i+2), true);
            slot3_attatchments = Attatchments.getSlotThreeInstance().get(csv.getRowString(i+3), true);
            barrels            = Barrels.getInstance().get(csv.getRowString(i+4), true);
            bolts              = Bolts.getInstance().get(csv.getRowString(i+5), true);
            fireModes          = FireModes.getInstance().get(csv.getRowString(i+6), false);
            magazines          = Magazines.getInstance().get(csv.getRowString(i+7), true);
            scopes             = Sights.getInstance().get(csv.getRowString(i+8), true);
            stocks             = Stocks.getInstance().get(csv.getRowString(i+9), true);
            
            temp = createModifierSet(name, slot1_attatchments, slot2_attatchments, slot3_attatchments, barrels, bolts, fireModes, magazines, scopes, stocks);
            if (temp != null)
                toReturn.add(temp);
        }
        
        return toReturn.toArray(new ModifierSet[toReturn.size()]);
    }
    
    static private ModifierSet createModifierSet(String[] nameArray,
                                           ArrayList<Attatchment> slot1Attatchments,
                                           ArrayList<Attatchment> slot2Attatchments,
                                           ArrayList<Attatchment> slot3Attatchments,
                                           ArrayList<Barrel> barrels,
                                           ArrayList<Bolt> bolts,
                                           ArrayList<FireMode> fireModes,
                                           ArrayList<Magazine> magazines,
                                           ArrayList<Scope> scopes,
                                           ArrayList<Stock> stocks)
    {
        if (nameArray.length < 1 || fireModes.size() < 1)
        {
            return null;
        }
        else
        {
            return new ModifierSet(
                    nameArray[0],
                    slot1Attatchments, 
                    slot2Attatchments,
                    slot3Attatchments,
                    barrels,
                    bolts,
                    fireModes,
                    magazines,
                    scopes,
                    stocks);
        }
    }
    
    static public class ModifierSet extends CSVValue
    {
        private final ArrayList<Attatchment> slot1Attatchments;
        private final ArrayList<Attatchment> slot2Attatchments;
        private final ArrayList<Attatchment> slot3Attatchments;
        private final ArrayList<Barrel> barrels;
        private final ArrayList<Bolt> bolts;
        private final ArrayList<FireMode> fireModes;
        private final ArrayList<Magazine> magazines;
        private final ArrayList<Scope> scopes;
        private final ArrayList<Stock> stocks;
        private final GunModifier[] modifiers;

        private ModifierSet(final String name,
                            final ArrayList<Attatchment> slot1Attatchments,
                            final ArrayList<Attatchment> slot2Attatchments,
                            final ArrayList<Attatchment> slot3Attatchments,
                            final ArrayList<Barrel> barrels,
                            final ArrayList<Bolt> bolts,
                            final ArrayList<FireMode> fireModes,
                            final ArrayList<Magazine> magazines,
                            final ArrayList<Scope> scopes,
                            final ArrayList<Stock> stocks)
        {
            super(name);
            this.slot1Attatchments = slot1Attatchments;
            this.slot2Attatchments = slot2Attatchments;
            this.slot3Attatchments = slot3Attatchments;
            this.barrels = barrels;
            this.bolts = bolts;
            this.fireModes = fireModes;
            this.magazines = magazines;
            this.scopes = scopes;
            this.stocks = stocks;
            this.modifiers = constructGunModifierArray();
        }

        public ArrayList<Attatchment> getSlot1Attatchments() { return slot1Attatchments; }
        public ArrayList<Attatchment> getSlot2Attatchments() { return slot2Attatchments; }
        public ArrayList<Attatchment> getSlot3Attatchments() { return slot3Attatchments; }
        public ArrayList<Barrel>      getBarrels()           { return barrels;      }
        public ArrayList<Bolt>        getBolts()             { return bolts;        } 
        public ArrayList<FireMode>    getFireModes()         { return fireModes;    }
        public ArrayList<Magazine>    getMagazines()         { return magazines;    }
        public ArrayList<Scope>       getScopes()            { return scopes;       }
        public ArrayList<Stock>       getStocks()            { return stocks;       }
        public GunModifier[]          getModifiers()         { return modifiers;    }

        public int getCombinationCount()
        {
            return slot1Attatchments.size() * slot2Attatchments.size() 
                    * slot3Attatchments.size() * barrels.size() * bolts.size() 
                    * fireModes.size() * magazines.size() * scopes.size() * stocks.size();
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
            mods.addAll(slot1Attatchments);
            mods.addAll(slot2Attatchments);
            mods.addAll(slot3Attatchments);
            mods.addAll(barrels);
            mods.addAll(bolts);
            mods.addAll(fireModes);
            mods.addAll(magazines);
            mods.addAll(scopes);
            mods.addAll(stocks);

            return mods.toArray(new GunModifier[mods.size()]);
        }
    }
}
