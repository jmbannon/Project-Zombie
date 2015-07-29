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
public enum FirearmAction
{
    SNIPER_BOLT  ("bolt",  "VILLAGER_DEATH-1-1-0", "VILLAGER_HAGGLE-1-1-0",    20, 16, 6,  false),
    HUNTING_BOLT ("bolt",  "VILLAGER_DEATH-1-1-0", "VILLAGER_HAGGLE-1-1-0",    20, 16, 6,  true),
    SMG_SLIDE    ("slide", null,                   null,                       0,  7,  11, false),
    SLIDE        ("slide", null,                   null,                       0,  10, 12, false),
    BREAK        ("break", "AMBIENCE_THUNDER-1-1-0", "AMBIENCE_THUNDER-1-1-0", 0,  12, 12, true),
    PUMP         ("break", "AMBIENCE_THUNDER-1-1-0", "HORSE_ANGRY-1-1-0",      0,  12, 12, true);
    
    //Action, SoundOpen, SoundClose, delay, open, close
    private final String type, soundOpen, soundClose;
    private final int openDuration, closeDuration, closeShootDelay;
    private final Boolean individualBullets;
    
    private FirearmAction(final String type,
                          final String soundOpen,
                          final String soundClose,
                          final int openDuration,
                          final int closeDuration,
                          final int closeShootDelay,
                          final boolean individualBullets)
    {
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
    
    @Override public String toString()    { return type;              }
}
