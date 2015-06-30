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
    SemiAuto    (0, "Semi-Auto"),
    Burst       (1, "Burst"),
    Automatic   (2, "Automatic"),
    SingleShot  (3, "Single Shot"),
    BoltAction  (4, "Bolt-Action"),
    PumpAction  (5, "Pump-Action"),
    OverUnder   (6, "Over/Under");
    
    private static final String title = "Fire Mode: ";
    
    private final int enumValue;
    private final String value;
    
    private FireMode(final int enumValue,
                          final String value) 
    {
        this.enumValue = enumValue;
        this.value = value;
    }
    
    public static String getValue(final int enumValue)
    {
        for (FireMode type : FireMode.values())
            if (type.enumValue == enumValue)
                return type.value;
        
        return SemiAuto.value;
    }

    public static String getTitle()
    {
        return title;
    }

    @Override
    public String getValue()
    {
        return this.value;
    }
}
