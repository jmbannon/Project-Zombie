/*
 * Copyright (C) 2016 jesse
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.weps;

import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Attatchments;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Attatchments.Attatchment;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Barrels;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Bolts;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.FireModes;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.GunModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Magazines;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Sights;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.Stocks;
import net.projectzombie.crackshot_enhanced.custom_weapons.weps.Guns.CrackshotGun;

/**
 *
 * @author jesse
 */
public class GunID
{
    
    
    private static final String GUN_MOD_ID_SEP = "|";
    private static final String CS_ID_SEP = "X";
    
    private final String uniqueID;
    
    public GunID(final GunSkeletons.GunSkeleton skeleton,
                final Attatchments.Attatchment attatchmentOne,
                final Attatchments.Attatchment attatchmentTwo,
                final Attatchments.Attatchment attatchmentThree,
                final Barrels.Barrel barrel,
                final Bolts.Bolt bolt,
                final FireModes.FireMode firemodeType,
                final Magazines.Magazine magazine,
                final Sights.Scope scopeType,
                final Stocks.Stock stock)
    {
        final StringBuilder stb = new StringBuilder();

        stb.append(skeleton.getIndex());                stb.append(GUN_MOD_ID_SEP);
        stb.append(getGunModifierID(barrel));           stb.append(GUN_MOD_ID_SEP);
        stb.append(getGunModifierID(bolt));             stb.append(GUN_MOD_ID_SEP);
        stb.append(getGunModifierID(firemodeType));     stb.append(GUN_MOD_ID_SEP);
        stb.append(getGunModifierID(magazine));         stb.append(GUN_MOD_ID_SEP);
        stb.append(getGunModifierID(scopeType));        stb.append(GUN_MOD_ID_SEP);
        stb.append(getGunModifierID(stock));            

        stb.append(CS_ID_SEP);

        stb.append(getGunModifierID(attatchmentOne));   stb.append(GUN_MOD_ID_SEP);
        stb.append(getGunModifierID(attatchmentTwo));   stb.append(GUN_MOD_ID_SEP);
        stb.append(getGunModifierID(attatchmentThree)); stb.append(GUN_MOD_ID_SEP);

        if (!isValidID(stb.toString()))
            this.uniqueID = stb.toString();
        else
            this.uniqueID = null;
    }
    
    
    
    public GunID(final CrackshotGun gun,
                 final GunModifier modifier)
    {
        
    }
    
    public String getUniqueID()
    {
        return uniqueID;
    }
    
    public String getCSUniqueID()
    {
        if (uniqueID == null)
            return null;
        else
            return uniqueID.split(CS_ID_SEP)[0];
    }
    
    @Override public String toString()
    {
        return this.uniqueID;
    }
    
    /**
     * Checks to see if the ID is valid by checking length and comparing Skeleton
     * and GunModifier values to see if they're in range of their instance arrays.
     * @param ID ID to be checked.
     * @return True if the ID is valid. False otherwise.
     */
    static public boolean isValidID(final String ID)
    {
        final String[] split = ID.split(CS_ID_SEP);
        if (split.length != 2)
            return false;
        
        final String[] csInfo = split[0].split(GUN_MOD_ID_SEP);
        final String[] attInfo = split[1].split(GUN_MOD_ID_SEP);
        
        if (csInfo.length != 7 && attInfo.length != 3)
            return false;
        
        final int[] csIDXs = new int[csInfo.length];
        final int[] attIDXs = new int[attInfo.length];
        
        for (int i = 0; i < csInfo.length; i++)
            csIDXs[i] = Integer.valueOf(csInfo[i]);
        for (int i = 0; i < attInfo.length; i++)
            attIDXs[i] = Integer.valueOf(attInfo[i]);
        
        return csIDXs[0] < GunSkeletons.getInstance().initialize()
                && csIDXs[1] < Barrels.getInstance().initialize()
                && csIDXs[2] < Bolts.getInstance().initialize()
                && csIDXs[3] < FireModes.getInstance().initialize()
                && csIDXs[4] < Magazines.getInstance().initialize()
                && csIDXs[5] < Sights.getInstance().initialize()
                && csIDXs[6] < Stocks.getInstance().initialize()
                && attIDXs[0] < Attatchments.getSlotOneInstance().initialize()
                && attIDXs[1] < Attatchments.getSlotOneInstance().initialize()
                && attIDXs[2] < Attatchments.getSlotOneInstance().initialize();
    }
    
    private static String getGunModifierID(final GunModifier mod)
    {
        if (mod != null)
            return String.valueOf(mod.getIndex());
        else
            return String.valueOf(-1);
    }
}
