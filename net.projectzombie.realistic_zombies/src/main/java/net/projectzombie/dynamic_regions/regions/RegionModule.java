/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.dynamic_regions.regions;

import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import java.util.ArrayList;
import static net.projectzombie.dynamic_regions.regions.Controller.WGRegionManager;
import net.projectzombie.dynamic_regions.utilities.PlayerMethods;
import org.bukkit.entity.Player;

/**
 *
 * @author jbannon
 */
public abstract class RegionModule
{
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
    
    public void executeForAllPlayers()
    {
        final ArrayList<Player> onlinePlayers = PlayerMethods.getOnlinePlayers();
        for (Player player : onlinePlayers)
            if (inRegion(player))
                executeModule(player);
    }
    
    private boolean inRegion(final Player player)
    { 
        ApplicableRegionSet set = WGRegionManager.getApplicableRegions(player.getLocation());
        for (ProtectedRegion region : set)
            if (region.getId().equals(this.regionName))
                return true;
        
        return false;
    }
}
