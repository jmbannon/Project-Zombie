/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.types;

import net.projectzombie.crackshot_enhanced.custom_weapons.types.WeaponType;
import static net.projectzombie.crackshot_enhanced.custom_weapons.types.WeaponType.STAT_COLOR;
import static net.projectzombie.crackshot_enhanced.custom_weapons.types.WeaponType.VALUE_COLOR;

/**
 *
 * @author jbannon
 */
public class FireModeTypes
{
    private FireModeTypes() { /* Do nothing */ }
    
    public static String getFireMode(final WeaponType weaponType,
                                     final int fireType)
    {
        switch(weaponType)
        {
        case HUNTING_RIFLE:
        case SNIPER:
            return getSniperFireMode(fireType);
        case SHOTGUN:
            return getShotgunFireMode(fireType);
        default:
            return getGenericFireMode(fireType);
        }
    }
    
    private static String getGenericFireMode(final int fireType)
    {
        switch(fireType)
        {
        case 0: return "Semi-Auto";
        case 1: return "Burst";
        case 2: return "Automatic";
        default: return "Single ShotXXX";
        }
    }
    
    private static String getSniperFireMode(final int fireType)
    {
        switch(fireType)
        {
        case 0: return "Bolt-Action";
        case 1: return "Semi-Auto";
        default: return "Bolt-ActionXXX";
        }
    }
    
    private static String getShotgunFireMode(final int fireType)
    {
        switch(fireType)
        {
        case 0: return "Pump-Action";
        case 1: return "Semi-Auto";
        case 2: return "Over-Under";
        default: return "Pump-ActionXXX";
        }
    }
}
