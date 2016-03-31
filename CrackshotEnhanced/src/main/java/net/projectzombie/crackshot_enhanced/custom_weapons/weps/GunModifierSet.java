/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.weps;

import java.util.ArrayList;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Modifier;

/**
 *
 * @author jb
 * @param <T>
 */
public abstract class GunModifierSet<T extends Modifier>
{
    final ArrayList<T> modifierSet;
    
    GunModifierSet(final ArrayList<T> mods)
    {
        this.modifierSet = mods;
    }
    
    public ArrayList<T> getModifiers()
    {
        return modifierSet;
    }
    
}
