/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.dynamic_regions.modules;

import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import java.util.ArrayList;
import net.projectzombie.dynamic_regions.utilities.PlayerMethods;
import net.projectzombie.dynamic_regions.world.DRWorld;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

/**
 *
 * @author jbannon
 */
public abstract class RegionModule
{
    private final String regionName;
    private final int frequencyTicks;
    
    public RegionModule(final String regionName,
                        final int frequencySeconds)
    {
        this.regionName = regionName;
        this.frequencyTicks = DRWorld.getTickAmount(frequencySeconds);
    }
    
    public String getRegionName()      { return this.regionName; }
    public int    getFrequencyTicks()  { return this.frequencyTicks;  }
    
    public abstract boolean executeModule(final World world,
                                          final Player player);
    
    public void executeForAllPlayers(final World world,
                                     final RegionManager WGRegionManager)
    {
        final ArrayList<Player> onlinePlayers = PlayerMethods.getOnlinePlayers();
        for (Player player : onlinePlayers)
            if (player.getWorld().equals(world) && inRegion(WGRegionManager, player))
                executeModule(world, player);
    }
    
    private boolean inRegion(final RegionManager WGRegionManager,
                             final Player player)
    { 
        final ApplicableRegionSet set = WGRegionManager.getApplicableRegions(player.getLocation());
        for (ProtectedRegion region : set)
            if (region.getId().equals(this.regionName))
                return true;
            else
                player.sendMessage("Nope, " + region.getId() + " != " + this.regionName);
        
        return false;
    }
}
