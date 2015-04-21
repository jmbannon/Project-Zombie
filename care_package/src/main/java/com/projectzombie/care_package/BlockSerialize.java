/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectzombie.care_package;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.block.Block;

/**
 *
 * @author jbannon
 */
public class BlockSerialize 
{
    private final String serializedBlock;
    private static final Server server = Bukkit.getServer();
    /**
     * Serializes blocks in the form of
     * "world_name,x,y,z,id,data#"
     * @param block 
     */
    public BlockSerialize(final Block block)
    {
	final StringBuilder temp = new StringBuilder();
	
	temp.append(block.getWorld().getName());
	temp.append(',');
	temp.append(block.getX());
	temp.append(',');
	temp.append(block.getY());
	temp.append(',');
	temp.append(block.getZ());
	temp.append(',');
	temp.append(block.getTypeId());
	temp.append(',');
	temp.append(block.getData());
	temp.append('#');
	
	serializedBlock = temp.toString();
    }
    
    public String getSerialized() 
    {
	return serializedBlock;
    }
    
    public void toBlock(final String serializedString)
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
