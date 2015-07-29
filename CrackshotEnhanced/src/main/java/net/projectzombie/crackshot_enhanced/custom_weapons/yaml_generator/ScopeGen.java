/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.yaml_generator;

import net.projectzombie.crackshot_enhanced.custom_weapons.types.CrackshotGun;

/**
 *
 * @author jbannon
 */
public class ScopeGen
{
    private ScopeGen() { /* Do nothing */ }
    
    static
    protected int getZoomAmount(final CrackshotGun gun)
    {
        switch(gun.getScopeType())
        {
        case ACOG: return 4;
        case TACT: return 5;
        case LONG: return 10;
        default:   return 0;
        }
    }
}
