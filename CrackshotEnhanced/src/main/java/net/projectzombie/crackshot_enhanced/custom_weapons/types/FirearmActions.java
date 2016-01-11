/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.types;

import java.util.ArrayList;
import net.projectzombie.crackshot_enhanced.custom_weapons.csv.CSVReader;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Stocks;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.CSVInput;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.CSVValue;


/**
 *
 * @author jbannon
 */
public class FirearmActions extends CSVInput
{
    static private FirearmActions singleton = null;
    static public FirearmActions getInstance()
    {
        if (singleton == null)
            singleton = new FirearmActions();
        return singleton;
    }
    
    static private final String ACTIONS_CSV_NAME = "FirearmActions.csv";
    static private final String[] ACTION_VALUES = {
        "Display Name (STR)",
        "CS Firearm Action Type (STR)",
        "Sound Open (STR)",
        "Sound Close (STR)",
        "Open Duration (INT)",
        "Close Duration (INT)",
        "Close Shoot Delay (INT)",
        "Individual Bullets (T/F)",
    };
    
    private final FirearmAction2[] firearmActions;
    private FirearmActions()
    {
        super(ACTIONS_CSV_NAME, ACTION_VALUES);
        this.firearmActions = buildFirearmActions();
    }
    
    @Override
    public FirearmAction2[] getAll()
    {
        return firearmActions;
    }

    @Override
    public FirearmAction2[] get(final String[] names)
    {
        return get(names, false);
    }
    
    @Override
    public FirearmAction2 get(String name)
    {
        final int index = super.getIndex(name);
        if (index == -1)
            return null;
        else
            return firearmActions[index];
    }

    @Override
    public FirearmAction2[] get(final String[] names,
                        final boolean includeNull)
    {
        final FirearmAction2 toReturn[];
        final ArrayList<Integer> indexes = super.getIndexes(names);
        int j = 0;
        
        if (indexes == null || indexes.isEmpty())
            return null;
        else
        {
            toReturn = new FirearmAction2[indexes.size()];
            for (Integer i : indexes)
            {
                toReturn[j++] = firearmActions[i];
            }
            return toReturn;
        }
    }
    
    private FirearmAction2[] buildFirearmActions()
    {
        final CSVReader csv = new CSVReader(ACTIONS_CSV_NAME, ACTION_VALUES);
        final int rowCount = csv.getRowCount();
        
        if (rowCount <= 0)
        {
            return new FirearmAction2[] { null };
        }
        int j = 0;
        final FirearmAction2[] toReturn   = new FirearmAction2[rowCount + 1];
        final String[]  displayNames      = csv.getColumnString(j++);
        final String[]  type              = csv.getColumnString(j++);
        final String[]  soundOpen         = csv.getColumnString(j++);
        final String[]  soundClose        = csv.getColumnString(j++);
        final int[]     openDuration      = csv.getColumnInt(j++);
        final int[]     closeDuration     = csv.getColumnInt(j++);
        final int[]     closeShootDelay   = csv.getColumnInt(j++);
        final boolean[] individualBullets = csv.getColumnBoolean(j++);
        
        toReturn[rowCount] = null;
        for (int i = 0; i < rowCount; i++)
        {
            toReturn[i] = new FirearmAction2(displayNames[i],
                                      type[i],
                                      soundOpen[i],
                                      soundClose[i],
                                      openDuration[i],
                                      closeDuration[i],
                                      closeShootDelay[i],
                                      individualBullets[i]);
        }
        System.out.println("Initialized " + rowCount + " firearm actions.");
        return toReturn;
    }

    public class FirearmAction2 extends CSVValue
    {
        private final String type;
        private final String soundOpen;
        private final String soundClose;

        private final int openDuration;
        private final int closeDuration;
        private final int closeShootDelay;
        private final Boolean individualBullets;

        private FirearmAction2(final String displayName,
                               final String type,
                               final String soundOpen,
                               final String soundClose,
                               final int openDuration,
                               final int closeDuration,
                               final int closeShootDelay,
                               final boolean individualBullets)
        {
            super(displayName);
            this.type = type;
            this.soundOpen = soundOpen;
            this.soundClose = soundClose;
            this.openDuration = openDuration;
            this.closeDuration = closeDuration;
            this.closeShootDelay = closeShootDelay;
            this.individualBullets = individualBullets;
        }

        public String  getSoundOpen()         { return soundOpen;         } 
        public String  getSoundClose()        { return soundClose;        }
        public int     getOpenDuration()      { return openDuration;      }
        public int     getCloseDuration()     { return closeDuration;     }
        public int     getCloseShootDelay()   { return closeShootDelay;   }
        public Boolean getIndividualBullets() { return individualBullets; }
        public String toString()    { return type;              }

    }
}
