/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.projectile;

/**
 *
 * @author jesse
 */
public interface BleedoutModifier extends ProjectileHitModifier
{
    public double getBleedoutDurationValue();
    public double getBleedoutDurationMultiplier();
    
    public double getBleedoutDamageValuePerSecond();
    public double getBleedoutDamageMultiplerFromShrapnel();
}
