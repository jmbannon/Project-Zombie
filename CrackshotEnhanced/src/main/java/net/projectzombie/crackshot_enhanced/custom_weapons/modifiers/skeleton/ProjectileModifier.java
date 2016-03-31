/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.skeleton;

/**
 *
 * @author jesse
 */
public interface ProjectileModifier extends SkeletonModifier
{
    public int getProjectileValue();
    public double getProjectileSpeedMultiplier();
    
    public int getProjectileRangeValue();
    public double getProjectileRangeMultiplier();
}
