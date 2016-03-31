/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.crackshot_enhanced.custom_weapons.weps;

import java.util.ArrayList;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.GunModifier;
import net.projectzombie.crackshot_enhanced.custom_weapons.modifiers.projectile.StunModifier;
import static net.projectzombie.crackshot_enhanced.custom_weapons.utilities.Constants.TPS;

/**
 *
 * @author jb
 */
public class GunStun extends Chance<StunModifier>
{
    private final double durationInSeconds;
    private final double durationInTicks;
    
    public GunStun(final GunModifier[] mods)
    {
        super(getStunModifiers(mods),
              getStunChance(mods));
        
        this.durationInSeconds = getStunDurationInSeconds(mods);
        this.durationInTicks = getStunDurationInTicks(mods);
    }
    
    static
    public double getStunChance(final GunModifier[] mods)
    {
        double stunChance = 0;
        for (StunModifier mod : getStunModifiers(mods))
        {
            stunChance += mod.getStunChance();
        }
        return Math.max(0, stunChance);
    }
    
    
    static
    public double getStunDurationInSeconds(final GunModifier[] mods)
    {
        double stunDuration = 0;
        for (StunModifier mod : getStunModifiers(mods))
        {
            stunDuration += mod.getStunDuration();
        }
        return stunDuration;
    }
    
    static
    public double getStunDurationInTicks(final GunModifier[] mods)
    {
        return getStunDurationInSeconds(mods) / TPS;
    }
        
    /**
     * @param gunMods
     * @return Returns all StunModifiers on the gun.
     */
    static 
    public ArrayList<StunModifier> getStunModifiers(final GunModifier[] gunMods)
    {
        final ArrayList<StunModifier> mods = new ArrayList<>();
        for (GunModifier mod : gunMods)
        {
            if (mod instanceof StunModifier)
                mods.add((StunModifier)mod);
        }
        return mods;
    }
    
}
