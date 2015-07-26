/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.utilities;

import net.projectzombie.crackshot_enhanced.custom_weapons.types.CrackshotGun;
import org.bukkit.Bukkit;

/**
 *
 * @author jbannon
 */
public class GunAccess
{
    static private CrackshotGun guns[] = null;
    
    static
    public void initilize()
    {
        if (guns != null) return;

        final CrackshotGun CrackshotGuns[] = CrackshotGun.values();
        Bukkit.broadcastMessage("" + CrackshotGuns.length);
        guns = new CrackshotGun[CrackshotGuns.length];
        
        for (CrackshotGun gun : CrackshotGuns)
            guns[gun.getEnumValue()] = gun;
    }
    
    static
    public CrackshotGun get(final int gunID)
    {
        initilize();
        return (gunID >= 0 && gunID < guns.length) ? guns[gunID] : null;
    }
}
