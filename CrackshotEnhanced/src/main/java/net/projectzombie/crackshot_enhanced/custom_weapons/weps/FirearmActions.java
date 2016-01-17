/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.weps;

import net.projectzombie.crackshot_enhanced.custom_weapons.csv.CSVReader;
import net.projectzombie.crackshot_enhanced.custom_weapons.csv.CSVInput;
import net.projectzombie.crackshot_enhanced.custom_weapons.csv.CSVValue;
import net.projectzombie.crackshot_enhanced.custom_weapons.weps.FirearmActions.FirearmAction;


/**
 *
 * @author jbannon
 */
public class FirearmActions extends CSVInput<FirearmAction>
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
        "Close Shoot Delay (INT)"
    };
    
    private FirearmActions()
    {
        super(ACTIONS_CSV_NAME, buildFirearmActions(), ACTION_VALUES);
    }
    
    static private FirearmAction[] buildFirearmActions()
    {
        final CSVReader csv = new CSVReader(ACTIONS_CSV_NAME, ACTION_VALUES);
        final int rowCount = csv.getRowCount();
        
        if (rowCount <= 0)
        {
            return new FirearmAction[] { null };
        }
        
        
        int j = 0;
        final FirearmAction[] toReturn   = new FirearmAction[rowCount + 1];
        final String[]  displayNames      = csv.getColumnString(j++);
        final String[]  type              = csv.getColumnString(j++);
        final String[]  soundOpen         = csv.getColumnString(j++);
        final String[]  soundClose        = csv.getColumnString(j++);
        final int[]     openDuration      = csv.getColumnInt(j++);
        final int[]     closeDuration     = csv.getColumnInt(j++);
        final int[]     closeShootDelay   = csv.getColumnInt(j++);
        
        toReturn[rowCount] = null;
        for (int i = 0; i < rowCount; i++)
        {
            toReturn[i] = new FirearmAction(displayNames[i],
                                      type[i],
                                      soundOpen[i],
                                      soundClose[i],
                                      openDuration[i],
                                      closeDuration[i],
                                      closeShootDelay[i]);
        }
        return toReturn;
    }

    static public class FirearmAction extends CSVValue
    {
        private final String type;
        private final String soundOpen;
        private final String soundClose;

        private final int openDuration;
        private final int closeDuration;
        private final int closeShootDelay;

        private FirearmAction(final String displayName,
                               final String type,
                               final String soundOpen,
                               final String soundClose,
                               final int openDuration,
                               final int closeDuration,
                               final int closeShootDelay)
        {
            super(displayName);
            this.type = type;
            this.soundOpen = soundOpen;
            this.soundClose = soundClose;
            this.openDuration = openDuration;
            this.closeDuration = closeDuration;
            this.closeShootDelay = closeShootDelay;
        }

        public String  getSoundOpen()         { return soundOpen;         } 
        public String  getSoundClose()        { return soundClose;        }
        public int     getOpenDuration()      { return openDuration;      }
        public int     getCloseDuration()     { return closeDuration;     }
        public int     getCloseShootDelay()   { return closeShootDelay;   }
        @Override public String toString()    { return type;              }

    }
}
