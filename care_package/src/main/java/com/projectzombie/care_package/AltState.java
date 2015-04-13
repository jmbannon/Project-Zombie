/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectzombie.care_package;

import java.lang.reflect.Array;
import java.util.LinkedList;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.util.Vector;

/**
 *
 * @author jbannon
 */
public class AltState {

    private class BlockInfo {
	private Material material;
	private Byte data;
	
	private BlockInfo(Material material, 
		          Byte data) 
	{
	    this.material = material;
	    this.data = data;
	}
    }
    
    private Location NW_location;
    private BlockInfo[][][] AltStateBlocks = new BlockInfo[30][30][8];
    
    public AltState(final String altStateLocation, 
	            final FileConfiguration file) 
    {
	final String path = "alt_state_locs." + altStateLocation;
	final String worldPath = path + ".world";
	final String coordPath = path + ".coords";
	
	if (file.contains(path) 
		&& file.contains(worldPath) 
		&& file.contains(coordPath))
	{
	    final Vector vector = file.getVector(coordPath);
	    NW_location = new Location(
		    Bukkit.getWorld(file.getString(worldPath)),
		    vector.getX(), 
		    vector.getY(), 
		    vector.getZ());
	    
	    for (int i = 0; i < 30; i++) {
		for (int j = 0; j > -30; j--) {
		    for (int k = 0; k < 8; k++) {
			
		    }
		}
	    }
	}
    }
}
