package net.projectzombie.dynamic_regions.utilities;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class WorldGuardPlugin
{
    
    public static WorldGuardPlugin getWorldGuard()
    {
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");

        if (plugin == null || !(plugin instanceof WorldGuardPlugin))
        {
            return null;
        }

        return (WorldGuardPlugin) plugin;
    }
    
}
