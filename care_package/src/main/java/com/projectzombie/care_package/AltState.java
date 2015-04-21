/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectzombie.care_package;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
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

    
    private Location altStateLoc;
    private Location baseStateLoc;
    
    private final String altStateName;
    private final String baseStateName;
    
    private String altStateDesc;
    private String baseStateDesc;
    
    private final File stateFile;
    
    public AltState(final String altStateName,
                    final String baseStateName,
		    final File file,
	            final FileConfiguration stateConfig) throws IOException
    {
	stateFile = new File(file.getParentFile().getAbsolutePath(), "buffer.txt");	
	final FileWriter stateWriter = new FileWriter(stateFile);
	
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
        
	if (!stateConfig.contains(altPath) 
		|| !stateConfig.contains(altWorldPath) 
		|| !stateConfig.contains(altCoordPath)
                || !stateConfig.contains(altDescPath)
                || !stateConfig.contains(basePath) 
		|| !stateConfig.contains(baseWorldPath) 
		|| !stateConfig.contains(baseCoordPath)
                || !stateConfig.contains(baseDescPath))
	{
            return;
        } 
        
        altStateDesc = stateConfig.getString(altDescPath);
        baseStateDesc = stateConfig.getString(baseDescPath);
        
        final Vector altVector = stateConfig.getVector(altCoordPath);
        final Vector baseVector = stateConfig.getVector(baseCoordPath);
        
        altStateLoc = new Location(
                Bukkit.getWorld(stateConfig.getString(altWorldPath)),
                altVector.getX(), 
                altVector.getY(), 
                altVector.getZ());
        
        baseStateLoc = new Location(
                Bukkit.getWorld(stateConfig.getString(baseWorldPath)),
                baseVector.getX(), 
                baseVector.getY(), 
                baseVector.getZ());
	    
        final Block altInitBlock = altStateLoc.getBlock();
        final Block baseInitBlock = baseStateLoc.getBlock(); 
	Block temp;
            
        for (int i = 0; i < 30; i++) 
        {
            for (int j = 0; j < 30; j++) 
            {
                for (int k = 0; k < 8; k++) 
                {
		    temp = baseInitBlock.getRelative(i, k, j);
                    if (temp.getType() == Material.AIR)
		    {
			stateWriter.write(new BlockSerialize(temp).getSerialized());
			temp.setType(altInitBlock.getRelative(i, k, j).getType());
			temp.setData(altInitBlock.getRelative(i, k, j).getData());
		    }
                }
            }
        }
	stateWriter.flush();
	stateWriter.close();
    }
    
    public void restoreState() throws FileNotFoundException, IOException 
    {
	BufferedReader reader = new BufferedReader(new FileReader(stateFile));
	BlockDeserialize deserialize;
	String[] blocks = reader.readLine().split("#");
	
	for (String block : blocks)
	     deserialize = new BlockDeserialize(block);
	
	reader.close();
	stateFile.delete();
    }
}
