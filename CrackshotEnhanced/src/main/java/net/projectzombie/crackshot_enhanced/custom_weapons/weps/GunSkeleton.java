/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.weps;

import java.util.Arrays;
import net.projectzombie.crackshot_enhanced.custom_weapons.utilities.Particles;
import net.projectzombie.crackshot_enhanced.custom_weapons.utilities.Sounds;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types.GunModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Scope;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.FireMode;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Attatchment;
import net.projectzombie.crackshot_enhanced.custom_weapons.types.Weapon;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Barrel;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Bolt;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Magazine;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.ModifierSet;
import static net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.ModifierSet.*;
import static net.projectzombie.crackshot_enhanced.custom_weapons.utilities.Particles.*;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Stock;
import static net.projectzombie.crackshot_enhanced.custom_weapons.utilities.Sounds.*;
import static net.projectzombie.crackshot_enhanced.custom_weapons.types.Weapon.*;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author jbannon
 * Contains standard Crackshot guns with no modifiers (i.e. scopes, 
 * 
 */
public enum GunSkeleton
{
    //      Name,                   Type,    Id/byt, BS, Delay,Dur,Dmg,Recoil,Shoot,   Sil,  Part,  Mag, RelDur, ReloadSound
    DEAGLE ("Desert Eagle",        PISTOL,   293, 0, 1.101, 6,  120, 8,  0,  FIRE_DEAGLE, null,  null, 7,  40,  REL_DEG, PISTOL_1),
    COLT45 ("Colt 45"     ,        PISTOL,   338, 0, 1.40,  5,  120, 8,  0,  FIRE_COLT45, null,  null, 7,  30,  REL_SMALLMAG, PISTOL_1),
    P228   ("P228"        ,        PISTOL,   351, 8, 1.19,  5,  120, 8,  0,  FIRE_P228,   SIL_1, null, 15, 30,  REL_SMALLMAG, PISTOL_2),
    USP    ("USP"         ,        PISTOL,   292, 0, 1.32,  5,  120, 8,  0,  FIRE_USP,    SIL_1, null, 12, 30,  REL_SMALLMAG, PISTOL_2),
              
    MAG44   ("44 Magnum"  ,        REVOLVER, 294, 0,  0.78, 9,  120, 23, 0,  FIRE_44MAG,  null,  FLAM, 6,  72,  REL_MAGNUM, REVOLVER_1),
    FRANK   ("Dirty Frank",        REVOLVER, 336, 0,  0.50, 6,  120, 23, 0,  FIRE_FRANK,  null,  FLAM, 6,  72,  REL_MAGNUM, REVOLVER_2),
             
    SCOUT   ("Scout",              SNIPER,   351, 9,  0.43, 0,  120, 15, 0,  FIRE_SCOUT,  SIL_2, null, 10, 40,  REL_SNIPER,  BOLT_SNIPER_1),
    AWP     ("AWP"  ,              SNIPER,   278, 0,  0.31, 0,  120, 23, 1,  FIRE_AWP,    null,  FLAM, 5,  40,  REL_SNIPER,  BOLT_SNIPER_1),
            
    G3SG1 ("G3SG1",                AUTO_S,   277, 0,  0.48, 11, 120, 12, 0,  FIRE_G3,     SIL_2, null, 20, 55,  REL_ASSAULT, SEMI_SNIPER_1),
    DRAG  ("Dragonuv",             AUTO_S,   351, 14, 0.46, 17, 120, 16, 0,  FIRE_DRAG,   null,  FLAM, 10, 40,  REL_SNIPER, SEMI_SNIPER_1),
       
    REM    ("Remington 700",       HUNTING,  351, 12, 0.26, 0,  120, 15, 0,  FIRE_REM,     null, FLAM, 6,  13,  REL_HUNTING, BOLT_SNIPER_1),
    SPRING ("M1903 Springfield",   HUNTING,  351, 13, 0.52, 0,  120, 15, 0,  FIRE_SPRING, null,  FLAM, 6,  13,  REL_HUNTING,  BOLT_SNIPER_1),
    MODEL  ("Winchester Model 70", HUNTING,  279, 0,  0.41, 0,  120, 15, 0,  FIRE_MODEL,  null,  FLAM, 5,  13,  REL_HUNTING,  BOLT_SNIPER_1),
     
    MAC10 ("Mac 10",               SMG,      351, 6,  1.25, 4,  120, 8,  0,  FIRE_MAC10,  SIL_3, null, 30, 30,  REL_SMALLMAG,  SMG_1),
    UMP45 ("Ump 45",               SMG,      351, 10, 0.99, 6,  120, 8,  0,  FIRE_UMP45,  SIL_3, null, 30, 40,  REL_SMALLMAG,  SMG_1),
    P90   ("P90",                  SMG,      351, 7,  1.16, 4,  120, 8,  0,  FIRE_P90,    null,  null, 50, 40,  REL_SMALLMAG,  SMG_3),
    MP5   ("MP5",                  SMG,      285, 0,  0.93, 5,  120, 8,  0,  FIRE_MP5,    SIL_3, null, 30, 40,  REL_SMALLMAG,  SMG_2),
    TMP   ("TMP",                  SMG,      284, 0,  1.105, 4,  120, 8,  0, FIRE_TMP,    SIL_3, null, 30, 30,  REL_SMALLMAG, SMG_1),
    
    SG552 ("SG552",                ASSAULT,  351, 11, 0.61,  5, 120, 10, 0,  FIRE_SG552, null,   null, 30, 45,  REL_SNIPER,  ASSUALT_2),
    AUG   ("AUG",                  ASSAULT,  348, 0,  0.55,  6, 120, 10, 0,  FIRE_AUG,   SIL_2,  null, 30, 45,  REL_ASSAULT, ASSUALT_1),
    AK47  ("AK-47",                ASSAULT,  281, 0,  0.765, 5, 120, 10, 0,  FIRE_AK47,  null,   null, 30, 40,  REL_ASSAULT, ASSUALT_2),
    M16   ("M16",                  ASSAULT,  362, 0,  0.598, 6, 120, 10, 0,  FIRE_M16,   SIL_2,  null, 30, 40,  REL_ASSAULT, ASSUALT_1),
    GALIL ("Galil",                ASSAULT,  351, 2,  0.85,  5, 120, 10, 0,  FIRE_GALIL, null,   null, 30, 45,  REL_ASSAULT, ASSUALT_2),
    FAMAS ("Famas",                ASSAULT,  351, 4,  0.697, 6, 120, 10, 0,  FIRE_FAMAS, SIL_2,  null, 30, 45,  REL_ASSAULT, ASSUALT_1),
    M4A1  ("M4A1",                 ASSAULT,  271, 0,  0.704, 6, 120, 10, 0,  FIRE_M4A1,  SIL_2,  null, 30, 40,  REL_ASSAULT,ASSUALT_1),
    SAW   ("SAW",                  ASSAULT,  274, 0,  1.215, 4, 120, 10, 0,  FIRE_SAW,   null,   FLAM, 100,120, REL_SAW,    ASSUALT_3),
    
    OLYMPIA ("Olympia",            SH_BREAK, 91,  0, 2.43, 3,  120, 7,  0,   FIRE_OLYM,  null,   FLAM, 2,  12,  REL_SHOTGUN, SHOTGUN_1),
    AA12    ("AA-12",              SH_SLIDE, 286, 0, 2.0,  3,  120, 7,  0,   FIRE_AA12,  null,   FLAM, 8,  40,  REL_AA12,    SHOTGUN_2),
    M3      ("M3",                 SH_PUMP,  351, 5, 1.88, 15, 120, 7,  0,   FIRE_M3,    null,   FLAM, 6,  12,  REL_SHOTGUN, SHOTGUN_3),
    XM1014  ("XM1014",             SH_BREAK, 290, 0, 2.2,  4,  120, 7,  0,   FIRE_XM,    null,   FLAM, 6,  12,  REL_SHOTGUN,  SHOTGUN_4);
    
    private final String weaponName;
    private final Weapon weaponType;
    private final double bulletSpread;
    private final ModifierSet modSet;
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
    
    private final String
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
                          final ModifierSet set)
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
        this.soundShoot = (sounds_shoot != null) ? sounds_shoot.toString() : null;
        this.soundSilenced = (sounds_silenced != null) ? sounds_silenced.toString() : null;
        this.particleShoot = particle_shoot;
        this.reloadAmount = reload_amount;
        this.reloadDuration = reload_duration;
        this.soundReload = (sounds_reloading != null) ? sounds_reloading.toString() : null;
        this.modSet = set;
    }
    
    public String        getName()           { return weaponName;           }
    public String        getFileName()       { return this.name().toLowerCase(); }
    public Weapon        getWeaponType()     { return weaponType;     }
    public double        getBulletSpread()   { return bulletSpread;   }
    public int           getItemID()         { return itemID;         }
    public int           getItemData()       { return itemData;       }
    public Material      getItemMaterial()   { return Material.getMaterial(itemID); }
    public int           getShootDelay()     { return shootDelay;     }
    public int           getMaxDurability()  { return maxDurability;  }
    public int           getDamage()         { return damage;         }
    public int           getRecoil()         { return recoilAmount;   }
    public String        getShootSound()     { return soundShoot;    }
    public String        getSilencedSound()  { return soundSilenced; }
    public String        getShootParticle()  { return particleShoot.toString(); }
    public int           getReloadAmount()   { return reloadAmount;   }
    public int           getReloadDuration() { return reloadDuration; }
    public String        getReloadSound()    { return soundReload;   }
    public ItemStack     getBareItemStack()  { return new ItemStack(itemID, 1, (short)itemData); }
    public ModifierSet   getModifierSet()    { return modSet; }
    public GunModifier[] getModifiers()      { return modSet.getModifiers(); }
    
    public CrackshotGun[] getGuns(final int uniqueIDOffset)
    {
        final int combinationCount = modSet.getCombinationCount();
        int i = 0;
        
        if (combinationCount <= 0)
            return null;
        
        CrackshotGun guns[] = new CrackshotGun[combinationCount];

        for (Attatchment attatchment : modSet.getAttatchments())
        {
            for (Barrel barrel : modSet.getBarrels())
            {
                for (Bolt bolt : modSet.getBolts())
                {
                    for (FireMode fireMode : modSet.getFireModes())
                    {
                        for (Magazine magazine : modSet.getMagazines())
                        {
                            for (Scope scope : modSet.getScopes())
                            {
                                for (Stock stock : modSet.getStocks())
                                {
                                     guns[i] = new CrackshotGun(uniqueIDOffset, uniqueIDOffset + i, this, attatchment, barrel, bolt, fireMode, magazine, scope, stock);
                                     ++i;
                                }
                            }
                        }
                    }
                }
            }
        }
        return guns;
    }
    
    public CrackshotGun getModifiedGun(final CrackshotGun gun,
                                       final GunModifier modifier)
    {
        final int gunIDOffset = gun.getIDOffset();
        final int gunCombinationCount = gun.getSkeleton().getModifierSet().getCombinationCount();
        boolean containsModifier = false;
        
        GunModifier[] modifiedSet = gun.getCraftableModifiers();
        for (int i = 0; i < modifiedSet.length; i++)
        {
            if (modifiedSet[i].getClass() == modifier.getClass())
            {
                modifiedSet[i] = modifier;
                containsModifier = true;
                break;
            }
        }
        
        if (!containsModifier)
            return null;
        
        for (int i = gunIDOffset; i < gunIDOffset + gunCombinationCount; i++)
        {
            if (Arrays.equals(Guns.get(i).getCraftableModifiers(), modifiedSet))
                return Guns.get(i);
        }
        
        return null;
    }
    
    
    private boolean containsMod(final GunModifier modifier)
    {
        boolean containsMod = false;
        for (GunModifier mod : getModifiers())
        {
            if (mod.equals(modifier))
                containsMod = true;
        }
        
        return containsMod;
    }
}
