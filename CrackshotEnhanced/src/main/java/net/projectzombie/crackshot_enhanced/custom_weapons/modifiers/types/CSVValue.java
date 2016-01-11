/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.types;

/**
 *
 * @author jesse
 */
public abstract class CSVValue {
    
    private final String name;
    
    public CSVValue(final String name)
    {
        this.name = name;
    }
    
    public String getName()
    {
        return name;
    }
}
