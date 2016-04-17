/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package custom_weapons.modifiers.projectile;

import custom_weapons.modifiers.GunModifierSet;
import java.util.ArrayList;
import java.util.Random;
import custom_weapons.modifiers.Modifier;

/**
 *
 * @author jb
 * @param <T>
 */
public abstract class Chance<T extends Modifier> extends GunModifierSet
{
    private final double chance;
    
    public Chance(final String name,
                  final double chance)
    {
        super(name);
        this.chance = Math.max(0, chance);
    }
    
    public double getChance() { return chance; }
    
    @Override
    public boolean hasStats()
    {
        return chance > 0;
    }
    
    public boolean rollDice()
    {
        return new Random().nextDouble() <= chance;
    }
    
}
