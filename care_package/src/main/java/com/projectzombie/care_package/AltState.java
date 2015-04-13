/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectzombie.care_package;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.util.Vector;

/**
 *
 * @author jbannon
 */
public class AltState {

    private class BlockInfo {
	private final Material material;
	private final Byte data;
	
	private BlockInfo(final Material material, 
		          final Byte data) 
	{
	    this.material = material;
	    this.data = data;
	}
    }
    
    private Location altStateLoc;
    private Location baseStateLoc;
    
    private final String altStateName;
    private final String baseStateName;
    
    private String altStateDesc;
    private String baseStateDesc;
    
    private BlockInfo[][][] blockBuffer = new BlockInfo[30][30][8];
    
    private boolean success;
    
    public AltState(final String altStateName,
                    final String baseStateName,
	            final FileConfiguration file) 
    {
        /* Initialize file paths */
        this.altStateName = altStateName;
	final String altPath = "alt_states." + this.altStateName;
	final String altWorldPath = altPath + ".world";
	final String altCoordPath = altPath + ".coords";
        final String altDescPath = altPath + ".desc";
        
        this.baseStateName = baseStateName;
	final String basePath = "base_states." + this.baseStateName;
	final String baseWorldPath = basePath + ".world";
	final String baseCoordPath = basePath + ".coords";
        final String baseDescPath = basePath + ".desc";
        
	if (!file.contains(altPath) 
		|| !file.contains(altWorldPath) 
		|| !file.contains(altCoordPath)
                || !file.contains(altDescPath)
                || !file.contains(basePath) 
		|| !file.contains(baseWorldPath) 
		|| !file.contains(baseCoordPath)
                || !file.contains(baseDescPath))
	{
            return;
        } 
        
        altStateDesc = file.getString(altDescPath);
        baseStateDesc = file.getString(baseDescPath);
        
        final Vector altVector = file.getVector(altCoordPath);
        final Vector baseVector = file.getVector(baseCoordPath);
        
        altStateLoc = new Location(
                Bukkit.getWorld(file.getString(altWorldPath)),
                altVector.getX(), 
                altVector.getY(), 
                altVector.getZ());
        
        baseStateLoc = new Location(
                Bukkit.getWorld(file.getString(baseWorldPath)),
                baseVector.getX(), 
                baseVector.getY(), 
                baseVector.getZ());
	    
        final Block altInitBlock = altStateLoc.getBlock();
        final Block baseInitBlock = baseStateLoc.getBlock(); 
            
        for (int i = 0; i < 30; i++) 
        {
            for (int j = 0; j < 30; j++) 
            {
                for (int k = 0; k < 8; k++) 
                {
                    blockBuffer[i][j][k] = new BlockInfo(
                        baseInitBlock.getRelative(i, k, j).getType(),
                        baseInitBlock.getRelative(i, k, j).getData());
                    
                    baseInitBlock.getRelative(i, k, j).setType(
                        altInitBlock.getRelative(i, k, j).getType());
                    
                    baseInitBlock.getRelative(i, k, j).setData(
                        altInitBlock.getRelative(i, k, j).getData());
                }
            }
        }	
    }
    
    public void restoreState() 
    {
        if (blockBuffer == null)
            return;
        
        final Block baseInitBlock = baseStateLoc.getBlock(); 
        
        for (int i = 0; i < 30; i++)
        {
            for (int j = 0; j < 30; j++) 
            {
                for (int k = 0; k < 8; k++) 
                {
                    baseInitBlock.getRelative(i, j, k).setType(
                        blockBuffer[i][j][k].material);
                    
                    baseInitBlock.getRelative(i, j, k).setData(
                        blockBuffer[i][j][k].data);
                    
                    blockBuffer[i][j][k] = null;
                }
            }
        }
        blockBuffer = null;
    }
}
