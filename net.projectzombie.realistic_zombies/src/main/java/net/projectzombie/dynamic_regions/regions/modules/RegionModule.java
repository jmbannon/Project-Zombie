/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.dynamic_regions.regions.modules;

import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import java.util.Collection;
import net.projectzombie.dynamic_regions.utilities.WorldGuardPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 *
 * @author jbannon
 */
public abstract class RegionModule
{
    
    protected static final WorldGuardPlugin WorldGuard = WorldGuardPlugin.getWorldGuard();
    protected final String regionName;
    protected final int frequency;
    
    
    
    public RegionModule(final String regionName,
                        final int frequency)
    {
        this.regionName = regionName;
        this.frequency = frequency;
    }
    
    public String getRegionName() { return this.regionName; }
    public int    getFrequency()  { return this.frequency;  }
    
    public abstract boolean executeModule(final Player player);
    
    
    
}
