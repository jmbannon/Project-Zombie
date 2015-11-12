/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.utilities;

/**
 *
 * @author jesse
 */
public enum Sounds
{
    
    FIRE_DEAGLE("BLAZE_DEATH-6-1-0"),
    FIRE_COLT45("ENDERDRAGON_GROWL-5-1-0"),
    FIRE_P228  ("MAGMACUBE_WALK-4-1-0"),   
    FIRE_USP   ("MAGMACUBE_WALK-4-3-0"),   
    FIRE_44MAG ("DONKEY_HIT-5-1-0"),  
    FIRE_FRANK ("DONKEY_HIT-5-1-0"),          
    FIRE_SCOUT ("PIG_IDLE-4-1-0"),          
    FIRE_AWP   ("BLAZE_BREATH-9-1-0"),      
    FIRE_G3    ("ANVIL_BREAK-7-1-1"),        
    FIRE_DRAG  ("SKELETON_IDLE-6-1-0"),     
    FIRE_REM   ("PIG_WALK-6-1-0"),          
    FIRE_SPRING("SKELETON_DEATH-6-1-0"),    
    FIRE_MODEL ("SHEEP_WALK-5-1-0"),        
    FIRE_MAC10 ("SLIME_ATTACK-5-1-0"),      
    FIRE_UMP45 ("SLIME_WALK-6-1-1"),        
    FIRE_P90   ("SKELETON_WALK-6-1-0"),     
    FIRE_MP5   ("BAT_DEATH-6-1-1"),         
    FIRE_TMP   ("SLIME_ATTACK-5-1-0"),      
    FIRE_SG552 ("ENDERDRAGON_DEATH-6-1-1"),
    FIRE_AUG   ("DONKEY_IDLE-6-1-0"),      
    FIRE_AK47  ("ANVIL_USE-7-1-1"),        
    FIRE_M16   ("ANVIL_BREAK-7-1-1"),      
    FIRE_GALIL ("GHAST_CHARGE-7-1-0"),     
    FIRE_FAMAS ("ENDERDRAGON_HIT-6-1-0"),  
    FIRE_M4A1  ("ENDERDRAGON_HIT-6-1-2"),  
    FIRE_SAW   ("CREEPER_DEATH-9-1-0"),   
    FIRE_OLYM  ("GHAST_DEATH-5-1-0"),       
    FIRE_AA12  ("GHAST_MOAN-6-1-0"),        
    FIRE_M3    ("GHAST_SCREAM-5-1-0"),      
    FIRE_XM    ("GHAST_FIREBALL-5-1-0"),
    
    SIL_1 ("BLAZE_HIT-1-1-0"),
    SIL_2("BAT_IDLE-2-1-1"),
    SIL_3("BAT_HURT-1-1-1"),
    
    REL_AA12("VILLAGER_YES-1-1-0"),
    REL_DEG("WITHER_SPAWN-1-1-0"),
    REL_SAW("HORSE_BREATHE-1-1-0"),
    REL_SMALLMAG("WITHER_IDLE-1-1-2"),
    REL_MAGNUM("AMBIENCE_RAIN-1-1-0"),
    REL_SNIPER("WITHER_SHOOT-1-1-0"),
    REL_ASSAULT("SHEEP_IDLE-1-1-0"),
    REL_HUNTING("SHEEP_SHEAR-1-1-0"),
    REL_SHOTGUN("VILLAGER_YES-1-1-0"),
    
    BOLT_OPEN("VILLAGER_DEATH-1-1-0"),
    BOLT_CLOSE("VILLAGER_HAGGLE-1-1-0"),
    
    REL_BREAK("AMBIENCE_THUNDER-1-1-0"),
    REL_PUMP("HORSE_ANGRY-1-1-0");
    
    
    private final String crackshotSound;
    
    private Sounds(final String crackshotSound)
    {
        this.crackshotSound = crackshotSound;
    }
    
    @Override
    public String toString()
    {
        return crackshotSound;
    }
}
