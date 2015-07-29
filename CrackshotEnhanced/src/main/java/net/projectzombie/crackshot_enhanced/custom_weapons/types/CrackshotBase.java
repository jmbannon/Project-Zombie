/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.types;

import static net.projectzombie.crackshot_enhanced.custom_weapons.types.Weapon.*;

/**
 *
 * @author jbannon
 */
public enum CrackshotBase
{
    //      Name,                  Type,     Id/byt,Delay,Dur,Dmg,Recoil, ShootSound,            ShootSilenced,  ParticleShoot,Mag, RelDur, ReloadSound
    DEAGLE ("Desert Eagle",        PISTOL,   293, 0,  6,  120, 8,  0, "BLAZE_DEATH-6-1-0",       null,              null,       7,  40, "WITHER_SPAWN-1-1-0"),
    COLT45 ("Colt 45"     ,        PISTOL,   338, 0,  5,  120, 8,  0, "ENDERDRAGON_GROWL-5-1-0", null,              null,       7,  30, "WITHER_IDLE-1-1-2"),
    P228   ("P228"        ,        PISTOL,   351, 8,  5,  120, 8,  0, "MAGMACUBE_WALK-4-1-0",    "BLAZE_HIT-1-1-0", null,       15, 30, "WITHER_IDLE-1-1-2"),
    USP    ("USP"         ,        PISTOL,   292, 0,  5,  120, 8,  0, "MAGMACUBE_WALK-4-3-0",    "BLAZE_HIT-1-1-0", null,       12, 30, "WITHER_IDLE-1-1-2"),
             
    MAG44   ("44 Magnum"  ,        REVOLVER, 294, 0,  9,  120, 23, 0, "DONKEY_HIT-5-1-0",        null,              "FLAMES-1", 6,  72, "AMBIENCE_RAIN-1-1-0"),
    FRANK   ("Dirty Frank",        REVOLVER, 336, 0,  6,  120, 23, 0, "DONKEY_HIT-5-1-0",        null,              "FLAMES-1", 6,  72, "AMBIENCE_RAIN-1-1-0"),
            
    SCOUT   ("Scout",              SNIPER,   351, 9,  0,  120, 15, 0, "PIG_IDLE-4-1-0",          "BAT_IDLE-2-1-1", null,        10, 40, "WITHER_SHOOT-1-1-0"),
    AWP     ("AWP"  ,              SNIPER,   278, 0,  0,  120, 23, 1, "BLAZE_BREATH-9-1-0",       null,            "FLAMES-1",  5,  40, "WITHER_SHOOT-1-1-0"),
           
    G3SG1 ("G3SG1",                AUTO_S,   277, 0,  11, 120, 12, 0, "ANVIL_BREAK-7-1-1",        "BAT_IDLE-2-1-1", null,       20, 55, "SHEEP_IDLE-1-1-0"),
    DRAG  ("Dragonuv",             AUTO_S,   351, 14, 17, 120, 16, 0, "SKELETON_IDLE-6-1-0",      null,             "FLAMES-1", 10, 40, "WITHER_SHOOT-1-1-0"),
      
    REM    ("Remington 700",       HUNTING,  351, 12, 0,  120, 15, 0, "PIG_WALK-6-1-0",           null,             "FLAMES-1", 6,  13, "SHEEP_SHEAR-1-1-0"),
    SPRING ("M1903 Springfield",   HUNTING,  351, 13, 0,  120, 15, 0, "SKELETON_DEATH-6-1-0",     null,             "FLAMES-1", 6,  13, "SHEEP_SHEAR-1-1-0"),
    MODEL  ("Winchester Model 70", HUNTING,  279, 0,  0,  120, 15, 0, "SHEEP_WALK-5-1-0",         null,             "FLAMES-1", 5,  13, "SHEEP_SHEAR-1-1-0"),
    
    MAC10 ("Mac 10",               SMG,      351, 6,  4,  120, 8,  0, "SLIME_ATTACK-5-1-0",       "BAT_HURT-1-1-1", null,       30, 30, "WITHER_IDLE-1-1-0"),
    UMP45 ("Ump 45",               SMG,      351, 10, 6,  120, 8,  0, "SLIME_WALK-6-1-1",         "BAT_HURT-1-1-1", null,       30, 40, "WITHER_IDLE-1-1-0"),
    P90   ("P90",                  SMG,      351, 7,  4,  120, 8,  0, "SKELETON_WALK-6-1-0",       null,            null,       50, 40, "WITHER_IDLE-1-1-0"),
    MP5   ("MP5",                  SMG,      285, 0,  5,  120, 8,  0, "BAT_DEATH-6-1-1",          "BAT_HURT-1-1-1", null,       30, 40, "WITHER_IDLE-1-1-0"),
    TMP   ("TMP",                  SMG,      284, 0,  4,  120, 8,  0, "SLIME_ATTACK-5-1-0",       "BAT_HURT-1-1-1", null,       30, 30, "WITHER_IDLE-1-1-0"),
    
    SG552 ("SG552",                ASSAULT,  351, 11, 5,  120, 10, 0, "ENDERDRAGON_DEATH-6-1-1",  null,             null,       30, 45, "WITHER_SHOOT-1-1-0"),
    AUG   ("AUG",                  ASSAULT,  348, 0,  6,  120, 10, 0, "DONKEY_IDLE-6-1-0",        "BAT_IDLE-2-1-1", null,       30, 45, "SHEEP_IDLE-1-1-0"),
    AK47  ("AK-47",                ASSAULT,  281, 0,  5,  120, 10, 0, "ANVIL_USE-7-1-1",          null,             null,       30, 40, "SHEEP_IDLE-1-1-0"),
    M16   ("M16",                  ASSAULT,  362, 0,  6,  120, 10, 0, "ANVIL_BREAK-7-1-1",        "BAT_IDLE-2-1-1", null,       30, 40, "SHEEP_IDLE-1-1-2"),
    GALIL ("Galil",                ASSAULT,  351, 2,  5,  120, 10, 0, "GHAST_CHARGE-7-1-0",        null,            null,       30, 45, "SHEEP_IDLE-1-1-2"),
    FAMAS ("Famas",                ASSAULT,  351, 4,  6,  120, 10, 0, "ENDERDRAGON_HIT-6-1-0",    "BAT_IDLE-2-1-1", null,       30, 45, "SHEEP_IDLE-1-1-2"),
    M4A1  ("M4A1",                 ASSAULT,  271, 0,  6,  120, 10, 0, "ENDERDRAGON_HIT-6-1-2",    "BAT_IDLE-2-1-1", null,       30, 40, "SHEEP_IDLE-1-1-2"),
    SAW   ("SAW",                  ASSAULT,  274, 0,  4,  120, 10, 0, "CREEPER_DEATH-9-1-0",      null,             "FLAMES-1", 100,120,"HORSE_BREATHE-1-1-0"),
    
    OLYMPIA ("Olympia",            SH_BREAK, 91,  0,  3,  120, 7,  0, "GHAST_DEATH-5-1-0",        null,             "FLAMES-1", 2,  12, "VILLAGER_YES-1-1-0"),
    AA12    ("AA-12",              SH_SLIDE, 286, 0,  3,  120, 7,  0, "GHAST_MOAN-6-1-0",         null,             "FLAMES-1", 8,  40, "HORSE_ARMOR-1-1-0"),
    M3      ("M3",                 SH_PUMP,  351, 5,  15, 120, 7,  0, "GHAST_SCREAM-5-1-0",       null,             "FLAMES-1", 6,  12, "VILLAGER_YES-1-1-0"),
    XM1014  ("XM1014",             SH_BREAK, 290, 0,  4,  120, 7,  0, "GHAST_FIREBALL-5-1-0",     null,             "FLAMES-1", 6,  12, "VILLAGER_YES-1-1-0");
    
    private final Weapon weaponType;
    private final int itemID, itemData, shootDelay, maxDurability, damage, recoilAmount, reloadAmount, reloadDuration;
    private final String name, shootSound, silencedSound, shootParticle, reloadSound;
    
    private CrackshotBase(final String name,
                          final Weapon weaponType,
                          final int materialID,
                          final int materialData,
                          final int delay_between_shots,
                          final int maxDurability,
                          final int damage,
                          final int recoilAmount,
                          
                          final String sounds_shoot,
                          final String sounds_silenced,
                          final String particle_shoot,
                          
                          final int reload_amount,
                          final int reload_duration,
                          final String sounds_reloading)
    {
        this.name = name;
        this.weaponType = weaponType;
        this.itemID = materialID;
        this.itemData = materialData;
        this.shootDelay = delay_between_shots;
        this.maxDurability = maxDurability;
        this.damage = damage;
        this.recoilAmount = recoilAmount;    
        this.shootSound = sounds_shoot;
        this.silencedSound = sounds_silenced;
        this.shootParticle = particle_shoot;
        this.reloadAmount = reload_amount;
        this.reloadDuration = reload_duration;
        this.reloadSound = sounds_reloading;
    }
    
    public Weapon   getWeaponType()     { return weaponType;     }
    public int      getItemID()         { return itemID;       }
    public int      getItemData()       { return itemData;   }
    public int      getShootDelay()     { return shootDelay;     }
    public int      getMaxDurability()  { return maxDurability;  }
    public int      getDamage()         { return damage;         }
    public int      getRecoil()         { return recoilAmount;   }
    public String   getShootSound()     { return shootSound;     }
    public String   getSilencedSound()  { return silencedSound;  }
    public String   getShootParticle()  { return shootParticle;  }
    public int      getReloadAmount()   { return reloadAmount;   }
    public int      getReloadDuration() { return reloadDuration; }
    public String   getReloadSound()    { return reloadSound;    }
    
    @Override public String toString()  { return name;           }
}
