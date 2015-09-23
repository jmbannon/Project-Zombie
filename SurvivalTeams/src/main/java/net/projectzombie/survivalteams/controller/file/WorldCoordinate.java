/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.projectzombie.survivalteams.controller.file;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;

/**
 *
 * @author jb
 */
public class WorldCoordinate
{
    static public String toWorldCoordinate(final Block location)
    {
        final StringBuilder stb = new StringBuilder();
        stb.append(location.getWorld().getUID().toString());
        stb.append(",");
        stb.append(location.getX());
        stb.append(",");
        stb.append(location.getY());
        stb.append(",");
        stb.append(location.getZ());
        return stb.toString();
    }
    
    static public Location toLocation(final String worldCoordinate)
    {
        final String split[] = worldCoordinate.split(",");
        return split.length == 4 ? new Location(Bukkit.getWorld(split[0]), Double.valueOf(split[1]), Double.valueOf(split[2]),  Double.valueOf(split[3])) : null;
    }
}
