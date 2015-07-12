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
public enum FireMode implements Type
{
    SEMI    (0, "Semi-Auto"),
    BURST   (1, "Burst"),
    AUTO    (2, "Automatic"),
    SING    (3, "Single Shot"),
    BOLT    (4, "Bolt-Action"),
    PUMP    (5, "Pump-Action"),
    OVER    (6, "Over/Under");
    
    private static final String TITLE = "Fire Mode: ";
    
    private final int enumValue;
    private final String value;
    
    private FireMode(final int enumValue,
                     final String value) 
    {
        this.enumValue = enumValue;
        this.value = value;
    }
    
    public static String getTitle()     { return TITLE;      }
    
    @Override public String toString()  { return value;      }
    @Override public int getEnumValue() { return enumValue;  }
    
    public static String toString(final int enumValue)
    {
        for (FireMode type : FireMode.values())
            if (type.enumValue == enumValue)
                return type.value;
        
        return SEMI.value;
    }
}
