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
    SEMI_AUTO    (0, "Semi-Auto"),
    BURST        (1, "Burst"),
    AUTOMATIC    (2, "Automatic"),
    SINGLE_SHOT  (3, "Single Shot"),
    BOLT_ACTION  (4, "Bolt-Action"),
    PUMP_ACTION  (5, "Pump-Action"),
    OVER_UNDER   (6, "Over/Under");
    
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
        
        return SEMI_AUTO.value;
    }
}
