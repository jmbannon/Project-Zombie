/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.yaml_generator;

import static net.projectzombie.crackshot_enhanced.custom_weapons.types.Attatchment.EXT;
import static net.projectzombie.crackshot_enhanced.custom_weapons.types.Attatchment.REL;
import net.projectzombie.crackshot_enhanced.custom_weapons.types.CrackshotGun;
import net.projectzombie.crackshot_enhanced.custom_weapons.types.FirearmAction;

/**
 *
 * @author jbannon
 */
public class ReloadGen
{
    private ReloadGen() { /* Do nothing */ }
    
    static
    protected int getReloadAmount(final CrackshotGun gun)
    {
        final int reloadAmount = gun.getBase().getReloadAmount();
        return (gun.getAttatchment().equals(EXT)) ? (reloadAmount*5)/3 : reloadAmount;
    }
    
    static
    protected int getReloadDuration(final CrackshotGun gun)
    {
        final int reloadDuration = gun.getBase().getReloadDuration();
        return (gun.getAttatchment().equals(REL)) ? reloadDuration/2 : reloadDuration;
    }
    
    static
    protected Boolean reloadBulletsIndividually(final CrackshotGun gun)
    {
        final FirearmAction action = gun.getBase().getWeaponType().getAction();
        return action != null &&
                (  action.equals(FirearmAction.HUNTING_BOLT)
                //|| action.equals(FirearmAction.SNIPER_BOLT)
                || action.equals(FirearmAction.PUMP)
                || action.equals(FirearmAction.BREAK));
    }
}