/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.yaml_generator;

import net.projectzombie.crackshot_enhanced.custom_weapons.types.Attatchment;
import net.projectzombie.crackshot_enhanced.custom_weapons.types.CrackshotBase;
import net.projectzombie.crackshot_enhanced.custom_weapons.types.CrackshotGun;
import net.projectzombie.crackshot_enhanced.custom_weapons.types.FireMode;
import net.projectzombie.crackshot_enhanced.custom_weapons.types.Weapon;
import net.projectzombie.crackshot_enhanced.custom_weapons.utilities.GunUtils;

/**
 *
 * @author jbannon
 */
public class Shooting
{
    static
    protected String getSoundsShoot(final CrackshotGun gun)
    {
        final CrackshotBase base = gun.getBase();
        return (gun.getAttatchment().equals(Attatchment.SUP)) ? 
                base.getSilencedSound() : base.getShootSound();
    }
    static
    protected int getDelayBetweenShots(final CrackshotGun gun)
    {
        final int shootDelay = gun.getBase().getShootDelay();
        final FireMode fireMode = gun.getFireMode();
        final Weapon weaponType = gun.getBase().getWeaponType();
        
        if (shootDelay == 0 || fireMode.equals(FireMode.AUTO)
                || weaponType.equals(Weapon.SNIPER)
                || weaponType.equals(Weapon.HUNTING))
            return 0;
        
        else if (fireMode.equals(FireMode.BURST))
            return 9 + shootDelay;
        else
            return shootDelay;
    }
    
    static
    protected int getProjectileSpeed(final CrackshotGun gun)
    {
        return (GunUtils.isShotgun(gun)) ? 35 : 1000;
    }
    
    static
    protected String getRemovalOrDragDelay(final CrackshotGun gun)
    {
        return (GunUtils.isShotgun(gun)) ? "13-true" : null;
    }
}
