/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.dynamic_regions.regions;

import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import java.util.Collection;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 *
 * @author jbannon
 */
public class DynamicRegion
{
    
    private String regionName;
    
    public DynamicRegion(final String name)
    {
        this.regionName = name;
    }
    
    public boolean inRegion(final Player player)
    {
        RegionManager regionManager = WGBukkit.getRegionManager(player.getWorld());
        ApplicableRegionSet set = regionManager.getApplicableRegions(player.getLocation());
        for (ProtectedRegion region : set)
            if (region.getId().equals(this.regionName))
                return true;
        
        return false;
    }
    
}
