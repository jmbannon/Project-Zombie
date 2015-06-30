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
public enum Scope implements Type
{
    
    na          (0, "n/a"),
    acog        (1, "ACOG"),
    tactical    (2, "Tactical"),
    longrange   (3, "Long-Range");
    
    private static final String title = "Scope: ";
    
    private final int enumValue;
    private final String value;
    
    private Scope(final int enumValue,
                       final String value)
    {
        this.enumValue = enumValue;
        this.value = value;
    }
    
    /**
     * Returns the string to display based on the weapon lore's scope type.
     * 
     * @param enumValue Integer set in Crackshot lore for scope type.
     * @return String to display for fire mode.
     */
    public static String getScopeDisplay(final int enumValue)
    {
        for (Scope type : Scope.values())
            if (type.enumValue == enumValue)
                return type.value;
        
        return na.value;
    }
    
    public static String getTitle()
    {
        return title;
    }

    @Override
    public String getValue() {
        return value;
    }
}
