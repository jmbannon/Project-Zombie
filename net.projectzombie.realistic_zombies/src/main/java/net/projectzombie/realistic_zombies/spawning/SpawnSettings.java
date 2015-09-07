/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.realistic_zombies.spawning;

import java.util.Random;
import org.bukkit.Location;

/**
 *
 * @author jesse
 */
public abstract class SpawnSettings
{   
    private final String[] mobNames;
    private final int frequency;
    protected Random RAND = new Random();
    
    public SpawnSettings(final String[] mobNames,
                         final int frequency)
    {
        this.mobNames  = mobNames;
        this.frequency = frequency;
    }
    
    public String getRandomMob()
    {
        return mobNames[0];
    }
    
    public abstract SpawnOffset[] calculateOffsets(final Location playerLocation);
}
