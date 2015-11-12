/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.utilities;

/**
 *
 * @author jesse
 */
public enum Particles
{
    FLAM("FLAMES-1");
    
    private final String crackshotParticle;
    private Particles(final String crackshotParticle)
    {
        this.crackshotParticle = crackshotParticle;
    }
    
    @Override
    public String toString()
    {
        return crackshotParticle;
    }
}
