/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.utilities;

import net.projectzombie.crackshot_enhanced.custom_weapons.types.CrackshotGun;
import net.projectzombie.crackshot_enhanced.custom_weapons.types.FireMode;
import net.projectzombie.crackshot_enhanced.custom_weapons.types.Scope;
import net.projectzombie.crackshot_enhanced.custom_weapons.types.Weapon;

/**
 *
 * @author jbannon
 */
public class GunUtils
{
    static
    public Boolean isShotgun(final CrackshotGun gun)
    {
        final Weapon weaponType = gun.getBase().getWeaponType();
        return (weaponType.equals(Weapon.SH_BREAK)
                || weaponType.equals(Weapon.SH_PUMP)
                || weaponType.equals(Weapon.SH_SLIDE));
    }
    
    static
    public Boolean isSniper(final CrackshotGun gun)
    {
        return gun.getBase().getWeaponType().equals(Weapon.SNIPER);
    }
    
    static
    public Boolean isHuntingRifle(final CrackshotGun gun)
    {
        return gun.getBase().getWeaponType().equals(Weapon.HUNTING);
    }
    
    static
    public Boolean hasScope(final CrackshotGun gun)
    {
        return !gun.getScopeType().equals(Scope.IRON);
    }
    
    static
    public Boolean isBurstFire(final CrackshotGun gun)
    {
        return gun.getFireMode().equals(FireMode.BURST);
    }
    
    static
    public Boolean isAutomatic(final CrackshotGun gun)
    {
        return gun.getFireMode().equals(FireMode.AUTO);
    }
}
