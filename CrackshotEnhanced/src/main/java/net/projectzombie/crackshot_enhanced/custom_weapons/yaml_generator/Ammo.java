/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.yaml_generator;

import net.projectzombie.crackshot_enhanced.custom_weapons.types.CrackshotGun;
import net.projectzombie.crackshot_enhanced.custom_weapons.types.Weapon;

/**
 *
 * @author jbannon
 */
public class Ammo
{
    private Ammo() { /* Do nothing */ }
    
    static
    protected String getAmmoID(final CrackshotGun gun)
    {
        final Weapon weaponType = gun.getBase().getWeaponType();
        final int ammoData = weaponType.getAmmoData();
        final StringBuilder stb = new StringBuilder();
        
        stb.append(weaponType.getAmmoID());
        if (ammoData != 0)
        {
            stb.append('~');
            stb.append(ammoData);
        }
        return stb.toString();
    }
}
