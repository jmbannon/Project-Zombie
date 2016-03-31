/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.weps;

import java.util.ArrayList;
import java.util.Random;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Modifier;

/**
 *
 * @author jb
 * @param <T>
 */
public abstract class Chance<T extends Modifier> extends GunModifierSet
{
    private final double chance;
    
    public Chance(final ArrayList<T> mods,
                  final double chance)
    {
        super(mods);
        this.chance = Math.max(0, chance);
    }
    
    public double getChance() { return chance; }
    
    public boolean rollDice()
    {
        return new Random().nextDouble() <= chance;
    }
    
}
