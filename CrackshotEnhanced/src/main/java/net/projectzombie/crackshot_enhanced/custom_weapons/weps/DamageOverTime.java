/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.weps;

import java.util.ArrayList;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Modifier;
import static net.projectzombie.crackshot_enhanced.custom_weapons.utilities.Constants.TPS;

/**
 *
 * @author jb
 */
public abstract class DamageOverTime<T extends Modifier> extends GunModifierSet<T>
{
    
    final double damagePerSecond;
    final double duration;
    
    public DamageOverTime(ArrayList<T> modifiers,
                          final double damagePerSecond,
                          final double damageDuration)
    {
        super(modifiers);
        this.damagePerSecond = damagePerSecond;
        this.duration = damageDuration;
    }
    
    public double getDamagePerSecond()
    {
        return damagePerSecond;
    }
    
    public double getDamagePerTick()
    {
        return damagePerSecond / TPS;
    }
    
    public double getDurationInSeconds()
    {
        return duration;
    }
    
    public double getDurationInTicks()
    {
        return duration / TPS;
    }
    
}
