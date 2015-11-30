/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.types;

import net.projectzombie.crackshot_enhanced.custom_weapons.utilities.Sounds;
import static net.projectzombie.crackshot_enhanced.custom_weapons.utilities.Sounds.*;

/**
 *
 * @author jbannon
 */
public enum FirearmAction
{
    SNIPER_BOLT  ("bolt",  BOLT_OPEN, BOLT_CLOSE,    20, 16, 6,  false),
    HUNTING_BOLT ("bolt",  BOLT_OPEN, BOLT_CLOSE,  20, 16, 6,  true),
    SMG_SLIDE    ("slide", null,      null,                           0,  7,  11, false),
    SLIDE        ("slide", null,      null,                                0,  10, 12, false),
    BREAK        ("break", REL_BREAK, REL_BREAK,          0,  12, 12, true),
    PUMP         ("pump",  REL_BREAK, REL_PUMP,           0,  12, 12, true);
    
    //Action, SoundOpen, SoundClose, delay, open, close
    private final String soundOpen, soundClose;
    private final String type;
    private final int openDuration, closeDuration, closeShootDelay;
    private final Boolean individualBullets;
    
    private FirearmAction(final String type,
                          final Sounds soundOpen,
                          final Sounds soundClose,
                          final int openDuration,
                          final int closeDuration,
                          final int closeShootDelay,
                          final boolean individualBullets)
    {
        this.type = type;
        this.soundOpen = (soundOpen != null) ? soundOpen.toString() : null;
        this.soundClose = (soundClose != null) ? soundClose.toString() : null;
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
    
    @Override public String toString()    { return type;              }
}
