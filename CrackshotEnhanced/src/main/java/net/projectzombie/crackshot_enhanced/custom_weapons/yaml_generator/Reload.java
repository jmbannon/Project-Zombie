/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.yaml_generator;

import net.projectzombie.crackshot_enhanced.custom_weapons.types.CrackshotGun;
import net.projectzombie.crackshot_enhanced.custom_weapons.types.FirearmAction;

/**
 *
 * @author jbannon
 */
public class Reload
{
    private Reload() { /* Do nothing */ }
    
    static
    protected Boolean reloadBulletsIndividually(final CrackshotGun gun)
    {
        final FirearmAction action = gun.getBase().getWeaponType().getAction();
        return action != null &&
                (  action.equals(FirearmAction.HUNTING_BOLT)
                || action.equals(FirearmAction.SNIPER_BOLT)
                || action.equals(FirearmAction.PUMP)
                || action.equals(FirearmAction.BREAK));
    }
}
