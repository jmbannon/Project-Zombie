/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.projectzombie.care_package.serialize;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

/**
 *
 * @author jbannon
 */
public class BlockSerialize implements Listener {

    private static final Server server = Bukkit.getServer();

    public BlockSerialize() { /* Do nothing */ }
    
    /**
     * Serializes blocks in the form of "world_name,x,y,z,type,data,\n"
     *
     * @param block
     * @return 
     */
    public static String serialize(final Block block) {
        final StringBuilder temp = new StringBuilder();
        
        temp.append(block.getWorld().getName());
        temp.append(',');
        temp.append(block.getX());
        temp.append(',');
        temp.append(block.getY());
        temp.append(',');
        temp.append(block.getZ());
        temp.append(',');
        temp.append(block.getType().toString());
        temp.append(',');
        temp.append(block.getData());
        temp.append('#');

        return temp.toString();
    }
    
    /**
     * Deserializes the string and sets the block in the specified world.
     * @param serializedString
     */
    public static void deserializeAndSet(final String serializedString) {
        String[] parts = serializedString.split(",");
        
        final Block theBlock = server.getWorld(parts[0]).getBlockAt(
                Integer.valueOf(parts[1]), 
                Integer.valueOf(parts[2]), 
                Integer.valueOf(parts[3]));
        
        BlockBreakEvent event = new BlockBreakEvent(theBlock, null);
        theBlock.setType(Material.valueOf(parts[4]));
        theBlock.setData(Byte.valueOf(parts[5]));
    }
    
    @EventHandler (priority = EventPriority.NORMAL)
    public static void onBreak(BlockBreakEvent evt) {
        evt.setCancelled(true);
        evt.getBlock().getDrops().clear();
        evt.getBlock().getLocation();
        evt.getBlock().setType(Material.AIR);
    }
}
