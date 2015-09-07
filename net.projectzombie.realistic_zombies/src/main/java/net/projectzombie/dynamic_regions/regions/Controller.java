/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.dynamic_regions.regions;

import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.managers.RegionManager;
import java.util.ArrayList;
import net.projectzombie.dynamic_regions.utilities.Plugins;
import org.bukkit.Bukkit;

/**
 *
 * @author jesse
 */
public class Controller
{
    static protected final WorldGuardPlugin        WorldGuard      = Plugins.getWorldGuard();
    static protected final RegionManager           WGRegionManager = WGBukkit.getRegionManager(Bukkit.getWorld("PZRebornNew"));
    static private   final ArrayList<RegionModule> DynamicRegions  = new ArrayList<>();
    
    static private final int TICKS_PER_SECOND = 20;
    
    static public void initiate()
    {
        setup();
        for (RegionModule region : DynamicRegions)
            scheduleExecution(region);
    }
    
    static private void setup()
    {
        DynamicRegions.add(new FlatSpawns      ("city_base", 60));
        DynamicRegions.add(new SkyscraperSpawns("city_skyscrapers", 20));
    }
    
    static private void scheduleExecution(final RegionModule module)
    {
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Plugins.getPlugin(), new Runnable()
        {
            @Override
            public void run()
            {
                module.executeForAllPlayers();
                scheduleExecution(module);
            }
        }, getTickAmount(module.getFrequency()));
    }
    
    static private int getTickAmount(final int seconds)
    {
        return seconds * TICKS_PER_SECOND;
    }
    
}
