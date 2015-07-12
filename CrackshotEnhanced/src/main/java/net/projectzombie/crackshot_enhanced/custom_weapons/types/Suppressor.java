/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.types;

/**
 *
 * @author jbannon
 */
public enum Suppressor implements Type
{
    NA (0, "Not Available"),
    ON (1, "On"),
    OFF(2, "Off");
    
    private final int enumValue;
    private final String value;
    private static final String title = "Suppressor: ";
    
    Suppressor(final int enumValue,
               final String value)
    {
        this.enumValue = enumValue;
        this.value = value;
    }
    
    static public String getTitle()     { return title;     }
    @Override public int getEnumValue() { return enumValue; }
    @Override public String toString()  { return value;     }
}
