/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.yaml_generator;

import static net.projectzombie.crackshot_enhanced.custom_weapons.types.Attatchment.LUB;
import net.projectzombie.crackshot_enhanced.custom_weapons.types.CrackshotGun;
import net.projectzombie.crackshot_enhanced.custom_weapons.types.FirearmAction;
/**
 *
 * @author jbannon
 */
public class FirearmActionGen
{
    private FirearmActionGen() { /* Do nothing. */ }
    
    static
    protected int getOpenDuration(final CrackshotGun gun)
    {
        final FirearmAction action = gun.getWeaponType().getAction();
        if (action != null)
        {
            final int openDuration = action.getOpenDuration();
            return (gun.getAttatchment().equals(LUB)) ? openDuration/2 : openDuration;
        }
        else
            return 0;
    }
    
    static
    protected int getCloseDuration(final CrackshotGun gun)
    {
        final FirearmAction action = gun.getWeaponType().getAction();
        if (action != null)
        {
            final int closeDuration = action.getCloseDuration();
            return (gun.getAttatchment().equals(LUB)) ? closeDuration/2 : closeDuration;
        }
        else
            return 0;
    }
    
    static
    protected int getCloseShootDelay(final CrackshotGun gun)
    {
        final FirearmAction action = gun.getWeaponType().getAction();
        if (action != null)
        {
            final int closeShootDelay = action.getCloseShootDelay();
            return (gun.getAttatchment().equals(LUB)) ? closeShootDelay/2 : closeShootDelay;
        }
        else
            return 0;
    }
}
