package net.projectzombie.dynamic_regions.utilities;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class Plugins
{
    private static Plugin plugin;
    
    public static void initialize(Plugin plugin) { Plugins.plugin = plugin; }
    
    public static WorldGuardPlugin getWorldGuard()
    {
        Plugin WGPlugin = Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
        if (WGPlugin == null || !(WGPlugin instanceof WorldGuardPlugin))
            return null;
        
        return (WorldGuardPlugin) WGPlugin;
    }
    
    public static Plugin getPlugin() { return plugin; }
    
}
