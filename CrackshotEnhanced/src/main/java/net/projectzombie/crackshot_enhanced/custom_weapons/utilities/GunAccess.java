/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.utilities;

import net.projectzombie.crackshot_enhanced.custom_weapons.types.Gun;

/**
 *
 * @author jbannon
 */
public class GunAccess
{
    static private Gun guns[];
    
    static
    public void initilize()
    {
        guns = new Gun[Gun.values().length];
        for (Gun gun : Gun.values())
            guns[gun.getEnumValue()] = gun;
    }
    
    static
    public Gun get(final int gunID)
    {
        return (gunID >= 0 && gunID < guns.length) ? guns[gunID] : null;
    }
}
