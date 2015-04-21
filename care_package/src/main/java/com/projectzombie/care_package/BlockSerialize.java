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
}
