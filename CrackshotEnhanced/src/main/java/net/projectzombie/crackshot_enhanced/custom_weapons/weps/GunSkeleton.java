/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.weps;

import net.projectzombie.crackshot_enhanced.custom_weapons.utilities.Particles;
import net.projectzombie.crackshot_enhanced.custom_weapons.utilities.Sounds;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.GunModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Scope;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.FireMode;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Attatchment;
import java.util.ArrayList;
import net.projectzombie.crackshot_enhanced.custom_weapons.types.Weapon;
import static net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Attatchment.*;
import static net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.FireMode.*;
import static net.projectzombie.crackshot_enhanced.custom_weapons.utilities.Particles.*;
import static net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Scope.*;
import static net.projectzombie.crackshot_enhanced.custom_weapons.utilities.Sounds.*;
import static net.projectzombie.crackshot_enhanced.custom_weapons.types.Weapon.*;

/**
 *
 * @author jbannon
 * Contains standard Crackshot guns with no modifiers (i.e. scopes, 
 * 
 */
public enum GunSkeleton
{
    //      Name,                   Type,    Id/byt, BS, Delay,Dur,Dmg,Recoil,Shoot,   Sil,  Part,  Mag, RelDur, ReloadSound
    DEAGLE ("Desert Eagle",        PISTOL,   293, 0, 1.101, 6,  120, 8,  0, FIRE_DEAGLE, null,  null, 7,  40, REL_DEG, new GunModifier[] { SEMI, IRON, ACOG, NA, REL, EXT, INC}),
    COLT45 ("Colt 45"     ,        PISTOL,   338, 0, 1.40,  5,  120, 8,  0, FIRE_COLT45, null,  null, 7,  30, REL_SMALLMAG, new GunModifier[] { SEMI, IRON, ACOG, NA, EXT, REL, INC}),
    P228   ("P228"        ,        PISTOL,   351, 8, 1.19,  5,  120, 8,  0, FIRE_P228,   SIL_1, null, 15, 30, REL_SMALLMAG, new GunModifier[] { SEMI, BURST, IRON, ACOG, NA, SUP, REL, EXT, INC}),
    USP    ("USP"         ,        PISTOL,   292, 0, 1.32,  5,  120, 8,  0, FIRE_USP,    SIL_1, null, 12, 30, REL_SMALLMAG, new GunModifier[] { SEMI, BURST, IRON, ACOG, SUP, NA, REL, EXT, INC}),
             
    MAG44   ("44 Magnum"  ,        REVOLVER, 294, 0,  0.78, 9,  120, 23, 0, FIRE_44MAG,  null, FLAM, 6,  72, REL_MAGNUM, new GunModifier[] { SEMI, IRON, ACOG, NA, REL, INC}),
    FRANK   ("Dirty Frank",        REVOLVER, 336, 0,  0.50, 6,  120, 23, 0, FIRE_FRANK,  null, FLAM, 6,  72, REL_MAGNUM, new GunModifier[] { SEMI, ACOG, TACT, IRON, NA, REL, INC}),
            
    SCOUT   ("Scout",              SNIPER,   351, 9,  0.43, 0,  120, 15, 0, FIRE_SCOUT,  SIL_2, null,  10, 40, REL_SNIPER, new GunModifier[] { BOLT, LONG, TACT, ACOG, IRON, NA, SUP, LUB, EXT, INC}),
    AWP     ("AWP"  ,              SNIPER,   278, 0,  0.31, 0,  120, 23, 1, FIRE_AWP,     null,  FLAM,  5,  40, REL_SNIPER, new GunModifier[] { BOLT, LONG, TACT, ACOG, IRON, NA, REL, EXT, LUB, INC}),
           
    G3SG1 ("G3SG1",                AUTO_S,   277, 0,  0.48, 11, 120, 12, 0, FIRE_G3,      SIL_2, null, 20, 55, REL_ASSAULT, new GunModifier[] { SEMI, LONG, TACT, ACOG, IRON, SUP, NA, REL, EXT, INC}),
    DRAG  ("Dragonuv",             AUTO_S,   351, 14, 0.46, 17, 120, 16, 0, FIRE_DRAG,   null, FLAM, 10, 40, REL_SNIPER, new GunModifier[] { SEMI, LONG, TACT, ACOG, IRON, NA, REL, EXT, INC}),
      
    REM    ("Remington 700",       HUNTING,  351, 12, 0.26, 0,  120, 15, 0, FIRE_REM,     null, FLAM, 6, 13, REL_HUNTING, new GunModifier[] { BOLT, LONG, TACT, ACOG, IRON, NA, EXT, REL, LUB, INC}),
    SPRING ("M1903 Springfield",   HUNTING,  351, 13, 0.52, 0,  120, 15, 0, FIRE_SPRING, null, FLAM, 6, 13, REL_HUNTING, new GunModifier[] { BOLT, IRON, ACOG, TACT, LONG, NA, EXT, REL, LUB, INC}),
    MODEL  ("Winchester Model 70", HUNTING,  279, 0,  0.41, 0,  120, 15, 0, FIRE_MODEL,  null, FLAM, 5, 13, REL_HUNTING, new GunModifier[] { BOLT, LONG, TACT, ACOG, IRON, NA, REL, LUB, EXT, INC}),
    
    MAC10 ("Mac 10",               SMG,      351, 6,  1.25, 4,  120, 8,  0, FIRE_MAC10,  SIL_3, null, 30, 30, REL_SMALLMAG, new GunModifier[] { AUTO, BURST, SEMI, IRON, ACOG, NA, SUP, EXT, REL, INC}),
    UMP45 ("Ump 45",               SMG,      351, 10, 0.99, 6,  120, 8,  0, FIRE_UMP45,  SIL_3, null, 30, 40, REL_SMALLMAG, new GunModifier[] { AUTO, BURST, SEMI, IRON, ACOG, NA, SUP, EXT, REL, INC}),
    P90   ("P90",                  SMG,      351, 7,  1.16, 4,  120, 8,  0, FIRE_P90,    null,  null, 50, 40, REL_SMALLMAG, new GunModifier[] { AUTO, BURST, SEMI, IRON, ACOG, NA, EXT, REL, INC}),
    MP5   ("MP5",                  SMG,      285, 0,  0.93, 5,  120, 8,  0, FIRE_MP5,    SIL_3, null, 30, 40, REL_SMALLMAG, new GunModifier[] { SEMI, BURST, AUTO, IRON, ACOG, TACT, NA, SUP, EXT, REL, INC}),
    TMP   ("TMP",                  SMG,      284, 0,  1.105, 4,  120, 8,  0, FIRE_TMP,    SIL_3, null, 30, 30, REL_SMALLMAG, new GunModifier[] { AUTO, BURST, SEMI, IRON, ACOG, SUP, NA, EXT, REL, INC}),
    
    SG552 ("SG552",                ASSAULT,  351, 11, 0.61, 5,  120, 10, 0, FIRE_SG552, null,  null, 30, 45, REL_SNIPER, new GunModifier[] { AUTO, BURST, SEMI, ACOG, IRON, TACT, NA, EXT, REL, INC}),
    AUG   ("AUG",                  ASSAULT,  348, 0,  0.55, 6,  120, 10, 0, FIRE_AUG,   SIL_2, null, 30, 45, REL_ASSAULT, new GunModifier[] { AUTO, BURST, SEMI, ACOG, IRON, TACT, LONG, NA, EXT, REL, SUP, INC}),
    AK47  ("AK-47",                ASSAULT,  281, 0,  0.765, 5,  120, 10, 0, FIRE_AK47,  null,  null, 30, 40, REL_ASSAULT, new GunModifier[] { AUTO, BURST, SEMI, IRON, ACOG, TACT, NA, EXT, REL, INC}),
    M16   ("M16",                  ASSAULT,  362, 0,  0.598, 6,  120, 10, 0, FIRE_M16,   SIL_2, null, 30, 40, REL_ASSAULT, new GunModifier[] { AUTO, BURST, SEMI, IRON, ACOG, TACT, LONG, NA, SUP, REL, EXT, INC}),
    GALIL ("Galil",                ASSAULT,  351, 2,  0.85,  5,  120, 10, 0, FIRE_GALIL, null,  null, 30, 45, REL_ASSAULT, new GunModifier[] { AUTO, BURST, SEMI, IRON, ACOG, TACT, NA, EXT, REL, INC}),
    FAMAS ("Famas",                ASSAULT,  351, 4,  0.697, 6,  120, 10, 0, FIRE_FAMAS, SIL_2, null, 30, 45, REL_ASSAULT, new GunModifier[] { AUTO, BURST, SEMI, IRON, ACOG, TACT, LONG, NA, SUP, REL, EXT, INC}),
    M4A1  ("M4A1",                 ASSAULT,  271, 0,  0.704, 6,  120, 10, 0, FIRE_M4A1,  SIL_2, null, 30, 40, REL_ASSAULT, new GunModifier[] { AUTO, BURST, SEMI, IRON, ACOG, TACT, LONG, SUP, NA, REL, EXT, INC}),
    SAW   ("SAW",                  ASSAULT,  274, 0,  1.215, 4,  120, 10, 0, FIRE_SAW,  null,  FLAM, 100,120, REL_SAW, new GunModifier[] { AUTO, BURST, SEMI, IRON, ACOG, TACT, NA, EXT, REL, INC}),
    
    OLYMPIA ("Olympia",            SH_BREAK, 91,  0, 2.43, 3,  120, 7,  0, FIRE_OLYM,  null,  FLAM, 2,  12, REL_SHOTGUN, new GunModifier[] { SING, IRON, ACOG, NA, SO, REL, INC}),
    AA12    ("AA-12",              SH_SLIDE, 286, 0, 2.0,  3,  120, 7,  0, FIRE_AA12,  null,  FLAM, 8,  40, REL_AA12, new GunModifier[] { SEMI, IRON, ACOG, NA, SO, REL, EXT, INC}),
    M3      ("M3",                 SH_PUMP,  351, 5, 1.88, 15, 120, 7,  0, FIRE_M3,    null,  FLAM, 6,  12, REL_SHOTGUN, new GunModifier[] { PUMP, IRON, ACOG, NA, SO, REL, EXT, INC}),
    XM1014  ("XM1014",             SH_BREAK, 290, 0, 2.2,  4,  120, 7,  0, FIRE_XM,    null, FLAM, 6,  12, REL_SHOTGUN, new GunModifier[] { SEMI, IRON, ACOG, NA, SO, REL, EXT, INC});
    
    private final String weaponName;
    private final Weapon weaponType;
    private final double bulletSpread;
    private final GunModifier[] modifiers;
    private final Particles particleShoot;
    
    private final int
            itemID, 
            itemData, 
            shootDelay, 
            maxDurability, 
            damage, 
            recoilAmount, 
            reloadAmount, 
            reloadDuration;
    
    private final Sounds
            soundShoot,
            soundSilenced,
            soundReload;
    
    private GunSkeleton(final String name,
                          final Weapon weaponType,
                          final int materialID,
                          final int materialData,
                          final double initialBulletSpread,
                          final int delay_between_shots,
                          final int maxDurability,
                          final int damage,
                          final int recoilAmount,
                          
                          final Sounds sounds_shoot,
                          final Sounds sounds_silenced,
                          final Particles particle_shoot,
                          
                          final int reload_amount,
                          final int reload_duration,
                          final Sounds sounds_reloading,
                          final GunModifier[] modifiers)
    {
        this.weaponName = name;
        this.weaponType = weaponType;
        this.itemID = materialID;
        this.itemData = materialData;
        this.shootDelay = delay_between_shots;
        this.maxDurability = maxDurability;
        this.bulletSpread = initialBulletSpread;
        this.damage = damage;
        this.recoilAmount = recoilAmount;    
        this.soundShoot = sounds_shoot;
        this.soundSilenced = sounds_silenced;
        this.particleShoot = particle_shoot;
        this.reloadAmount = reload_amount;
        this.reloadDuration = reload_duration;
        this.soundReload = sounds_reloading;
        this.modifiers = modifiers;
    }
    
    public String   getName()           { return weaponName;           }
    public String   getFileName()       { return this.name().toLowerCase(); }
    public Weapon   getWeaponType()     { return weaponType;     }
    public double   getBulletSpread()   { return bulletSpread;   }
    public int      getItemID()         { return itemID;         }
    public int      getItemData()       { return itemData;       }
    public int      getShootDelay()     { return shootDelay;     }
    public int      getMaxDurability()  { return maxDurability;  }
    public int      getDamage()         { return damage;         }
    public int      getRecoil()         { return recoilAmount;   }
    public String   getShootSound()     { return soundShoot.toString();    }
    public String   getSilencedSound()  { return soundSilenced.toString(); }
    public String   getShootParticle()  { return particleShoot.toString(); }
    public int      getReloadAmount()   { return reloadAmount;   }
    public int      getReloadDuration() { return reloadDuration; }
    public String   getReloadSound()    { return soundReload.toString();   }
    public GunModifier[] getModifiers() { return modifiers; }

    public CrackshotGun[] getGuns(final int uniqueIDOffset)
    {
        final ArrayList<FireMode>    fireModes    = getFireModes();
        final ArrayList<Scope>       scopes       = getScopes();
        final ArrayList<Attatchment> attatchments = getAttatchments();
        
        final int combinationCount = fireModes.size() * scopes.size() * attatchments.size();
        int i = 0;
        
        if (combinationCount <= 0)
            return null;
        
        CrackshotGun guns[] = new CrackshotGun[combinationCount];

        for (FireMode fireMode : fireModes)
        {
            for (Scope scope : scopes)
            {
                for (Attatchment attatchment : attatchments)
                {
                    guns[i] = new CrackshotGun(uniqueIDOffset + i, this, fireMode, scope, attatchment);
                    ++i;
                }
            }
        }
        return guns;
    }
    
    public CrackshotGun getModifiedGun(final CrackshotGun gun,
                                       final GunModifier modifier)
    {
        int gunIndex = -1;
        int modIndex = -1;
        int i = 0;
        
        if (!containsMod(modifier))
            return null;
        
        for (FireMode fireMode : getFireModes())
        {
            for (Scope scope : getScopes())
            {
                for (Attatchment attatchment : getAttatchments())
                {
                    if (gun.containsMods(fireMode, scope, attatchment))
                        gunIndex = i;
                    else if (modifier instanceof FireMode && gun.containsMods((FireMode)modifier, scope, attatchment))
                        modIndex = i;
                    else if (modifier instanceof Scope && gun.containsMods(fireMode, (Scope)modifier, attatchment))
                        modIndex = i;
                    else if (modifier instanceof Attatchment && gun.containsMods(fireMode, scope, (Attatchment)modifier))
                        modIndex = i;
                }
            }
        }
        
        if (gunIndex != -1 && modIndex != -1)
            return Guns.get(gun.getUniqueId() + (modIndex - gunIndex));
        else
            return null;
    }
    
    private ArrayList<FireMode> getFireModes()
    {

        final ArrayList<FireMode> fireModes = new ArrayList<>();
        for (GunModifier mod : modifiers)
        {
            if (mod instanceof FireMode && !fireModes.contains((FireMode)mod))
                fireModes.add((FireMode)mod);
        }
        return fireModes;
    }
   
    private ArrayList<Scope> getScopes()
    {

        final ArrayList<Scope> scopes = new ArrayList<>();
        for (GunModifier mod : modifiers)
        {
            if (mod instanceof Scope && !scopes.contains((Scope)mod))
                scopes.add((Scope)mod);
        }
        return scopes;
    }
    
    private ArrayList<Attatchment> getAttatchments()
    {

        final ArrayList<Attatchment> attatchments = new ArrayList<>();
        for (GunModifier mod : modifiers)
        {
            if (mod instanceof Attatchment && !attatchments.contains((Attatchment)mod))
                attatchments.add((Attatchment)mod);
        }
        return attatchments;
    }
    
    
    private boolean containsMod(final GunModifier modifier)
    {
        boolean containsMod = false;
        for (GunModifier mod : modifiers)
        {
            if (mod == modifier)
                containsMod = true;
        }
        return containsMod;
    }
}
