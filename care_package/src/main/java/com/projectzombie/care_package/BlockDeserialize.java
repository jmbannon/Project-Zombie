/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectzombie.care_package;

import org.bukkit.Bukkit;
import org.bukkit.Server;

/**
 *
 * @author jbannon
 */
public class BlockDeserialize 
{  
    private static final Server server = Bukkit.getServer();
    
	public BlockDeserialize() { /* Do nothing */ }
	
	/**
	 * 
	 * @param serializedString 
	 */
    public void deserialize(final String serializedString)
    {
		String[] parts = serializedString.split(",");
		final String worldName = parts[0];
		final Integer x = Integer.valueOf(parts[1]);
		final Integer y = Integer.valueOf(parts[2]);
		final Integer z = Integer.valueOf(parts[3]);
		final Integer id = Integer.valueOf(parts[4]);
		final Byte data = Byte.valueOf(parts[5]);
	
		server.getWorld(worldName).getBlockAt(x, y, z).setTypeId(id);
		server.getWorld(worldName).getBlockAt(x, y, z).setData(data);
    }
}
